package com.example.yemeksiparisuygulamasi.data.datasource.remote

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.yemeksiparisuygulamasi.data.datasource.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.example.yemeksiparisuygulamasi.domain.entity.ResultData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class MenuRemoteDataSourceImpl @Inject constructor() :
    MenuRemoteDataSource {
    @FlowPreview
    override suspend fun getAllFoods(context: Context): Flow<List<Food>?> {
        return flowViaChannel { flowChannel ->
            var foodList : ArrayList<Food> = arrayListOf()
            val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/tum_yemekler.php"

            val requestToUrl = StringRequest(Request.Method.GET, webServiceUrl, { responseOfUrl ->

                try{
                    val jsonObj = JSONObject(responseOfUrl)
                    val yemekler = jsonObj.getJSONArray("yemekler")

                    for(index in 0 until yemekler.length()){

                        val y = yemekler.getJSONObject(index)

                        val yemek_id = y.getInt("yemek_id")
                        val yemek_adi = y.getString("yemek_adi")
                        val yemek_resim_adi = y.getString("yemek_resim_adi")
                        val yemek_fiyat = y.getInt("yemek_fiyat")

                        val yemek = Food(yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat)
                        foodList.add(yemek)
                    }
                    flowChannel.sendBlocking(foodList)
                }
                catch(e: JSONException){
                    flowChannel.sendBlocking(null)
                }

            }, Response.ErrorListener {
                flowChannel.sendBlocking(null)
            })

            Volley.newRequestQueue(context).add(requestToUrl)
        }
    }
}