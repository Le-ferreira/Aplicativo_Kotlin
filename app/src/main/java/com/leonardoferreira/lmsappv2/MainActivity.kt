package com.leonardoferreira.lmsappv2

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login.*

class MainActivity : DebugActivity  () {

    private val  TAG = "MainActivity"

    // Variaveis Globais

    private var email: String? = null
    private var password: String? = null

    // Elemento da interface UI

    private var tvForgotPassword: TextView? = null
    private var etEmail: TextView? = null
    private var etPassword: TextView? = null
    private var btnLogin: TextView? = null
    private var btnRegister: TextView? = null
    private var mProgressBar: ProgressDialog? = null

    // Referencias ao banco de dados

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        campoImagem.setImageResource(R.drawable.ic_login)

        initialise()
    }

    private fun initialise () {
        tvForgotPassword = findViewById<TextView>(R.id.tv_forgot_password)
        etEmail = findViewById<EditText>(R.id.et_email)
        etPassword = findViewById<EditText>(R.id.et_password)
        btnLogin = findViewById<Button>(R.id.btn_login_main)
        btnRegister = findViewById<Button>(R.id.btn_register_main)
        mProgressBar = ProgressDialog (this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!
            .setOnClickListener { startActivity(Intent(this@MainActivity,RecoverPasswordActivity::class.java))}

        btnRegister!!
            .setOnClickListener { startActivity((Intent(this@MainActivity, CreateAccountActivity::class.java)))
                btn_register_loading.visibility = View.VISIBLE}

        btnLogin!!.setOnClickListener { LoginUser()
            btn_login_loading.visibility = View.VISIBLE}
    }

    private fun LoginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ){

            Log.d(TAG, "Login do usuario")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){
                task ->

                mProgressBar!!.hide()

                //Autenticando o usuario, atualizando Ui com As informações de login

                if (task.isSuccessful) {
                    Log.d(TAG, "Logado com sucesso")
                    updateUi()
                } else {
                    Log.d(TAG, "erro ao logar", task.exception)
                    Toast.makeText(this@MainActivity, "Autenticação falhou.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi() {
        val intent = Intent(this@MainActivity, DebugActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
