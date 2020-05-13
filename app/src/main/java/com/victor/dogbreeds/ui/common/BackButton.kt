package com.victor.dogbreeds.ui.common

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.victor.dogbreeds.R
import kotlinx.android.synthetic.main.back_button.view.*

class BackButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.back_button, this)

        handleAttrs(attrs)

        if (!isInEditMode) {
            context as Activity
            setOnClickListener { context.onBackPressed() }
        }
    }

    private fun handleAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.BackButton).apply {
            bind(getColor(R.styleable.BackButton_button_color, Color.BLACK))
        }
    }

    private fun bind(color: Int) {
        backButton.setColorFilter(color)
    }
}