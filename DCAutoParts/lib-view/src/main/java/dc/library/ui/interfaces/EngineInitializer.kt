package dc.library.ui.interfaces

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import dc.library.ui.base.Engine
import dc.library.ui.base.app

internal class EngineInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        app = context
        (app as? Application)?.registerActivityLifecycleCallbacks(Engine.activityCallbacks)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}