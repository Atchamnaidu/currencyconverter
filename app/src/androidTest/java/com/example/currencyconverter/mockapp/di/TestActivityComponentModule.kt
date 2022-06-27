package com.example.currencyconverter.mockapp.di

import com.example.currencyconverter.di.ActivityScope
import com.example.currencyconverter.di.module.ExRatesViewModelModule
import com.example.currencyconverter.view.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class TestActivityComponentModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ExRatesViewModelModule::class, TestExRatesRepositoryModule::class])
    abstract fun providesMainActivity(): MainActivity
}