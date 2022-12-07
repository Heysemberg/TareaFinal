package com.tareafinal.repository

import androidx.lifecycle.MutableLiveData
import com.tareafinal.data.PostreDao
import com.tareafinal.model.Postre

class PostreRepository(private val postreDao: PostreDao) {
    fun guardarPostre(postre: Postre){
        postreDao.guardarPostre(postre)
    }
    fun eliminarPostre(postre: Postre){
        postreDao.eliminarPostre(postre)
    }

    val obtenerPostres: MutableLiveData<List<Postre>> = postreDao.getPostres()
}