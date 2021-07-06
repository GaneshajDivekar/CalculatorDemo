package calculator.demo.mainmodule.di

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import calculator.demo.MainActivity
import dagger.Component
import dagger.Module

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [MainActivityModule::class])
interface MainActivityCompoment {
    fun inject(mainActivity: MainActivity)
    fun getSharedPreference(): SharedPreferences
    fun getSharedPreferenceHelper(): SharedPreferenceHelper
}
@Module
class MainActivityModule(private val activity: AppCompatActivity)
{



}