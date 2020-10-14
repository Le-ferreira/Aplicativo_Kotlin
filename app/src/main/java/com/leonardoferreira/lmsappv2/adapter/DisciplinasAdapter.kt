package com.leonardoferreira.lmsappv2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.leonardoferreira.lmsappv2.Disciplina
import com.leonardoferreira.lmsappv2.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation


class DisciplinasAdapter (
    val disciplina: List<Disciplina>,
    val onClick: (Disciplina) -> Unit
): RecyclerView.Adapter<DisciplinasAdapter.DisciplinasViewHolder> () {

    class DisciplinasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardNome: TextView
        val cardImg: ImageView
        val cardProgress: ProgressBar
        val card: CardView

        init {
            cardNome = view.findViewById(R.id.cardTvNome)
            cardImg = view.findViewById(R.id.cardImg)
            cardProgress = view.findViewById(R.id.cardProgress)
            card = view.findViewById<CardView>(R.id.card_disciplinas)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplinasViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_disciplina, parent, false)

        return DisciplinasViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisciplinasViewHolder, position: Int) {
        val disciplina = this.disciplina[position]

        // atualizar dados de disciplina
        holder.cardNome.text = disciplina.nome
        holder.cardProgress.visibility = View.VISIBLE

        Picasso.with(holder.itemView.context).load(disciplina.foto).fit().into(holder.cardImg,
            object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    holder.cardProgress.visibility = View.GONE
                }

                override fun onError() {
                    holder.cardProgress.visibility = View.GONE
                }
            })

        holder.itemView.setOnClickListener { onClick(disciplina) }
    }

    override fun getItemCount(): Int {
        return this.disciplina.size
    }

}