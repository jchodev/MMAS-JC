package com.jerryalberto.mmas.core.domain.usecase

import com.jerryalberto.mmas.core.domain.repository.TransactionRepository
import com.jerryalberto.mmas.core.model.data.AccountBalanceDataType
import com.jerryalberto.mmas.core.model.data.Transaction
import com.jerryalberto.mmas.core.model.data.TransactionSummary
import com.jerryalberto.mmas.core.model.data.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun getLatestTransaction(): Flow<List<Transaction>> {
        return transactionRepository.getLatestTransaction(latest = 4)
    }

    suspend fun insertTransaction(transaction: Transaction){
        transactionRepository.insertTransaction(transaction = transaction)
    }

    suspend fun getSumAmountGroupedByType(
        dataType: AccountBalanceDataType
    ): Flow<TransactionSummary> {
        val daoResult = if (dataType == AccountBalanceDataType.TOTAL){
            transactionRepository.getSumAmountGrouped()
        } else {
            val dateRange = when (dataType){
                AccountBalanceDataType.TODAY -> {
                    val currentDateMillis = getCurrentDateMillis()
                    Pair(currentDateMillis, currentDateMillis)
                }
                AccountBalanceDataType.WEEK -> getThisWeekDateMillisRange()
                AccountBalanceDataType.MONTH -> getThisMonthDateMillisRange()
                else -> Pair(Long.MIN_VALUE, Long.MAX_VALUE)
            }
            transactionRepository.getSumAmountGroupedByDateRange(dateRange.first, dateRange.second)
        }

        return daoResult.map { results->
            TransactionSummary(
                income = results.find { it.type == TransactionType.INCOME.value }?.totalAmount ?: 0.0,
                expenses = results.find { it.type == TransactionType.EXPENSES.value }?.totalAmount ?: 0.0
            )
        }
    }

    private fun getCurrentDateMillis(): Long {
        return getDateMillisByCalendar(Calendar.getInstance())
    }

    private fun getDateMillisByCalendar(cal: Calendar): Long {
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun getThisWeekDateMillisRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeekMillis = getDateMillisByCalendar(calendar)
        calendar.add(Calendar.DATE, 6)
        val endOfWeekMillis = getDateMillisByCalendar(calendar)
        return Pair(startOfWeekMillis, endOfWeekMillis)
    }

    private fun getThisMonthDateMillisRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonthMillis = getDateMillisByCalendar(calendar)
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val endOfMonthMillis = getDateMillisByCalendar(calendar)
        return Pair(startOfMonthMillis, endOfMonthMillis)
    }


}