package com.leonardoferreira.lmsappv2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {


    // Elementos da interface do Usuario

    private var etNameRegister: EditText? = null
//    private var etlasName: EditText? = null
    private var etEmailRegister: EditText? = null
    private var etPasswordRegister: EditText? = null
    private var btnRegister: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Referencias ao Banco de dados

    private var mdataReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase?=null
    private var mAuth: FirebaseAuth?=null

    private val TAG = "CreateAccontActivity"

    // Variaveis Globais

    private var nameRegister: String? = null
    private var lastName: String? = null
    private var emailRegister: String? = null
    private var passwordRegister: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()
    }
    private fun initialise () {
        etNameRegister = findViewById<EditText>(R.id.et_nameRegister)
        etEmailRegister = findViewById<EditText>(R.id.et_emailRegister)
        etPasswordRegister = findViewById<EditText>(R.id.et_passwordRegister)
        btnRegister = findViewById<Button>(R.id.btn_register)
        mProgressBar = ProgressDialog (this)


        mDatabase = FirebaseDatabase.getInstance()
        mdataReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnRegister!!.setOnClickListener{ CreateAccount() }
    }

    private fun CreateAccount () {

        nameRegister = etNameRegister?.text.toString()
        emailRegister = etEmailRegister?.text.toString()
        passwordRegister = etPasswordRegister?.text.toString()

        if (!TextUtils.isEmpty(nameRegister)
            && !TextUtils.isEmpty(emailRegister)
            && !TextUtils.isEmpty(passwordRegister)) {

            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }

        mProgressBar!!.setMessage("Registrando Usuario...")
        mProgressBar!!.show()

        mAuth!!
            .createUserWithEmailAndPassword(emailRegister!!, passwordRegister!!). addOnCompleteListener(this) { task ->
                mProgressBar!!.hide()

                if (task.isSuccessful){
                    Log.d(TAG, "createUserWithEmail:Sucess")

                    val userId = mAuth!!.currentUser!!.uid


                    //Verificar se o ususario verificou o email
                    verifyEmail ()

                    val currenUserDb = mdataReference!!.child(userId)
                    currenUserDb.child("fistName").setValue(nameRegister)
                    currenUserDb.child("lastName").setValue(lastName)

                    // Atualizar as informações no banco de dados
                    updateUserInfoandUi()

                } else {
                    Log.w(TAG,"createUserWithEmail:Failure", task.exception)
                    Toast.makeText(this@CreateAccountActivity, "Verifique se todas as informações foram preenchidas corretamente", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun updateUserInfoandUi() {
        //Iniciar nova atividade
        val intent = Intent (this@CreateAccountActivity, TelaInicialActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail(){
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification().addOnCompleteListener(this) {
            task ->

            if (task.isSuccessful) {
                Toast.makeText(this@CreateAccountActivity,  "Verification email sent to " + mUser.getEmail(),
                    Toast.LENGTH_SHORT).show()

            } else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@CreateAccountActivity,  "Failed to send email sent to ",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}