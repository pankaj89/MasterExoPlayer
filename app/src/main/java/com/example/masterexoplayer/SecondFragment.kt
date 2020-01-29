package com.example.masterexoplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.example.masterexoplayer.databinding.ItemBinding
import com.master.exoplayer.MasterExoPlayerHelper
import com.simpleadapter.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class SecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_second_fragment, container, false)
    }
}