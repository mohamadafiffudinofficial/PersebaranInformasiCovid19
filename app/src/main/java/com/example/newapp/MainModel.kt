package com.example.newapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

data class MainModel ( val name: String, val positif: String, val sembuh: String,
                       val meninggal: String )