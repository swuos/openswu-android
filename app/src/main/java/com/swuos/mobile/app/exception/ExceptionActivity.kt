package com.swuos.mobile.app.exception

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.swuos.mobile.app.App
import com.swuos.mobile.utils.lgE
import java.io.ByteArrayOutputStream
import java.io.PrintStream

/**
 * 异常捕获页面
 * Created by wangyu on 2017/7/25.
 */
class ExceptionActivity: AppCompatActivity() {

    private lateinit var mExceptionView: TextView

    companion object {
        private val TAG: String = ExceptionActivity::class.java.simpleName
        fun showException(throwable: Throwable) {
            val app : App? = App.getInstance()
            if (app != null && App.isDebug()) {
                val byteArrayOutputStream = ByteArrayOutputStream()
                throwable.printStackTrace(PrintStream(byteArrayOutputStream))
                val msg = String(byteArrayOutputStream.toByteArray())

                try {
                    val intent = Intent(app, ExceptionActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("msg", msg)
                    app.startActivity(intent)
                } catch (e : Exception) {
                    lgE(TAG, "异常捕获未在manifest中声明")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scrollView = ScrollView(this)
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        scrollView.addView(linearLayout)
        setContentView(scrollView)
        mExceptionView = TextView(this)
        linearLayout.addView(mExceptionView)
        mExceptionView.setTextColor(Color.RED)
        handlerIntent(intent, false)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handlerIntent(intent, true)
    }

    private fun handlerIntent(intent: Intent?, isNew: Boolean) {
        val msg : String? = intent!!.getStringExtra("msg") ?: return
        if (isNew) {
            mExceptionView.append("\n\n\n\n\n")
        }
        mExceptionView.append(msg)

    }
}
