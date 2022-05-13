package com.example.pustaka_apps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)

        viewPenerbit()

        val btnSimpan = findViewById<Button>(R.id.idbtn_simpan)
        btnSimpan.setOnClickListener {
            addPenerbit()
        }

    }

    //Untuk menampilkan data penerbit pada RecyclerView
    private fun viewPenerbit(){
        val rvBuku = findViewById<RecyclerView>(R.id.idrv_penerbit)
        rvBuku.layoutManager = LinearLayoutManager(this)

        apiInterface.getPenerbit().enqueue(object : retrofit2
        .Callback<ArrayList<PenerbitModel>>{
            override fun onResponse(
                call: Call<ArrayList<PenerbitModel>>,
                response: Response<ArrayList<PenerbitModel>>
            ) {
                var penerbitData = response?.body()!!
                if (penerbitData.size > 0) {
                    rvBuku.adapter = PenerbitAdapter(penerbitData)
                }
            }

            override fun onFailure(
                call: Call<ArrayList<PenerbitModel>>,
                t: Throwable
            ) {
                Log.e("error", "error ${t.message}")
            }

        })
    }

    private fun addPenerbit(){
        val namaPenerbit = findViewById<EditText>(R.id.idedt_nama_penerbit).text
        val alamatPenerbit = findViewById<EditText>(R.id.idedt_alamat_penerbit).text
        //cek jika masih ada field yang kosong
        if (namaPenerbit.toString().trim() == "" || alamatPenerbit.toString().trim() == ""){
            Toast.makeText(this, "Tolong lengkapi field yang kosong", Toast.LENGTH_LONG).show()
        }else{
            var requestCall: Call<ResponseModel> = apiInterface
                .addPenerbit(namaPenerbit.toString(), alamatPenerbit.toString())

            requestCall.enqueue(object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    Log.d("response", response.body()!!.message.toString())
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Data berhasil tersimpan", Toast.LENGTH_LONG).show()
                        viewPenerbit()
                        namaPenerbit.clear()
                        alamatPenerbit.clear()
                    } else {
                        Toast.makeText(this@MainActivity, "Data gagal tersimpan", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(baseContext, "Data gagal tersimpan", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}

