package com.jerryalberto.mmas.core.data.model

import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.ext.convertMillisToYearMonthDay
import com.jerryalberto.mmas.core.model.data.Transaction


fun Transaction.asEntity() : TransactionEntity {
    val yearMonthDay = date.convertMillisToYearMonthDay()
    return TransactionEntity(
        id = id,
        type = type?.value ?: "",
        amount = amount,
        category = category?.type?.value ?: "",
        description = description,
        uri = uri,
        year = yearMonthDay.first,
        month = yearMonthDay.second,
        day = yearMonthDay.third,
        hour = hour,
        minute = minute,
    )
}