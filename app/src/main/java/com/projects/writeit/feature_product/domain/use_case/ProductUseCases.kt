package com.projects.writeit.feature_product.domain.use_case

import com.projects.writeit.feature_product.domain.use_case.single_use_case.AddProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.DeleteProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProduct
import com.projects.writeit.feature_product.domain.use_case.single_use_case.GetProducts

data class ProductUseCases(
    val getProducts: GetProducts,
    val deleteProduct: DeleteProduct,
    val addProduct: AddProduct,
    val getProduct: GetProduct
) {
}