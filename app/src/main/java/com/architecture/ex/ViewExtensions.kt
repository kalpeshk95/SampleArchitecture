package com.architecture.ex

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.showSnackBar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    val snackBar = Snackbar.make(this, context.getString(msgId), length)
    snackBar.setAction(context.getString(actionMessageId)) {
        action(this)
    }.show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 1)
    // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
}

fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    format: String,
    minDate: Long,
    maxDate: Long,
    initialCalendar: Calendar
) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat(format, Locale.getDefault())
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            datePickerOnDataSetListener,
            initialCalendar.get(Calendar.YEAR),
            initialCalendar.get(Calendar.MONTH),
            initialCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            datePicker.minDate = minDate
            datePicker.maxDate = maxDate

            show()
        }
    }
}