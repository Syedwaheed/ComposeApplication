package com.newapp.composeapplicationstart.di

import android.content.Context
import com.newapp.composeapplicationstart.BuildConfig
import com.newapp.composeapplicationstart.data.datasource.remote.TMBDApiInterface
import com.newapp.composeapplicationstart.data.mapper.MovieMapper
import com.newapp.composeapplicationstart.data.repository.MovieRepositoryImpl
import com.newapp.composeapplicationstart.data.repository.TrendingMovieRepoImpl
import com.newapp.composeapplicationstart.domain.repository.MovieRepository
import com.newapp.composeapplicationstart.domain.repository.TrendingMovieRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieRepositoryModule {
    @Singleton
    @Provides
    fun provideAPIKEY(): String {
        return BuildConfig.API_KEY
    }
    @Singleton
    @Provides
    fun provideMovieRepository(apiService: TMBDApiInterface, movieMapper: MovieMapper,apikey: String): MovieRepository {
        return MovieRepositoryImpl(apiService,movieMapper,apikey)
    }
    @Singleton
    @Provides
    fun provideTrendingMovie(
        apiService: TMBDApiInterface,
        movieMapper: MovieMapper,
    ): TrendingMovieRepo = TrendingMovieRepoImpl(
        apiService,
        movieMapper
    )
}