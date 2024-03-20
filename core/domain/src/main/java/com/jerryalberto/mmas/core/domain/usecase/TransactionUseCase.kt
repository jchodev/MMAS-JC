package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.model.data.Transaction
import javax.inject.Inject


class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getAllTransaction(): List<Transaction> {
        return transactionRepository.getAllTransaction()
    }

    suspend fun insertTransaction(transaction: Transaction){
        transactionRepository.insertTransaction(transaction = transaction)
    }
}