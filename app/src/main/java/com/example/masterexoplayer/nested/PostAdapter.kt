package com.example.masterexoplayer.nested

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.masterexoplayer.Model
import com.example.masterexoplayer.R
import com.example.masterexoplayer.databinding.ItemBinding
import com.example.masterexoplayer.databinding.ItemImageBinding
import com.example.masterexoplayer.databinding.ItemNestedBinding
import com.example.masterexoplayer.databinding.NestedItemPlayerBinding
import com.master.exoplayer.MasterExoPlayerHelper
import com.simpleadapter.SimpleAdapter

class PostAdapter(val list: ArrayList<Model>, val masterExoPlayerHelper: MasterExoPlayerHelper) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    val TYPE_SINGLE = 1
    val TYPE_MULTIPLE = 2
    val TYPE_IMAGE= 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        when (viewType) {
            TYPE_SINGLE -> {
                return SingleViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            TYPE_IMAGE->{
                return ImageViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                return MultipleViewHolder(ItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
                    masterExoPlayerHelper.attachToRecyclerView(binding.recyclerView)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(list.get(position).sourcesList.isNotEmpty()){
            TYPE_MULTIPLE
        }
        else if(list.get(position).sources.isBlank()) {
            TYPE_IMAGE
        } else {
            TYPE_SINGLE
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list.get(holder.adapterPosition)
        holder.onBind(model)
    }

    abstract class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(model: Model);
    }

    class SingleViewHolder(val binding: ItemBinding) : MyViewHolder(binding.root) {

        override fun onBind(model: Model) {

            binding.text.text = model.title
            binding.frame.url = model.sources
            binding.frame.imageView = binding.image
            binding.image.load(model.thumb)

        }
    }
    class ImageViewHolder(val binding: ItemImageBinding) : MyViewHolder(binding.root) {

        override fun onBind(model: Model) {

            binding.text.text = model.title
            binding.image.load(model.thumb)

        }
    }

    class MultipleViewHolder(val binding: ItemNestedBinding) : MyViewHolder(binding.root) {
        val adapter:SimpleAdapter<Model>
        init {
            adapter = SimpleAdapter.with<Model, NestedItemPlayerBinding>(R.layout.nested_item_player) { adapterPosition, model, binding ->
                binding.frame.url = model.sources
                binding.frame.imageView = binding.image
                binding.image.load(model.thumb)
                binding.ivVolume.setOnClickListener {
                    binding.frame.isMute = !binding.frame.isMute
                }
            }
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context,RecyclerView.HORIZONTAL, false);
            binding.recyclerView.adapter = adapter
        }
        override fun onBind(model: Model) {

            binding.text.text = model.title
            adapter.clear()
            adapter.addAll(model.sourcesList.toList())
            adapter.notifyDataSetChanged()

        }
    }
}
