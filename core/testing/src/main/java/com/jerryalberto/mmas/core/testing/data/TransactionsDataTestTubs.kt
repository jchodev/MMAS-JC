package com.jerryalberto.mmas.core.testing.data

import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.ext.convertMillisToYearMonthDay
import com.jerryalberto.mmas.core.model.data.TransactionSummary
import java.util.Calendar

class TransactionsDataTestTubs {

    companion object {

        val currentDateCalendar: Calendar by lazy {
            getCurrentDateCalendarInstance()
        }

        //today transaction
        val mockTodayTransactions = listOf(
            TransactionEntity(
                type = "INCOME",
                amount = 100.0,
                category = "SALARY",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                type = "EXPENSES",
                amount = 200.0,
                category = "FOOD",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 2,
                minute = 2,
                uri = "",
            ),
            TransactionEntity(
                type = "INCOME",
                amount = 100.0,
                category = "SALARY",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 3,
                minute = 3,
                uri = "",
            ),
            TransactionEntity(
                type = "EXPENSES",
                amount = 200.0,
                category = "FOOD",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 4,
                minute = 4,
                uri = "",
            ),
            TransactionEntity(
                type = "INCOME",
                amount = 100.0,
                category = "SALARY",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 5,
                minute = 5,
                uri = "",
            ),
            TransactionEntity(
                type = "EXPENSES",
                amount = 200.0,
                category = "FOOD",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 6,
                minute = 6,
                uri = "",
            )
        )

        //last week transaction
        val lastWeekTransactions = mockTodayTransactions.map {
            it.copy(
                year = getLastWeekDateDateMillis().timeInMillis.convertMillisToYearMonthDay().first,
                month = getLastWeekDateDateMillis().timeInMillis.convertMillisToYearMonthDay().second,
                day = getLastWeekDateDateMillis().timeInMillis.convertMillisToYearMonthDay().third,
            )
        }

        //last Month transaction
        val lastMonthTransactions  = mockTodayTransactions.map {
            it.copy(
                year = getLastMonthDateDateMillis().timeInMillis.convertMillisToYearMonthDay().first,
                month = getLastMonthDateDateMillis().timeInMillis.convertMillisToYearMonthDay().second,
                day = getLastMonthDateDateMillis().timeInMillis.convertMillisToYearMonthDay().third,
            )
        }

        private fun getCurrentDateCalendarInstance(): Calendar {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar
        }

        private fun getLastWeekDateDateMillis(): Calendar {
            val calendar = currentDateCalendar.clone() as Calendar
            calendar.add(Calendar.DAY_OF_YEAR, -7)
            return calendar
        }

        private fun getLastMonthDateDateMillis(): Calendar {
            val calendar = currentDateCalendar.clone() as Calendar
            calendar.add(Calendar.MONTH, -1)
            return calendar
        }

        val transactionSummary = TransactionSummary(
                income = 100.0,
                expenses = 200.0
        )

    }



}