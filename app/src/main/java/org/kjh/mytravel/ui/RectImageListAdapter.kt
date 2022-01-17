package org.kjh.mytravel.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.kjh.mytravel.databinding.VhRectImageBinding

/**
 * MyTravel
 * Class: RectImageListAdapter
 * Created by mac on 2022/01/10.
 *
 * Description:
 */
class RectImageListAdapter(
    private val images: List<Int>,
    private val onClickImg: () -> Unit,
): RecyclerView.Adapter<RectImageListAdapter.RectImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RectImageViewHolder {
        return RectImageViewHolder(
            VhRectImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickImg
        )
    }

    override fun onBindViewHolder(holder: RectImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    class RectImageViewHolder(
        val binding: VhRectImageBinding,
        val onClickImg: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(imgResource: Int) {
            binding.imgResource = imgResource

            binding.clRectImgContainer.setOnClickListener {
                onClickImg()
            }
        }
    }
}