package com.musalasoft.weatherapp.ui.view.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.musalasoft.weatherapp.R
import kotlinx.android.synthetic.main.error_search_view.view.*

class SearchErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.error_search_view, this)
    }

    fun setErrorMessage(errorMessage: String?) {
        tv_errorSearch_message.text = errorMessage
    }
}