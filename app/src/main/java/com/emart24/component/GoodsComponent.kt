package com.emart24.component

import com.emart24.*
import com.emart24.service.GoodsModule
import dagger.Component

@Component(modules = [GoodsModule::class])
interface GoodsComponent {
    fun inject(activity: InputActivity)
    fun inject(activity: ScanQRActivity)
    fun inject(activity: GoodsResultActivity)
    fun inject(activity: CreateQRActivity)
    fun inject(activity: ProductListActivity)
}