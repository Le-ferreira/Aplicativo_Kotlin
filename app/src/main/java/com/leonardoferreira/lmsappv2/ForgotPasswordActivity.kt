package com.leonardoferreira.lmsappv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG="ForgotPasswordActivity"

    //Elementos da Interface UI
    private var etEmail:EditText? = null
    private var btnSubmit:Button? = null

    //Referencias ao Firebase
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        inicialise()
    }
    private fun inicialise() {
        etEmail = findViewById(R.id.et_email_forgot) as EditText
        btnSubmit = findViewById(R.id.btn_submit) as Button

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener{ sendPasswordEmail() }
    }

    private fun sendPasswordEmail() {
        val email = etEmail?.text.toString()

        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val message = "Email enviado"
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUi()
                    } else {
                        task.exception!!.message?.let { Log.w(TAG, it) }
                        Toast.makeText(this, "Email não cadastraddo", Toast.LENGTH_SHORT).show()
                    }
                }
        } else{
            Toast.makeText(this, "Entre com um email valido ", Toast.LENGTH_SHORT).show()

        }

    }

    private fun updateUi() {
        val intent = Intent (this@ForgotPasswordActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}