package com.tareafinal.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Postre(
    var id: String,
    val nombre: String,
    val tipo: String?,
    val precio: String?,
    val tamaño: String?,
    val rutaImagen: String?
) : Parcelable{
    constructor():
            this("","","","","","")
}
