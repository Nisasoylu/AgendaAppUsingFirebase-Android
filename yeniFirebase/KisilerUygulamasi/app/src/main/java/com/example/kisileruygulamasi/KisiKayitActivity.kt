package com.example.kisileruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_kisi_kayit.*
import kotlinx.android.synthetic.main.activity_main.*

class KisiKayitActivity : AppCompatActivity() {

    private lateinit var refKisiler: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kisi_kayit)

        toolbarKisiKayitActivity.setTitle("Kişi Kayıt")
        setSupportActionBar(toolbarMainActivity)

        val db = FirebaseDatabase.getInstance()
        refKisiler = db.getReference("kisiler")

        buttonKaydet.setOnClickListener {
            val kisiAd = editTextKisiAd.text.toString()
            val kisiTel = editTextKisiTel.text.toString()

            kayit(kisiAd,kisiTel)
        }

    }

    fun kayit(kisi_ad:String,kisi_tel:String){
        Log.e("Kişi kayıt","$kisi_ad - $kisi_tel")

        val kisi = Kisiler("",kisi_ad,kisi_tel)

        refKisiler.push().setValue(kisi)

        startActivity(
            Intent(
            this@KisiKayitActivity
            ,MainActivity::class.java)
        )
    }
}

