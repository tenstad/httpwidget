package io.amund.httpwidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.widget.Button
import android.widget.ImageButton
import android.widget.RemoteViews

internal class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val views: RemoteViews = RemoteViews(
                context.packageName,
                R.layout.http_widget_layout
            ).apply {
                setOnClickPendingIntent(R.id.button, widgetIntent(context, appWidgetId))
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    fun widgetIntent(context: Context, appWidgetId: Int) : PendingIntent =
         PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, ClickReceiver::class.java)
                .setAction("io.amund.httpwidgets.CLICK")
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}
