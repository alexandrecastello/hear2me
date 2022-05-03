package com.lft.hear4me.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import com.denzcoskun.imageslider.ImageSlider
//import com.denzcoskun.imageslider.constants.ScaleTypes
//import com.denzcoskun.imageslider.models.SlideModel
import com.lft.hear4me.R
import com.lft.hear4me.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    lateinit var imageSlides_princ:ImageSlider
//    lateinit var imageSlides_secund:ImageSlider
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        imageSlides_princ=binding.imageSlider
//        val imageList_princ = ArrayList<SlideModel>() // Create image list
//        imageList_princ.add(SlideModel(R.drawable.fotobanner1, "Esteja presente",
//            ScaleTypes.CENTER_CROP))
//        imageList_princ.add(SlideModel(R.drawable.fotobanner2, "Esteja disponível",ScaleTypes.CENTER_CROP))
//        imageList_princ.add(SlideModel(R.drawable.fotobanner3, "Aproveite o momento",ScaleTypes.CENTER_CROP))
//        imageSlides_princ.setImageList(imageList_princ)

//        imageSlides_secund=binding.imageSlider
//        val imageList_secund = ArrayList<SlideModel>() // Create image list
//        imageList_secund.add(SlideModel(R.drawable.etapa1, "1 - Selecione o audio e clique no menu expansível do aplicativo Whatsapp.",
//            ScaleTypes.FIT))
//        imageList_secund.add(SlideModel(R.drawable.etapa2, "2 - Selecione compartilhar.",ScaleTypes.FIT))
//        imageList_secund.add(SlideModel(R.drawable.etapa3, "3 - Compartilhe o áudio para o aplicativo hear4me.",ScaleTypes.FIT))
//        imageSlides_secund.setImageList(imageList_secund)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}