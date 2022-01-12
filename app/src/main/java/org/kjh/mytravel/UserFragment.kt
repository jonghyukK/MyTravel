package org.kjh.mytravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.databinding.VhPostSmallItemBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val args: UserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbUserToolbar.setupWithNavController(findNavController())
        binding.tbUserToolbar.title = args.userEmail

        binding.rvUserPostList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = PostSmallListAdapter(tempPostItemList) { item ->
                val action = NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
                findNavController().navigate(action)
            }
            setHasFixedSize(true)
            addItemDecoration(GridLayoutItemDecoration(requireContext()))
        }
    }
}