package com.bekmnsrw.fakestore.feature.main.di

import com.bekmnsrw.fakestore.feature.main.data.ProductRepositoryImpl
import com.bekmnsrw.fakestore.feature.main.domain.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module(includes = [MainBindModule::class])
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    fun provideProductRepository(
        firebaseFirestore: FirebaseFirestore
    ): ProductRepository = ProductRepositoryImpl(firebaseFirestore)

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}

@Module
@InstallIn(SingletonComponent::class)
interface MainBindModule {

    @Binds
    fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
