package com.master.exoplayer

import androidx.annotation.FloatRange

class Configs {
    var autoPlay: Boolean = true

    @FloatRange(from = 0.0, to = 1.0)
    var playStrategy: Float = PlayStrategy.DEFAULT

    @MuteStrategy.Values
    var muteStrategy: Int = MuteStrategy.ALL
    var defaultMute: Boolean = false
    var useController: Boolean = false
    var thumbHideDelay: Long = 0

    var enableCache: Boolean = false
    var cacheSizeInMb: Long = 500
    var loopVideo: Boolean = false
    var loopCount: Int = Integer.MAX_VALUE
    var minBufferMs: Int = 5000
    var maxBufferMs: Int = 5000
    var bufferForPlaybackMs: Int = 5000
    var bufferForPlaybackAfterRebufferMs: Int = 5000
}