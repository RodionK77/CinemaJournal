package com.example.cinemajournal.ui.theme.screens

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cinemajournal.R
import com.example.cinemajournal.data.models.RoomModels.CountriesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.GenresForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.compose.AppTheme
import com.example.example.Countries
import com.example.example.Genres
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ContentAdditionScreen(navController: NavController, additionViewModel: AdditionViewModel, authViewModel: AuthViewModel){


    var showDialog by rememberSaveable{ mutableStateOf(false) }
    var dialogueText by rememberSaveable { mutableStateOf("") }
    var dialogueSate by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            //.padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(ScrollState(0)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.name)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = additionViewModel.uiState.nameText,
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeNameText(it)
                },
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.year)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.width(100.dp),
                value = additionViewModel.uiState.yearText,
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeYearText(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.rating)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            TextField(
                modifier = Modifier.width(100.dp),
                value = additionViewModel.uiState.ratingText.toString(),
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeRatingText(it.toDouble())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.time)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.width(100.dp),
                value = additionViewModel.uiState.timeText.toString(),
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeTimeText(it.toInt())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.age)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.width(100.dp),
                value = additionViewModel.uiState.ageRatingText,
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeAgeRatingText(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.budget)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.width(144.dp),
                value = additionViewModel.uiState.budgetText.toString(),
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeBudgetText(it.toInt())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.fees_world)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.width(144.dp),
                value = additionViewModel.uiState.feesWorldText.toString(),
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeFeesWorldText(it.toLong())
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {

            Text(
                text = stringResource(R.string.genre),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        dialogueSate = true
                        showDialog = !showDialog
                    }
                    .padding(start = 2.dp),
                imageVector = Icons.Outlined.Add,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(R.string.add)
            )
            if(additionViewModel.uiState.genres.isNotEmpty()){
                Icon(
                    modifier = Modifier
                        .scale(0.8f)
                        .clickable {
                            additionViewModel.changeGenres(mutableListOf())
                        },
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_all)
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
        ) {
            itemsIndexed(
                additionViewModel.uiState.genres
            ) { _, item ->
                Text(
                    item.name?:"",
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {

            Text(
                text = stringResource(R.string.countries),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        dialogueSate = false
                        showDialog = !showDialog
                    }
                    .padding(start = 2.dp),
                imageVector = Icons.Outlined.Add,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(R.string.add)
            )
            if(additionViewModel.uiState.countries.isNotEmpty()){
                Icon(
                    modifier = Modifier
                        .scale(0.8f)
                        .clickable {
                            additionViewModel.changeCountries(mutableListOf())
                        },
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete_all)
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
        ) {
            itemsIndexed(
                additionViewModel.uiState.countries
            ) { _, item ->
                Text(
                    item.name?:"",
                    color = MaterialTheme.colorScheme.surfaceTint,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "${stringResource(R.string.description)}:",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextField(
                value = additionViewModel.uiState.descriptionText,
                placeholder = {Text(
                    text = "...",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )},
                onValueChange = {
                    additionViewModel.changeDescriptionText(it)
                },
                textStyle = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    disabledTextColor = MaterialTheme.colorScheme.onSecondary,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIndicatorColor =  MaterialTheme.colorScheme.onSecondary,
                ),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val composableScope = rememberCoroutineScope()

        Button(onClick = {
            composableScope.launch {
                additionViewModel.addMovieToDB(RoomMovieInfoForRetrofit(name = additionViewModel.uiState.nameText))

                while (additionViewModel.uiState.responseId == null) {
                    Log.d(
                        "R",
                        "ждём id фильма",
                    )
                    delay(50)
                }

                additionViewModel.changeIdInLists(additionViewModel.uiState.responseId?:0)

                Log.d(
                    "R",
                    "жанры: ${additionViewModel.uiState.genres.toString()}",
                )

                val movieInfo = RoomMovieInfoForRetrofit(
                    id = additionViewModel.uiState.responseId,
                    name = additionViewModel.uiState.nameText,
                    feesWorld = additionViewModel.uiState.feesWorldText,
                    budget = additionViewModel.uiState.budgetText,
                    worldPremier = additionViewModel.uiState.yearText,
                    kpRating = additionViewModel.uiState.ratingText,
                    typeNumber = 1,
                    movieLength = additionViewModel.uiState.timeText,
                    description = additionViewModel.uiState.descriptionText,
                    year = if(additionViewModel.uiState.yearText.isNotEmpty()) additionViewModel.uiState.yearText.toInt() else 0,
                    ageRating = additionViewModel.uiState.ageRatingText,
                    countriesStr = additionViewModel.uiState.countries.mapNotNull { it.name }.joinToString(", "),
                    genresStr = additionViewModel.uiState.genres.mapNotNull { it.name }.joinToString(", "),
                    //countries = additionViewModel.uiState.countries,
                    //genres = additionViewModel.uiState.genres,
                )

                Log.d(
                    "R",
                    "фильм: ${movieInfo}",
                )

                additionViewModel.addMovieToDB(movieInfo)
                additionViewModel.addWatchedMovieToDB(WatchedMoviesForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieInfo))

                var movie = RoomMovieInfo(
                    id = additionViewModel.uiState.responseId!!,
                    name = additionViewModel.uiState.nameText,
                    feesWorld = additionViewModel.uiState.feesWorldText,
                    budget = additionViewModel.uiState.budgetText,
                    worldPremier = additionViewModel.uiState.yearText,
                    kpRating = additionViewModel.uiState.ratingText,
                    typeNumber = 1,
                    movieLength = additionViewModel.uiState.timeText,
                    description = additionViewModel.uiState.descriptionText,
                    year = if(additionViewModel.uiState.yearText.isNotEmpty()) additionViewModel.uiState.yearText.toInt() else 0,
                    ageRating = additionViewModel.uiState.ageRatingText,
                )
                val countriesList: List<Countries> = additionViewModel.uiState.countries.toCountriesList()
                val genresList: List<Genres> = additionViewModel.uiState.genres.toGenresList()
                additionViewModel.addMovieToLocalDB(movie)
                additionViewModel.writeCountriesToLocalDB(countriesList,additionViewModel.uiState.responseId!!)
                additionViewModel.writeGenresToLocalDB(genresList, additionViewModel.uiState.responseId!!)
                additionViewModel.addWatchedMovieToLocalDB(WatchedMovies(userId = authViewModel.uiState.user!!.id, movieId = additionViewModel.uiState.responseId!!))

            }
        }) {
            Text(stringResource(R.string.add_to_watched))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            composableScope.launch {

                val movieForRetrofit = RoomMovieInfoForRetrofit(name = additionViewModel.uiState.nameText)
                additionViewModel.addMovieToDB(movieForRetrofit)

                while (additionViewModel.uiState.responseId == null) {
                    Log.d(
                        "R",
                        "ждём id фильма",
                    )
                    delay(50)
                }

                additionViewModel.changeIdInLists(additionViewModel.uiState.responseId?:0)

                val movieInfo = RoomMovieInfoForRetrofit(
                    id = additionViewModel.uiState.responseId,
                    name = additionViewModel.uiState.nameText,
                    feesWorld = additionViewModel.uiState.feesWorldText,
                    budget = additionViewModel.uiState.budgetText,
                    worldPremier = additionViewModel.uiState.yearText,
                    kpRating = additionViewModel.uiState.ratingText,
                    typeNumber = 1,
                    movieLength = additionViewModel.uiState.timeText,
                    description = additionViewModel.uiState.descriptionText,
                    year = if(additionViewModel.uiState.yearText.isNotEmpty()) additionViewModel.uiState.yearText.toInt() else 0,
                    ageRating = additionViewModel.uiState.ageRatingText,
                    countriesStr = additionViewModel.uiState.countries.mapNotNull { it.name }.joinToString(", "),
                    genresStr = additionViewModel.uiState.genres.mapNotNull { it.name }.joinToString(", "),
                    //countries = additionViewModel.uiState.countries,
                    //genres = additionViewModel.uiState.genres,
                )

                additionViewModel.addMovieToDB(movieInfo)
                additionViewModel.addMovieToWatchToDB(MoviesToWatchForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieInfo))

                var movie = RoomMovieInfo(
                    id = additionViewModel.uiState.responseId!!,
                    name = additionViewModel.uiState.nameText,
                    feesWorld = additionViewModel.uiState.feesWorldText,
                    budget = additionViewModel.uiState.budgetText,
                    worldPremier = additionViewModel.uiState.yearText,
                    kpRating = additionViewModel.uiState.ratingText,
                    typeNumber = 1,
                    movieLength = additionViewModel.uiState.timeText,
                    description = additionViewModel.uiState.descriptionText,
                    year = if(additionViewModel.uiState.yearText.isNotEmpty()) additionViewModel.uiState.yearText.toInt() else 0,
                    ageRating = additionViewModel.uiState.ageRatingText,
                )
                val countriesList: List<Countries> = additionViewModel.uiState.countries.toCountriesList()
                val genresList: List<Genres> = additionViewModel.uiState.genres.toGenresList()
                additionViewModel.addMovieToLocalDB(movie)
                additionViewModel.writeCountriesToLocalDB(countriesList,additionViewModel.uiState.responseId!!)
                additionViewModel.writeGenresToLocalDB(genresList, additionViewModel.uiState.responseId!!)
                additionViewModel.addMovieToWatchToLocalDB(MoviesToWatch(userId = authViewModel.uiState.user!!.id, movieId = additionViewModel.uiState.responseId!!))

            }
        }) {
            Text(stringResource(R.string.add_to_wanted_to_watch))
        }

        if (showDialog) {
            AlertDialog(
                icon = {
                    //Icon(icon, contentDescription = "Example Icon")
                },
                title = {
                    Text(stringResource(R.string.monosyllabic_text))
                },
                text = {
                    TextField(
                        value = dialogueText,
                        onValueChange = {
                            dialogueText = it
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        ),
                        singleLine = true,
                    )
                },
                onDismissRequest = {
                    showDialog = false
                    dialogueText = ""
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if(dialogueSate){
                                additionViewModel.addGenre(GenresForRetrofit(genresId = null, contentId = null, name = dialogueText))
                            }else {
                                additionViewModel.addCountry(CountriesForRetrofit(countriesId = null, contentId = null, name = dialogueText))
                            }
                            dialogueText = ""
                            showDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.accept))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            dialogueText = ""
                            showDialog = false
                        }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
    }

    /*if (/*showDialog*/) {
        AlertDialog(
            icon = {
                //Icon(icon, contentDescription = "Example Icon")
            },
            title = {
                Text(stringResource(R.string.monosyllabic_text))
            },
            text = {
                TextField(
                    value = ""/*dialogueText*/,
                    onValueChange = {
                        //dialogueText = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    singleLine = true,
                )
            },
            onDismissRequest = {
                //showDialog = false
                //dialogueText = ""
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        /*if(dialogueSate){
                            reviewViewModel.addLike(Likes(userId = authViewModel.uiState.user?.id?:0, movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, description = dialogueText))
                        }else {
                            reviewViewModel.addDislike(Dislikes(userId = authViewModel.uiState.user?.id?:0, movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, description = dialogueText))
                        }
                        dialogueText = ""
                        showDialog = false*/
                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        //dialogueText = ""
                        //showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }*/
}

fun CountriesForRetrofit.toCountries(): Countries {
    return Countries(name)
}

fun MutableList<CountriesForRetrofit>.toCountriesList(): List<Countries> {
    return this.map { it.toCountries() }
}

fun GenresForRetrofit.toGenres(): Genres {
    return Genres(name)
}

fun MutableList<GenresForRetrofit>.toGenresList(): List<Genres> {
    return this.map { it.toGenres() }
}

//@Preview(showBackground = true)
//@Composable
//fun AdditionPreview() {
//    AppTheme {
//        ContentAdditionScreen()
//    }
//}