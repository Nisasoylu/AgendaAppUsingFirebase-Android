package com.example.kisileruygulamasi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference

class KisilerAdapter(private var mContext:Context
    ,private var kisilerListe:ArrayList<Kisiler>
    ,private val refKisiler: DatabaseReference)
    : RecyclerView.Adapter<KisilerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(tasarim:View) : RecyclerView.ViewHolder(tasarim){
        var satir_card:CardView
        var satir_yazi:TextView
        var satir_resim:ImageView

        init {
            satir_card = tasarim.findViewById(R.id.satir_card)
            satir_yazi = tasarim.findViewById(R.id.satir_yazi)
            satir_resim = tasarim.findViewById(R.id.satir_resim)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext)
            .inflate(R.layout.card_tasarim,parent,false)
        return CardTasarimTutucu(tasarim)
    }

    override fun getItemCount(): Int {
        return kisilerListe.size
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val kisi = kisilerListe.get(position)

        holder.satir_yazi.text = "${kisi.kisi_ad} - ${kisi.kisi_tel}"

        holder.satir_resim.setOnClickListener {
            Toast.makeText(mContext,"${kisi.kisi_ad} silindi",Toast.LENGTH_SHORT).show()
            //kisi nesnesinin boş olmayacağından eminim !! koyduk.
            refKisiler.child(kisi.kisi_id!!).removeValue()
        }

        holder.satir_card.setOnClickListener {
            val intent = Intent(mContext,KisiDetayActivity::class.java)
            intent.putExtra("nesne",kisi)
            mContext.startActivity(intent)
        }

    }
}


