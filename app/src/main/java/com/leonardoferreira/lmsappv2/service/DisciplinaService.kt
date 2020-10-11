package com.leonardoferreira.lmsappv2

object DisciplinaService {

    fun getDisciplina() : List<Disciplina> {
        val  disciplina = mutableListOf<Disciplina>()

        for (i in 1..10) {
            val d = Disciplina()
            d.nome  ="Disciplina $i"
            d.ementa = "Ementa $i"
            d.professor = "Professor $i"
            d.foto = "https://www.oficinadanet.com.br/imagens/post/13939/android-10-google.jpg"
            disciplina.add(d)
        }
        return disciplina
    }
}