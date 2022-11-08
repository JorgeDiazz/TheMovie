package com.rappipay.movies.domain.usecases

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
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
class GetSuggestedMoviesUseCaseTest {

  @MockK
  private lateinit var moviesRepository: IMoviesRepository

  private lateinit var useCase: GetSuggestedMoviesUseCase

  @BeforeEach
  fun setUp() {
    useCase = GetSuggestedMoviesUseCase(moviesRepository)
  }

  companion object {
    @JvmStatic
    fun source() = listOf(
      arrayOf("en", 2022),
      arrayOf("es", null),
      arrayOf(null, 2020),
      arrayOf<String?>(null, null),
    )
  }

  @ParameterizedTest
  @MethodSource("source")
  internal fun `Should emit moviesList when GetSuggestedMoviesUseCase is invoked`(languageIsoCode: String?, releaseYear: Int?): Unit = runTest(UnconfinedTestDispatcher()) {
    // Given
    val moviesList = listOf(mockk<Movie>())

    every { moviesRepository.getSuggestedMoviesAvailableMoviesFilters(languageIsoCode, releaseYear) } returns flow {
      emit(moviesList)
    }

    // When
    val flow = useCase.execute(languageIsoCode to releaseYear)

    // Then
    flow.test {
      assertEquals(moviesList, awaitItem())
      awaitComplete()
    }
  }
}
