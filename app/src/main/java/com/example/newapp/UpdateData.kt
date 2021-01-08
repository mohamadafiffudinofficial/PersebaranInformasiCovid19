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
import kotlinx.android.synthetic.main.activity_update_data.*

class UpdateData : AppCompatActivity() {


    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekJudul: String? = null
    private var cekIsi: String? = null



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
                startActivity(Intent(this@UpdateData, MainActivity::class.java))
            }

            R.id.itemData->{
                startActivity(Intent(this@UpdateData, TestApi::class.java))
            }

            R.id.itemInfo->{
                startActivity(Intent(this@UpdateData, Create::class.java))
            }

            R.id.itemAbt->{
                startActivity(Intent(this@UpdateData, About::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)



        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data //memanggil method "data"
        update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Mendapatkan Data Mahasiswa yang akan dicek
                cekJudul = new_judul.getText().toString()
                cekIsi = new_isi.getText().toString()
                //Mengecek agar tidak ada data yang kosong, saat proses update
                if (isEmpty(cekJudul!!) || isEmpty(cekIsi!!)) {
                    Toast.makeText(
                        this@UpdateData,
                        "Data cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    /*Menjalankan proses update data.
                    Method Setter digunakan untuk mendapakan data baru yang diinputkan User.*/
                    val setInfo = data_info()
                    setInfo.judul = new_judul.getText().toString()
                    setInfo.isi = new_isi.getText().toString()
                    updateInfo(setInfo)
                }
            }
        })
    }


    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }
    //Menampilkan data yang akan di update
    private val data: Unit
        private get() {
            //Menampilkan data dari item yang dipilih sebelumnya
            val getJudul = intent.extras!!.getString("dataJudul")
            val getIsi = intent.extras!!.getString("dataIsi")
            new_judul!!.setText(getJudul)
            new_isi!!.setText(getIsi)
        }
    //Proses Update data yang sudah ditentukan
    private fun updateInfo(info: data_info) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child("Info")
            .child(getKey!!)
            .setValue(info)
            .addOnSuccessListener {
                new_judul!!.setText("")
                new_isi!!.setText("")
                Toast.makeText(this@UpdateData, "Data changed successfully",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}