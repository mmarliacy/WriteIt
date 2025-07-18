package com.projects.writeit.feature_product.domain.util


/**
 * Exception déclenchée lorsqu'un produit ne respecte pas les règles de validation.
 *
 * @param message Le message décrivant l'erreur de validation.
 */
class InvalidItemException(
    message: String
) : Exception(message)