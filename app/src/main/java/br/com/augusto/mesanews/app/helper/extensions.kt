package br.com.augusto.mesanews.app.helper

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Activity.toast(@StringRes errorString: Int) {
    Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(errorString: String) {
    Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
}

fun View.isVisible(visible: Boolean) {
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}

fun <T> Any.toJson(type: Class<T>): String {
    val moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
    return jsonAdapter.toJson(this as T)
}

fun AppCompatActivity.startDialog(dialogFragment: DialogFragment) {
    val fm = supportFragmentManager
    val yourDialog = dialogFragment
    yourDialog.show(fm, "dialog")
}

fun Fragment.startDialog(dialogFragment: DialogFragment) {
    val fm = activity?.supportFragmentManager
    val yourDialog = dialogFragment
    yourDialog.setTargetFragment(this, 2121)
    if (fm != null) {
        yourDialog.show(fm, "dialog")
    }
}