package com.rappipay.movies.domain.usecases

import androidx.paging.PagingData
import app.cash.turbine.test
import com.rappipay.movies.domain.model.Movie
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
class GetUpcomingMoviesUseCaseTest {

  @MockK
  private lateinit var moviesRepository: IMoviesRepository

  private lateinit var useCase: GetUpcomingMoviesUseCase

  @BeforeEach
  fun setUp() {
    useCase = GetUpcomingMoviesUseCase(moviesRepository)
  }

  @Test
  internal fun `Should emit moviesPagingData when GetUpcomingMoviesUseCase is invoked`(): Unit = runTest(UnconfinedTestDispatcher()) {
    // Given
    val moviesList = listOf(mockk<Movie>())
    val moviesPagingData = PagingData.from(moviesList)

    every { moviesRepository.getUpcomingMovies(any()) } returns flow {
      emit(moviesPagingData)
    }

    // When
    val flow = useCase.execute(Unit)

    // Then
    flow.test {
      assertEquals(moviesPagingData, awaitItem())
      awaitComplete()
    }
  }
}
