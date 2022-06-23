package com.example.currencyconverter.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.base.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {
    @Singleton
    @Binds
    abstract fun providesViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}