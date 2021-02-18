package com.example.yemeksiparisuygulamasi.ui.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.yemeksiparisuygulamasi.R
import com.example.yemeksiparisuygulamasi.domain.entity.Food
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alertview_layout.view.*

class MenuAdapter(private var myContext:Context, private var YemekListesi:ArrayList<Food>, var counter:Int, private val listener: ItemClickListener)
    :RecyclerView.Adapter<MenuAdapter.CardViewDesignHolder>(){
    inner class CardViewDesignHolder(design:View) : RecyclerView.ViewHolder(design){
        var parentCardView : CardView = design.findViewById(R.id.parent_card_view)
        var foodNameText : TextView = design.findViewById(R.id.food_name_text_view)
        var foodImage : ImageView = design.findViewById(R.id.food_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewDesignHolder {
        val cardDesign = LayoutInflater.from(myContext).inflate(R.layout.food_item_list, parent, false)
        return CardViewDesignHolder(cardDesign)
    }

    override fun onBindViewHolder(holder: CardViewDesignHolder, position: Int) {
        val yemek = YemekListesi.get(position)

        holder.foodNameText.text = "${yemek.name}"

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.image_path}"
        Picasso.get().load(url).into(holder.foodImage)


        holder.parentCardView.setOnClickListener {

            alertView(yemek,position)
        }

    }

    override fun getItemCount(): Int {
        return YemekListesi.size
    }

    fun alertView(yemek :Food,position: Int){

        val design = LayoutInflater.from(myContext).inflate(R.layout.alertview_layout, null)

        val alert = AlertDialog.Builder(myContext)
            .setTitle("Ürün Detayı")
            .setView(design)

        counter = 1
        design.order_counter_text.text = counter.toString()

        design.alert_text.text = "${yemek.name} - ${yemek.price} \u20ba"

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.image_path}"
        Picasso.get().load(url).into(design.alert_food_image)

        design.add_button.setOnClickListener {
            counter++
            design.order_counter_text.text = counter.toString()
        }
        design.remove_button.setOnClickListener {
            if(counter != 1) {
                counter--
                design.order_counter_text.text = counter.toString()
            }
        }


        alert.setPositiveButton("Sepete Ekle"){ dialogInterface, i->

            Toast.makeText(myContext, "${yemek.name} Sepete Eklendi", Toast.LENGTH_SHORT).show()

            //addToBasket(yemek)

            listener.clickRow(position)
            notifyDataSetChanged()
        }
        alert.setNegativeButton("İptal"){ dialogInterface, i->

            Toast.makeText(myContext, "Seçim İptal Edildi", Toast.LENGTH_SHORT).show()
            dialogInterface.dismiss()
        }
        alert.create().show()
    }
/*
    fun addToBasket(yemek: Food){

        val webServiceUrl = "http://kasimadalan.pe.hu/yemekler/insert_sepet_yemek.php"

        val requestToUrl = object : StringRequest(Request.Method.POST, webServiceUrl, { responseOfUrl ->

            Log.e("Sepete Ekleme:", responseOfUrl)

        }, Response.ErrorListener {
            Toast.makeText(myContext, "Hata oluştu", Toast.LENGTH_SHORT).show()
        }){

            override fun getParams(): MutableMap<String, String> {

                val parameter = HashMap<String, String>()

                parameter["yemek_siparis_adet"] = counter.toString()
                parameter["yemek_id"] = yemek.id.toString()
                parameter["yemek_adi"] = yemek.name
                parameter["yemek_resim_adi"] = yemek.image_path
                parameter["yemek_fiyat"] = yemek.price.toString()

                return parameter
            }
        }

        Volley.newRequestQueue(myContext).add(requestToUrl)
    }

 */
    interface ItemClickListener
    {
        fun clickRow(position: Int)
    }
}