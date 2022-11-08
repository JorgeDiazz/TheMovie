package com.app.base.interfaces

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<T, R> {
  abstract suspend fun execute(input: T): Flow<R>
}
