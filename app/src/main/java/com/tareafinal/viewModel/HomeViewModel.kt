package com.tareafinal.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tareafinal.data.PostreDao
import com.tareafinal.model.Postre
import com.tareafinal.repository.PostreRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostreRepository = PostreRepository(PostreDao())
    val obtenerPostres: MutableLiveData<List<Postre>>

    init{
        obtenerPostres = repository.obtenerPostres
    }

    fun GuardarPostre(postre: Postre){
        repository.guardarPostre(postre)
    }

    fun eliminarPostre(postre: Postre){
        repository.eliminarPostre(postre)
    }
}