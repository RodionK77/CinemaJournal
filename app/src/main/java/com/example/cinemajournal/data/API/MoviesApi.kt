package com.example.cinemajournal.data.API

import com.example.example.MovieInfo
import com.example.example.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.kinopoisk.dev/v1.4/movie?id=394&token=S8MRT2H-2FWM831-PWMYTHB-XVJ76DF

//https://api.kinopoisk.dev/v1.4/movie?page=1&limit=10&selectFields=id&selectFields=name&selectFields=enName&selectFields=alternativeName&selectFields=description&selectFields=type&selectFields=isSeries&selectFields=year&selectFields=releaseYears&selectFields=rating&selectFields=ageRating&selectFields=budget&selectFields=movieLength&selectFields=seriesLength&selectFields=genres&selectFields=countries&selectFields=poster&selectFields=persons&selectFields=fees&selectFields=premiere&id=777&token=S8MRT2H-2FWM831-PWMYTHB-XVJ76DF

//https://api.kinopoisk.dev/v1.4/movie?page=1&limit=20&selectFields=id&selectFields=name&selectFields=enName&selectFields=alternativeName&selectFields=description&selectFields=type&selectFields=typeNumber&selectFields=isSeries&selectFields=year&selectFields=releaseYears&selectFields=rating&selectFields=ageRating&selectFields=budget&selectFields=movieLength&selectFields=seriesLength&selectFields=genres&selectFields=countries&selectFields=poster&selectFields=persons&selectFields=fees&selectFields=premiere&type=movie&rating.kp=8.0-10&token=S8MRT2H-2FWM831-PWMYTHB-XVJ76DF

//https://api.kinopoisk.dev/v1.4/movie?page=1&limit=1&selectFields=id&selectFields=name&selectFields=enName&selectFields=alternativeName&selectFields=description&selectFields=type&selectFields=typeNumber&selectFields=isSeries&selectFields=year&selectFields=releaseYears&selectFields=rating&selectFields=ageRating&selectFields=budget&selectFields=movieLength&selectFields=seriesLength&selectFields=genres&selectFields=countries&selectFields=poster&selectFields=persons&selectFields=fees&selectFields=premiere&id=712&token=S8MRT2H-2FWM831-PWMYTHB-XVJ76DF

interface MoviesApi {

    @GET("/v1.4/movie?page=1&limit=20&selectFields=id&selectFields=name&selectFields=enName&selectFields=alternativeName&selectFields=description&selectFields=type&selectFields=typeNumber&selectFields=isSeries&selectFields=year&selectFields=releaseYears&selectFields=rating&selectFields=ageRating&selectFields=budget&selectFields=movieLength&selectFields=seriesLength&selectFields=genres&selectFields=countries&selectFields=poster&selectFields=persons&selectFields=fees&selectFields=premiere&type=movie&rating.kp=8.0-10&token=${Tokens.TOKEN1}")
    suspend fun getTop20Movies(): MovieListResponse

    /*@GET("/v1.4/movie?page=1&limit=1&selectFields=id&selectFields=name&selectFields=enName&selectFields=alternativeName&selectFields=description&selectFields=type&selectFields=typeNumber&selectFields=isSeries&selectFields=year&selectFields=releaseYears&selectFields=rating&selectFields=ageRating&selectFields=budget&selectFields=movieLength&selectFields=seriesLength&selectFields=genres&selectFields=countries&selectFields=poster&selectFields=persons&selectFields=fees&selectFields=premiere&id={id}&token=S8MRT2H-2FWM831-PWMYTHB-XVJ76DF")
    fun getMovieById(@Path("id") id: Int): Call<MovieListResponse>*/
    @GET("/v1.4/movie")
    suspend fun getFilteredMovies(@Query("page") page: Int = 1,
                          @Query("limit") limit: Int = 20,
                          @Query("selectFields") selectFields: List<String> = listOf("id","name","enName","alternativeName","description","type","typeNumber","isSeries","year","releaseYears","rating","ageRating","budget","movieLength","seriesLength","genres","countries","poster","persons","fees","premiere"),
                          @Query("type") type: List<String>? = listOf(),
                          @Query("year") year: String? = "",
                          @Query("rating.kp") rating: String? = "",
                          @Query("ageRating") ageRating: String? = "",
                          @Query("movieLength") time: String? = "",
                          @Query("genres.name") genresName: List<String>? = listOf(),
                          @Query("countries.name") countriesName: List<String>? = listOf(),
                          @Query("token") token: String = Tokens.TOKEN1): MovieListResponse
    @GET("/v1.4/movie/search")
    suspend fun getMoviesByName(@Query("page") page: Int = 1,
                          @Query("limit") limit: Int = 20,
                          @Query("query") query: String = "",
                          @Query("token") token: String = Tokens.TOKEN1): MovieListResponse

    @GET("/v1.4/movie/{id}?token=${Tokens.TOKEN1}")
    suspend fun getMovieById(@Path("id") id: Int): MovieInfo
}