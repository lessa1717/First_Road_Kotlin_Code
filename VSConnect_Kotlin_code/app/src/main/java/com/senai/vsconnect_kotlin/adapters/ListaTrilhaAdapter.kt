package com.senai.vsconnect_kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.senai.vsconnect_kotlin.R
import com.senai.vsconnect_kotlin.models.Trilha

class ListaTrilhaAdapter(
    private val context: Context,
    private var listaTrilhas: List<Trilha>
) : RecyclerView.Adapter<ListaTrilhaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Essa função é responsável por chamar e atribuir valores para as views do item da RecyclerView
        fun vincularDadosNoItem(trilha: Trilha) {

            val titulo_trilha = itemView.findViewById<TextView>(R.id.tituloTrilha)
            titulo_trilha.text = trilha.titulo_trilha

            val descricao_trilha = itemView.findViewById<TextView>(R.id.link_conteudo)
            descricao_trilha.text = "Ir para o Conteúdo"
        }

    }

    fun atualizarDados(novaListaTrilhas: List<Trilha>) {
        listaTrilhas = novaListaTrilhas
        notifyDataSetChanged()
    }

    // Interface para lidar com cliques nos itens da RecyclerView
    interface OnItemClickListener {
        fun onItemClick(link: String)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaTrilhaAdapter.ViewHolder {
        val inflater = LayoutInflater.from(context);

        val view = inflater.inflate(R.layout.fragment_trilha, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaTrilhaAdapter.ViewHolder, position: Int) {
        val itemTrilha = listaTrilhas[position]


        holder.vincularDadosNoItem(itemTrilha)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(itemTrilha.descricao_trilha)
        }
    }

    override fun getItemCount(): Int {
        return listaTrilhas.size
    }
}