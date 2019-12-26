package com.johnathan.tgskelompok.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MenuModel : Serializable {

    @SerializedName("id")
    lateinit var ids: String
    @SerializedName("nama")
    lateinit var name1: String

    @SerializedName("isi")
    lateinit var isi1: String

    @SerializedName("isii")
    lateinit var isii1: String

    @SerializedName("foto")
    lateinit var foto1: String



    fun setId(Ids: String){
        this.ids = ids
    }

    fun getId() : String{
        return ids
    }

    fun setName(name1: String){
        this.name1 = name1
    }

    fun getName() : String {
        return name1
    }

    fun setIsi(isi1: String){
        this.isi1 = isi1
    }

    fun getIsi() : String {
        return isi1
    }
    fun setIsii(isii1: String){
        this.isii1 = isii1
    }

    fun getIsii() : String {
        return isii1
    }

    fun setFoto(foto1: String){
        this.foto1 = foto1
    }

    fun getFoto() : String {
        return foto1
    }





}