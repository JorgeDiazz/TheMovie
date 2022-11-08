package com.rappipay.movies.domain.usecases

import app.cash.turbine.test
import com.rappipay.movies.domain.model.MoviesFilters
import com.rappipay.movies.domain.repositories.IMoviesRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class GetAvailableMoviesFiltersUseCaseTest {

  @MockK
  private lateinit var moviesRepository: IMoviesRepository

  private lateinit var useCase: GetAvailableMoviesFiltersUseCase

  @BeforeEach
  fun setUp() {
    useCase = GetAvailableMoviesFiltersUseCase(moviesRepository)
  }

  @Test
  internal fun `Should emit moviesPagingData when getUpcomingMovies is invoked`(): Unit = runTest(UnconfinedTestDispatcher()) {
    // Given
    val moviesFilters = MoviesFilters(mockk(), mockk())

    every { moviesRepository.getAvailableMoviesFilters() } returns flow {
      emit(moviesFilters)
    }

    // When
    val flow = useCase.execute(Unit)

    // Then
    flow.test {
      assertEquals(moviesFilters, awaitItem())
      awaitComplete()
    }
  }
}
