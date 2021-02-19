package com.example.yemeksiparisuygulamasi.data.datasource.remote

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.yemeksiparisuygulamasi.data.datasource.MenuRemoteDataSource
import com.example.yemeksiparisuygulamasi.domain.entity.Food
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
            val foodList : ArrayList<Food> = arrayListOf()
            val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/tum_yemekler.php"

            val requestToUrl = StringRequest(Request.Method.GET, webServiceUrl, { responseOfUrl ->

                try{
                    val jsonObj = JSONObject(responseOfUrl)
                    val foods = jsonObj.getJSONArray("yemekler")

                    for(index in 0 until foods.length()){
                        val foodData = foods.getJSONObject(index)

                        val yemek_id = foodData.getInt("yemek_id")
                        val yemek_adi = foodData.getString("yemek_adi")
                        val yemek_resim_adi = foodData.getString("yemek_resim_adi")
                        val yemek_fiyat = foodData.getInt("yemek_fiyat")

                        val food = Food(yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat)
                        foodList.add(food)
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
    override suspend fun searchFood(context: Context, keyWord: String): Flow<List<Food>?> {
        return flowViaChannel { flowChannel ->
            val foodList: ArrayList<Food> = arrayListOf()
            val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/tum_yemekler_arama.php"

            val requestToUrl = object : StringRequest(
                Request.Method.POST,
                webServiceUrl,
                Response.Listener { responseOfUrl ->
                    foodList.clear()
                    try {
                        val jsonObj = JSONObject(responseOfUrl)
                        val foodsData = jsonObj.getJSONArray("yemekler")

                        for (index in 0 until foodsData.length()) {
                            val foods = foodsData.getJSONObject(index)
                            val yemek_id = foods.getInt("yemek_id")
                            val yemek_adi = foods.getString("yemek_adi")
                            val yemek_resim_adi = foods.getString("yemek_resim_adi")
                            val yemek_fiyat = foods.getInt("yemek_fiyat")

                            val yemek = Food(yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat)

                            foodList.add(yemek)
                        }
                        flowChannel.sendBlocking(foodList)
                    } catch (e: JSONException) {
                        flowChannel.sendBlocking(null)
                    }

                },
                Response.ErrorListener {
                    flowChannel.sendBlocking(null)
                }) {
                override fun getParams(): MutableMap<String, String> {
                    val parameter = HashMap<String, String>()
                    parameter["yemek_adi"] = keyWord
                    return parameter
                }
            }
            Volley.newRequestQueue(context).add(requestToUrl)
        }
    }
}