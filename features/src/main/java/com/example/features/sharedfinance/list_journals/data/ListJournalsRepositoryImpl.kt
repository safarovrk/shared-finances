package com.example.features.sharedfinance.list_journals.data

import com.example.features.sharedfinance.list_journals.domain.CreateJournalRequest
import com.example.features.sharedfinance.list_journals.domain.Journal
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import com.example.features.sharedfinance.list_journals.domain.ListJournalsRepository
import com.example.features.sharedfinance.list_journals.domain.RoleRequest
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ListJournalsRepositoryImpl @Inject constructor(
    private val api: ListJournalsApi
) : ListJournalsRepository {

    override suspend fun getJournals(journalsRequest: JournalsRequest): Response<List<Journal>> {
        return api.getJournals(journalsRequest)
    }

    override suspend fun createJournal(createJournalRequest: CreateJournalRequest): Response<ResponseBody> {
        return api.createJournal(createJournalRequest)
    }

    override suspend fun getUserRole(roleRequest: RoleRequest): Long {
        return api.getUserRole(roleRequest)
    }
}