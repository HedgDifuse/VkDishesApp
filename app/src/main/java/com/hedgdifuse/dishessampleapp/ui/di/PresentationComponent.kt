package com.hedgdifuse.dishessampleapp.ui.di

import com.hedgdifuse.dishessampleapp.common.di.CommonModule
import com.hedgdifuse.dishessampleapp.domain.di.DomainModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module(includes = [DomainModule::class, CommonModule::class])
class PresentationComponent
