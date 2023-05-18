package com.example.features.sharedfinance.di

import com.example.core.network.RetrofitClient
import com.example.features.sharedfinance.home.categories.data.CategoriesApi
import com.example.features.sharedfinance.home.categories.data.CategoriesRepositoryImpl
import com.example.features.sharedfinance.home.categories.domain.CategoriesRepository
import com.example.features.sharedfinance.home.journal.data.JournalApi
import com.example.features.sharedfinance.home.journal.data.JournalRepositoryImpl
import com.example.features.sharedfinance.home.journal.domain.JournalRepository
import com.example.features.sharedfinance.home.settings.data.SettingsApi
import com.example.features.sharedfinance.home.settings.data.SettingsRepositoryImpl
import com.example.features.sharedfinance.home.settings.domain.SettingsRepository
import com.example.features.sharedfinance.invitations.data.InvitationsApi
import com.example.features.sharedfinance.invitations.data.InvitationsRepositoryImpl
import com.example.features.sharedfinance.invitations.domain.InvitationsRepository
import com.example.features.sharedfinance.list_journals.data.ListJournalsApi
import com.example.features.sharedfinance.list_journals.data.ListJournalsRepositoryImpl
import com.example.features.sharedfinance.list_journals.domain.ListJournalsRepository
import com.example.features.sharedfinance.login.data.LoginApi
import com.example.features.sharedfinance.login.data.LoginRepositoryImpl
import com.example.features.sharedfinance.login.domain.LoginRepository
import com.example.features.sharedfinance.registration.data.RegistrationApi
import com.example.features.sharedfinance.registration.data.RegistrationRepositoryImpl
import com.example.features.sharedfinance.registration.domain.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeaturesModule {

    @Provides
    @Singleton
    fun provideRegistrationApi(retrofit: RetrofitClient): RegistrationApi {
        return retrofit.getUnauthorizedInstance().create(RegistrationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(api: RegistrationApi): RegistrationRepository {
        return RegistrationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: RetrofitClient): LoginApi {
        return retrofit.getUnauthorizedInstance().create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideListJournalsApi(retrofit: RetrofitClient): ListJournalsApi {
        return retrofit.getAuthorizedInstance().create(ListJournalsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideListJournalsRepository(api: ListJournalsApi): ListJournalsRepository {
        return ListJournalsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideInvitationsApi(retrofit: RetrofitClient): InvitationsApi {
        return retrofit.getAuthorizedInstance().create(InvitationsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideInvitationsRepository(api: InvitationsApi): InvitationsRepository {
        return InvitationsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: RetrofitClient): CategoriesApi {
        return retrofit.getAuthorizedInstance().create(CategoriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoriesRepository(api: CategoriesApi): CategoriesRepository {
        return CategoriesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSettingsApi(retrofit: RetrofitClient): SettingsApi {
        return retrofit.getAuthorizedInstance().create(SettingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(api: SettingsApi): SettingsRepository {
        return SettingsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideJournalApi(retrofit: RetrofitClient): JournalApi {
        return retrofit.getAuthorizedInstance().create(JournalApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJournalRepository(api: JournalApi): JournalRepository {
        return JournalRepositoryImpl(api)
    }

}