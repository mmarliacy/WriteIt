package com.projects.writeit.feature_product.presentation.products

import androidx.lifecycle.ViewModel
import com.projects.writeit.feature_product.domain.use_case.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel(){
     
}