package com.example.yemeksiparisuygulamasi.data.remote

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.yemeksiparisuygulamasi.model.Basket
import com.example.yemeksiparisuygulamasi.model.Food
import com.example.yemeksiparisuygulamasi.model.ResultData
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowViaChannel
import org.json.JSONException
import org.json.JSONObject

class BasketRemoteDataSourceImpl {
    suspend fun addBasket(context: Context, food: Food, counter: Int): Flow<ResultData<Unit>> {
        return flowViaChannel { flowChannel ->
            val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/insert_sepet_yemek.php"

            val requestToUrl =
                object : StringRequest(Request.Method.POST, webServiceUrl, { responseOfUrl ->
                    flowChannel.sendBlocking(ResultData.Success())
                }, Response.ErrorListener {
                    flowChannel.sendBlocking(ResultData.Failed())
                }) {

                    override fun getParams(): MutableMap<String, String> {
                        val parameter = HashMap<String, String>()
                        parameter["yemek_siparis_adet"] = counter.toString()
                        parameter["yemek_id"] = food.id.toString()
                        parameter["yemek_adi"] = food.name
                        parameter["yemek_resim_adi"] = food.image_path
                        parameter["yemek_fiyat"] = food.price.toString()
                        return parameter
                    }
                }

            Volley.newRequestQueue(context).add(requestToUrl)
        }
    }

    suspend fun removeBasket(context: Context, foodFromBasket: Basket): Flow<ResultData<Unit>> {
        return flowViaChannel { flowChannel ->
            val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/delete_sepet_yemek.php"

            val requestToUrl = object : StringRequest(Request.Method.POST, webServiceUrl, Response.Listener{ responseOfUrl ->

                flowChannel.sendBlocking(ResultData.Success())
            }, Response.ErrorListener {
                flowChannel.sendBlocking(ResultData.Failed())
            }) {

                override fun getParams(): MutableMap<String, String> {

                    val parameter = HashMap<String, String>()

                    parameter["yemek_id"] = ((foodFromBasket.food).id).toString()

                    return parameter
                }
            }
            //SepetListesi.clear()


            Volley.newRequestQueue(context).add(requestToUrl)
        }
    }

    suspend fun getBasket(context: Context): Flow<List<Basket>?> {
        return flowViaChannel { flowChannel ->
            val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/tum_sepet_yemekler.php"

            val requestToUrl = StringRequest(Request.Method.GET, webServiceUrl, { responseOfUrl ->

                var tempSepetListesi = ArrayList<Basket>()
                tempSepetListesi = arrayListOf()
                tempSepetListesi.clear()

                var yemek_total_fiyat = 0

                try {
                    val jsonObj = JSONObject(responseOfUrl)
                    val sepet = jsonObj.getJSONArray("sepet_yemekler")


                    for (index in 0 until sepet.length()) {

                        val s = sepet.getJSONObject(index)

                        val yemek_id = s.getInt("yemek_id")
                        val yemek_adi = s.getString("yemek_adi")
                        val yemek_resim_adi = s.getString("yemek_resim_adi")
                        val yemek_fiyat = s.getInt("yemek_fiyat")
                        val yemek_siparis_adet = s.getInt("yemek_siparis_adet")

                        yemek_total_fiyat += (yemek_fiyat) * (yemek_siparis_adet)

                        val yemek = Food(yemek_id, yemek_adi, yemek_resim_adi, yemek_fiyat)
                        val sepettekiler = Basket(yemek, yemek_siparis_adet)
                        val sepettekiler2 = HashMap<Int, Int>()
                        sepettekiler2[yemek_id] = yemek_siparis_adet


                        tempSepetListesi.add(sepettekiler)
                    }
                    flowChannel.sendBlocking(tempSepetListesi)

                } catch (e: JSONException) {
                    flowChannel.sendBlocking(null)
                }

                if (tempSepetListesi.isEmpty()) {
                    //totalPriceView.text = "Sepetinizde Ürün Bulunmamaktadır"
                } else {
                    //totalPriceView.text = "Genel Toplam: ${yemek_total_fiyat} \u20ba"
                }
                //SepetListesi = tempSepetListesi

                //notifyDataSetChanged()
            }, Response.ErrorListener { flowChannel.sendBlocking(null) })

            Volley.newRequestQueue(context).add(requestToUrl)
        }
    }
}