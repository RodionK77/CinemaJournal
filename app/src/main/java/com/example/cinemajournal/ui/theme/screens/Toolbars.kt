package com.example.cinemajournal.ui.theme.screens

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Print
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.placeholder
import com.example.cinemajournal.R
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.RoomModels.WatchedMovies
import com.example.cinemajournal.data.models.RoomModels.WatchedMoviesForRetrofit
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.example.example.MovieInfo
import com.itextpdf.html2pdf.HtmlConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.io.StringReader


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalsToolbar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, journalsViewModel: JournalsViewModel, authViewModel: AuthViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                stringResource(R.string.journals),
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing= 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            if(authViewModel.uiState.user?.role == 0){
                IconButton(onClick = {
                    navController.navigate("ContentAdditionScreen")
                }) {
                    Icon(
                        imageVector = Icons.Outlined.AddBox,
                        contentDescription = "Localized description"
                    )
                }
            }
            IconButton(onClick = {
                journalsViewModel.changeAccountDialogState(true)
            }) {
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
fun AdditionToolbar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, additionViewModel: AdditionViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Text("Добавление фильма", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
                additionViewModel.changeId(null)
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
            /*IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = "Localized description"
                )
            }*/
            IconButton(onClick = {
                navController.navigateUp()
                additionViewModel.clearAllField()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryToolbar(scrollBehavior: TopAppBarScrollBehavior, galleryViewModel: GalleryViewModel, authViewModel: AuthViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if(!galleryViewModel.uiState.isSearch){
                Text(
                    text = stringResource(R.string.gallery),
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
                        text = stringResource(R.string.search_query),
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
                                Text(stringResource(R.string.search), fontSize = 20.sp)
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
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = {
            if(galleryViewModel.uiState.isSearch || authViewModel.uiState.user?.role?:0 == 1){
                IconButton(onClick = { galleryViewModel.changeDrawerState(!galleryViewModel.uiState.drawerState)
                    Log.d("R", galleryViewModel.uiState.drawerState.toString(), )}) {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = "Localized description"
                    )
                }
            }

            if(!galleryViewModel.uiState.isSearch && authViewModel.uiState.user?.role?:0 == 0){
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
fun ContentToolbar(navController: NavController, currentDestination: String, descriptionViewModel: DescriptionViewModel, reviewViewModel: ReviewViewModel, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel){

    val context = LocalContext.current

    if(descriptionViewModel.uiState.movieInfo != null){
        descriptionViewModel.checkMovieToWatch(descriptionViewModel.uiState.movieInfo?.id?: 0, authViewModel.uiState.user?.id?:0)
        descriptionViewModel.checkWatchedMovie(descriptionViewModel.uiState.movieInfo?.id?: 0, authViewModel.uiState.user?.id?:0)
    } else {
        descriptionViewModel.checkMovieToWatch(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?: 0, authViewModel.uiState.user?.id?:0)
        descriptionViewModel.checkWatchedMovie(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?: 0, authViewModel.uiState.user?.id?:0)
    }

    var toWatchText = if(!descriptionViewModel.uiState.movieToWatchStatus) stringResource(R.string.add_to_wanted_to_watch) else stringResource(R.string.delete_from_wanted_to_watch)
    var watchedText = if(!descriptionViewModel.uiState.watchedMovieStatus) stringResource(R.string.add_to_watched) else stringResource(R.string.delete_from_watched)


    val review_string_for_print = stringResource(R.string.review)
    val rating_string_for_print = stringResource(R.string.rating)
    val likes_string_for_print = stringResource(R.string.likes)
    val dislikes_string_for_print = stringResource(R.string.dislikes)
    val notes_string_for_print = stringResource(R.string.notes)

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
            if(currentDestination == "ContentReviewScreen"){
                IconButton(onClick = {
                    savePDF(createPDFFromHtml(createHtml(reviewViewModel, review_string_for_print, rating_string_for_print, likes_string_for_print, dislikes_string_for_print, notes_string_for_print), context, reviewViewModel.uiState.roomMovieInfoForRetrofit?.name?:""), context)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Print,
                        contentDescription = "Localized description"
                    )
                }
            }
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
                contentDescription = stringResource(R.string.expand)
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
            if(descriptionViewModel.uiState.movieToWatchStatus){
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.remind_to_watch))},
                    onClick = {
                        descriptionViewModel.updateReminderDialogueStatus(true)
                    }
                )
            }
        }
    }
}

fun createHtml(reviewViewModel: ReviewViewModel, review: String, rating: String, likes: String, dislikes: String, notes: String): String{
    return buildString {
        appendHTML().html {
            head {
                title {  +"${reviewViewModel.uiState.roomMovieInfoForRetrofit?.name?:"ревью на фильм"}" }
            }
            body {
                h1 { +"${review}: ${reviewViewModel.uiState.roomMovieInfoForRetrofit?.name}" }
                p {
                    em { +"${rating}: "}
                    +"${reviewViewModel.uiState.rating}"
                }
                var likes = ""
                reviewViewModel.uiState.likesForReview?.forEach {
                    likes = "$likes${it.description}, "
                }
                var dislikes = ""
                reviewViewModel.uiState.dislikesForReview?.forEach {
                    dislikes = "$dislikes${it.description}, "
                }
                p {
                    em { +"${likes}: "}
                    +"$likes"
                }
                p {
                    em { +"${dislikes}: "}
                    +"$dislikes"
                }
                p {
                    em { +"${notes}: "}
                    +"${reviewViewModel.uiState.reviewText}"
                }
            }
        }
    }
}

fun createPDFFromHtml(html: String, context: Context, name: String): File? {


    val outputStream = ByteArrayOutputStream()

    HtmlConverter.convertToPdf(html, outputStream)

    val pdfFile = File(context.cacheDir, "Ревью_на_фильм_${name}.pdf")

    FileOutputStream(pdfFile).use { fileOutputStream ->
        outputStream.writeTo(fileOutputStream)
    }

    return pdfFile
}

fun savePDF(pdfFile: File?, context: Context){

    if(pdfFile!=null){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        val pdfUri = FileProvider.getUriForFile(context, context.packageName + ".provider", pdfFile)
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, "Share PDF with"),
            null
        )
    }


}