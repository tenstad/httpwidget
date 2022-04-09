package io.amund.httpwidgets

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

internal class ClickReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val PREF_HTTPCALLS = "httpcalls"

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val fname = "button_$appWidgetId"

        context
            ?.getSharedPreferences(fname, AppCompatActivity.MODE_PRIVATE)
            ?.getString(PREF_HTTPCALLS, "")
            ?.split("\n")
            ?.filter { it.isNotEmpty() }
            ?.let {
                val queue = Volley.newRequestQueue(context)
                it.forEach { url ->
                    queue.add(
                        StringRequest(
                            Request.Method.GET,
                            url,
                            {},
                            {
                                Toast
                                    .makeText(context, "err: $url", Toast.LENGTH_LONG)
                                    .show()
                            }
                        )
                    )
                }
            }
    }
}
