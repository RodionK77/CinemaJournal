package com.example.cinemajournal.ui.theme.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Start
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.placeholder
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.example.MovieInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalsToolbar(scrollBehavior: TopAppBarScrollBehavior){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Журналы",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing= 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Outlined.AddBox,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Outlined.Contacts,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryToolbar(scrollBehavior: TopAppBarScrollBehavior, galleryViewModel: GalleryViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if(!galleryViewModel.uiState.isSearch){
                Text(
                    text = "Галерея",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing= 8.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = galleryViewModel.uiState.searchQuery,
                    placeholder = {Text(
                        text = "Поисковой запрос...",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )},
                    onValueChange = {
                        galleryViewModel.changeSearchQuery(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 26.sp, fontWeight = FontWeight.SemiBold),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        if(galleryViewModel.uiState.searchQuery.isNotEmpty()){
                            ElevatedButton(onClick = {
                                galleryViewModel.changeQueryGo(true)
                                galleryViewModel.refreshSearchMovies(galleryViewModel.uiState.searchQuery)
                            },
                            modifier = Modifier.scale(0.8f)) {
                                Text("Поиск", fontSize = 20.sp)
                            }
                        }
                    }
                )
            }
        },
        navigationIcon = {
            if(galleryViewModel.uiState.isSearch){
                IconButton(onClick = {
                    if(galleryViewModel.uiState.drawerState){
                        galleryViewModel.changeDrawerState(!galleryViewModel.uiState.drawerState)
                    }

                    galleryViewModel.changeSearchState(false)
                    galleryViewModel.changeSearchQuery("")
                    galleryViewModel.changeQueryGo(false)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if(galleryViewModel.uiState.isSearch){
                IconButton(onClick = { galleryViewModel.changeDrawerState(!galleryViewModel.uiState.drawerState)
                    Log.d("R", galleryViewModel.uiState.drawerState.toString(), )}) {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = "Localized description"
                    )
                }
            }

            if(!galleryViewModel.uiState.isSearch){
                IconButton(onClick = {
                    galleryViewModel.changeSearchState(true)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        scrollBehavior = if (!galleryViewModel.uiState.isSearch) scrollBehavior else TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentToolbar(navController: NavController, descriptionViewModel: DescriptionViewModel, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel){


    if(descriptionViewModel.uiState.movieInfo != null){
        descriptionViewModel.checkMovieToWatch(descriptionViewModel.uiState.movieInfo?.id?: 0, authViewModel.uiState.user?.id?:0)
        descriptionViewModel.checkWatchedMovie(descriptionViewModel.uiState.movieInfo?.id?: 0, authViewModel.uiState.user?.id?:0)
    } else {
        descriptionViewModel.checkMovieToWatch(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?: 0, authViewModel.uiState.user?.id?:0)
        descriptionViewModel.checkWatchedMovie(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?: 0, authViewModel.uiState.user?.id?:0)
    }

    var toWatchText = if(!descriptionViewModel.uiState.movieToWatchStatus) "Добавить в список к просмотру" else "Удалить из списка к просмотру"
    var watchedText = if(!descriptionViewModel.uiState.watchedMovieStatus) "Добавить в список просмотренных" else "Удалить из списка просмотренных"


    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Column {
                if(descriptionViewModel.uiState.movieInfo != null){
                    Text(descriptionViewModel.uiState.movieInfo?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
                    if(descriptionViewModel.uiState.movieInfo?.enName == "" || descriptionViewModel.uiState.movieInfo?.enName == null){
                        Text(descriptionViewModel.uiState.movieInfo?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                    }else Text(descriptionViewModel.uiState.movieInfo?.enName ?: descriptionViewModel.uiState.movieInfo?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                } else {
                    Text(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
                    if(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.enName == "" || descriptionViewModel.uiState.roomMovieInfoForRetrofit?.enName == null){
                        Text(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                    }else Text(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.enName ?: descriptionViewModel.uiState.roomMovieInfoForRetrofit?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
                //descriptionViewModel.refreshCurrentMovieInfo(null)
                //descriptionViewModel.refreshCurrentMovieInfoRoom(null)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            dropDownMenu(descriptionViewModel, navController, authViewModel, journalsViewModel, toWatchText, watchedText)
        }
    )
}

@Composable
fun dropDownMenu(descriptionViewModel: DescriptionViewModel, navController: NavController, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel, toWatchText:String, watchedText: String) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var text = remember{ descriptionViewModel.uiState.movieToWatchStatus }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {Text(toWatchText)},
                onClick = {
                    val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                    val movieForRetrofit = RoomMovieInfoForRetrofit(movieId!!)
                    if(!descriptionViewModel.uiState.movieToWatchStatus){
                        descriptionViewModel.addMovieToDB(movieForRetrofit)
                        descriptionViewModel.addMovieToWatchToDB(MoviesToWatchForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieForRetrofit))
                        if(!descriptionViewModel.uiState.watchedMovieStatus){
                            if(descriptionViewModel.uiState.movieInfo != null){
                                var movie = descriptionViewModel.uiState.movieInfo!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.fees?.world?.value?:0, feesUsa = movie.fees?.usa?.value?:0,
                                    budget = movie.budget?.value?:0, posterUrl = movie.poster?.url?:"", worldPremier = movie.premiere?.world?:"", russiaPremier = movie.premiere?.russia?:"",
                                    kpRating = movie.rating?.kp?:0.0, imdbRating = movie.rating?.imdb?:0.0, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength,
                                    totalSeriesLength = movie.totalSeriesLength))
                                journalsViewModel.writeCountriesToLocalDB(movie.countries, movie.id!!)
                                journalsViewModel.writeGenresToLocalDB(movie.genres, movie.id!!)
                                journalsViewModel.writePersonsToLocalDB(movie.persons, movie.id!!)
                                journalsViewModel.writeSeasonsInfoToLocalDB(movie.seasonsInfo, movie.id!!)
                            }else {
                                var movie = descriptionViewModel.uiState.roomMovieInfoForRetrofit!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.feesWorld, feesUsa = movie.feesUsa,
                                    budget = movie.budget?:0, posterUrl = movie.posterUrl?:"", worldPremier = movie.worldPremier?:"", russiaPremier = movie.russiaPremier?:"",
                                    kpRating = movie.kpRating, imdbRating = movie.imdbRating, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength,
                                    totalSeriesLength = movie.totalSeriesLength))
                            }

                        }
                        journalsViewModel.addMovieToWatchToLocalDB(MoviesToWatch(userId = authViewModel.uiState.user!!.id, movieId = movieId!!))
                    }else {
                        val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                        descriptionViewModel.deleteMovieToWatchFromDB(MoviesToWatchForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieForRetrofit))
                        descriptionViewModel.deleteMovieToWatchFromLocalDB(authViewModel.uiState.user!!.id, movieId!!)
                        if(!descriptionViewModel.uiState.watchedMovieStatus){
                            journalsViewModel.deleteMovieByIdFromLocalDB(movieId!!)
                        }
                        if(descriptionViewModel.uiState.movieInfo == null){
                            navController.navigateUp()
                        }
                    }
                    descriptionViewModel.checkMovieToWatch(movieId, authViewModel.uiState.user?.id?:0)
                }
            )
            DropdownMenuItem(
                text = { Text(watchedText)},
                onClick = {
                    val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                    val movieForRetrofit = RoomMovieInfoForRetrofit(movieId!!)
                    if(!descriptionViewModel.uiState.watchedMovieStatus){
                        descriptionViewModel.addMovieToDB(movieForRetrofit)
                        descriptionViewModel.addWatchedMovieToDB(WatchedMoviesForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieForRetrofit))
                        if(!descriptionViewModel.uiState.movieToWatchStatus){
                            if(descriptionViewModel.uiState.movieInfo != null){
                                var movie = descriptionViewModel.uiState.movieInfo!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.fees?.world?.value?:0, feesUsa = movie.fees?.usa?.value?:0,
                                    budget = movie.budget?.value?:0, posterUrl = movie.poster?.url?:"", worldPremier = movie.premiere?.world?:"", russiaPremier = movie.premiere?.russia?:"",
                                    kpRating = movie.rating?.kp?:0.0, imdbRating = movie.rating?.imdb?:0.0, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength,
                                    totalSeriesLength = movie.totalSeriesLength))
                                journalsViewModel.writeCountriesToLocalDB(movie.countries, movie.id!!)
                                journalsViewModel.writeGenresToLocalDB(movie.genres, movie.id!!)
                                journalsViewModel.writePersonsToLocalDB(movie.persons, movie.id!!)
                                journalsViewModel.writeSeasonsInfoToLocalDB(movie.seasonsInfo, movie.id!!)
                            }else {
                                var movie = descriptionViewModel.uiState.roomMovieInfoForRetrofit!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.feesWorld, feesUsa = movie.feesUsa,
                                    budget = movie.budget?:0, posterUrl = movie.posterUrl?:"", worldPremier = movie.worldPremier?:"", russiaPremier = movie.russiaPremier?:"",
                                    kpRating = movie.kpRating, imdbRating = movie.imdbRating, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength,
                                    totalSeriesLength = movie.totalSeriesLength))
                            }

                        }
                        journalsViewModel.addWatchedMovieToLocalDB(WatchedMovies(userId = authViewModel.uiState.user!!.id, movieId = movieId))
                    }else {
                        val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                        descriptionViewModel.deleteWatchedMovieFromDB(WatchedMoviesForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieForRetrofit))
                        descriptionViewModel.deleteWatchedMovieFromLocalDB(authViewModel.uiState.user!!.id, movieId!!)
                        if(!descriptionViewModel.uiState.movieToWatchStatus){
                            journalsViewModel.deleteMovieByIdFromLocalDB(movieId)
                        }
                        if(descriptionViewModel.uiState.movieInfo == null){
                            navController.navigateUp()
                        }
                    }
                    descriptionViewModel.checkWatchedMovie(movieId, authViewModel.uiState.user?.id?:0)
                }
            )
        }
    }
}