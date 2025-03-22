package com.newapp.composeapplicationstart.data.mapper


import com.newapp.composeapplicationstart.data.datasource.model.response.MovieResponseDTO
import com.newapp.composeapplicationstart.data.datasource.model.response.TrendingMovieResponseDTO
import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import javax.inject.Inject

class MovieMapper @Inject constructor(){
    fun mapFromDTO(movieResultDTO: MovieResponseDTO.MovieResultDTO?): MovieResponse {
        return MovieResponse(
            id = movieResultDTO?.id,
            title = movieResultDTO?.title,
            overview = movieResultDTO?.overview,
            posterPath =  movieResultDTO?.posterPath
        )
    }

    fun mapTrendingResponseDTO(movieDTO: TrendingMovieResponseDTO.Result): TrendingMovieResponse?{
        return (movieDTO.name ?: movieDTO.title)?.let {
            TrendingMovieResponse(
                id = movieDTO.id ?: 0,
                name = it,
                posterPath = movieDTO.posterPath ?: "",
                rating = movieDTO.voteAverage ?: 0.0
            )
        }

    }
}