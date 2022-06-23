package com.example.currencyconverter.di

import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelFactoryModule::class,
        ActivityComponentModule::class]
)
interface AppComponent : AndroidInjector<CurrencyConverterApplication> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: CurrencyConverterApplication): Builder
    }
}