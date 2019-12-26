package com.johnathan.tgskelompok

import android.app.Dialog
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.johnathan.tgskelompok.R

class RecyclerViewAdapter (internal var mContext: Context,  internal  var mData: List<User>): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){



    internal var myDialog: Dialog? = null

   init {

        this.mContext = mContext
        this.mData = mData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {

        val v: View
        v = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false)

        return RecyclerViewAdapter.MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {

        holder.tv_name.text = mData[position].name
        holder.tv_phone.text = mData[position].nim

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val item_contact: LinearLayout
        val tv_name: TextView
        val tv_phone: TextView


        init {
            item_contact = itemView.findViewById<View>(R.id.contact_item_id) as LinearLayout
            tv_name = itemView.findViewById<View>(R.id.name_contact) as TextView
            tv_phone = itemView.findViewById<View>(R.id.phone_contact) as TextView


        }
    }

    companion object {
        var urlFoto = "http://172.16.10.246/ch/images/"
    }
}