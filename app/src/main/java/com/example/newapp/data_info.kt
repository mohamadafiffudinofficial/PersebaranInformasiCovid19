package com.example.newapp

class data_info {

    //Deklarasi Variable
    var judul: String? = null
    var isi: String? = null
    var key: String? = null
    //Membuat Konstuktor kosong untuk membaca data snapshot
    constructor() {}
    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    constructor(judul: String?, isi: String?) {
        this.judul = judul
        this.isi = isi
    }
}