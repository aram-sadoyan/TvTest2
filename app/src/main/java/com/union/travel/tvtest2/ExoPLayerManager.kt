package com.union.travel.tvtest2


import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ExoPlaybackException.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.lang.ref.WeakReference

private const val TAG_SAVE_POS = "position"
private const val TAG_SAVE_IS_PLAY = "is_playing"

private const val DEFAULT_COLOR = "#000000"

class ExoPlayerManager(
		lifecycleOwner: LifecycleOwner,
		context: Context,
		playerView: PlayerView) : LifecycleObserver, PlayerManager {

	private val player by lazy {
		ExoPlayerFactory.newSimpleInstance(
				_context, rendererFactory, trackSelector, loadControl)
	}
	private var lifecycleOwner = WeakReference(lifecycleOwner)
	private val _context = context.applicationContext
	private var mPlayerView = WeakReference(playerView)
	private var handleAudioFocus = false
	private var _videoCallback: VideoCallback? = null

	private val trackSelector by lazy { DefaultTrackSelector() }
	private val loadControl by lazy { DefaultLoadControl() }
	private val rendererFactory by lazy { DefaultRenderersFactory(_context) }
	private val audioAttributes by lazy {
		AudioAttributes.Builder()
				.setUsage(C.USAGE_MEDIA)
				.setContentType(C.CONTENT_TYPE_UNKNOWN)
				.build()
	}

	private var currentPosMls = 0L
	private var isPlaying = false

	private lateinit var _videoPath: String

	private var currentVolume: Float = 0f

	private var shutterBackgroundColor = Color.parseColor(DEFAULT_COLOR)

	private var resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL


	@OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
	fun onPause() {
		if (Util.SDK_INT <= Build.VERSION_CODES.N) {
			pause()
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_STOP)
	fun onStop() {
		if (Util.SDK_INT > Build.VERSION_CODES.N) {
			pause()
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	fun onStart() {
		if (Util.SDK_INT > Build.VERSION_CODES.N) {
			initializePlayer()
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
	fun onResume() {
		if (Util.SDK_INT <= Build.VERSION_CODES.N) {
			initializePlayer()
		}
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
	fun onDestroy() {
		release()
		lifecycleOwner.get()?.lifecycle?.removeObserver(this)
		player.release()
	}

	private fun release() {
		player.stop()
		currentPosMls = player.currentPosition
		isPlaying = player.playWhenReady
	}

	override fun pause() {
		player.playWhenReady = false
	}

	override fun setShouldRequestAudioFocus(audioFocus: Boolean) {
		handleAudioFocus = audioFocus
	}

	override fun setVideoCallback(videoCallback: VideoCallback) {
		_videoCallback = videoCallback
	}

	override fun toggleMute(mute: Boolean) {
		if (mute) {
			currentVolume = player.volume
			player.volume = 0f
		} else {
			player.volume = currentVolume
		}
	}

	override fun isMuted() = player.volume == 0f

	override fun setResizeMode(reSizeMode: ResizeModeType) {
		val mode = when (reSizeMode) {
			ResizeModeType.RESIZE_MODE_FILL -> AspectRatioFrameLayout.RESIZE_MODE_FILL
			ResizeModeType.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_FIT
			ResizeModeType.RESIZE_MODE_FIXED_HEIGHT -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
			ResizeModeType.RESIZE_MODE_FIXED_WIDTH -> AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
			ResizeModeType.RESIZE_MODE_ZOOM -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
		}
		this.resizeMode = mode
	}

	private fun initializePlayer() {
		player.playWhenReady = true
		player.repeatMode = Player.REPEAT_MODE_OFF
		player.setAudioAttributes(audioAttributes, handleAudioFocus)
		player.addListener(object : Player.EventListener {
			override fun onPlayerError(error: ExoPlaybackException?) {
				error?.let {
					val msg = when (it.type) {
						TYPE_SOURCE -> "TYPE_SOURCE: ${it.sourceException.message}"
						TYPE_RENDERER -> "TYPE_RENDERER: ${it.rendererException.message}"
						TYPE_UNEXPECTED -> "TYPE_UNEXPECTED: ${it.unexpectedException.message}"
						else -> "UNKNOWN"
					}
					_videoCallback?.onVideoFail(_videoPath, msg)
				}
			}

			override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
				when (playbackState) {
					Player.STATE_ENDED -> _videoCallback?.onVideoEnd(_videoPath)
					Player.STATE_READY -> {
						_videoCallback?.onVideoBufferingEnd()
						if (playWhenReady) _videoCallback?.onVideoStart(_videoPath)
					}
				}
			}
		})

		mPlayerView.get()?.let {
			it.useController = false
			it.player = player
			it.setShutterBackgroundColor(shutterBackgroundColor)
			it.resizeMode = resizeMode
		}

		if (isPlaying) {
			playStream(currentPosMls)
		}
	}


	override fun playStream(posMls: Long) {
		lifecycleOwner.get()?.lifecycle?.addObserver(this)
		mPlayerView.get()?.post {
			if (!::_videoPath.isInitialized) return@post
			val userAgent = Util.getUserAgent(_context, "")
			val mediaSource = ExtractorMediaSource
					.Factory(DefaultDataSourceFactory(_context, userAgent))
					.setExtractorsFactory(DefaultExtractorsFactory())
					.createMediaSource(Uri.parse(_videoPath))
			player.prepare(mediaSource)
			player.seekTo(posMls)
			player.playWhenReady = true
		}
	}

	override fun isPlaying(): Boolean {
		return isPlaying
	}

	override fun setVideoPath(path: String) {
		_videoPath = path


	}

	override fun stopPlayer() {
		player.playWhenReady = false
		player.stop()
	}

	override fun getCurrentPosition() = maxOf(0, currentPosMls)

	override fun restoreSavedData(savedInstanceState: Bundle?) {
		savedInstanceState?.let {
			currentPosMls = it.getLong(TAG_SAVE_POS)
			isPlaying = it.getBoolean(TAG_SAVE_IS_PLAY)
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		outState.putLong(TAG_SAVE_POS, getCurrentPosition())
		outState.putBoolean(TAG_SAVE_IS_PLAY, player.playWhenReady)
	}

	fun seekToMls(i: Int) {
		val userAgent = Util.getUserAgent(_context, "")

		val mediaSource = ExtractorMediaSource
				.Factory(DefaultDataSourceFactory(_context, userAgent))
				.setExtractorsFactory(DefaultExtractorsFactory())
				.createMediaSource(Uri.parse(_videoPath))
		player.seekTo(10000L)
		player.prepare(mediaSource)
		player.seekTo(10, 1000L)

	}

	interface VideoCallback {
		fun onVideoStart(url: String) {}
		fun onVideoEnd(url: String) {}
		fun onVideoFail(url: String, errorMsg: String) {}
		fun onVideoBufferingEnd() {}
	}

	companion object {
		@JvmStatic
		fun createInstance(lifecycleOwner: LifecycleOwner, context: Context, playerView: PlayerView): ExoPlayerManager {
			return ExoPlayerManager(lifecycleOwner, context, playerView)
		}
	}
}