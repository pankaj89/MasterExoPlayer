package com.master.exoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.google.android.exoplayer2.ui.PlayerView

/**
 * @author Pankaj Sharma
 * MasterExoPlayer is view used to place in recyclerview item.
 *
 */
class MasterExoPlayer : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object {
//        const val ID = 0x11203
    }

    var url: String? = ""
    var imageView: ImageView? = null
    var isMute: Boolean = true
        set(value) {
            field = value
            if (playerView != null && playerView!!.tag != null && playerView!!.tag is MasterExoPlayerHelper) {
                val masterExoPlayerHelper = (playerView!!.tag as MasterExoPlayerHelper)
                masterExoPlayerHelper.isMute = value
                if (value)
                    masterExoPlayerHelper.exoPlayerHelper.mute()
                else
                    masterExoPlayerHelper.exoPlayerHelper.unMute()
            }
        }

    var playerView: PlayerView? = null

    fun addPlayer(playerView: PlayerView, autoPlay: Boolean) {
        if (this.playerView == null) {
            this.playerView = playerView
            playerView.useController = false
            addView(playerView)
            //This autoplay flag is used so we don't hide image view
            if (autoPlay) {
//                imageView?.animate()?.setDuration(0)?.alpha(0f)
            }
        }
    }

    fun removePlayer() {
        if (playerView != null) {
            removeView(playerView)
            playerView = null
            imageView?.visibility = View.VISIBLE
            imageView?.animate()?.setDuration(0)?.alpha(1f)
            listener?.onStop()
        }
    }

    override fun removeView(view: View?) {
        super.removeView(view)
        if (view is PlayerView) {
            playerView = null
            imageView?.visibility = View.VISIBLE
            imageView?.animate()?.setDuration(0)?.alpha(1f)
        }
    }

    fun hideThumbImage(thumbHideDelay: Long) {
        imageView?.animate()?.setStartDelay(thumbHideDelay)?.setDuration(0)?.alpha(0f)
    }

    var listener: ExoPlayerHelper.Listener? = null
}