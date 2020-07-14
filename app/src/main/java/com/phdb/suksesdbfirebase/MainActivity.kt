package com.phdb.suksesdbfirebase

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var ratingBar: RatingBar
    lateinit var buttonSave: Button
    lateinit var listView: ListView

    lateinit var heroList: MutableList<Hero>
    lateinit var ref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heroList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("heroes")

        editTextName = findViewById(R.id.etName)
        ratingBar = findViewById(R.id.rateBar)
        buttonSave = findViewById(R.id.btnSave)
        listView = findViewById(R.id.lvHero)

        buttonSave.setOnClickListener {
            saveHero()
        }

        ref.addValueEventListener(object:ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) { //Snapshot ini menyimpan semua data hero yang sama pada firebase

                if (p0.exists()) {

                    for(h in p0.children){
                        val hero = h.getValue(Hero::class.java)
                        heroList.add(hero!!)
                    }

                    val adapter = HeroAdapter(this@MainActivity, R.layout.heroes, heroList)
                    listView.adapter = adapter
                }
            }
        })
    }
    private fun saveHero() {
        val name = editTextName.text.toString().trim()

        if(name.isEmpty()){
            editTextName.error = "Tolong isi Nama Hero"
            return
        }

        val heroId = ref.push().key // Untuk push unik key pada firebase
        val hero = Hero(heroId.toString(), name, ratingBar.rating.toInt())

        ref.child(heroId.toString()).setValue(hero).addOnCompleteListener {
            Toast.makeText(this@MainActivity, "Hero berhasil disimpan", Toast.LENGTH_LONG).show()
        }
    }
}