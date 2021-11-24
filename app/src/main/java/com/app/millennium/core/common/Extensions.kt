package com.app.millennium.core.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.app.millennium.core.utils.FileUtil
import com.app.millennium.data.model.Product
import com.app.millennium.data.model.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File
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


//Extension para validar el nombre de usuario
fun String?.isUsername(): Boolean {
    if (this.isNotNull()){
        return this?.length!! >= 4
    }
    return false
}

//Extension para validar un email
fun String?.isEmail(): Boolean {
    if (this!=null){
        val exp = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar el telefono
fun String?.isPhone(): Boolean {
    if (this!=null){
        val exp = "^[0-9]{9}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar la contraseña
fun String?.isPassword(): Boolean {
    if (this!=null){
        val exp = "^[A-Za-z0-9!¡#~$%&()¿?*+Ç:;<>=_.-]{8,}$"
        val pattern: Pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(this)
        return matcher.matches()
    }
    return false
}

//Extension para validar el titulo y/o la descripcion del producto
fun String?.isTitleOrDescription(): Boolean {
    if (this.isNotNull()){
        return this?.length!! >= 10
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

//Funcion de extension para comprobar si un tipo de fichero es permitido
fun File.permitted(): Boolean{
    return when (FileUtil.splitFileName(this.name)[1].toString()) {
        Constant.FORMAT_JPEG -> true
        Constant.FORMAT_JPG -> true
        Constant.FORMAT_PNG -> true
        else -> false
    }
}

/**
 * Funcion de extension que lanza un evento después de introducir
 * texto en un edittext y lo retorna este valor en una lambda
 */
fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

/**
 * Extension para construir un objeto user a partir de un map de propiedades
 */
fun Map<String, Any>?.convertUser(): User {

    val user = User()
    this?.get(Constant.PROP_ID_USER)?.let { user.id = it.toString() }
    this?.get(Constant.PROP_USERNAME_USER)?.let { user.name = it.toString() }
    this?.get(Constant.PROP_EMAIL_USER)?.let { user.email = it.toString() }
    this?.get(Constant.PROP_PHONE_USER)?.let { user.phone = it.toString() }
    this?.get(Constant.PROP_UPLOADED_PRODUCTS_USER)?.let { user.uploadedProducts = it.toString().toInt() }
    this?.get(Constant.PROP_OPINIONS_USER)?.let { user.opinions = it.toString().toInt() }
    this?.get(Constant.PROP_IMG_PROFILE_USER)?.let { user.imgProfile = it.toString() }
    this?.get(Constant.PROP_IMG_COVER_USER)?.let { user.imgCover = it.toString() }
    this?.get(Constant.PROP_TIMESTAMP_USER)?.let { user.timestamp = it.toString().toLong() }

    return user
}

/**
 * Extension para construir un objeto product a partir de un map de propiedades
 */
fun Map<String, Any>?.converProduct(): Product {

    val product = Product()
    this?.get(Constant.PROP_ID_PRODUCT)?.let { product.id = it.toString() }
    this?.get(Constant.PROP_ID_USER_PRODUCT)?.let { product.idUser = it.toString() }
    this?.get(Constant.PROP_TITLE_PRODUCT)?.let { product.title = it.toString() }
    this?.get(Constant.PROP_DESCRIPTION_PRODUCT)?.let { product.description = it.toString() }
    this?.get(Constant.PROP_CATEGORY_PRODUCT)?.let { product.category = it.toString() }
    this?.get(Constant.PROP_PRICE_PRODUCT)?.let { product.price = it.toString().toDouble() }
    this?.get(Constant.PROP_NEGOTIABLE_PRODUCT)?.let { product.negotiable = it.toString() }
    this?.get(Constant.PROP_IMAGE1_PRODUCT)?.let { product.image1 = it.toString() }
    this?.get(Constant.PROP_IMAGE2_PRODUCT)?.let { product.image2 = it.toString() }
    this?.get(Constant.PROP_IMAGE3_PRODUCT)?.let { product.image3 = it.toString() }
    this?.get(Constant.PROP_IMAGE4_PRODUCT)?.let { product.image4 = it.toString() }
    this?.get(Constant.PROP_TIMESTAMP_PRODUCT)?.let { product.timestamp = it.toString().toLong() }

    return product
}
