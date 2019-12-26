package com.johnathan.tgskelompok.MVC

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.johnathan.tgskelompok.R
import kotlinx.android.synthetic.main.lay_detail.*


class Detail : AppCompatActivity(){

    lateinit var btn_back: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_detail)

        btn_back = findViewById(R.id.btn_back)

        RxView.clicks(btn_back)
            .subscribe{
                val intentLogout = Intent(this,MenuActivity::class.java)
                startActivity(intentLogout)
//                Toast.makeText(this, "Anda Telah LogOut", Toast.LENGTH_SHORT).show()

            }

        val Nama : String = intent.getStringExtra("Nama")
        val Isi : String = intent.getStringExtra("Isi")
        val Isii : String = intent.getStringExtra("Isii")
        val foto : String = intent.getStringExtra("Foto")

        tvNama.text=Nama
//        tvIsi.text=Isi
        tvKategori.text=Isii


        Glide.with(this).load(foto).into(imgItem)

    }
}