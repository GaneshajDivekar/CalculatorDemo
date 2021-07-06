package calculator.demo.mainmodule.di

import javax.inject.Scope


interface ViewModelContract<T> {
    fun getViewModel(): T
}

interface ItemClickCallback {
    fun onClicked(albumId: Int)
}

interface HomeListItemType {
    fun getType(): Int
}

@Scope
@Retention()
annotation class ActivityScope

@Scope
@Retention()
annotation class FragmentScope