package io.amund.httpwidgets

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import io.amund.httpwidgets.databinding.ActivityConfigureBinding


class ConfigureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigureBinding

    private var icon: Int = R.layout.http_widget_layout

    val PREF_HTTPCALLS = "httpcalls"
    val PREF_ICON = "icon"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setResult(RESULT_CANCELED)

        binding = ActivityConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val fname = "button_$appWidgetId"
        val prefs = getSharedPreferences(fname, MODE_PRIVATE)

        binding.textfield.text.clear()
        binding.textfield.text.insert(0, prefs.getString(PREF_HTTPCALLS, ""))

        icon = prefs.getInt(PREF_ICON, R.drawable.ic_http_black_24dp)
        binding.icon.setImageResource(icon)
        binding.icon.setOnClickListener {
            IconDialog { i: Int? ->
                i?.let {
                    icon = i
                    binding.icon.setImageResource(i)
                }
            }.show(supportFragmentManager, "icon")
        }

        binding.fab.setOnClickListener {
            prefs.edit()
                .putString(PREF_HTTPCALLS, binding.textfield.text.toString())
                .putInt(PREF_ICON, icon)
                .apply()

            val views = RemoteViews(applicationContext.packageName, R.layout.http_widget_layout)
                .apply{
                    setImageViewResource(R.id.button, icon)
                }

            AppWidgetManager
                .getInstance(applicationContext)
                .partiallyUpdateAppWidget(appWidgetId, views)

            setResult(Activity.RESULT_OK,
                Intent()
                    .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            )

            finish()
        }
    }
}
