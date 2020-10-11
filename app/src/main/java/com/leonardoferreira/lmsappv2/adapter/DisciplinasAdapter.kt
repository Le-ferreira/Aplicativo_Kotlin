package com.leonardoferreira.lmsappv2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DisciplinasAdapter (
    val disciplina: List<Disciplina>,
    val onClick: (Disciplina) -> Unit
): RecyclerView.Adapter<DisciplinasAdapter.DisciplinasViewHolder> () {

    class  DisciplinasViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val cardNome: TextView
        val cardImg: ImageView
        val cardProgress: ProgressBar

        init {
            cardNome = view.findViewById(R.id.cardNome)
            cardImg = view.findViewById(R.id.cardImg)
            cardProgress = view.findViewById(R.id.cardProgress)
        }
    }

    override fun getItemCount() = this.disciplina.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplinasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_disciplina, parent, false)

        val holder = DisciplinasViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: DisciplinasViewHolder, position: Int) {
        val disciplina = this.disciplina[position]

        holder.cardNome.text = disciplina.nome
        holder.cardProgress.visibility = View.VISIBLE

        Picasso.with(holder.itemView.context).load(disciplina.foto).fit().into(holder.cardImg,
        object: com.squareup.picasso.Callback {
            override fun onSuccess() {
                holder.cardProgress.visibility = View.GONE
            }
            override fun onError() {
                holder.cardProgress.visibility = View.GONE
            }
        })

        holder.itemView.setOnClickListener {onClick(disciplina)}
    }

}