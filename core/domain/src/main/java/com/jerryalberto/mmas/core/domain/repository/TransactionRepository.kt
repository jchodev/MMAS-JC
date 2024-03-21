package com.jerryalberto.mmas.core.domain.repository


import com.jerryalberto.mmas.core.model.data.Transaction

interface TransactionRepository {

    suspend fun getTransactionByDate(date: Long): List<Transaction>
    suspend fun getAllTransaction(): List<Transaction>

    suspend fun insertTransaction(transaction: Transaction)

}