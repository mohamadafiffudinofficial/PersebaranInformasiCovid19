package com.example.newapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create.*

class Create : AppCompatActivity(), View.OnClickListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        simpan.setOnClickListener(this)
        lihat_data.setOnClickListener(this)
        //Mendapatkan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
        when(v?.getId()) {
            R.id.simpan -> {
                // Statement program untuk simpan data
                //Mendapatkan UserID dari pengguna yang Terautentikasi
//                val getUserID = auth!!.currentUser!!.uid
                //Mendapatkan Instance dari Database
                val database = FirebaseDatabase.getInstance()
                //Menyimpan Data yang diinputkan User kedalam Variable
                val getJudul: String = judul.getText().toString()
                val getIsi: String = isi.getText().toString()
                // Mendapatkan Referensi dari Database
                val getReference: DatabaseReference
                getReference = database.reference
                // Mengecek apakah ada data yang kosong
                if (TextUtils.isEmpty(getJudul) || TextUtils.isEmpty(getIsi)) {
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(this@Create, "Data cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    /* Jika Tidak, maka data dapat diproses dan meyimpannya pada
                   Database Menyimpan data referensi pada Database berdasarkan User ID
                   dari masing-masing Akun
                    */

                    getReference.child("Admin").child("Info").push()
                        .setValue(data_info(getJudul, getIsi))
                        .addOnCompleteListener(this) { //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                            judul.setText("")
                            isi.setText("")
                            Toast.makeText(this@Create, "Stored Data",
                                Toast.LENGTH_SHORT).show()
                        }
                }
            }
            R.id.lihat_data -> {
                startActivity(Intent(this@Create, MainActivity::class.java))
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //Inflate menu
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

    //Method to Handle Option Item Click

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val selectedItem= item!!.itemId

        when(selectedItem){

            R.id.itemHome->{
                startActivity(Intent(this@Create, MainActivity::class.java))
            }

            R.id.itemData->{
                startActivity(Intent(this@Create, TestApi::class.java))
            }

            R.id.itemInfo->{
                startActivity(Intent(this@Create, Create::class.java))
            }

            R.id.itemAbt->{
                startActivity(Intent(this@Create, About::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }

}

