package com.example.harajtask.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.harajtask.data.Message
import com.example.harajtask.data.get


inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    val adaptiveLength = if (length == Toast.LENGTH_LONG) {
        Toast.LENGTH_LONG
    } else {
        val wordCount = message.split(' ').size
        val charCount = message.length
        if (wordCount > 4 || charCount > 35) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    }
    Toast.makeText(this, message, adaptiveLength).show()
}

fun Context.toast(res: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, res, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    context?.toast(message, length)
}

fun Fragment.toast(message: Message, length: Int = Toast.LENGTH_SHORT) {
    context?.toast(message.get(context!!), length)
}


fun Fragment.toast(res: Int, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(res, length)
}

fun View.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    context.toast(message, length)
}

fun View.show(){
    visibility = View.VISIBLE
}
fun View.hide(){
    visibility = View.GONE
}

fun Fragment.shareText(textToShare: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textToShare)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)

}

