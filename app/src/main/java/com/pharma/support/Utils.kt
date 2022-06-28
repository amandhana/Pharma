package com.pharma.support

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Html
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import cc.cloudist.acplibrary.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.pharma.R
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern


class Utils {
    companion object {
        fun hideStatusBar(context: Activity) {
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.window.insetsController?.hide(WindowInsets.Type.statusBars())
            } else {
                context.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }

        /*fun saveLoginUser(context: Activity?, loginResponse: LoginResponse?) {
            val gson = Gson()
            val preference = Preference().getInstance(context!!)
            preference!!.putString("loginUser", gson.toJson(loginResponse))
        }*/

        /* fun saveProfileDetail(context: Activity?, success: Success?) {
             val gson = Gson()
             val preference: Preference = Preference.getInstance(context)
             preference.putString("profile_detail", gson.toJson(success))
         }*/

        /*fun getSaveLoginUser(context: Activity?): LoginResponse? {
            val preference = Preference().getInstance(context!!)
            val resposne = preference!!.getString("loginUser")
            if (!resposne.isNullOrEmpty())
                return getObject(
                    resposne,
                    LoginResponse::class.java
                ) as LoginResponse
            else return null
        }*/

        /* fun getProfileDetail(context: Activity?): Success? {
             val preference: Preference = Preference.getInstance(context)
             val resposne = preference.getString("profile_detail")
             return com.urgcare.support.Utils.getObject(resposne, Success::class.java) as Success
         }*/

        /*fun getUserId(context: Activity?): String? {
            try {
                val preference = Preference().getInstance(context!!)
                val resposne = preference!!.getString("loginUser")
                if (!resposne.isNullOrEmpty()) {
                    val loginResponse: LoginResponse = getObject(
                        resposne,
                        LoginResponse::class.java
                    ) as LoginResponse
                    return loginResponse.getResults()!!.getId()
                } else return ""
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }

        fun getImage(context: Activity?): String? {
            val preference = Preference().getInstance(context!!)
            val resposne = preference!!.getString("loginUser")
            val loginResponse: LoginResponse = getObject(
                resposne!!,
                LoginResponse::class.java
            ) as LoginResponse
            return loginResponse.getResults()!!.getImage()
        }*/

        fun startActivityFinish(activity: Activity, aClass: Class<*>?) {
            activity.startActivity(Intent(activity, aClass))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            activity.finish()
        }

        fun startActivity(activity: Activity, aClass: Class<*>?) {
            activity.startActivity(Intent(activity, aClass))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun getImageUri(context: Activity, inImage: Bitmap): Uri? {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path =
                MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
            return Uri.parse(path)
        }

        fun clearGlide(context: Activity?, imageView: ImageView?) {
            Glide.with(context!!)
                .clear(imageView!!)
        }

        fun setProfileImageUsingGlide(context: Activity?, url: String?, imageView: ImageView?) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
            Glide.with(context!!)
                .load(url)
                .apply(options)
                .into(imageView!!)
        }


        private var acpProgressFlower: ACProgressFlower? = null

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        fun showProgressBar(activity: Activity?) {
            try {
                hideProgressBar()
                acpProgressFlower = ACProgressFlower.Builder(activity)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(ContextCompat.getColor(activity!!, R.color.white))
                    .text("Please wait")
                    .textColor(ContextCompat.getColor(activity, R.color.white))
                    .bgColor(ContextCompat.getColor(activity, R.color.black))
                    .textSize(30).build()
                acpProgressFlower!!.setCanceledOnTouchOutside(false)
                acpProgressFlower!!.setCancelable(false)
                acpProgressFlower!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun setHtmlText(text: String?, textView: TextView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
            } else {
                textView.text = Html.fromHtml(text)
            }
        }

        fun hideProgressBar() {
            if (acpProgressFlower != null && acpProgressFlower!!.isShowing()) {
                acpProgressFlower!!.dismiss()
            }
        }

        fun setImageUsingGlide(context: Activity?, url: String?, imageView: ImageView?) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_app_icon)
                .error(R.drawable.ic_app_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
            Glide.with(context!!)
                .load(url)
                .apply(options)
                .into(imageView!!)
        }

        fun hideKeyboard(context: Activity) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = context.currentFocus
            if (view == null)
                view = View(context)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showKeyboard(context: Activity) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = context.currentFocus
            if (view == null)
                view = View(context)
            imm.showSoftInput(view, 0)
        }

        fun isNetworkAvailable(context: Activity): Boolean {
            val connectivityManager =
                context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
            return false
        }

        var progressDialog: ProgressDialog? = null
        fun showCustomProgressDialog(context: Activity, message: String?) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#03dac5")))
            val spannableString = SpannableString(message)
            spannableString.setSpan(RelativeSizeSpan(1.5f), 0, spannableString.length, 0)
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#ffffff")),
                0,
                spannableString.length,
                0
            )
            progressDialog!!.setMessage(spannableString)
            progressDialog!!.setCanceledOnTouchOutside(false)
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            Objects.requireNonNull(progressDialog)?.show()
        }

        fun printLog(message: String?) {
            println(message)
        }

        fun toBase64(message: String): String? {
            val data: ByteArray
            data = message.toByteArray(StandardCharsets.UTF_8)
            return Base64.encodeToString(data, Base64.NO_WRAP)
        }

        private var progressDialogCommonForAll: ProgressDialog? = null
        fun showCustomProgressDialogCommonForAll(context: Activity, message: String?) {
            progressDialogCommonForAll = ProgressDialog(context)
            val spannableString = SpannableString(message)
            spannableString.setSpan(RelativeSizeSpan(1.3f), 0, spannableString.length, 0)
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#000000")),
                0,
                spannableString.length,
                0
            )
            progressDialogCommonForAll!!.setMessage(spannableString)
            progressDialogCommonForAll!!.setCancelable(false)
            try {
                if (progressDialogCommonForAll != null) progressDialogCommonForAll!!.show()
            } catch (e: java.lang.Exception) {
            }
        }

        fun hideCustomProgressDialogCommonForAll() {
            try {
                if (progressDialogCommonForAll != null) {
                    progressDialogCommonForAll!!.dismiss()
                }
            } catch (e: Exception) {
            }
        }



        fun hideCustomProgressDialog() {
            if (progressDialog != null && progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }

        fun isValidAadhar(aadhaarStr: String): Boolean {
            val PATTERN = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$"
            val pattern = Pattern.compile(PATTERN)
            val matcher = pattern.matcher(aadhaarStr)
            return matcher.matches()
        }

        fun isValidEmail(email: String?): Boolean {
            val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun getObject(name: String, aClass: Class<*>): Any {
            val gson = Gson()
            return gson.fromJson(name, aClass)
        }

        fun printLog(key: String, value: String) {
            if (BuildConfig.DEBUG) {
                Log.e(key, value)
            }
        }

        var dialog: Dialog? = null
        fun showToastPopup(context: Activity, message: String) {
            try {
                if (dialog == null)
                    dialog = Dialog(context)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.setContentView(R.layout.toast_popup)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setCanceledOnTouchOutside(false)
                val tvMessage = dialog!!.findViewById<TextView>(R.id.message_tv)
                val okBtn = dialog!!.findViewById<TextView>(R.id.ok_btn)
                tvMessage.text = message
                okBtn.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        dialog!!.dismiss()
                        dialog = null
                    }
                })
                dialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
}