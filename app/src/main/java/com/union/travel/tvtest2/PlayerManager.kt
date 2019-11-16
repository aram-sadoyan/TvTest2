package com.union.travel.tvtest2

import android.os.Bundle

interface PlayerManager {
	fun pause()

	fun setShouldRequestAudioFocus(audioFocus: Boolean)

	fun setVideoCallback(videoCallback: ExoPlayerManager.VideoCallback)

	fun toggleMute(mute: Boolean)

	fun isMuted(): Boolean

	fun setResizeMode(reSizeMode: ResizeModeType)

	fun playStream(posMls: Long)

	fun stopPlayer()

	fun getCurrentPosition(): Long

	fun restoreSavedData(savedInstanceState: Bundle?)

	fun onSaveInstanceState(outState: Bundle)

	fun setVideoPath(path: String)

	fun isPlaying(): Boolean
}