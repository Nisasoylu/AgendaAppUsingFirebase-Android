package com.example.kisileruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,SearchView.OnQueryTextListener {

    private lateinit var kisilerListe:ArrayList<Kisiler>
    private lateinit var adapter:KisilerAdapter

    private lateinit var refKisiler: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMainActivity.setTitle("Kişiler")
        setSupportActionBar(toolbarMainActivity)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        val db = FirebaseDatabase.getInstance()
        refKisiler = db.getReference("kisiler")

        kisilerListe = ArrayList()

        adapter = KisilerAdapter(this,kisilerListe,refKisiler)

        rv.adapter = adapter

        tumKisiler()

        fab.setOnClickListener {
            startActivity(Intent(
                this@MainActivity
                ,KisiKayitActivity::class.java))
        }
    }

    override fun onBackPressed() {
        val yeniIntent = Intent(Intent.ACTION_MAIN)
        yeniIntent.addCategory(Intent.CATEGORY_HOME)
        yeniIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(yeniIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_arama_menu,menu)

        val item = menu.findItem(R.id.action_ara)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.e("Gönderilen Arama Sonucu",query)
        aramaYap(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.e("Harf Girdikçe Sonuc",newText)
        aramaYap(newText)
        return true
    }

    fun tumKisiler(){
        refKisiler.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(d: DataSnapshot) {

                kisilerListe.clear()

                for(c in d.children){
                    val kisi = c.getValue(Kisiler::class.java)

                    if(kisi != null){
                        kisi.kisi_id = c.key
                        kisilerListe.add(kisi)
                    }
                }

                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    fun aramaYap(aramaKelime:String){
        refKisiler.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(d: DataSnapshot) {

                kisilerListe.clear()

                for(c in d.children){
                    val kisi = c.getValue(Kisiler::class.java)

                    if(kisi != null){

                        if(kisi.kisi_ad!!.contains(aramaKelime)){
                            kisi.kisi_id = c.key
                            kisilerListe.add(kisi)
                        }
                    }
                }

                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

