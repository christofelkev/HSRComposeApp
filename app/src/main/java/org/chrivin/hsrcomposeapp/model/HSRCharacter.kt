package org.chrivin.hsrcomposeapp.model

data class HSRCharacter(
    val id: Int,
    val name: String,
    val photo: Int,
    val description: String,
    val backstory: String,
    var isFavorite: Boolean = false
)