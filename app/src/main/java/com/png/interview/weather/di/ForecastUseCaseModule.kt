package com.png.interview.weather.di

import com.png.interview.weather.domain.*
import dagger.Binds
import dagger.Module

@Module
abstract class ForecastUseCaseModule {

    @Binds
    abstract fun bindGetForecastDataUseCase(usecase: DefaultGetForecastDataUseCase): GetForecastDataUseCase

    @Binds
    abstract fun bindGetThreeDayForecastRepFromQueryUseCase(usecase: DefaultCreateThreeDayForecastRepFromQueryUseCase): CreateThreeDayForecastRepFromQueryUseCase
}