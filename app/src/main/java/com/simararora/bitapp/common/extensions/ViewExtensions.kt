package com.simararora.bitapp.common.extensions

import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.StyleRes
import com.google.android.material.snackbar.Snackbar

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun TextView.setTextStyle(@StyleRes styleRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(styleRes)
    } else {
        setTextAppearance(context, styleRes)
    }
}

fun View.showSnackBar(
    message: String,
    action: String? = null,
    actionHandler: (() -> Unit)? = null
) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        if (action != null) {
            setAction(action) { actionHandler?.invoke() }
        }
    }.show()
}

inline fun <reified T> Spinner.setOnItemSelectedListener(
    crossinline onItemSelected: (T) -> Unit
) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            adapterView: AdapterView<*>,
            childView: View,
            position: Int,
            id: Long
        ) {
            val item = adapterView.adapter.getItem(position) as T
            onItemSelected.invoke(item)
        }

        override fun onNothingSelected(adapterView: AdapterView<*>) {
        }
    }
}
