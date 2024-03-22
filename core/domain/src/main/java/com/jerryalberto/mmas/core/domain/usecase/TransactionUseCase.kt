package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.database.model.TransactionSummaryQueryResult
import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionSummary
import com.jerryalberto.mmas.core.model.data.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getLatestTransaction(): Flow<List<Transaction>> {
        return transactionRepository.getLatestTransaction()
    }

    suspend fun insertTransaction(transaction: Transaction){
        transactionRepository.insertTransaction(transaction = transaction)
    }

    suspend fun getSumAmountGroupedByType(): Flow<TransactionSummary> {
        return transactionRepository.getSumAmountGroupedByType().map { results->
            TransactionSummary(
                income = results.find { it.type == TransactionType.INCOME.value }?.totalAmount ?: 0.0,
                expenses = results.find { it.type == TransactionType.EXPENSES.value }?.totalAmount ?: 0.0
            )
        }
    }
}