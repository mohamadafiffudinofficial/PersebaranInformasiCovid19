package com.example.newapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.dataListener {

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
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
            }

            R.id.itemData->{
                startActivity(Intent(this@MainActivity, TestApi::class.java))
            }

            R.id.itemInfo->{
                startActivity(Intent(this@MainActivity, Create::class.java))
            }

            R.id.itemAbt->{
                startActivity(Intent(this@MainActivity, About::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }

    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    //Deklarasi Variable Database Reference & ArrayList dengan Parameter Class Model kita.
    val database = FirebaseDatabase.getInstance()
    private var dataInfo = ArrayList<data_info>()
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.datalist)
//        supportActionBar!!.title = "Data Mahasiswa"
        auth = FirebaseAuth.getInstance()
        MyRecyclerView()
        GetData()
    }

    private fun GetData() {
        Toast.makeText(applicationContext, "Please wait..", Toast.LENGTH_LONG).show()
//        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
        getReference.child("Admin").child("Info")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (snapshot in dataSnapshot.children) {
                                //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                                val info = snapshot.getValue(data_info::class.java)
                                //Mengambil Primary Key, digunakan untuk proses Update/Delete
                                info?.key = snapshot.key
                                dataInfo.add(info!!)
                            }
                            //Inisialisasi Adapter dan data Mahasiswa dalam bentuk Array
                            adapter = RecyclerViewAdapter(dataInfo, context = this@MainActivity)
                            //Memasang Adapter pada RecyclerView
                            recyclerView?.adapter = adapter
                            (adapter as RecyclerViewAdapter).notifyDataSetChanged()
                            Toast.makeText(applicationContext,"Data Loaded Successfully", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        // Kode ini akan dijalankan ketika ada error, simpan ke LogCat
                        Toast.makeText(applicationContext, "Data Failed To Load",
                                Toast.LENGTH_LONG).show()
                        Log.e("MyListActivity", databaseError.details + " " +
                                databaseError.message)
                    }
                })
    }
    //Methode yang berisi kumpulan baris kode untuk mengatur RecyclerView
    private fun MyRecyclerView() {
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        //Membuat Underline pada Setiap Item Didalam List
        val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.line)!!)
        recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun onDeleteData(data: data_info?, position: Int) {
//    `    val getUserID: String = auth?.getCurrentUser()?.getUid().toString()`
        val getReference = database.getReference()
//        val getKey = intent.extras!!.getString("getPrimaryKey")
        if (getReference != null) {
            getReference.child("Admin")
                    .child("Info")
                    .child(data?.key.toString())
                    .removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(
                                this@MainActivity, "Data Deleted Successfully",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

        }else{
            Toast.makeText(this@MainActivity, "Reference Blank",
                    Toast.LENGTH_SHORT).show()
        }
    }
}