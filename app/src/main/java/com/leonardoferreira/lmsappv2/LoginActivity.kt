package com.leonardoferreira.lmsappv2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.leonardoferreira.lmsappv2.R.id
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity: DebugActivity  () {

    private val TAG = "MainActivity"
    private var firebaseAuth: FirebaseAuth? = null

    // Variaveis Globais

    private var email: String? = null
    private var password: String? = null

    // Elemento da interface UI

    private var tvForgotPassword: TextView? = null
    private var etEmail: TextView? = null
    private var etPassword: TextView? = null
    private var btnLogin: TextView? = null
    private var btnRegister: TextView? = null
    private var btnLoginGithub: TextView? = null
    private var mProgressBar: ProgressDialog? = null

    // Referencias ao banco de dados

    private var mAuth: FirebaseAuth? = null
    private var provider = OAuthProvider.newBuilder("github.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

//        campoImagem.setImageResource(R.drawable.ic_login)

        initialise()

    }

    private fun initialise() {
        tvForgotPassword = findViewById<TextView>(id.tv_forgot_password)
        etEmail = findViewById<EditText>(id.et_email)
        etPassword = findViewById<EditText>(id.et_password)
        btnLogin = findViewById<Button> (id.btn_login_main)
        btnRegister = findViewById<TextView>(id.tv_register_main)
//        btnLoginGithub = findViewById<Button>(id.iv_login_github)
        mProgressBar = ProgressDialog(this)

        firebaseAuth = FirebaseAuth.getInstance()

        tvForgotPassword!!
            .setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        ForgotPasswordActivity::class.java
                    )
                )
            }

        btnRegister!!
            .setOnClickListener {
                startActivity(
                    (Intent(
                        this@LoginActivity,
                        CreateAccountActivity::class.java
                    ))
                )
                tv_register_main.visibility = View.VISIBLE
            }

//        btnLoginGithub!!.setOnClickListener {
//            authWithGithub()
//        }

        btnLogin!!.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgressBar!!.setMessage("Verificando usuario")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuario $email, $password")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->

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
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Entre com mais detalhes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authWithGithub() {
        btnLoginGithub?.setOnClickListener {
            firebaseAuth!!
                .startActivityForSignInWithProvider(this,  provider.build())
                .addOnSuccessListener(
                    OnSuccessListener {
                        val profile = it?.getAdditionalUserInfo()?.getProfile()
                        val name = profile?.get("name")
                        Log.i(TAG, "Sucesso!, $profile")
                        Toast.makeText(applicationContext, "Bem-vindo $name", Toast.LENGTH_SHORT)
                            .show()
                        updateUI()
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, it.toString())
                    })
        }
    }

    private fun updateUI() {
        val intent = Intent(this, TelaInicialActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}
