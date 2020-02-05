package com.example.masterexoplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.example.masterexoplayer.databinding.ItemBinding
import com.master.exoplayer.ExoPlayerHelper
import com.master.exoplayer.MasterExoPlayerHelper
import com.master.exoplayer.MuteStrategy
import com.simpleadapter.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class VidePlayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    lateinit var masterExoPlayerHelper: MasterExoPlayerHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        masterExoPlayerHelper = MasterExoPlayerHelper(mContext = activity!!, id = R.id.frame, useController = true, defaultMute = false, muteStrategy = MuteStrategy.ALL)
        masterExoPlayerHelper.makeLifeCycleAware(this)
        setAdapter()
        masterExoPlayerHelper.attachToRecyclerView(recyclerView)

        parentFragmentManager.addOnBackStackChangedListener {
            if (parentFragmentManager.fragments.last() == this) {
                //resume
                masterExoPlayerHelper.exoPlayerHelper.play()
            } else {
                //pause
                masterExoPlayerHelper.exoPlayerHelper.pause()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("TAG", "Pause")
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        Log.i("TAG", "Pause : " + menuVisible)
    }

    override fun onStop() {
        super.onStop()
        Log.i("TAG", "Stop")
    }

    fun setAdapter() {
        val adapter =
            SimpleAdapter.with<Model, ItemBinding>(R.layout.item) { adapterPosition, model, binding ->
                binding.text.text = model.title
                binding.frame.url = model.sources
                binding.frame.imageView = binding.image
                binding.image.load(model.thumb)

                binding.ivVolume.setOnClickListener {
                    binding.frame.isMute = !binding.frame.isMute

                    if (binding.frame.isMute) {
                        binding.ivVolume.setImageResource(R.drawable.ic_volume_off)
                    } else {
                        binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
                    }
                }

                binding.frame.listener = object : ExoPlayerHelper.Listener {
                    override fun onBuffering(isBuffering: Boolean) {
                        super.onBuffering(isBuffering)
                        Log.i("TAG", isBuffering.toString())
                    }

                    override fun onPlayerReady() {
                        super.onPlayerReady()
                        binding.ivVolume.visibility = View.VISIBLE
                        if (binding.frame.isMute) {
                            binding.ivVolume.setImageResource(R.drawable.ic_volume_off)
                        } else {
                            binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
                        }
                    }

                    override fun onStop() {
                        super.onStop()
                        binding.ivVolume.visibility = View.GONE
                    }
                }
            }
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter
        adapter.addAll(getSampleData())
        adapter.notifyDataSetChanged()
    }

    fun getSampleData(): ArrayList<Model> {
        return arrayListOf<Model>(
            Model().apply {
                title = "Big Buck Bunny"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"
            },
            Model().apply {
                title = "Elephant Dream"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
            },
            Model().apply {
                title = "For Bigger Blazes"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg"
            },
            Model().apply {
                title = "For Bigger Escape"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg"
            },
            Model().apply {
                title = "For Bigger Fun"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg"
            },
            Model().apply {
                title = "For Bigger Joyrides"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg"
            },
            Model().apply {
                title = "For Bigger Meltdowns"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg"
            },
            Model().apply {
                title = "Sintel"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg"
            },
            Model().apply {
                title = "Subaru Outback On Street And Dirt"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg"
            },
            Model().apply {
                title = "Tears of Steel"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg"
            },
            Model().apply {
                title = "Volkswagen GTI Review"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/VolkswagenGTIReview.jpg"
            },
            Model().apply {
                title = "We Are Going On Bullrun"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/WeAreGoingOnBullrun.jpg"
            },
            Model().apply {
                title = "What care can you get for a grand?"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/WhatCarCanYouGetForAGrand.jpg"
            }
        )
    }
}