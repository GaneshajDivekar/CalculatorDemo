package calculator.demo.mainmodule.di

import android.content.Context
import android.content.SharedPreferences
import calculator.demo.BuildConfig
import calculator.demo.CalculatorApplications
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {fun getAppRetrofit(): Retrofit
    fun getTokenManager(): TokenManger
    fun getOkHttpClient(): OkHttpClient
    fun getContext(): Context
    fun getSharedPreference(): SharedPreferences
    fun getSharedPreferenceHelper(): SharedPreferenceHelper
    fun inject(calculatorApplications: CalculatorApplications)
}

@Module
class AppModule(private val baseUrl: String, val context: Context) {

    @Singleton
    @Provides
    fun providesOkttp() = OkHttpClient()
        .newBuilder()
        .connectTimeout(1200, TimeUnit.SECONDS)
        .readTimeout(1200, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().serializeNulls().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .build()


    @Singleton
    @Provides
    fun providesTokenManger(context: Context) = TokenManger(context)

    @Provides
    fun provideContext(): Context {
        return this.context
    }

    @Provides
    fun providesGson(): Gson = GsonBuilder().serializeNulls().create()


    @Singleton
    @Provides
    fun providesLoggingInterceptor(tokenManger: TokenManger, gson: Gson) = TokenInterceptor(tokenManger, gson).apply {
        level = if (BuildConfig.DEBUG) TokenInterceptor.Level.BODY else TokenInterceptor.Level.NONE
    }

    @Singleton
    @Provides
    fun providesSharePreference(): SharedPreferences =
        context.getSharedPreferences(CONST_PREF_FILE_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providesSharePreferenceHelper(appContext: Context): SharedPreferenceHelper =
        SharedPreferenceHelper(appContext)

}