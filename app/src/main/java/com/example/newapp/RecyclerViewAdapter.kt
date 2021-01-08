package com.example.newapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter (private var listInfo: ArrayList<data_info>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    private var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Judul: TextView
        val Isi: TextView
        val ListItem: LinearLayout
        init {//Menginisialisasi View yang terpasang pada layout RecyclerView kita
            Judul = itemView.findViewById(R.id.judulx)
            Isi = itemView.findViewById(R.id.isix)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
//Membuat View untuk Menyiapkan & Memasang Layout yang digunakan pada RecyclerView
        val V: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.view_design, parent, false)
        return ViewHolder(V)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Mengambil Nilai/Value pada RecyclerView berdasarkan Posisi Tertentu
        val Judul: String? = listInfo.get(position).judul
        val Isi: String? = listInfo.get(position).isi
        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.Judul.text = "$Judul"
        holder.Isi.text = "$Isi"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                holder.ListItem.setOnLongClickListener { view ->
                    val action = arrayOf("Update", "Delete")
                    val alert: AlertDialog.Builder = AlertDialog.Builder(view.context)
                    alert.setItems(action, DialogInterface.OnClickListener { dialog, i ->
                        when (i) {
                            0 -> {
                                /* Berpindah Activity pada halaman layout updateData dan mengambil data pada
                               listMahasiswa, berdasarkan posisinya untuk dikirim pada activity selanjutnya */
                                val bundle = Bundle()
                                bundle.putString("dataJudul", listInfo[position].judul)
                                bundle.putString("dataIsi", listInfo[position].isi)
                                bundle.putString("getPrimaryKey", listInfo[position].key)
                                val intent = Intent(view.context, UpdateData::class.java)
                                intent.putExtras(bundle)
                                context.startActivity(intent)
                            }
                            1 -> {
                                //Menggunakan interface untuk mengirim data mahasiswa, yang akan dihapus
                                listener?.onDeleteData(listInfo.get(position), position)
                            }
                        }
                    })
                    alert.create()
                    alert.show()
                    true
                }
                return true;
            }

        })
    }
    override fun getItemCount(): Int {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listInfo.size
    }


    var listener: dataListener? = null
    //Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.context = context
        this.listener = context as MainActivity?
    }

    //Membuat Interfece
    interface dataListener {
        fun onDeleteData(data: data_info?, position: Int)
    }


    //Deklarasi objek dari Interfece
    //Membuat Konstruktor, untuk menerima input dari Database
    fun RecyclerViewAdapter(listInfo: ArrayList<data_info>?, context:
    Context?) {
        this.listInfo = listInfo!!
        this.context = context!!
        listener = context as MainActivity?
    }
}