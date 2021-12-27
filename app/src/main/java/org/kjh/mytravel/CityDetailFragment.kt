package org.kjh.mytravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import org.kjh.mytravel.databinding.FragmentCityDetailBinding

class CityDetailFragment : Fragment() {

    private lateinit var binding: FragmentCityDetailBinding
    val args: CityDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appConfig = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbToolbar2.setupWithNavController(findNavController(), appConfig)

        val str = args.myArg
        binding.tvCityName2.text = str
    }
}