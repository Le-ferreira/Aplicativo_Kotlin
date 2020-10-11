package com.leonardoferreira.lmsappv2.userInterface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.leonardoferreira.lmsappv2.R
import kotlinx.android.synthetic.main.toolbar.*

class DisciplinaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disciplina)
        val disciplina = intent.getSerializableExtra("disciplina")
        Toast.makeText(this, "$disciplina", Toast.LENGTH_SHORT).show()

        setSupportActionBar(toolbar_view)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        if (id == R.id.action_buscar) {
            Toast.makeText(this, "Botão de buscar", Toast.LENGTH_LONG).show()
        }
        else if (id == R.id.action_atualizar){
            Toast.makeText(this, "Botão atualizar", Toast.LENGTH_LONG).show()
        }
        else if (id == R.id.action_config) {
            Toast.makeText(this, "Botão config", Toast.LENGTH_LONG).show()
        }
        else if (id == R.id.action_exit) {
            finish()
//            Toast.makeText(this, "Botão de sair", Toast.LENGTH_LONG).show()
        }
        else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}