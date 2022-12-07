package com.tareafinal.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.tareafinal.model.Postre

class PostreDao {
    private var codigoUsuario: String
    private var firestore: FirebaseFirestore

    init{
        codigoUsuario = Firebase.auth.currentUser?.email.toString()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getPostres(): MutableLiveData<List<Postre>> {
        val listaPostres = MutableLiveData<List<Postre>>()
        firestore
            .collection("Postres")
            .document(codigoUsuario)
            .collection("misPostres")
            .addSnapshotListener{ snapshot, e ->
                if(e != null){
                    return@addSnapshotListener
                }
                if(snapshot != null){
                    val lista = ArrayList<Postre>()
                    val postres = snapshot.documents
                    postres.forEach{
                        val postre = it.toObject(Postre::class.java)
                        if(postre != null){
                            lista.add(postre)
                        }
                    }
                    listaPostres.value = lista
                }
            }
        return listaPostres
    }

    fun guardarPostre(postre: Postre){
        val document: DocumentReference
        if(postre.id.isEmpty()){

            document = firestore
                .collection("Postres")
                .document(codigoUsuario)
                .collection("misPostres")
                .document()
            postre.id = document.id
        }
        else{

            document = firestore
                .collection("Postres")
                .document(codigoUsuario)
                .collection("misPostres")
                .document(postre.id)
        }
        document.set(postre)
            .addOnCompleteListener{
                Log.d("guardarPostre","Guardado con exito")
            }
            .addOnCompleteListener {
                Log.e("guardarPostre", "Error al guardar")
            }
    }

    fun eliminarPostre(postre: Postre){
        if(postre.id.isNotEmpty()){
            firestore
                .collection("Postres")
                .document(codigoUsuario)
                .collection("misPostres")
                .document(postre.id)
                .delete()
                .addOnCompleteListener{
                    Log.d("eliminarPostre", "Eliminado con exito")
                }
                .addOnCanceledListener{
                    Log.e("eliminarPostre", "Error al eliminar")
                }
        }
    }
}