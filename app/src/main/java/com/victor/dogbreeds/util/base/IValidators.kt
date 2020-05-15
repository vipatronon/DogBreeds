package com.victor.dogbreeds.util.base

import android.app.Activity
import android.text.TextWatcher
import com.victor.dogbreeds.ui.common.TextField

interface IValidators {
    fun addValidator(activity: Activity, callback: (text: String) -> Unit): TextWatcher
    fun addDateMask(textField: TextField): TextWatcher
    fun validateTextBirthDate(birthdate: String): Boolean
    fun validateTextEmail(email: String): Boolean
    fun validateTextLength(text: String, comparer: Int): Boolean
}