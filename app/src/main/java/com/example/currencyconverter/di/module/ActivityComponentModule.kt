package com.example.currencyconverter.di.module

import com.example.currencyconverter.di.ActivityScope
import com.example.currencyconverter.view.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton
@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityComponentModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ExRatesViewModelModule::class, ExRatesRepositoryModule::class])
    abstract fun providesMainActivity(): MainActivity
}