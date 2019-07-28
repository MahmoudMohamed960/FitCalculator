package ms.fitcalculator

import android.app.Application
import android.content.Context
import ms.fitcalculator.help.LocalManagerSetting

class MainApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalManagerSetting.onAttach(base))
    }
}