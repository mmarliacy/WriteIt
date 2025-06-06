package com.projects.writeit.feature_product.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity (tableName = "product_table")
data class Product (
    @PrimaryKey
    val id :Int? = null,
    val name: String,
    val quantity:Int,
    val price : Double,
    val category: String? = null,
    val timestamp: Long,
    val isArchived: Boolean = false
){
    companion object {
        val categories = listOf(
            "Indéfini",
            "Nourriture",
            "Divers",
            "Maison",
            "Petits achats",
            "Informatique",
            "Loisirs",
            "Soin du corps",
            "Bricolage",
            "Accessoires",
             "Animaux",
             "Sans catégorie",
        )
    }
}

class InvalidProductException(message: String): Exception(message)
