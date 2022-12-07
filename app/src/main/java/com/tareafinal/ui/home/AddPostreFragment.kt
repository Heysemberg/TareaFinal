package com.tareafinal.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.tareafinal.R
import com.tareafinal.databinding.FragmentAddPostreBinding
import com.tareafinal.model.Postre
import com.tareafinal.utilidades.ImagenUtiles
import com.tareafinal.viewModel.HomeViewModel


class AddPostreFragment : Fragment() {
    private var _binding : FragmentAddPostreBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var imagenUtiles: ImagenUtiles
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentAddPostreBinding.inflate(inflater,container,false)

        binding.btAgregar.setOnClickListener {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.visibility = TextView.VISIBLE
        }

        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if(result.resultCode == Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }
        }
        imagenUtiles = ImagenUtiles(requireContext(),binding.btPhoto, binding.btRotaL,binding.btRotaR
            , binding.imagen, tomarFotoActivity)

        return binding.root
    }

    private fun subirImagen(){
        val imagenFile = imagenUtiles.imagenFile
        if(imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()){
            val ruta = Uri.fromFile(imagenFile)
            val rutaNube = "tarea/${Firebase.auth.currentUser?.email}/imagenes/${imagenFile.name}"
            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)
            referencia.putFile(ruta)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            val rutaImagen = it.toString()
                            agregarPostre(rutaImagen)
                        }
                }
                .addOnCanceledListener { agregarPostre("") }
        }
        else{
            agregarPostre("")
        }
    }

    private fun agregarPostre(rutaImagen:String){
        val nombre = binding.etNombre.text.toString()
        val tipo = binding.etTipo.text.toString()
        val precio = binding.etPrecio.text.toString()
        val tamaño = binding.etTamaO.text.toString()

        if(nombre.isNotEmpty()){
            val postre = Postre("",nombre,tipo, precio, tamaño, rutaImagen)
            homeViewModel.GuardarPostre(postre)
            Toast.makeText(requireContext(),getText(R.string.ms_AddPostre), Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addPostreFragment_to_nav_home)
        }
        else{
            Toast.makeText(requireContext(),getText(R.string.ms_FaltaValores), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}