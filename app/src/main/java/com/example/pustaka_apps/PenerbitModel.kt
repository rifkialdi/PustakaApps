package com.example.pustaka_apps

import com.google.gson.annotations.SerializedName

class PenerbitModel(
    @SerializedName("kode_penerbit") //atribut sama dengan yang ada di tabel penerbit
    val kodePenerbit: String?,       //atribut digunakan di adapter
    @SerializedName("nama_penerbit")
    val namaPenerbit: String?,
    @SerializedName("alamat_penerbit")
    val alamatPenerbit: String?
)