package com.bancom.data.network

import com.bancom.data.datasource.users.UsersApi
import com.bancom.data.datasource.users.UsersNetwork
import com.bancom.domain.repository.users.IUsersNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    fun provideUsersNetwork(api: UsersApi): IUsersNetworkRepository {
        return UsersNetwork(api)
    }
}
