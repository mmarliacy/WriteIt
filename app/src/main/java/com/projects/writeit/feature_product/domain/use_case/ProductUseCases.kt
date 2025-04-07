package com.projects.writeit.feature_product.domain.use_case

data class ProductUseCases(
    val getProducts: GetProducts,
    val deleteProduct: DeleteProduct,
    val addProduct: AddProduct
) {
}