package org.kjh.mytravel.ui.upload

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSelectBucketBottomSheetBinding
import org.kjh.mytravel.databinding.VhBsSelectBucketBinding


class SelectBucketBottomSheet : BottomSheetDialogFragment() {
//
//    private lateinit var binding: FragmentSelectBucketBottomSheetBinding
//    private val selectPhotoViewModel: SelectPhotoViewModel by viewModels({ requireParentFragment() })
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentSelectBucketBottomSheetBinding.inflate(inflater, container, false)
//        binding.lifecycleOwner = viewLifecycleOwner
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.rvBucketList.apply {
//            adapter = SelectBucketListAdapter(selectPhotoViewModel.convertLocalImageMapToBucketUiState()) { item ->
//                selectPhotoViewModel.changeCurrentBucket(item.bucketName)
//                this@SelectBucketBottomSheet.dismissAllowingStateLoss()
//            }
//            setHasFixedSize(true)
//        }
//    }

    companion object {
        const val TAG = "SELECT_BUCKET_BOTTOM_SHEET"

        @JvmStatic
        fun newInstance() = SelectBucketBottomSheet()
    }
}

//data class BucketUiState(
//    val bucketName: String,
//    val bucketImg : Uri,
//    val bucketSize: Int
//)
//
//class SelectBucketListAdapter(
//    private val bucketItems: List<BucketUiState>,
//    private val onClickBucket: (BucketUiState) -> Unit
//): RecyclerView.Adapter<SelectBucketViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        SelectBucketViewHolder(
//            VhBsSelectBucketBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            ), onClickBucket
//        )
//
//    override fun onBindViewHolder(holder: SelectBucketViewHolder, position: Int) {
//        holder.bind(bucketItems[position])
//    }
//
//    override fun getItemCount() = bucketItems.size
//}
//
//class SelectBucketViewHolder(
//    val binding: VhBsSelectBucketBinding,
//    val onClickBucket: (BucketUiState) -> Unit
//): RecyclerView.ViewHolder(binding.root) {
//
//    fun bind(item: BucketUiState) {
//        binding.bucketUiState = item
//        binding.root.setOnClickListener {
//            onClickBucket(item)
//        }
//    }
//}