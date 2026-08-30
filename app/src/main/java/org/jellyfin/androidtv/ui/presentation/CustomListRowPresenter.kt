package org.jellyfin.androidtv.ui.presentation

import android.view.View
import androidx.core.view.isVisible
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.RowPresenter
import kotlin.math.roundToInt

open class CustomListRowPresenter @JvmOverloads constructor(
	private val topPadding: Int? = null
) : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_NONE, false) {
	init {
		headerPresenter = CustomRowHeaderPresenter()
		shadowEnabled = false
		enableChildRoundedCorners(false)
	}

	override fun isUsingDefaultShadow() = false

	override fun onSelectLevelChanged(holder: RowPresenter.ViewHolder) = Unit

	override fun onBindRowViewHolder(holder: RowPresenter.ViewHolder, item: Any) {
		super.onBindRowViewHolder(holder, item)

		val view = holder.view?.parent as? View ?: return
		if (topPadding != null) view.setPadding(view.paddingLeft, topPadding, view.paddingRight, view.paddingBottom)

		if (holder is ListRowPresenter.ViewHolder) {
			val density = holder.view.resources.displayMetrics.density
			holder.gridView.setInitialPrefetchItemCount(PREFETCH_ITEM_COUNT)
			holder.gridView.setItemViewCacheSize(CACHED_ITEM_COUNT)
			holder.gridView.setExtraLayoutSpace((EXTRA_LAYOUT_SPACE_DP * density).roundToInt())
		}

		// Hide header view when the item doesn't have one
		holder.headerViewHolder.view.isVisible = !(item is ListRow && item.headerItem == null)
	}

	private companion object {
		const val PREFETCH_ITEM_COUNT = 6
		const val CACHED_ITEM_COUNT = 8
		const val EXTRA_LAYOUT_SPACE_DP = 256
	}
}
