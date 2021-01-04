@file:Suppress("DEPRECATION")

package com.example.insurismoviesproject.utill

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.os.Environment
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.insurismoviesproject.R
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}

fun error(message: String) {

}


object AppUtil {

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }




    fun chkStatus(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (connMgr.activeNetworkInfo != null
                && connMgr.activeNetworkInfo!!.isAvailable
                && connMgr.activeNetworkInfo!!.isConnected)
    }

    @Suppress("unused")
    fun showAlert(msg: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(context.getString(R.string.app_name)).setMessage(msg)
            .setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    @Suppress("unused")
    fun sop(msg: String, tag: String) {
        println("$tag--------$msg")
    }

    fun hideKeyBoard(context: Context) {
        val view = (context as Activity).currentFocus
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun createProgressDialog(mContext: Context): ProgressDialog {
        val dialog = ProgressDialog(mContext)
        try {
            dialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }

        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.transparentprogresdialog)
        return dialog
    }

    fun createVideoProgressDialog(mContext: Context): ProgressDialog {
        val dialog = ProgressDialog(mContext)
        try {
            dialog.show()
        } catch (e: WindowManager.BadTokenException) {
            e.printStackTrace()
        }

        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.transparentprogresdialog)
        return dialog
    }

    fun isEmailValid(email: String?): Boolean {
        var isValid = false
        val expression = """^[\w.-]+@([\w\-]+\.)+[A-Z]{2,4}$"""
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun isPhoneValid(strPhone: String?): Boolean {
        return if (strPhone!!.contains("00000000")) {
            false
        } else Patterns.PHONE.matcher(strPhone).matches()
    }

    fun convertDate(dateStr: String): String {
        var startDate = ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        // val dateFormat2 = SimpleDateFormat("EEE, d MMM yyyy HH:mm a", Locale.US)
        val dateFormat2 = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US)
        // val dateFormat2 = SimpleDateFormat("MM/dd/yyy hh:mm a", Locale.US)


        try {
            val date = dateFormat.parse(dateStr)
            startDate = dateFormat2.format(date)

            return startDate

        } catch (e: Exception) {
            startDate = dateStr
            return startDate
        }

    }

    fun convertDate2(dateStr: String): String {
        var startDate = ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        // val dateFormat2 = SimpleDateFormat("EEE, d MMM yyyy HH:mm a", Locale.US)
        val dateFormat2 = SimpleDateFormat("MMM dd, hh:mm a", Locale.US)
        // val dateFormat2 = SimpleDateFormat("MM/dd/yyy hh:mm a", Locale.US)


        try {
            val date = dateFormat.parse(dateStr)
            startDate = dateFormat2.format(date)
            //var now = System.currentTimeMillis();
            // startDate= DateUtils.getRelativeTimeSpanString(date.getTime(), now, DateUtils.DAY_IN_MILLIS).toString();
            // var startDate2= DateUtils.formatElapsedTime(date.getTime()).toString();

            return startDate

        } catch (e: Exception) {
            startDate = dateStr
            return startDate
        }

    }

    fun onDispalySnack(view: View, toastString: String, context: Context) {
        try {
            val snack = Snackbar.make(view, toastString, Snackbar.LENGTH_LONG)
            snack.show()
        } catch (e: Throwable) {
            try {
                Toast.makeText(context, "" + toastString, Toast.LENGTH_SHORT).show()
            } catch (ee: Exception) {
                e.printStackTrace()
            }
        }

    }


    fun compressImage(imagePath: String, context: Context): String {

        var scaledBitmap: Bitmap? = null
        val options: BitmapFactory.Options = BitmapFactory.Options()
        //      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(imagePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        //      max Height and width values of the compressed image is taken as 816x612

        val maxHeight = 816.0f
        val maxWidth = 612.0f
        var imgRatio = 1.0f
        try {
            imgRatio = (actualWidth / actualHeight).toFloat()
        } catch (e: Exception) {
        }
        val maxRatio = maxWidth / maxHeight

        //      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = (maxHeight / actualHeight)
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = (maxWidth / actualWidth)
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()

            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        //      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false

        //      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        val buffer = ByteArray(16 * 1024)
        options.inTempStorage = buffer

        try {
            //          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(imagePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bmp,
            middleX - bmp.getWidth() / 2,
            middleY - bmp.getHeight() / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )

//      check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(imagePath)
            val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()

            when (orientation) {
                6 -> matrix.postRotate(90F)
                3 -> matrix.postRotate(180F)
                8 -> matrix.postRotate(270F)
            }

            scaledBitmap =
                Bitmap.createBitmap(
                    scaledBitmap,
                    0,
                    0,
                    scaledBitmap!!.width,
                    scaledBitmap.height,
                    matrix,
                    true
                )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val out: FileOutputStream?
        val filename: String = getFilename(context)
        try {
            out = FileOutputStream(filename)
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return filename


    }

    fun getFilename(context: Context): String {
        val file =
            File(
                Environment.getExternalStorageDirectory().path,
                context.getString(R.string.app_name) + "/Images"
            )
        if (!file.exists()) {
            file.mkdirs()
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg")
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }


}
