package com.leonardoferreira.lmsappv2

import android.app.ProgressDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: DebugActivity  () {

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
        setContentView(R.layout.activity_login)

        campoImagem.setImageResource(R.drawable.ic_login)

        initialise()

    }

    private fun initialise () {
        tvForgotPassword = findViewById<TextView>(R.id.tv_forgot_password)
        etEmail = findViewById<EditText>(R.id.et_email)
        etPassword = findViewById<EditText>(R.id.et_password)
        btnLogin = findViewById<Button>(R.id.btn_login_main)
        btnRegister = findViewById<Button>(R.id.btn_register_main)
        mProgressBar = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!
            .setOnClickListener { startActivity(
                Intent(
                    this@LoginActivity,
                    RecoverPasswordActivity::class.java
                )
            )}

        btnRegister!!
            .setOnClickListener { startActivity(
                (Intent(
                    this@LoginActivity,
                    CreateAccountActivity::class.java
                ))
            )
                btn_register_loading.visibility = View.VISIBLE}

        btnLogin!!.setOnClickListener { loginUser()
            btn_login_loading.visibility = View.VISIBLE}
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ){
            mProgressBar!!.setMessage("Verificando usuario")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuario $email, $password")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this){ task ->

                mProgressBar!!.hide()

                //Autenticando o usuario, atualizando Ui com As informações de activity_login

                if (task.isSuccessful) {
                    Log.d(TAG, "Logado com sucesso")
                    val user = mAuth!!.currentUser
                    updateUI()
                } else {
                    Log.w(TAG, "erro ao logar", task.exception)
                    Toast.makeText(
                        this@LoginActivity, "Autenticação falhou.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, TelaInicialActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}

