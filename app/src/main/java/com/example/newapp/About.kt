package com.example.newapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class About : AppCompatActivity() {
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
                startActivity(Intent(this@About, MainActivity::class.java))
            }

            R.id.itemData->{
                startActivity(Intent(this@About, TestApi::class.java))
            }

            R.id.itemInfo->{
                startActivity(Intent(this@About, Create::class.java))
            }

            R.id.itemAbt->{
                startActivity(Intent(this@About, About::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}