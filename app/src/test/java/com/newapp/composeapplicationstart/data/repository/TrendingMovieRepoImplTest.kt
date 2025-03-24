package com.newapp.composeapplicationstart.data.repository

import android.util.Log
import com.newapp.composeapplicationstart.data.datasource.model.response.TrendingMovieResponseDTO
import com.newapp.composeapplicationstart.data.datasource.remote.TMBDApiInterface
import com.newapp.composeapplicationstart.data.mapper.MovieMapper
import com.newapp.composeapplicationstart.data.utils.DataError
import com.newapp.composeapplicationstart.data.utils.Result
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.every
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class TrendingMovieRepoImplTest {

    private lateinit var repo: TrendingMovieRepoImpl

    private val apiService: TMBDApiInterface = mockk()
    private val movieMapper: MovieMapper = mockk()

    @Before
    fun setUp() {
        repo = TrendingMovieRepoImpl(apiService, movieMapper)
    }

    @Test
    fun `getTrendingMovie should return success when API returns valid data`() = runBlocking {
        //Given
        val mockResultDTO = TrendingMovieResponseDTO.Result(
            id = 1,
            name = "Movie 1",
            posterPath = "/poster1.jpg",
            voteAverage = 7.5,
            adult = false,
            backdropPath = "backdrop_path",
            firstAirDate = "2025-1-4",
            genreIds = emptyList(),
            mediaType = "",
            originCountry = emptyList(),
            originalLanguage = "en",
            originalName = "",
            originalTitle = "Testing",
            overview = "A great Moview",
            popularity = 100.0,
            releaseDate = "2024-01-01",
            title = "New Moview",
            video = false,
            voteCount = 1,
        )
        val mockTrendingResponseDTO = TrendingMovieResponseDTO(
            page = 1,
            results = listOf(mockResultDTO),
            totalPages = 1,
            totalResults = 1
        )
        val expectedTrendingResponse = TrendingMovieResponse(
            id = 1,
            name = "Movie 1",
            posterPath = "/poster1.jpg",
            rating = 7.5,
        )
     //Mock API call
     coEvery { apiService.getTrendingMovies() } returns mockTrendingResponseDTO

     //Mock mapper
     every { movieMapper.mapTrendingResponseDTO(mockResultDTO) } returns expectedTrendingResponse

     // When- execute Function
     val result = repo.getTrendingMovie().first()

     //Then - Verify
     assertTrue(result is Result.Success)
     assertEquals(listOf(expectedTrendingResponse), (result as Result.Success).data)

     coVerify { apiService.getTrendingMovies() }
     verify { movieMapper.mapTrendingResponseDTO(mockResultDTO) }
    }

    /*@Test
    fun `getTrendingMovie should return error when API call throw HttpException`() = runBlocking {
        // Given
        val httpException = mockk<HttpException>()
        val response = mockk<Response<*>>()
        every { response.code() } returns 404
        every { httpException.response() } returns response

        coEvery { apiService.getTrendingMovies() } throws httpException
        // When
        val result = repo.getTrendingMovie().first()
        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.NOT_FOUND, (result as Result.Error).error)

    }*/

//    @Test
//    fun `getTrendingMovie should return error when API call throws HttpException`() = runBlocking {
//        // Given
//        val httpException = mockk<HttpException>()
//        val response = mockk<Response<*>>() // Mock Response<*>
//
//        every { response.code() } returns 404 // Mock response code to return 404
//        every { httpException.response() } returns response // Mock response property of HttpException
//
//        coEvery { apiService.getTrendingMovies() } throws httpException // Mock API call to throw HttpException
//
//        // When
//        val result = repo.getTrendingMovie().first()
//
//        // Then
//        assertTrue(result is Result.Error)
//        assertEquals(DataError.Network.NOT_FOUND, (result as Result.Error).error)
//    }
/*@Test
fun `getTrendingMovie should return error when API call throws HttpException`() = runBlocking {
    // Given
    val httpException = mockk<HttpException>()
    val response = mockk<Response<*>>() // Mock retrofit2 Response<*>

    // Mock the response's code() method to return 404
    every { response.code() } returns 404

    // Mock httpException's response() method to return the mocked response
    every { httpException.response() } returns response

    // Mock API call to throw the HttpException
    coEvery { apiService.getTrendingMovies() } throws httpException

    // When
    val result = repo.getTrendingMovie().first()

    // Then
    assertTrue(result is Result.Error)
    assertEquals(DataError.Network.NOT_FOUND, (result as Result.Error).error)
}*/
@Test
fun `getTrendingMovie should return error when API call throws HttpException`() = runBlocking {
    // Given
    val response = mockk<Response<Any>>(relaxed = true){
        every{code()} returns 404
        every { message() } returns "Not found"
        every { errorBody() } returns null
    }

    val httpException = mockk<HttpException>(relaxed = true){
        every { response() } returns response
        every { code() } answers {response.code()}
    }
    coEvery { apiService.getTrendingMovies() } throws httpException

    // When
    val result = repo.getTrendingMovie().first()

    // Then
    assertTrue(result is Result.Error)
    assertEquals(DataError.Network.NOT_FOUND, (result as Result.Error).error)
}
    @Test
    fun `getTrendingMovie should return error when API call throw IOException`() = runBlocking {
        // Given
        val ioException = mockk<IOException>()
        coEvery { apiService.getTrendingMovies() } throws ioException
        // When
        val result = repo.getTrendingMovie().first()
        // Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.NO_INTERNET, (result as Result.Error).error)

    }
    @Test
    fun `getTrendingMovie should return error when API call throw unknown Exception`() = runBlocking {
        // Given
        val mockException = mockk<Exception>()
        coEvery { apiService.getTrendingMovies() } throws mockException
        //When
        val result = repo.getTrendingMovie().first()
        //Then
        assertTrue(result is Result.Error)
        assertEquals(DataError.Network.UNKNOWN_ERROR,(result as Result.Error).error)
    }
}

