package com.emart24.service

import dagger.Module
import dagger.Provides

@Module
class GoodsModule {

    @Provides
    fun provideFirebaseService(): GoodsService {
        return GoodsService()
    }

    @Provides
    fun provideQRService(): QRService {
        return QRService()
    }

}