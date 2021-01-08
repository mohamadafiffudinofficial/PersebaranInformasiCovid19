package com.example.newapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import retrofit.ApiService
import retrofit2.Call
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_test_api.*
import retrofit2.Callback

class TestApi : AppCompatActivity() {

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
                startActivity(Intent(this@TestApi, MainActivity::class.java))
            }

            R.id.itemData->{
                startActivity(Intent(this@TestApi, TestApi::class.java))
            }

            R.id.itemInfo->{
                startActivity(Intent(this@TestApi, Create::class.java))
            }

            R.id.itemAbt->{
                startActivity(Intent(this@TestApi, About::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_api)
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData(){
        showLoading(true)
        ApiService.endpoint.getData()
            .enqueue(object : Callback<List<MainModel>>{
                override fun onResponse(
                    call: Call<List<MainModel>>,
                    response: Response<List<MainModel>>
                ) {
                    showLoading(false)
                    if(response.isSuccessful){
                        val mainModel: List<MainModel> = response.body()!!
                        setResponse(mainModel)
                    }
                }

                override fun onFailure(call: Call<List<MainModel>>, t: Throwable) {
                  showLoading( false)
                }
            })
    }

    private fun setResponse(mainModel: List<MainModel>){
        val response = mainModel[0]
        tvPositive.setText(
            "${response.positif}"
        )
        tvRecover.setText(
            "${response.sembuh}"
        )
        tvHospitalized.setText(
            "36,516,411"
        )
        tvDeath.setText(
            "${response.meninggal}"
        )
    }

    private fun showLoading(loading: Boolean){
        when(loading){
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }
}
