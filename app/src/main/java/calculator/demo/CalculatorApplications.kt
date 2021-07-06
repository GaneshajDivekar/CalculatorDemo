package calculator.demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import calculator.demo.mainmodule.di.AppComponent
import calculator.demo.mainmodule.di.AppModule
import calculator.demo.mainmodule.di.DaggerAppComponent
import calculator.demo.mainmodule.di.TokenManger
import retrofit2.Retrofit
import javax.inject.Inject

class CalculatorApplications : MultiDexApplication() {

    init {
        instance = this
    }

    companion object {

        private var instance: CalculatorApplications? = null

        @Synchronized
        fun applicationContext(): CalculatorApplications {
            return instance as CalculatorApplications
        }
    }

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var tokenManger: TokenManger

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var mRedirectionBundle: Bundle? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule("https://beta.reflexion.ai/mobile/api/", this))
            .build()
        appComponent.inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}