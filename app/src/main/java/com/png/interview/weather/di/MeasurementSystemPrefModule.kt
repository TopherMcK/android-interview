package com.png.interview.weather.di

import com.png.interview.weather.domain.*
import dagger.Binds
import dagger.Module

@Module
abstract class MeasurementSystemPrefModule {

    @Binds
    abstract fun bindGetMeasurementSystemPrefDataUseCase(getMeasurementSystemPrefDataUseCase: DefaultGetMeasurementSystemPrefDataUseCase): GetMeasurementSystemPrefDataUseCase

    @Binds
    abstract fun bindCreateGetMeasurementSystemPrefFromSettingUseCase(createGetMeasurementSystemPrefFromSettingUseCase: DefaultCreateGetMeasurementSystemPrefFromSettingUseCase): CreateGetMeasurementSystemPrefFromSettingUseCase

    @Binds
    abstract fun bindSetMeasurementSystemPrefDataUseCase(setMeasurementSystemPrefDataUseCase: DefaultSetMeasurementSystemPrefDataUseCase): SetMeasurementSystemPrefDataUseCase

    @Binds
    abstract fun bindCreateSetMeasurementSystemPrefFromSettingUseCase(createSetMeasurementSystemPrefFromSettingUseCase: DefaultCreateSetMeasurementSystemPrefFromSettingUseCase): CreateSetMeasurementSystemPrefFromSettingUseCase

}