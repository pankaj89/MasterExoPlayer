package com.example.masterexoplayer.nested

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterexoplayer.Model
import com.example.masterexoplayer.R
import com.master.exoplayer.MasterExoPlayerHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityNested : AppCompatActivity() {

    lateinit var masterExoPlayerHelper: MasterExoPlayerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        masterExoPlayerHelper = MasterExoPlayerHelper(mContext = this, id = R.id.frame)
        masterExoPlayerHelper.makeLifeCycleAware(this)
        setAdapter()
        masterExoPlayerHelper.attachToRecyclerView(recyclerView)
    }


    fun setAdapter() {
        val adapter = PostAdapter(getSampleData(), masterExoPlayerHelper)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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
                title = "Multiple Video Right Swipe to see"
                sources =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                thumb =
                    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
                sourcesList = arrayOf(Model().apply {
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
                        sourcesList = arrayOf()
                    })
            },
            Model().apply {
                title = "Image Only"
                sources = ""
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
                sourcesList = arrayOf(Model().apply {
                    title = "Big Buck Bunny"
                    sources =
                        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
                    thumb =
                        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg"
                },
                    Model().apply {
                        title = "Elephant Dream"
                        sources =
                            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                        thumb =
                            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"
                        sourcesList = arrayOf()
                    })
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
