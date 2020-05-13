package com.victor.dogbreeds.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.victor.dogbreeds.R
import kotlinx.android.synthetic.main.text_field.view.*

class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.text_field, this)
        handleAttrs(attrs)
    }

    private fun handleAttrs(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.TextField).apply {
            bind(
                getString(R.styleable.TextField_hintText),
                getInt(R.styleable.TextField_android_inputType, 0)
            )
        }.recycle()
    }

    private fun bind(hintText: String?, inputType: Int) {
        textFieldInput.imeOptions = EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE
        textFieldInput.inputType = inputType

        textFieldBox.hint = hintText
    }
}