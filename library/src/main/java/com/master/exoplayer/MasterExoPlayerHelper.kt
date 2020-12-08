package com.master.exoplayer

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.view.View
import androidx.annotation.FloatRange
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.master.exoplayer.ExoPlayerHelper.Listener

/**
 * @author Pankaj Sharma
 * MasterExoPlayerHelper lightweight utility for playing video using ExoPlayer inside RecyclerView,
 * With this you can set
 * @param id Id of MasterExoPlayer which is placed inside RecyclerView Item
 * @param playStrategy Used to decide when video will play, this will be value between 0 to 1, if 0.5 set means when view has 50% visibility it will start play. Default is PlayStrategy.DEFAULT i.e. 0.75
 * @param autoPlay Used to device we need to autoplay video or not., Default value is true
 * @param muteStrategy Used to decide whether mute one player affects other player also or not, values may be MuteStrategy.ALL, MuteStrategy.INDIVIDUAL, if individual user need to manage isMute flag with there own
 * @param defaultMute Used to decide whether player is mute by default or not, Default Value is false
 * @param loop Used whether need to play video in looping or not, if 0 then no looping will be there, Default is Int.MAX_VALUE
 */
class MasterExoPlayerHelper(
    mContext: Context,
    private val id: Int,
    private val configs: Configs = Configs()
) {
    private val playerView: PlayerView
    val exoPlayerHelper: ExoPlayerHelper

    var isMute = configs.defaultMute

    init {
        playerView = PlayerView(mContext)
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        playerView.useController = configs.useController
        exoPlayerHelper = ExoPlayerHelper(
            mContext = mContext,
            playerView = playerView,
            configs = configs
        )
        exoPlayerHelper.setListener(false, object : Listener {
            override fun onStart() {
                super.onStart()
                playerView.getPlayerParent()?.hideThumbImage(configs.thumbHideDelay)
                playerView.getPlayerParent()?.listener?.onStart()
            }

            override fun onBuffering(isBuffering: Boolean) {
                super.onBuffering(isBuffering)
                playerView.getPlayerParent()?.listener?.onBuffering(isBuffering)
            }

            override fun onError(error: ExoPlaybackException?) {
                super.onError(error)
                playerView.getPlayerParent()?.listener?.onError(error)
            }

            override fun onPlayerReady() {
                super.onPlayerReady()
                playerView.getPlayerParent()?.listener?.onPlayerReady()
            }

            override fun onProgress(positionMs: Long) {
                super.onProgress(positionMs)
                playerView.getPlayerParent()?.listener?.onProgress(positionMs)
            }

            override fun onStop() {
                super.onStop()
                playerView.getPlayerParent()?.listener?.onStop()
            }

            override fun onToggleControllerVisible(isVisible: Boolean) {
                super.onToggleControllerVisible(isVisible)
                playerView.getPlayerParent()?.listener?.onToggleControllerVisible(isVisible)
            }
        })
        playerView.tag = this
    }

    /**
     * Make this helper lifecycler aware so it will stop player when activity goes to background.
     */
    fun makeLifeCycleAware(activity: AppCompatActivity) {
        exoPlayerHelper.makeLifeCycleAware(activity)
    }

    fun makeLifeCycleAware(fragment: Fragment) {
        exoPlayerHelper.makeLifeCycleAware(fragment)
    }

    private fun getViewRect(view: View): Rect {
        val rect = Rect()
        val offset = Point()
        view.getGlobalVisibleRect(rect, offset)
        return rect
    }

    private fun visibleAreaOffset(player: MasterExoPlayer, parent: View): Float {
        val videoRect = getViewRect(player)
        val parentRect = getViewRect(parent)

        if ((parentRect.contains(videoRect) || parentRect.intersect(videoRect))) {
            val visibleArea = (videoRect.height() * videoRect.width()).toFloat()
            val viewArea = player.getWidth() * player.getHeight()
            return if (viewArea <= 0f) 1f else visibleArea / viewArea
        } else {
            return 0f
        }
    }

    private fun visibleAreaOffset(parent: View): Float {
        val videoRect = getViewRect(parent)
        val parentRect = getViewRect(parent)

        if ((parentRect.contains(videoRect) || parentRect.intersect(videoRect))) {
            val visibleArea = (videoRect.height() * videoRect.width()).toFloat()
            val viewArea = parent.getWidth() * parent.getHeight()
            return if (viewArea <= 0f) 1f else visibleArea / viewArea
        } else {
            return 0f
        }
    }

    fun playCurrent(recyclerView: RecyclerView) {
        onScrollListener.onScrollStateChanged(
            recyclerView = recyclerView,
            newState = RecyclerView.SCROLL_STATE_IDLE
        )
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        internal var firstVisibleItem: Int = 0
        internal var lastVisibleItem: Int = 0
        internal var visibleCount: Int = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {

                    for (i in 0 until visibleCount) {
                        val view = recyclerView.getChildAt(i) ?: continue
                        if (visibleAreaOffset(view) >= configs.playStrategy) {
                            val masterExoPlayer = view.findViewById<View>(id)
                            if (masterExoPlayer != null && masterExoPlayer is MasterExoPlayer) {
                                play(view)
                            } else {
                                exoPlayerHelper.stop()
                                playerView.getPlayerParent()?.removePlayer()
                            }
                            break
                        }
                    }
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition() ?: 0;
            lastVisibleItem = layoutManager.findLastVisibleItemPosition() ?: 0;
            visibleCount = (lastVisibleItem - firstVisibleItem) + 1;

            if (dx == 0 && dy == 0 && recyclerView.childCount > 0) {
                play(recyclerView.getChildAt(0))
            }
        }
    }

    private val childAttachHandler = android.os.Handler()
    private val childAttachRunnable = object : Runnable {
        var _attachedRecyclerView: RecyclerView? = null
        override fun run() {
            if (_attachedRecyclerView != null)
                onScrollListener.onScrollStateChanged(
                    recyclerView = _attachedRecyclerView!!,
                    newState = RecyclerView.SCROLL_STATE_IDLE
                )
        }
    }

    private val onChildAttachStateChangeListener =
        object : RecyclerView.OnChildAttachStateChangeListener {
            var attachedRecyclerView: RecyclerView? = null
            override fun onChildViewDetachedFromWindow(view: View) {
                releasePlayer(view)
            }

            override fun onChildViewAttachedToWindow(view: View) {
                childAttachHandler.removeCallbacks(childAttachRunnable)
                childAttachHandler.postDelayed(childAttachRunnable.apply {
                    _attachedRecyclerView = attachedRecyclerView
                }, 500)
            }
        }


    /**
     * Used to attach this helper to recycler view. make call to this after setting LayoutManager to your recycler view
     */
    public fun attachToRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView.layoutManager != null) {
            recyclerView.removeOnScrollListener(onScrollListener)
            recyclerView.removeOnChildAttachStateChangeListener(onChildAttachStateChangeListener)

            recyclerView.addOnScrollListener(onScrollListener)
            recyclerView.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener.apply {
                attachedRecyclerView = recyclerView
            })
        } else {
            throw(RuntimeException("call attachToRecyclerView() after setting RecyclerView.layoutManager"))
        }
    }

    private fun play(view: View) {
        val masterExoPlayer = view.findViewById<View>(id)

        if (masterExoPlayer != null && masterExoPlayer is MasterExoPlayer) {
            if (masterExoPlayer.playerView == null) {

                playerView.getPlayerParent()?.removePlayer()
                masterExoPlayer.addPlayer(playerView, configs.autoPlay)
                if (masterExoPlayer.url?.isNotBlank() == true) {
                    if (configs.muteStrategy == MuteStrategy.ALL) {
                        masterExoPlayer.isMute = isMute
                        if (isMute) {
                            masterExoPlayer.isMute = true
                            exoPlayerHelper.mute()
                        } else {
                            masterExoPlayer.isMute = false
                            exoPlayerHelper.unMute()
                        }
                    } else if (configs.muteStrategy == MuteStrategy.INDIVIDUAL) {
                        if (masterExoPlayer.isMute) {
                            masterExoPlayer.isMute = true
                            exoPlayerHelper.mute()
                        } else {
                            masterExoPlayer.isMute = false
                            exoPlayerHelper.unMute()
                        }
                    }
                    exoPlayerHelper.setUrl(masterExoPlayer.url!!, configs.autoPlay)
                }
                playerView.getPlayerParent()?.listener?.onPlayerReady()
            }
        }
    }

    private fun releasePlayer(view: View) {
        val masterExoPlayer = view.findViewById<View>(id)
        if (masterExoPlayer != null && masterExoPlayer is MasterExoPlayer) {
            if (masterExoPlayer.playerView != null) {
                exoPlayerHelper.stop()
                masterExoPlayer.removePlayer()
            }
        }
    }

    private fun PlayerView.getPlayerParent(): MasterExoPlayer? {
        if (this.parent != null && this.parent is MasterExoPlayer) {
            return this.parent as MasterExoPlayer
        }
        return null
    }

    public fun getPlayerView(): PlayerView {
        return playerView
    }
}