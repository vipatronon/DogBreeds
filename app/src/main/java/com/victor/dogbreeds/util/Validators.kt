package com.victor.dogbreeds.util

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import com.victor.dogbreeds.ui.common.TextField
import com.victor.dogbreeds.util.base.IValidators
import kotlinx.android.synthetic.main.text_field.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import kotlin.concurrent.schedule

class Validators: KoinComponent, IValidators {
    val context: Context by inject()

    override fun addValidator(activity: Activity, callback: (text: String) -> Unit): TextWatcher {
        return object : TextWatcher {
            private var timer = Timer()

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer.cancel()
            }

            override fun afterTextChanged(string: Editable) {
                timer = Timer()

                timer.schedule(1000) {
                    activity.runOnUiThread {
                        callback(string.toString())
                    }
                }
            }
        }
    }

    override fun addDateMask(textField: TextField): TextWatcher {
        return object : TextWatcher {
            var edited = false
            val dividerCharacter = "/"

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (edited) {
                    edited = false
                    return
                }

                var working = getEditText()

                working = manageDateDivider(working, 2, start, before)
                working = manageDateDivider(working, 5, start, before)

                edited = true
                textField.textFieldInput.setText(working)
                textField.textFieldInput.setSelection(textField.textFieldInput.text!!.length)
            }

            private fun manageDateDivider(
                working: String,
                position: Int,
                start: Int,
                before: Int
            ): String {
                if (working.length == position) {
                    return if (before <= position && start < position)
                        working + dividerCharacter
                    else
                        working.dropLast(1)
                }
                return working
            }

            private fun getEditText(): String {
                return if (textField.textFieldInput.text!!.length >= 10)
                    textField.textFieldInput.text.toString().substring(0, 10)
                else
                    textField.textFieldInput.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        }
    }

    override fun validateTextBirthDate(birthdate: String): Boolean{
        var isValid = validateTextLength(birthdate, 10)

        if (!isValid) return false

        val pattern =
            """(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[13-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})${'$'}|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))${'$'}|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})""".toRegex()

        isValid = birthdate.matches(pattern)

        if (!isValid) return false

        return true
    }

    override fun validateTextEmail(email: String): Boolean {
        val pattern =
            """(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""".toRegex()

        return email.toLowerCase(Locale.getDefault()).matches(pattern)
    }

    override fun validateTextLength(text: String, comparer: Int): Boolean {
        if (text.length < comparer) return false

        return true
    }
}