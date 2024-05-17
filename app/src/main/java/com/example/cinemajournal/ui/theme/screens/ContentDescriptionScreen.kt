package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.cinemajournal.CinemaRowModel
import com.example.cinemajournal.ItemRowModel
import com.example.cinemajournal.R
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatch
import com.example.cinemajournal.data.models.RoomModels.MoviesToWatchForRetrofit
import com.example.cinemajournal.data.models.RoomModels.PersonsForRetrofit
import com.example.cinemajournal.data.models.RoomModels.SeasonsInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.data.models.SeasonsInfo
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ItemDescriptionUiState
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.compose.AppTheme
import com.example.example.Persons
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentDescriptionScreen(
    navController: NavController,
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel,
    journalsViewModel: JournalsViewModel,
    context: Context
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            //.padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(ScrollState(0)),
    ) {
        Log.d("R", "got: ${descriptionViewModel.uiState.movieInfo}")
        if (descriptionViewModel.uiState.movieInfo != null || descriptionViewModel.uiState.roomMovieInfoForRetrofit != null) {
            //descriptionViewModel.refreshCurrentMovie2()
            if(descriptionViewModel.uiState.dateToWatch == null){
                descriptionViewModel.getReminderDateAndTime(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0)
            }
            Content(descriptionViewModel)
        } else
            Text(
                text = stringResource(R.string.loading),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
            )

        val dateDialogueState = rememberMaterialDialogState()
        val timeDialogueState = rememberMaterialDialogState()
        
        if(descriptionViewModel.uiState.reminderDialogStatus){
            reminderDialogue(descriptionViewModel = descriptionViewModel, authViewModel = authViewModel, journalsViewModel = journalsViewModel, dateDialogueState = dateDialogueState, timeDialogueState = timeDialogueState, context = context)
        }

        MaterialDialog(
            dialogState = dateDialogueState,
            buttons = {
                positiveButton(text = stringResource(R.string.accept)) {

                    //val createNotification = CreateNotification(context)
                    //createNotification.scheduleNotification(2024, Calendar.APRIL, 9, 14, 28)
                }
                negativeButton (text = stringResource(R.string.cancel))
            }
        )
        {
            datepicker (
                initialDate = LocalDate.now(),
                title = stringResource(R.string.select_a_date),
            ){
                descriptionViewModel.changeDateToWatch(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(it))
            }
        }

        MaterialDialog(
            dialogState = timeDialogueState,
            buttons = {
                positiveButton(text = stringResource(R.string.accept)) {
                    //val createNotification = CreateNotification(context)
                    //createNotification.scheduleNotification(2024, Calendar.APRIL, 9, 14, 28)
                }
                negativeButton (text = stringResource(R.string.cancel))
            }
        )
        {
            timepicker (
                initialTime = LocalTime.now(),
                title = stringResource(R.string.select_a_time),
                is24HourClock = true
            ){
                descriptionViewModel.changeTimeToWatch(it.hour, it.minute)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Content(descriptionViewModel: DescriptionViewModel) {

    var showSeriesInfo by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model =
                if (descriptionViewModel.uiState.movieInfo != null) {
                    descriptionViewModel.uiState.movieInfo?.poster?.url?:R.drawable.poster_placeholder
                } else if(descriptionViewModel.uiState.roomMovieInfoForRetrofit != null) {
                    descriptionViewModel.uiState.roomMovieInfoForRetrofit?.posterUrl?:R.drawable.poster_placeholder
                } else {
                    painterResource(id = R.drawable.poster_placeholder)
                }
            ,
            contentDescription = stringResource(R.string.poster_not_loaded),
            //loading = placeholder(painterResource(id = R.drawable.poster_placeholder)),
            //failure = placeholder(painterResource(id = R.drawable.poster_placeholder)),
            modifier = Modifier
                .height(280.dp)
                .border(4.dp, MaterialTheme.colorScheme.primary),
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${stringResource(R.string.year)}: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(if (descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo?.year.toString() else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.year.toString())
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(stringResource(R.string.kp_rating_short))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(if (descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo?.rating?.kp.toString() else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.kpRating.toString())
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${stringResource(R.string.genre)}: \n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        var g = ""
                        if (descriptionViewModel.uiState.movieInfo != null) {
                            for (i in descriptionViewModel.uiState.movieInfo?.genres
                                ?: emptyList()) {
                                g += i.name + ", "
                            }
                        } else {
                            for (i in descriptionViewModel.uiState.roomMovieInfoForRetrofit?.genres
                                ?: emptyList()) {
                                g += i.name + ", "
                            }
                        }

                        append(g)
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${stringResource(R.string.time)}: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(if (descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo?.movieLength.toString() + " мин" else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.movieLength.toString() + "мин")
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${stringResource(R.string.age)}: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(if (descriptionViewModel.uiState.movieInfo != null) "${descriptionViewModel.uiState.movieInfo?.ageRating.toString()}+" else "${descriptionViewModel.uiState.roomMovieInfoForRetrofit?.ageRating.toString()}+")
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${stringResource(R.string.budget)}: \n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(if (descriptionViewModel.uiState.movieInfo != null) "${descriptionViewModel.uiState.movieInfo?.budget?.value} $" else "${descriptionViewModel.uiState.roomMovieInfoForRetrofit?.budget} $")
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${stringResource(R.string.fees_world)}: \n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append(if (descriptionViewModel.uiState.movieInfo != null) "${descriptionViewModel.uiState.movieInfo?.fees?.world?.value} $" else "${descriptionViewModel.uiState.roomMovieInfoForRetrofit?.feesWorld} $")
                    }
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = if (descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo?.description
            ?: "" else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.description ?: "",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.secondary,
        lineHeight = 16.sp
    )

    if (descriptionViewModel.uiState.movieInfo?.typeNumber == 2 || descriptionViewModel.uiState.movieInfo?.typeNumber == 5 || descriptionViewModel.uiState.roomMovieInfoForRetrofit?.typeNumber == 2 || descriptionViewModel.uiState.roomMovieInfoForRetrofit?.typeNumber == 5) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.seasons_and_episodes),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            itemsIndexed(
                if (descriptionViewModel.uiState.movieInfo != null) {
                    descriptionViewModel.uiState.movieInfo?.seasonsInfo ?: emptyList()
                } else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.seasonsInfo
                    ?: emptyList()

            ) { _, item ->
                if (descriptionViewModel.uiState.movieInfo != null) {
                    seriesItemRow(item as SeasonsInfo, null)
                } else seriesItemRow(null, item as SeasonsInfoForRetrofit)
            }
        }
    }


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(R.string.directors),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        itemsIndexed(
            if (descriptionViewModel.uiState.movieInfo != null) {
                descriptionViewModel.uiState.movieInfo?.persons!!.filter { it.enProfession == "director" }
            } else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.persons!!.filter { it.enProfession == "director" }
        ) { _, item ->
            if (descriptionViewModel.uiState.movieInfo != null) {
                photoItemRow(item as Persons, null)
            } else photoItemRow(null, item as PersonsForRetrofit)
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(R.string.actors),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {
        itemsIndexed(
            if (descriptionViewModel.uiState.movieInfo != null) {
                descriptionViewModel.uiState.movieInfo?.persons!!.filter { it.enProfession == "actor" }
            } else descriptionViewModel.uiState.roomMovieInfoForRetrofit?.persons!!.filter { it.enProfession == "actor" }
        ) { _, item ->
            if (descriptionViewModel.uiState.movieInfo != null) {
                photoItemRow(item as Persons, null)
            } else photoItemRow(null, item as PersonsForRetrofit)
        }
    }
}

@Composable
fun reminderDialogue(descriptionViewModel: DescriptionViewModel, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel, dateDialogueState: MaterialDialogState, timeDialogueState: MaterialDialogState, context: Context) {
    val titleForNotification = stringResource(R.string.time_to_watch)
    AlertDialog(
        icon = {
            //Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(
                text = stringResource(R.string.set_a_reminder),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column() {
                 Row() {

                     ClickableText(
                         text = AnnotatedString("${stringResource(R.string.select_a_date)}: "),
                         style = TextStyle(
                             fontSize = 16.sp,
                         ),
                         onClick = {
                             dateDialogueState.show()
                         }
                     )

                     Text(
                         text = descriptionViewModel.uiState.dateToWatch?:"",
                         fontSize = 16.sp,
                     )

                }
                Spacer(modifier = Modifier.height(24.dp))
                Row() {

                    ClickableText(
                        text = AnnotatedString("${stringResource(R.string.select_a_time)}: "),
                        style = TextStyle(
                            fontSize = 16.sp,
                        ),
                        onClick = {
                            timeDialogueState.show()
                        }
                    )

                    Text(
                        text = "${descriptionViewModel.uiState.hoursToWatch?:""}:${descriptionViewModel.uiState.minutesToWatch?:""}",
                        fontSize = 16.sp,
                    )

                }
            }
        },
        onDismissRequest = {
            descriptionViewModel.updateReminderDialogueStatus(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    descriptionViewModel.addMovieToWatchToDB(MoviesToWatchForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = descriptionViewModel.uiState.roomMovieInfoForRetrofit, reminderDate = descriptionViewModel.uiState.dateToWatch, reminderHour = descriptionViewModel.uiState.hoursToWatch, reminderMinute = descriptionViewModel.uiState.minutesToWatch))
                    journalsViewModel.addMovieToWatchToLocalDB(MoviesToWatch(userId = authViewModel.uiState.user!!.id, movieId = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id!!, reminderDate = descriptionViewModel.uiState.dateToWatch, reminderHour = descriptionViewModel.uiState.hoursToWatch, reminderMinute = descriptionViewModel.uiState.minutesToWatch))
                    val createNotification = CreateNotification(context, titleForNotification, descriptionViewModel.uiState.roomMovieInfoForRetrofit?.name?:"")
                    createNotification.scheduleNotification(
                        descriptionViewModel.uiState.dateToWatch?.substringAfterLast("/")?.toInt()?:2024,
                        descriptionViewModel.uiState.dateToWatch?.substringAfter("/")?.substringBefore("/")?.removePrefix("0")?.toInt()
                            ?.minus(1)
                            ?:4,
                        descriptionViewModel.uiState.dateToWatch?.substringBefore("/")?.removePrefix("0")?.toInt()?:3,
                        descriptionViewModel.uiState.hoursToWatch?:11,
                        descriptionViewModel.uiState.minutesToWatch?:0
                    )
                    Log.d("R", "Уведомление сделано, дата: ${descriptionViewModel.uiState.dateToWatch} ; ${descriptionViewModel.uiState.hoursToWatch}:${descriptionViewModel.uiState.minutesToWatch}")
                    Log.d("R", "${descriptionViewModel.uiState.dateToWatch?.substringAfterLast("/")?.toInt()?:2024}")
                    Log.d("R", "${descriptionViewModel.uiState.dateToWatch?.substringAfter("/")?.substringBefore("/")?.removePrefix("0")?.toInt()
                        ?.minus(1)
                        ?:4}")
                    Log.d("R", "${descriptionViewModel.uiState.dateToWatch?.substringBefore("/")?.removePrefix("0")?.toInt()?:3}")
                    descriptionViewModel.updateReminderDialogueStatus(false)
                }
            ) {
                Text(stringResource(R.string.remind))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    descriptionViewModel.updateReminderDialogueStatus(false)
                    descriptionViewModel.changeDateToWatch(null)
                    descriptionViewModel.changeTimeToWatch(null, null)
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

/*@Preview(showBackground = true)
@Composable
fun DescriptionPreview() {
    AppTheme {
        ContentDescriptionScreen(navController = rememberNavController(), uiState = ItemDescriptionUiState(
            title = "Железный человек",
            year = "2008",
            score = "8.0",
            genre = "фантастика, боевик, приключение",
            time = "2 ч 1 м",
            age = "12+",
            budget = "75 000 000 $",
            worldBoxOffice = "585 366 247 $",
            description = "Афганским тМиллиардер-изобретатель Тони Старк попадает в плен к еррористам, которые пытаются заставить его создать оружие массового поражения. В тайне от своих захватчиков Старк конструирует высокотехнологичную киберброню, которая помогает ему сбежать. Однако по возвращении в США он узнаёт, что в совете директоров его фирмы плетётся заговор, чреватый страшными последствиями. Используя своё последнее изобретение, Старк пытается решить проблемы своей компании радикально...",
            directors = listOf(
                ItemRowModel(imageId = R.drawable.jon, title = "Джон Фавро")
            ),
            actors = listOf(
                ItemRowModel(imageId = R.drawable.robert, title = "Роберт Дауни мл."),
                ItemRowModel(imageId = R.drawable.jeff, title = "Джефф Бриджес"),
                ItemRowModel(imageId = R.drawable.gwinet, title = "Гвинет Пэлтроу"),
                ItemRowModel(imageId = R.drawable.jon, title = "Джон Фавро")
            ),

            ))
    }
}*/