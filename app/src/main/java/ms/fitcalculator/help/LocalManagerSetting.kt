package ms.fitcalculator.help

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LocalManagerSetting {

    companion object {
        val LANGUAGE_KEY = "language"
        var sharedPref: SharedPrefHelper? = null

        fun onAttach(context: Context): Context {
            var lang = getCurrentLanguage(context)
            return setLocal(context, lang)
        }

        fun setLocal(context: Context, language: String): Context {
            saveCurrentLanguage(context, language)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return updateResources(context, language);
            }
            return updateResourceLegacy(context, language);
        }

        fun saveCurrentLanguage(context: Context, language: String) {
            sharedPref!!.save(LANGUAGE_KEY, language)
        }

        fun getCurrentLanguage(context: Context): String {
            if(sharedPref!=null) {
                return sharedPref!!.getValueString(LANGUAGE_KEY)
            }
            else
            {
                sharedPref=SharedPrefHelper(context)
                var lang=Locale.getDefault().language;
                LocalManagerSetting.saveCurrentLanguage(context,lang)
                return sharedPref!!.getValueString(LANGUAGE_KEY)
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun updateResources(context: Context, language: String): Context {
            var locale = Locale(language)
            Locale.setDefault(locale)
            var configuration: Configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        @Suppress("DEPRECATION")
        fun updateResourceLegacy(context: Context, language: String): Context {
            var locale = Locale(language)
            Locale.setDefault(locale)
            var resource = context.resources
            var configuration: Configuration = resource.configuration
            configuration.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale);
            }
            resource.updateConfiguration(configuration, resource.getDisplayMetrics());
            return context
        }

    }


}