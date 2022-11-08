package com.rappipay.movies.domain.usecases

import app.cash.turbine.test
import com.rappipay.movies.domain.model.MovieVideoData
import com.rappipay.movies.domain.repositories.IMoviesRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class GetMovieVideoDataUseCaseTest {

  @MockK
  private lateinit var moviesRepository: IMoviesRepository

  private lateinit var useCase: GetMovieVideoDataUseCase

  @BeforeEach
  fun setUp() {
    useCase = GetMovieVideoDataUseCase(moviesRepository)
  }

  @Test
  internal fun `Should emit movieVideoData when GetMovieVideoDataUseCase is invoked`(): Unit = runTest(UnconfinedTestDispatcher()) {
    // Given
    val movieId = 123
    val movieVideoData = mockk<MovieVideoData>()

    every { moviesRepository.getMovieVideoData(movieId) } returns flow {
      emit(movieVideoData)
    }

    // When
    val flow = useCase.execute(movieId)

    // Then
    flow.test {
      assertEquals(movieVideoData, awaitItem())
      awaitComplete()
    }
  }
}
