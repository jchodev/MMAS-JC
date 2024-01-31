package com.jerryalberto.mmas.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val mmasDispatcher: MmasDispatchers)

enum class MmasDispatchers {
    Default,
    IO,
}