package com.jerryalberto.mmas.core.data.model

import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.ext.convertMillisToYearMonthDay
import com.jerryalberto.mmas.core.model.data.Transaction


fun Transaction.asEntity() : TransactionEntity {
    val yearMonthDay = date?.convertMillisToYearMonthDay()
    return TransactionEntity(
        id = id,
        type = type?.value ?: "",
        amount = amount,
        category = category?.type?.value ?: "",
        description = description,
        uri = uri,
        year = yearMonthDay?.first ?: 0,
        month = yearMonthDay?.second ?: 0,
        day = yearMonthDay?.third ?: 0,
        hour = hour ?: 0,
        minute = minute ?: 0,
    )
}