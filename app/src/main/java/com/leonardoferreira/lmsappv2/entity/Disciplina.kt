package com.leonardoferreira.lmsappv2
import java.io.Serializable

class Disciplina: Serializable {
    var id: Long =0
    var nome = ""
    var foto = ""

    override fun toString(): String {
        return "Disciplina(nome='$nome')"
    }
}