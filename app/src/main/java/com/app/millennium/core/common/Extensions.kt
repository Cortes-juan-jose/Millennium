package com.app.millennium.core.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
fun String?.validatePatternEmail(): Boolean {
    if (this!=null){
        val exp = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar el nombre de usuario
fun String?.validatePatternUsername(): Boolean {
    if (this!=null){
        val exp = "^[A-Za-z0-9_.-]{4,}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar la contraseña
fun String?.validatePatternPassword(): Boolean {
    if (this!=null){
        val exp = "^[A-Za-z0-9!¡#~$%&()¿?*+Ç:;<>=_.-]{8,}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar el telefono
fun String?.validatePatternPhone(): Boolean {
    if (this!=null){
        val exp = "^[0-9]{9}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para aplicar propiedades de error a un text input layout
fun TextInputLayout.applyError(msg: String){

    this.let {
        isHelperTextEnabled = true
        helperText = msg
        boxStrokeColor = Color.RED
        hintTextColor = ColorStateList.valueOf(Color.RED)
        setHelperTextColor(ColorStateList.valueOf(Color.RED))
    }
}

//Extension para aplicar propiedades de error a un text input layout
fun TextInputLayout.removeError(){

    this.let {
        isHelperTextEnabled = false
        boxStrokeColor = Color.rgb(
            Constant.PRIMARY_COLOR_RED,
            Constant.PRIMARY_COLOR_GREEN,
            Constant.PRIMARY_COLOR_BLUE
        )
        hintTextColor =
            ColorStateList.valueOf(
                Color.rgb(
                    Constant.PRIMARY_COLOR_RED,
                    Constant.PRIMARY_COLOR_GREEN,
                    Constant.PRIMARY_COLOR_BLUE
                )
            )
        setHelperTextColor(
            ColorStateList.valueOf(
                Color.rgb(
                    Constant.PRIMARY_COLOR_RED,
                    Constant.PRIMARY_COLOR_GREEN,
                    Constant.PRIMARY_COLOR_BLUE
                )
            )
        )
    }
}

//Funcion de extension para quitar el foco de los campos del registro y cerrar el teclado si esta cerrado
fun Activity.reload(){
    val inputMethodManager : InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view: View? = this.currentFocus
    view?.let {
        it.clearFocus()
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
