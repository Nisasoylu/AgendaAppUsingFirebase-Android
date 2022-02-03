package com.example.kisileruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_kisi_detay.*
import kotlinx.android.synthetic.main.activity_kisi_detay.editTextKisiAd
import kotlinx.android.synthetic.main.activity_kisi_detay.editTextKisiTel
import kotlinx.android.synthetic.main.activity_kisi_kayit.*
import kotlinx.android.synthetic.main.activity_main.*

class KisiDetayActivity : AppCompatActivity() {

    private lateinit var kisi:Kisiler

    private lateinit var refKisiler: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kisi_detay)

        toolbarKisiDetayActivity.setTitle("Kişi Detay")
        setSupportActionBar(toolbarMainActivity)

        val db = FirebaseDatabase.getInstance()
        refKisiler = db.getReference("kisiler")

        kisi = intent.getSerializableExtra("nesne") as Kisiler

        editTextKisiAd.setText(kisi.kisi_ad)
        editTextKisiTel.setText(kisi.kisi_tel)

        buttonGuncelle.setOnClickListener {
            val kisiAd = editTextKisiAd.text.toString()
            val kisiTel = editTextKisiTel.text.toString()

            guncelle(kisi.kisi_id!!,kisiAd,kisiTel)
        }
    }

    fun guncelle(kisi_id:String,kisi_ad:String,kisi_tel:String){
        Log.e("Kişi güncelle","$kisi_id - $kisi_ad - $kisi_tel")

        val bilgiler = HashMap<String,Any>()
        bilgiler.put("kisi_ad",kisi_ad)
        bilgiler.put("kisi_tel",kisi_tel)

        refKisiler.child(kisi.kisi_id!!).updateChildren(bilgiler)

        startActivity(
            Intent(
                this@KisiDetayActivity
                ,MainActivity::class.java)
        )
    }
}


