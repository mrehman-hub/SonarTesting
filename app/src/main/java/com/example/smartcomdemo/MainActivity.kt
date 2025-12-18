package com.example.smartcomdemo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartcomdemo.network.RetrofitClient
import com.example.smartcomdemo.network.models.BaseResponse
import com.example.smartcomdemo.network.models.BaseResponse2
import com.example.smartcomdemo.network.models.RequestBody2
import com.example.smartcomdemo.network.models.RequestBodyData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val layout = findViewById<ConstraintLayout>(R.id.main)

        layout.clipToOutline = true

        findViewById<AppCompatButton>(R.id.showDialog).setOnClickListener {
            val email = findViewById<AppCompatEditText>(R.id).text.toString()
            if (email.isNotEmpty() && email.isNotBlank()) {
                createTokenOnBaseOfEmail(email)
            }
        }
        Log.i("TAG", "onCreate: ${resources.configuration.isScreenRound}")
    }

    private fun createTokenOnBaseOfEmail(email: String) {
        showLoadingDialog()
        RetrofitClient.instance.getUrl(RequestBodyData(email = email))
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    hideLoadingDialog()
                    if (response.isSuccessful) {
                        val url = response.body()?.redirectUrl
                        val token = response.body()?.token?.token
                        if (url != null && token != null) {
                            showWebViewDialog(this@MainActivity, url, token)
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    hideLoadingDialog()
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            })
    }

    private fun onPopupDismiss(request: RequestBody2) {
        showLoadingDialog()
        RetrofitClient.instance.onPopupDismiss(request).enqueue(object : Callback<BaseResponse2> {
            override fun onResponse(call: Call<BaseResponse2>, response: Response<BaseResponse2>) {
                hideLoadingDialog()
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Event: "+ response.body()?.data?.event.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse2>, t: Throwable) {
                hideLoadingDialog()
                runOnUiThread {
                    Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun showWebViewDialog(context: Context, url: String, token: String) {
        val dialog = Dialog(context, R.style.FullScreenDialogTheme)
        dialog.setContentView(R.layout.dialog_webview)
        dialog.setCancelable(false)
        val closeButton = dialog.findViewById<ImageView>(R.id.dialog_button_close)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        val webView = dialog.findViewById<WebView>(R.id.dialogWebView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                Log.i("TAG", "shouldOverrideUrlLoading: ${request?.url.toString()}")
                if (request?.url.toString() == "https://cosmotogether.com/products/jrtrack-5") {
                    request?.url.toString().let {
                        openUrl(it, this@MainActivity)
                        return true
                    }
                }
                else if (request?.url.toString() == "https://parent.cosmotogether.com/web-view/whats-new/finish") {
                    request?.url.toString().let {
                        dialog.dismiss()
                        return true
                    }
                }
                return false
            }
        }
        webView.loadUrl(url)

        dialog.window?.setLayout(
            (context.resources.displayMetrics.widthPixels * 0.9).toInt(),
            (context.resources.displayMetrics.heightPixels * 0.9).toInt()
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialog.setOnDismissListener {
            onPopupDismiss(RequestBody2(token = token, type = "closed"))
        }

    }

    private fun showLoadingDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null)
        loadingDialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()
        loadingDialog?.show()
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }

    fun openUrl(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())

        if (intent.resolveActivity(context.packageManager) != null) {
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Unable to open link", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "No app found to open this link", Toast.LENGTH_SHORT).show()
        }
    }
}