package com.leonardoferreira.lmsappv2

import android.content.Context

object DisciplinaService {

    fun getDisciplinas(context: Context): List<Disciplina> {
        val disciplina = mutableListOf<Disciplina>()

        val r1 = Disciplina()
        r1.nome = "Fundamental"
        r1.foto = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Closed_Book_Icon.svg/1200px-Closed_Book_Icon.svg.png"
        disciplina.add(r1)

        val r2 = Disciplina()
        r2.nome = "Front-End"
        r2.foto = "https://icon-library.com/images/frontend-icon/frontend-icon-24.jpg"
        disciplina.add(r2)

        val r3 = Disciplina()
        r3.nome = "Back-End"
        r3.foto = "https://hackernoon.com/hn-images/1*GkzKz-wfxLaShBREklifbg.png"
        disciplina.add(r3)

        val r4 = Disciplina()
        r4.nome = "DevOps"
        r4.foto ="https://www.vhv.rs/dpng/d/215-2152054_transparent-model-icon-png-devops-icon-png-download.png"
        disciplina.add(r4)

        return disciplina
    }
}