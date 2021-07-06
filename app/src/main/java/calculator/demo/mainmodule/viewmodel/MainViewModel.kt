package calculator.demo.mainmodule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import calculator.demo.mainmodule.repository.MainViewModelRepository

class MainViewModel (private val mainViewModelRepository: MainViewModelRepository)  : ViewModel() {

    class OurOnboardViewModelFactory(private val mainViewModelRepository: MainViewModelRepository) :
        ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(mainViewModelRepository) as T
        }
    }
}