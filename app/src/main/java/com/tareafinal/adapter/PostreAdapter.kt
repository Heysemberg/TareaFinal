package com.tareafinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tareafinal.ui.home.HomeFragmentDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tareafinal.databinding.PostreFilaBinding
import com.tareafinal.model.Postre

class PostreAdapter: RecyclerView.Adapter<PostreAdapter.PostreViewHolder>() {

    private var listaPostres = emptyList<Postre>()

    fun setPostres(postres: List<Postre>){
        listaPostres = postres
        notifyDataSetChanged()
    }

    inner class PostreViewHolder(private var itemBinding: PostreFilaBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun dibuja(postre: Postre){
            itemBinding.tvNombre.text = postre.nombre
            itemBinding.tvTipo.text = postre.tipo
            itemBinding.tvPrecio.text = postre.precio

            if(postre.rutaImagen?.isNotEmpty() == true){
                Glide.with(itemBinding.root.context)
                    .load(postre.rutaImagen)
                    .into(itemBinding.imagen)
            }

            itemBinding.vistaFila.setOnClickListener{
                val action = HomeFragmentDirections.actionNavHomeToUpdatePostreFragment(postre)
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostreViewHolder {
        val itemBinding = PostreFilaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostreViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PostreViewHolder, position: Int) {
        val postre = listaPostres [position]
        holder.dibuja(postre)
    }

    override fun getItemCount(): Int {
        return listaPostres.size
    }
}