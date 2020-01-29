package com.example.masterexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.master.exoplayer.MasterExoPlayerHelper
import kotlinx.android.synthetic.main.activity_with_fragment.*

class MainActivityWithFragment : AppCompatActivity() {

    lateinit var masterExoPlayerHelper: MasterExoPlayerHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)

        val videoPlayFragment = VidePlayFragment()

        supportFragmentManager.beginTransaction().replace(R.id.container, videoPlayFragment)
            .commit()

        btnSecond.setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.container, SecondFragment())
                .addToBackStack("SecondFragment")
                .commit()
        }
    }
}
