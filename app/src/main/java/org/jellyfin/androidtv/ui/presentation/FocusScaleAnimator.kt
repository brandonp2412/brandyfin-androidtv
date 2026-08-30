package org.jellyfin.androidtv.ui.presentation

import android.view.View
import android.view.animation.DecelerateInterpolator
import org.jellyfin.androidtv.R

private const val FOCUS_ANIMATION_DURATION_MS = 110L
private val focusInterpolator = DecelerateInterpolator()

internal fun View.installFastFocusScale(
	onFocusChanged: ((Boolean) -> Unit)? = null,
) {
	val focusedScale = resources.getFraction(R.fraction.card_scale_focus, 1, 1)

	setOnFocusChangeListener { view, focused ->
		onFocusChanged?.invoke(focused)
		view.animate().cancel()
		view.animate()
			.scaleX(if (focused) focusedScale else 1f)
			.scaleY(if (focused) focusedScale else 1f)
			.setDuration(FOCUS_ANIMATION_DURATION_MS)
			.setInterpolator(focusInterpolator)
			.start()
	}
}
