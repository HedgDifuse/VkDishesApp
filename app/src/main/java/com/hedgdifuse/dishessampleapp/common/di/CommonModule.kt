package com.hedgdifuse.dishessampleapp.common.di

import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
class CommonModule {

    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

    @Provides
    @Named(HAS_NETWORK_INJECT_NAME)
    fun provideHasNetwork(context: Application): () -> Boolean {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        return {
            wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED // Check only if wifi is enabled and scanning
        }
    }

    companion object {
        const val HAS_NETWORK_INJECT_NAME = "has_network"
    }
}
