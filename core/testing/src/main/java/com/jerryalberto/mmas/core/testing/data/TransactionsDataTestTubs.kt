package com.jerryalberto.mmas.core.testing.data

import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.ext.convertMillisToYearMonthDay
import java.util.Calendar

class TransactionsDataTestTubs {

    companion object {

        val currentDateCalendar: Calendar by lazy {
            getCurrentDateCalendarInstance()
        }

        //today transaction
        val todayTransactions = listOf(
            TransactionEntity(
                id = 1,
                type = "INCOME",
                amount = 0.0,
                category = "FOOD",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 1,
                minute = 1,
                uri = "",
            ),
            TransactionEntity(
                id = 2,
                type = "EXPENSES",
                amount = 2.0,
                category = "SALARY",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 2,
                minute = 2,
                uri = "",
            ),
            TransactionEntity(
                id = 3,
                type = "INCOME",
                amount = 0.0,
                category = "FOOD",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 3,
                minute = 3,
                uri = "",
            ),
            TransactionEntity(
                id = 4,
                type = "EXPENSES",
                amount = 2.0,
                category = "SALARY",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 4,
                minute = 4,
                uri = "",
            ),
            TransactionEntity(
                id = 5,
                type = "INCOME",
                amount = 0.0,
                category = "FOOD",
                description = "",
                year = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().first,
                month = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().second,
                day = currentDateCalendar.timeInMillis.convertMillisToYearMonthDay().third,
                hour = 5,
                minute = 5,
                uri = "",
            ),
            TransactionEntity(
                id = 6,
                type = "EXPENSES",
                amount = 2.0,
                category = "SALARY",
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
        val lastWeekTransactions = todayTransactions.map {
            it.copy(
                id = it.id * 10,
                year = getLastWeekDateDateMillis().timeInMillis.convertMillisToYearMonthDay().first,
                month = getLastWeekDateDateMillis().timeInMillis.convertMillisToYearMonthDay().second,
                day = getLastWeekDateDateMillis().timeInMillis.convertMillisToYearMonthDay().third,
            )
        }

        //last Month transaction
        val lastMonthTransactions  = todayTransactions.map {
            it.copy(
                id = it.id * 100,
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

        fun getLastWeekDateDateMillis(): Calendar {
            val calendar = currentDateCalendar.clone() as Calendar
            calendar.add(Calendar.DAY_OF_YEAR, -7)
            return calendar
        }

        fun getLastMonthDateDateMillis(): Calendar {
            val calendar = currentDateCalendar.clone() as Calendar
            calendar.add(Calendar.MONTH, -1)
            return calendar
        }
    }



}