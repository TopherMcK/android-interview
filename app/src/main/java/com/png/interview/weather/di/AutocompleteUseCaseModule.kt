package com.png.interview.weather.di

import com.png.interview.weather.domain.CreateAutocompleteRepFromQueryUseCase
import com.png.interview.weather.domain.DefaultCreateAutocompleteRepFromQueryUseCase
import com.png.interview.weather.domain.DefaultGetAutocompleteDataUseCase
import com.png.interview.weather.domain.GetAutocompleteDataUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AutocompleteUseCaseModule {

    @Binds
    abstract fun bindsGetAutocompleteDataUseCase(usecase: DefaultGetAutocompleteDataUseCase): GetAutocompleteDataUseCase

    @Binds
    abstract fun bindsCreateAutocompleteRepFromQueryUseCase(usecase: DefaultCreateAutocompleteRepFromQueryUseCase): CreateAutocompleteRepFromQueryUseCase
}