package com.example.currencyconverter.mockapp.di

import com.example.currencyconverter.di.AppComponent
import com.example.currencyconverter.di.module.DatabaseModule
import com.example.currencyconverter.di.module.NetworkModule
import com.example.currencyconverter.di.module.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestAppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ViewModelFactoryModule::class,
        TestActivityComponentModule::class
    ]
)
interface TestComponent : AppComponent {

}