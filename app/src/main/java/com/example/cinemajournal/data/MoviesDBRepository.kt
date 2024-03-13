package com.example.cinemajournal.data

import com.example.cinemajournal.data.API.MoviesDBApi
import com.example.cinemajournal.data.Room.UsersDatabase
import com.example.cinemajournal.data.models.RoomModels.Countries
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Genres
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Persons
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfo
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.UserForRetrofit
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesDBRepository @Inject constructor(database: UsersDatabase, private val moviesDBApi: MoviesDBApi) {

    private val dao = database.usersDao()


    suspend fun getUser(requestBody: User): UserForRetrofit?{
        return moviesDBApi.getUser(requestBody)
    }

    suspend fun addMovieToDB(requestBody: RoomMovieInfo): RoomMovieInfo{
        return moviesDBApi.addMovieToDB(requestBody)
    }

    suspend fun addMovieToWatchToDB(requestBody: MoviesToWatchForRetrofit): MoviesToWatchForRetrofit{
        return moviesDBApi.addMovieToWatchToDB(requestBody)
    }

    suspend fun deleteMovieToWatchFromDB(requestBody: MoviesToWatchForRetrofit){
        return moviesDBApi.deleteMovieToWatchFromDB(requestBody)
    }

    suspend fun updateMovieToWatchToDB(requestBody: MoviesToWatchForRetrofit){
        return moviesDBApi.updateMovieToWatchToDB(requestBody)
    }

    suspend fun addWatchedMovieToDB(requestBody: WatchedMoviesForRetrofit): WatchedMoviesForRetrofit?{
        return moviesDBApi.addWatchedMovieToDB(requestBody)
    }

    suspend fun deleteWatchedMovieFromDB(requestBody: WatchedMoviesForRetrofit){
        return moviesDBApi.deleteWatchedMovieFromDB(requestBody)
    }

    suspend fun updateWatchedMovieToDB(requestBody: WatchedMoviesForRetrofit){
        return moviesDBApi.updateWatchedMovieToDB(requestBody)
    }

    suspend fun addReviewToDB(requestBody: Review): Review{
        return moviesDBApi.addReviewToDB(requestBody)
    }

    suspend fun updateReviewToDB(requestBody: Review){
        return moviesDBApi.updateReviewToDB(requestBody)
    }

    fun saveMovieToWatchToLocalDB(item: MoviesToWatch) {
        dao.saveMovieToWatchToLocalDB(item)
    }
    suspend fun getAllMoviesToWatchByIdFromLocalDB(item: User) : List<MoviesToWatch>?{
       return dao.getAllMoviesToWatchById(item.id)
    }
    fun checkMovieToWatch(movieId: Int, userId: Int) : Boolean{
        return dao.checkMovieToWatch(movieId, userId)
    }
    fun deleteMovieToWatchByIdFromLocalDB(userId: Int, movieId: Int) {
        dao.deleteMoviesToWatchById(userId, movieId)
    }
    fun deleteAllMoviesToWatchFromLocalDB(){
        dao.deleteAllMoviesToWatchFromLocalDB()
    }

    fun saveWatchedMovieToLocalDB(item: WatchedMovies) {
        dao.saveWatchedMovieToLocalDB(item)
    }
    suspend fun getAllWatchedMoviesByIdFromLocalDB(item: User) : List<WatchedMovies>?{
        return dao.getAllWatchedMoviesById(item.id)
    }
    fun checkWatchedMovie(movieId: Int, userId: Int) : Boolean{
        return dao.checkWatchedMovie(movieId, userId)
    }
    fun deleteWatchedMovieByIdFromLocalDB(userId: Int, movieId: Int) {
        dao.deleteWatchedMoviesById(userId, movieId)
    }
    fun deleteAllWatchedMoviesFromLocalDB(){
        dao.deleteAllWatchedMoviesFromLocalDB()
    }

    fun saveReviewToLocalDB(item: Review) {
        dao.saveReviewToLocalDB(item)
    }
    suspend fun getAllReviewsByIdFromLocalDB(item: User) : List<Review>?{
        return dao.getAllReviewsById(item.id)
    }
    fun deleteReviewFromLocalDB(item: Review){
        dao.deleteReviewById(item.userId, item.movieId)
    }
    fun deleteAllReviewsFromLocalDB(){
        dao.deleteAllReviewsFromLocalDB()
    }

    fun saveLikesToLocalDB(item: Likes) {
        dao.saveLikesToLocalDB(item)
    }
    suspend fun getAllLikesByIdFromLocalDB(item: User) : List<Likes>?{
        return dao.getAllLikesById(item.id)
    }
    fun deleteLikesByIdFromLocalDB(item: Likes){
        dao.deleteLikesById(item.likesId)
    }
    fun deleteAllLikesFromLocalDB(){
        dao.deleteAllLikesFromLocalDB()
    }

    fun saveDislikesToLocalDB(item: Dislikes) {
        dao.saveDislikesToLocalDB(item)
    }
    suspend fun getAllDislikesByIdFromLocalDB(item: User) : List<Dislikes>?{
        return dao.getAllDislikesById(item.id)
    }
    fun deleteDislikesByIdFromLocalDB(item: Dislikes){
        dao.deleteDislikesById(item.dislikesId)
    }
    fun deleteAllDislikesFromLocalDB(){
        dao.deleteAllDislikesFromLocalDB()
    }

    fun saveMovieToLocalDB(item: RoomMovieInfo){
        dao.saveMovieToLocalDB(item)
    }
    suspend fun getMovieByIdFromLocalDB(id: Int): RoomMovieInfo?{
        return dao.getMovieByIdFromLocalDB(id)
    }
    suspend fun getAllMoviesFromLocalDB(): List<RoomMovieInfo>? {
        return dao.getAllMoviesFromLocalDB()
    }
    fun deleteMovieByIdFromLocalDB(item: RoomMovieInfo){
        dao.deleteMovieByIdFromLocalDB(item.id)
    }
    fun deleteAllMoviesFromLocalDB(){
        dao.deleteAllMoviesFromLocalDB()
    }

    fun saveCountriesToLocalDB(item: Countries){
        dao.saveCountriesToLocalDB(item)
    }
    suspend fun getCountriesByIdFromLocalDB(movieId: Int): Countries?{
        return dao.getCountriesByIdFromLocalDB(movieId)
    }
    fun saveGenresToLocalDB(item: Genres){
        dao.saveGenresToLocalDB(item)
    }
    suspend fun getGenresByIdFromLocalDB(movieId: Int): Genres?{
        return dao.getGenresByIdFromLocalDB(movieId)
    }
    fun savePersonsToLocalDB(item: Persons){
        dao.savePersonsToLocalDB(item)
    }
    suspend fun getPersonsByIdFromLocalDB(movieId: Int): Persons?{
        return dao.getPersonsByIdFromLocalDB(movieId)
    }
    fun saveSeasonsInfoToLocalDB(item: SeasonsInfo){
        dao.saveSeasonsInfoToLocalDB(item)
    }
    suspend fun getSeasonsInfoByIdFromLocalDB(movieId: Int): SeasonsInfo?{
        return dao.getSeasonsInfoByIdFromLocalDB(movieId)
    }

}