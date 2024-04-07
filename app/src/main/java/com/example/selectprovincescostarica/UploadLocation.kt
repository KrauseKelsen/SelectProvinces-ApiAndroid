package com.example.selectprovincescostarica

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.selectprovincescostarica.Api.RetrofitClientProvinces
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadLocation {

    private lateinit var provincesMap: Map<String, String>
    private lateinit var cantonsMap: Map<String, String>

    private var provinceKey = ""

    /**
     * Obtain the province from the API
     *
     * @param context
     * @param autoCompleteTextViewProvinces (view)
     * @param autoCompleteTextViewCantons (view)
     * @param autoCompleteTextViewDistricts (view)
     */
    fun uploadProvince(context: Context, autoCompleteTextViewProvinces: AutoCompleteTextView, autoCompleteTextViewCantons: AutoCompleteTextView, autoCompleteTextViewDistricts: AutoCompleteTextView){
        RetrofitClientProvinces.costaRicaApi.getProvinces()
            .enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(
                    call: Call<Map<String, String>>,
                    response: Response<Map<String, String>>
                ) {
                    if (response.isSuccessful) {
                        val provincesData = response.body()
                        if (provincesData != null) {
                            // Stores the Map<String, String> in the provincesMap variable
                            provincesMap = provincesData

                            // Get the list of province names and configure the adapter
                            val nameProvinces = provincesData.values.toList()
                            val provinceAdapter = ArrayAdapter(
                                context,
                                R.layout.simple_dropdown_item_1line,
                                nameProvinces
                            )
                            autoCompleteTextViewProvinces.setAdapter(provinceAdapter)
                        }
                    } else {
                        println("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        // Configure listener for the provinces AutoCompleteTextView
        autoCompleteTextViewProvinces.setOnItemClickListener { _, _, position, _ ->
            val selectedProvince = autoCompleteTextViewProvinces.adapter.getItem(position) as String
            val provinceKeys = getKeyByValue(selectedProvince, provincesMap)
            if (provinceKeys != null) {
                provinceKey = provinceKeys
                uploadCantones(provinceKeys, context,autoCompleteTextViewCantons,autoCompleteTextViewDistricts)
                // clear the fields
                autoCompleteTextViewDistricts.text.clear()
                autoCompleteTextViewCantons.text.clear()
            }
        }
    }

    /**
     * Function to get the key for a value in the Map
     *
     * @param value (value in String of the province, canton or district)
     * @param map (Map with all the provinces, cantons or districts)
     * @return String (key value of passed value)
     */
    private fun getKeyByValue(value: String, map: Map<String, String>): String? {
        return map.filterValues { it == value }.keys.firstOrNull()
    }

    /**
     * Obtain the cantons of the selected province from the API
     *
     * @param province (key value)
     * @param context
     * @param autoCompleteTextViewCantons (view)
     * @param autoCompleteTextViewDistricts (view)
     */
    private fun uploadCantones(province: String, context: Context, autoCompleteTextViewCantons: AutoCompleteTextView, autoCompleteTextViewDistricts: AutoCompleteTextView) {
        RetrofitClientProvinces.costaRicaApi.getCantones(province)
            .enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(
                    call: Call<Map<String, String>>,
                    response: Response<Map<String, String>>
                ) {
                    if (response.isSuccessful) {
                        val cantonData = response.body()
                        if (cantonData != null) {
                            // Stores the Map<String, String> in the cantonsMap variable
                            cantonsMap = cantonData

                            // Get the list of canton names and configure the adapter
                            val nameCanton = cantonData.values.toList()
                            val cantonAdapter = ArrayAdapter(
                                context,
                                android.R.layout.simple_dropdown_item_1line,
                                nameCanton
                            )
                            autoCompleteTextViewCantons.setAdapter(cantonAdapter)
                        }
                    } else {
                        println("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        // Configure listener for the AutoCompleteTextView of cantons
        autoCompleteTextViewCantons.setOnItemClickListener { _, _, position, _ ->
            val cantonSelected = autoCompleteTextViewCantons.adapter.getItem(position) as String
            val cantonKeys = getKeyByValue(cantonSelected, cantonsMap)
            if (cantonKeys != null) {
                uploadDistricts(provinceKey, cantonKeys, context,autoCompleteTextViewDistricts)
                // clear the fields
                autoCompleteTextViewDistricts.text.clear()
            }
        }
    }

    /**
     * // Obtain the districts of the canton and province selected from the API
     *
     * @param provincia (key value)
     * @param canton (key value)
     * @param context
     * @param autoCompleteTextViewDistricts (view)
     */
    private fun uploadDistricts(provincia: String, canton: String, context: Context, autoCompleteTextViewDistricts: AutoCompleteTextView) {
        RetrofitClientProvinces.costaRicaApi.getDistricts(provincia, canton)
            .enqueue(object : Callback<Map<String, String>> {
                override fun onResponse(
                    call: Call<Map<String, String>>,
                    response: Response<Map<String, String>>
                ) {
                    if (response.isSuccessful) {
                        val districtData = response.body()
                        if (districtData != null) {
                            // Get the list of district names and configure the adapter
                            val nameDistrict = districtData.values.toList()
                            val districtAdapter = ArrayAdapter(
                                context,
                                android.R.layout.simple_dropdown_item_1line,
                                nameDistrict
                            )
                            autoCompleteTextViewDistricts.setAdapter(districtAdapter)
                        }
                    } else {
                        println("Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    println("Error: ${t.message}")
                }
            })
        autoCompleteTextViewDistricts.setOnItemClickListener { _, _, position, _ ->
            val districtSelected = autoCompleteTextViewDistricts.adapter.getItem(position) as String
            val districtKeys = getKeyByValue(districtSelected, cantonsMap)
        }
    }
}