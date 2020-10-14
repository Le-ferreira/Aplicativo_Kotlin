package com.leonardoferreira.lmsappv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.leonardoferreira.lmsappv2.Disciplina
import com.leonardoferreira.lmsappv2.R
import kotlinx.android.synthetic.main.toolbar.*

class DisciplinaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disciplina)
        setSupportActionBar(toolbar_view)
        val disciplina = intent.getSerializableExtra("disciplina") as Disciplina
        supportActionBar?.title = disciplina.nome
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}