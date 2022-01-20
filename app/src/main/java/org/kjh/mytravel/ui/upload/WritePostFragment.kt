package org.kjh.mytravel.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.GridLayoutItemDecoration
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentWritePostBinding


@AndroidEntryPoint
class WritePostFragment : Fragment() {

    private lateinit var binding: FragmentWritePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWritePostBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbarWithNavigation()
        initImageRecyclerView()
    }

    private fun initImageRecyclerView() {

    }

    private fun initToolbarWithNavigation() {
        binding.tbWritePostToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_upload)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.upload -> {
                        true
                    }
                    else -> false
                }
            }
        }
    }
}