package org.kjh.mytravel.place

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentPlaceDayLogBinding


class PlaceDayLogFragment : Fragment() {
    private lateinit var binding: FragmentPlaceDayLogBinding

    private val postListAdapter by lazy {
        PostListAdapter(tempPostItemList) { item ->
            val action = NavGraphDirections.actionGlobalUserFragment(item.writer)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaceDayLogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val concatAdapter = ConcatAdapter()
        concatAdapter.addAdapter(postListAdapter)

        binding.rvPlaceDayLogList.apply {
            adapter = concatAdapter
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        if (binding.rvPlaceDayLogList.adapter == null) {
            binding.rvPlaceDayLogList.adapter = postListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.rvPlaceDayLogList.adapter = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceDayLogFragment()
    }
}