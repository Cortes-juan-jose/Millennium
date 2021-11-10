package com.app.millennium.core.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import com.app.millennium.R
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

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

//Extension para verificar si un objeto es nulo
fun Any?.isNull() = this==null

//Extension para verificar que un objeto no sea nulo
fun Any?.isNotNull() = this!=null

//Extension para validar un email
fun String?.validEmail(): Boolean {
    if (this!=null){
        val exp: String = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar el nombre de usuario
fun String?.validUsername(): Boolean {
    if (this!=null){
        val exp: String = "^[A-Za-z0-9_.-]{4,}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar la contraseña
fun String?.validPassword(): Boolean {
    if (this!=null){
        val exp: String = "^[A-Za-z0-9!¡#~$%&()¿?*+Ç:;<>=_.-]{6,}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar el telefono
fun String?.validPhone(): Boolean {
    if (this!=null){
        val exp: String = "^[0-9]{9}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}
