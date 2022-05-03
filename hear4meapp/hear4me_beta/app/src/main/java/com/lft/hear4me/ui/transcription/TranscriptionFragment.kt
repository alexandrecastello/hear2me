package com.lft.hear4me.ui.transcription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lft.hear4me.databinding.FragmentTranscriptionBinding

class TranscriptionFragment : Fragment() {

    private var _binding: FragmentTranscriptionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transcriptionViewModel =
            ViewModelProvider(this).get(TranscriptionViewModel::class.java)

        _binding = FragmentTranscriptionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTranscription
        transcriptionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}