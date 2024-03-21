package com.jerryalberto.mmas.core.data.model

import com.jerryalberto.mmas.core.database.model.TransactionEntity
import com.jerryalberto.mmas.core.model.data.Transaction


fun Transaction.asEntity() = TransactionEntity(
    id = id,
    type = type,
    amount = amount,
    category = category,
    description = description,
    uri = uri,
    date = date,
    hour = hour,
    minute = minute,
)