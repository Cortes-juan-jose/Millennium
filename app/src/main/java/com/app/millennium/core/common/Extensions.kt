package com.app.millennium.core.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

//Extension para construir toast
fun Activity.toast(
    text: String,
    lenght:Int = Toast.LENGTH_SHORT
){
    Toast.makeText(this, text, lenght).show()
}

//Extension para navegar a una activity
inline fun <reified T : Activity> Context.openActivity(noinline extra: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}

//Entension para verificar si un objeto es nulo
fun Any?.isNull() = this==null