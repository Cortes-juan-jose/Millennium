package com.app.millennium.app

import android.app.Application
import android.os.Environment

class MillenniumApp : Application() {

    override fun onCreate() {
        super.onCreate()
        deleteFilesPictureDir()
    }

    /**
     * Metodo para borrar todos los archivos que se han creado de la cámara
     */
    private fun deleteFilesPictureDir() {
        val picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val files = picturesDir?.listFiles()
        if (files!!.isNotEmpty()){
            files.forEach {
                it?.delete()
            }
        }
    }
}