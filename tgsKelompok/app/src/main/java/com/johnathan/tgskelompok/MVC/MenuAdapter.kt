package com.johnathan.tgskelompok.MVC

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.johnathan.tgskelompok.Model.MenuModel
import android.content.Intent
import android.R
import android.app.Activity
import android.widget.Button



class MenuAdapter : RecyclerView.Adapter<MenuAdapter.Companion.Holder> {

    lateinit var mactivity : Activity
    lateinit var context: Context
    lateinit var rv:View
    lateinit var rd:View
    lateinit var list: List<MenuModel>

    constructor(list: List<MenuModel>) : super() {
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        var holder : Holder
        context = parent!!.context
        rv = LayoutInflater.from(parent.context).inflate(com.johnathan.tgskelompok.R.layout.lay_item_menu,parent,false)
        holder = Holder(rv)

        return holder



    }


    override fun getItemCount(): Int {

        return list.size

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder!!.tvNama.setText(list.get(position).getName())
        holder!!.tvIsi.setText(list.get(position).getIsi())
        holder!!.tvKategori.setText(list.get(position).getIsii())
        val img_url = "http://172.16.10.246/BERITA/public/storage/" + list.get(position).getFoto()
        Glide.with(context).load(img_url).into(holder.foto)

        holder.tmbl_detail.setOnClickListener {
            val tvNama: String = list.get(position).getName()
            val tvKategori: String = list.get(position).getIsi()
            val tvIsi: String = list.get(position).getIsii()
            val foto: String = img_url


            val intent = Intent(context, Detail::class.java)
            intent.putExtra("Nama", tvNama)
            intent.putExtra("Isi", tvIsi)
            intent.putExtra("Isii", tvKategori)

            intent.putExtra("Foto", foto)
            context.startActivity(intent)
        }
    }

    companion object {

        class Holder : RecyclerView.ViewHolder{
            lateinit var tvNama: TextView
            lateinit var tvIsi: TextView
            lateinit var tvKategori: TextView
            lateinit var foto: ImageView
            lateinit var tmbl_detail:Button


            constructor(itemView: View) : super(itemView) {
                tvNama = itemView!!.findViewById(com.johnathan.tgskelompok.R.id.tvNama)
                tvIsi = itemView!!.findViewById(com.johnathan.tgskelompok.R.id.tvIsi)
                tvKategori = itemView!!.findViewById(com.johnathan.tgskelompok.R.id.tvKategori)
                foto = itemView.findViewById(com.johnathan.tgskelompok.R.id.imgItem)
                tmbl_detail=itemView.findViewById(com.johnathan.tgskelompok.R.id.btn_detail)
            }
        }

    }



}