package com.leonardoferreira.lmsappv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.login.*

class MainActivity : DebugActivity  () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        campoImagem.setImageResource(R.drawable.icon_login)

        botaoLogin.setOnClickListener {
            val valorUsuario = campoUsuario.text.toString()
            val valorSenha = campoSenha.text.toString()
            //Toast.makeText(this, "Usuario: $valorUsuario; Senha: $valorSenha", Toast.LENGTH_LONG).show()

            var intent = Intent(this, TelaInicialActivity::class.java)
            var params = Bundle()

//            params.putString("nome_usuario",valorUsuario)
//            params.putInt("numero", 10)
//
//            intent.putExtras(params)

            intent.putExtra("nome_usuario", valorUsuario)
            intent.putExtra("numero", 10)

            startActivity(intent)
        }
    }
}