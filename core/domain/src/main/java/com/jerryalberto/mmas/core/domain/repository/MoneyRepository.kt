package com.jerryalberto.mmas.core.domain.repository

import com.jerryalberto.mmas.core.database.model.MoneyEntity

interface MoneyRepository {

    suspend fun getMoneyByDate(date: String): List<MoneyEntity>

}