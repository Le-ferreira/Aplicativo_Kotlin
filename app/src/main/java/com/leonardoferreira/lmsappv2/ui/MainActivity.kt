package com.leonardoferreira.lmsappv2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.leonardoferreira.lmsappv2.adapter.DisciplinasAdapter
import com.leonardoferreira.lmsappv2.ui.DisciplinaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : NavigationDrawer() {
    private val TAG = "Dashboard"
    private val context: Context get() = this
    private var disciplinass = listOf<Disciplina>()
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_view)
        setConfigDrawer(drawer_dashboard)

        supportActionBar?.title = getString(R.string.title_menu)
        mAuth = FirebaseAuth.getInstance();

        RecyclerViewDisciplinas?.layoutManager = LinearLayoutManager(this)
        RecyclerViewDisciplinas?.itemAnimator = DefaultItemAnimator()
        RecyclerViewDisciplinas?.setHasFixedSize(true)


    }

    override fun onResume() {
        super.onResume()
        this.getDisciplinas()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        (menu?.findItem(R.id.action_buscar)?.actionView as androidx.appcompat.widget.SearchView?)?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("SEARCH_TOOLBAR", "toolbar text change: $newText")
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(context, "buscando $query", Toast.LENGTH_SHORT).show()
                if(progressBar.visibility == View.VISIBLE) return false
                progressBar.visibility = View.VISIBLE
                Thread(Runnable {
                    Thread.sleep(1000)
                    progressBar.visibility = View.INVISIBLE
                }).start()
                return false
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_atualizar -> this.onLoading()
//            R.id.action_config -> {
//                var intent = Intent(this, SettingsActivity::class.java)
//                startActivity(intent)
//            }
            R.id.action_exit -> logoutApplication()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onLoading() {
        if(progressBar.visibility == View.VISIBLE) return
        progressBar.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(10000)
            progressBar.visibility = View.INVISIBLE
        }).start()
    }

    fun openDisciplinaActivity(disciplina: Disciplina) {
        Toast.makeText(context, "Clicou repositrio ${disciplina.nome}", Toast.LENGTH_SHORT).show()
        var intent = Intent(this, DisciplinaActivity::class.java)
        intent.putExtra("disciplina", disciplina)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_menu -> getDisciplinas()
            R.id.nav_favoritos -> getFavoritos()
            R.id.nav_sair -> logoutApplication()
        }
        drawer_dashboard.closeDrawer(GravityCompat.START)
        return true
    }

    fun getDisciplinas() {
        supportActionBar?.title = getString(R.string.title_menu)
        this.disciplinass = DisciplinaService.getDisciplinas(context)
        RecyclerViewDisciplinas?.adapter = DisciplinasAdapter(disciplinass) {openDisciplinaActivity(it)}
    }

    fun getFavoritos() {
        supportActionBar?.title = "Favoritos"
        RecyclerViewDisciplinas.adapter = null
    }

    fun logoutApplication() {
        FirebaseAuth.getInstance().signOut();
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}

