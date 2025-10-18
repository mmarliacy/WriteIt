package com.projects.writeit.feature_product.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "item_table")
data class Item (
    @PrimaryKey
    val id :Int?,
    val name: String,
    val quantity:Int,
    val price : Double,
    val category: String? = null,
    val timestamp: Long,
    val isInTheCaddy: Boolean = false
){
    companion object {
        val categories = listOf(
            "Indéfini",
            "Boucherie",
            "Boulangerie",
            "Charcuterie",
            "Crèmerie",
            "Épicerie salée",
            "Épicerie sucrée",
            "Fruits et légumes",
            "Poissonnerie",
            "Surgelés",
            "Pâtes, riz et céréales",
            "Conserves",
            "Produits laitiers",
            "Boissons",
            "Eaux et jus",
            "Alcools et vins",
            "Petit déjeuner",
            "Biscuits et gâteaux",
            "Confiseries et chocolats",
            "Produits bio",
            "Produits sans gluten",
            "Produits bébé",
            "Hygiène et beauté",
            "Entretien et nettoyage",
            "Animaux",
            "Textile et habillement",
            "Papeterie et fournitures",
            "Électroménager",
            "Loisirs et jeux",
            "Bricolage et jardinage",
            "Auto et accessoires"
        )
    }
}
