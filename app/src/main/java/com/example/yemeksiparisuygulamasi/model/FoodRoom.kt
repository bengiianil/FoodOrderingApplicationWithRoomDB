package com.example.yemeksiparisuygulamasi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FoodRoom(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val foodName: String?,
    @ColumnInfo(name = "image_path")
    @SerializedName("image_path")
    val foodImagePath: String?,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val foodPrice: String?)
{
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0
}