package de.reiss.android.losungen.widget.yearly

import android.content.Context
import android.support.annotation.WorkerThread
import de.reiss.android.losungen.App
import de.reiss.android.losungen.widget.WidgetRemoteViewsFactory

class YearlyWidgetRemoteViewsFactory(context: Context) : WidgetRemoteViewsFactory(context) {

    private val yearlyWidgetTextRefresher: YearlyWidgetTextRefresher by lazy {
        App.component.yearlyWidgetTextRefresher
    }

    @WorkerThread
    override fun loadWidgetText(): String = yearlyWidgetTextRefresher.retrieveCurrentText()

}