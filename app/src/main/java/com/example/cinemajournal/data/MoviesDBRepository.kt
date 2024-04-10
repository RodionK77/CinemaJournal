package com.example.cinemajournal.data

import com.example.cinemajournal.data.API.MoviesDBApi
import com.example.cinemajournal.data.Room.UsersDatabase
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.CountriesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.GenresForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.PersonsForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDBRepository @Inject constructor(database: UsersDatabase, private val moviesDBApi: MoviesDBApi) {

    private val dao = database.usersDao()


    suspend fun getUser(id: Int): UserForRetrofit?{
        return moviesDBApi.getUser(id)
    }

    suspend fun getMovie(id: Int): RoomMovieInfoForRetrofit?{
        return moviesDBApi.getMovie(id)
    }

    suspend fun addMovieToDB(requestBody: RoomMovieInfoForRetrofit): RoomMovieInfoForRetrofit{
        return moviesDBApi.addMovieToDB(requestBody)
    }

    suspend fun addMovieToWatchToDB(requestBody: MoviesToWatchForRetrofit): MoviesToWatchForRetrofit{
        return moviesDBApi.addMovieToWatchToDB(requestBody)
    }

    suspend fun deleteMovieToWatchFromDB(requestBody: MoviesToWatchForRetrofit): Response<Unit>{
        return moviesDBApi.deleteMovieToWatchFromDB(requestBody)
    }

    suspend fun updateMovieToWatchToDB(requestBody: MoviesToWatchForRetrofit){
        return moviesDBApi.updateMovieToWatchToDB(requestBody)
    }

    suspend fun addWatchedMovieToDB(requestBody: WatchedMoviesForRetrofit): WatchedMoviesForRetrofit?{
        return moviesDBApi.addWatchedMovieToDB(requestBody)
    }

    suspend fun deleteWatchedMovieFromDB(requestBody: WatchedMoviesForRetrofit): Response<Unit> {
        return moviesDBApi.deleteWatchedMovieFromDB(requestBody)
    }

    suspend fun updateWatchedMovieToDB(requestBody: WatchedMoviesForRetrofit){
        return moviesDBApi.updateWatchedMovieToDB(requestBody)
    }

    suspend fun addReviewToDB(requestBody: ReviewForRetrofit): ReviewForRetrofit?{
        return moviesDBApi.addReviewToDB(requestBody)
    }

    suspend fun updateReviewToDB(requestBody: Review){
        return moviesDBApi.updateReviewToDB(requestBody)
    }

    suspend fun saveMovieToWatchToLocalDB(item: MoviesToWatch) {
        dao.saveMovieToWatchToLocalDB(item)
    }
    suspend fun getAllMoviesToWatchByIdFromLocalDB(item: User) : List<MoviesToWatch>?{
       return dao.getAllMoviesToWatchById(item.id)
    }
    suspend fun checkMovieToWatch(movieId: Int, userId: Int) : Boolean{
        return dao.checkMovieToWatch(movieId, userId)
    }
    suspend fun deleteMovieToWatchByIdFromLocalDB(userId: Int, movieId: Int) {
        dao.deleteMoviesToWatchById(userId, movieId)
    }
    suspend fun deleteAllMoviesToWatchFromLocalDB(){
        dao.deleteAllMoviesToWatchFromLocalDB()
    }

    suspend fun getReminderDateByIdFromToWatchFromLocalDB(movieId: Int) : String?{
        return dao.getReminderDateByIdFromToWatchFromLocalDB(movieId)
    }

    suspend fun getReminderHourByIdFromToWatchFromLocalDB(movieId: Int) : Int?{
        return dao.getReminderHourByIdFromToWatchFromLocalDB(movieId)
    }

    suspend fun getReminderMinuteByIdFromToWatchFromLocalDB(movieId: Int) : Int?{
        return dao.getReminderMinuteByIdFromToWatchFromLocalDB(movieId)
    }

    suspend fun saveWatchedMovieToLocalDB(item: WatchedMovies) {
        dao.saveWatchedMovieToLocalDB(item)
    }
    suspend fun getAllWatchedMoviesByIdFromLocalDB(item: User) : List<WatchedMovies>?{
        return dao.getAllWatchedMoviesById(item.id)
    }
    suspend  fun checkWatchedMovie(movieId: Int, userId: Int) : Boolean{
        return dao.checkWatchedMovie(movieId, userId)
    }
    suspend fun deleteWatchedMovieByIdFromLocalDB(userId: Int, movieId: Int) {
        dao.deleteWatchedMoviesById(userId, movieId)
    }
    suspend fun deleteAllWatchedMoviesFromLocalDB(){
        dao.deleteAllWatchedMoviesFromLocalDB()
    }

    suspend fun getMoviesFromToWatchFromLocalDB(): List<RoomMovieInfo>?{
        return dao.getMoviesFromToWatchFromLocalDB()
    }
    suspend fun getMoviesFromWatchedFromLocalDB(): List<RoomMovieInfo>?{
        return dao.getMoviesFromWatchedFromLocalDB()
    }

    suspend fun saveReviewToLocalDB(item: Review) {
        dao.saveReviewToLocalDB(item)
    }
    suspend fun getAllReviewsByIdFromLocalDB(item: User) : List<Review>?{
        return dao.getAllReviewsById(item.id)
    }
    suspend fun deleteReviewFromLocalDB(item: Review){
        dao.deleteReviewById(item.userId, item.movieId)
    }
    suspend fun deleteAllReviewsFromLocalDB(){
        dao.deleteAllReviewsFromLocalDB()
    }

    suspend fun saveLikesToLocalDB(item: Likes) {
        dao.saveLikesToLocalDB(item)
    }
    suspend fun getAllLikesByIdFromLocalDB(item: User) : List<Likes>?{
        return dao.getAllLikesById(item.id)
    }
    suspend fun getLikesByIdFromLocalDB(movieId: Int): List<Likes>{
        return dao.getLikesByIdFromLocalDB(movieId)
    }
    suspend fun deleteLikesByIdFromLocalDB(movieId: Int){
        dao.deleteLikesByIdFromLocalDB(movieId)
    }
    suspend fun deleteAllLikesFromLocalDB(){
        dao.deleteAllLikesFromLocalDB()
    }

    suspend fun saveDislikesToLocalDB(item: Dislikes) {
        dao.saveDislikesToLocalDB(item)
    }
    suspend fun getAllDislikesByIdFromLocalDB(item: User) : List<Dislikes>?{
        return dao.getAllDislikesById(item.id)
    }
    suspend fun getDislikesByIdFromLocalDB(movieId: Int): List<Dislikes>{
        return dao.getDislikesByIdFromLocalDB(movieId)
    }
    suspend fun deleteDislikesByIdFromLocalDB(movieId: Int){
        dao.deleteDislikesByIdFromLocalDB(movieId)
    }
    suspend fun deleteAllDislikesFromLocalDB(){
        dao.deleteAllDislikesFromLocalDB()
    }

    suspend fun saveMovieToLocalDB(item: RoomMovieInfo){
        dao.saveMovieToLocalDB(item)
    }
    suspend fun getMovieByIdFromLocalDB(id: Int): RoomMovieInfo?{
        return dao.getMovieByIdFromLocalDB(id)
    }
    suspend fun getAllMoviesFromLocalDB(): List<RoomMovieInfo>? {
        return dao.getAllMoviesFromLocalDB()
    }
    suspend fun deleteMovieByIdFromLocalDB(id: Int){
        dao.deleteMovieByIdFromLocalDB(id)
    }
    suspend fun deleteAllMoviesFromLocalDB(){
        dao.deleteAllMoviesFromLocalDB()
    }

    suspend fun saveCountriesToLocalDB(item: Countries){
        dao.saveCountriesToLocalDB(item)
    }
    suspend fun getCountriesByIdFromLocalDB(movieId: Int): List<CountriesForRetrofit>{
        return dao.getCountriesByIdFromLocalDB(movieId)
    }
    suspend fun saveGenresToLocalDB(item: Genres){
        dao.saveGenresToLocalDB(item)
    }
    suspend fun getGenresByIdFromLocalDB(movieId: Int): List<GenresForRetrofit>{
        return dao.getGenresByIdFromLocalDB(movieId)
    }
    suspend fun savePersonsToLocalDB(item: Persons){
        dao.savePersonsToLocalDB(item)
    }
    suspend fun getPersonsByIdFromLocalDB(movieId: Int): List<PersonsForRetrofit>{
        return dao.getPersonsByIdFromLocalDB(movieId)
    }

    suspend fun getReviewByIdFromLocalDB(movieId: Int): Review{
        return dao.getReviewByIdFromLocalDB(movieId)
    }

    suspend fun saveSeasonsInfoToLocalDB(item: SeasonsInfo){
        dao.saveSeasonsInfoToLocalDB(item)
    }
    suspend fun getSeasonsInfoByIdFromLocalDB(movieId: Int): List<SeasonsInfoForRetrofit>{
        return dao.getSeasonsInfoByIdFromLocalDB(movieId)
    }

}