package com.example.selectprovincescostarica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.selectprovincescostarica.Api.RetrofitClientProvinces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var autoCompleteTextViewProvinces: AutoCompleteTextView
    private lateinit var autoCompleteTextViewCantones: AutoCompleteTextView
    private lateinit var autoCompleteTextViewDistricts: AutoCompleteTextView

    private var uploadLocation: UploadLocation = UploadLocation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autoCompleteTextViewProvinces = findViewById(R.id.province)
        autoCompleteTextViewCantones = findViewById(R.id.canton)
        autoCompleteTextViewDistricts = findViewById(R.id.district)

        uploadLocation.uploadProvince(this@MainActivity,autoCompleteTextViewProvinces,autoCompleteTextViewCantones,autoCompleteTextViewDistricts)

    }
}