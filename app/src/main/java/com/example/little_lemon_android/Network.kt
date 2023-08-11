package com.example.little_lemon_android

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
@Serializable
class MenuNetworkData(
    @SerialName("menu")
    val menu: List<MenuItems>
)


@Serializable
data class MenuItems(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val desc: String,
    @SerialName("price")
    val price: Double,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
) {
    fun storeInDatabase() = MenuItemDatabase(
        id,
        title,
        desc,
        price,
        image,
        category
    )
}