package com.example.cinemajournal.ui.theme.screens
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.cinemajournal.ItemRowModel
import com.example.cinemajournal.R
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.DislikesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.data.models.RoomModels.LikesForRetrofit
import com.example.cinemajournal.data.models.RoomModels.Review
import com.example.cinemajournal.data.models.RoomModels.ReviewForRetrofit
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.data.models.RoomModels.User
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ItemDescriptionUiState
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.example.compose.AppTheme
import com.smarttoolfactory.ratingbar.RatingBar
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.html.InputType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import androidx.work.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentReviewScreen(navController: NavController, reviewViewModel: ReviewViewModel, authViewModel: AuthViewModel, context: Context) {

    Content(reviewViewModel, authViewModel, context)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun Content(reviewViewModel: ReviewViewModel, authViewModel: AuthViewModel, context: Context) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            //.padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(ScrollState(0)),
    ) {

        var descriptionIsExpanded = rememberSaveable {
            mutableStateOf(false)
        }

        if(reviewViewModel.uiState.likesForReview == null){
            reviewViewModel.changeLikes(reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.likes?.toMutableList())
        }
        if(reviewViewModel.uiState.dislikesForReview == null){
            reviewViewModel.changeDislikes(reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.dislikes?.toMutableList())
        }
        if(reviewViewModel.uiState.rating == null){
            reviewViewModel.changeRating(reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.rating?.toFloat()?:0.0f)
        }
        if(reviewViewModel.uiState.reviewText == null){
            reviewViewModel.changeReviewText(reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.notes?:"")
        }
        if(reviewViewModel.uiState.dateWatched == null){
            reviewViewModel.changeDate(reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.dateWatched)
        }


        var showDialog by rememberSaveable{ mutableStateOf(false) }
        var dialogueText by rememberSaveable { mutableStateOf("") }

        var dialogueSate by rememberSaveable { mutableStateOf(false) }

        var likes: List<Likes>? = reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.likes
        var dislikes: List<Dislikes>? = reviewViewModel.uiState.roomMovieInfoForRetrofit?.review?.dislikes

        val dateDialogueState = rememberMaterialDialogState()

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GlideImage(
                model = reviewViewModel.uiState.roomMovieInfoForRetrofit?.posterUrl,
                contentDescription = stringResource(R.string.poster_not_loaded),
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
                            append("Год: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(reviewViewModel.uiState.roomMovieInfoForRetrofit?.year.toString())
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
                            append("${stringResource(R.string.kp_rating_short)}: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(reviewViewModel.uiState.roomMovieInfoForRetrofit?.kpRating.toString())
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
                            for (i in reviewViewModel.uiState.roomMovieInfoForRetrofit?.genres?: emptyList()) {
                                g += i.name + ", "
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
                            append(reviewViewModel.uiState.roomMovieInfoForRetrofit?.movieLength.toString())
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
                            append(reviewViewModel.uiState.roomMovieInfoForRetrofit?.ageRating + "+")
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
                            append(reviewViewModel.uiState.roomMovieInfoForRetrofit?.budget.toString() + "$")
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
                            append(reviewViewModel.uiState.roomMovieInfoForRetrofit?.feesWorld.toString() + "$")
                        }
                    }
                )
                Row(
                    modifier = Modifier.clickable {
                        descriptionIsExpanded.value = !descriptionIsExpanded.value
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.description),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Icon(
                        modifier = Modifier.scale(0.8f),
                        imageVector = if (descriptionIsExpanded.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = stringResource(R.string.expand)
                    )

                }
            }
        }

        if (descriptionIsExpanded.value) {
            Text(
                text = reviewViewModel.uiState.roomMovieInfoForRetrofit?.description?:"",
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        RatingBar(
            rating = reviewViewModel.uiState.rating?:0.0f,
            imageVectorEmpty = Icons.Outlined.Grade,
            imageVectorFilled = Icons.Filled.Grade,
            tintEmpty = MaterialTheme.colorScheme.outline,
            tintFilled = MaterialTheme.colorScheme.onPrimaryContainer,
            itemSize = 36.dp,
            itemCount = 10,

        ) {
            reviewViewModel.changeRating(it)
        }

        Spacer(modifier = Modifier.height(8.dp))

        ClickableText(
            modifier = Modifier.align(Alignment.Start),
            text = AnnotatedString("${stringResource(R.string.date_of_watch)}: ${reviewViewModel.uiState.dateWatched?:""}"),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            onClick = {
                dateDialogueState.show()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {

            Text(
                text = stringResource(R.string.likes),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
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
            if(reviewViewModel.uiState.likesForReview?.isNotEmpty() == true){
                Icon(
                    modifier = Modifier
                        .scale(0.8f)
                        .clickable {
                            reviewViewModel.changeLikes(mutableListOf())
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
                reviewViewModel.uiState.likesForReview?: emptyList()
            ) { _, item ->
                wordItemRow(text = item.description?:"")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = stringResource(R.string.dislikes),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
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
            if(reviewViewModel.uiState.dislikesForReview?.isNotEmpty() == true){
                Icon(
                    modifier = Modifier
                        .scale(0.8f)
                        .clickable {
                            reviewViewModel.changeDislikes(mutableListOf())
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
                reviewViewModel.uiState.dislikesForReview?: emptyList()
            ) { _, item ->
                wordItemRow(text = item.description?:"")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(R.string.notes),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = reviewViewModel.uiState.reviewText?:"",
            onValueChange = {
                reviewViewModel.changeReviewText(it)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {

            reviewViewModel.addReviewToDB(ReviewForRetrofit(user = authViewModel.uiState.user, movie = RoomMovieInfoForRetrofit(id = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0), contentId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, rating = reviewViewModel.uiState.rating?.toDouble()?:0.0, notes = reviewViewModel.uiState.reviewText, likes = reviewViewModel.uiState.likesForReview, dislikes = reviewViewModel.uiState.dislikesForReview, dateWatched = reviewViewModel.uiState.dateWatched))
            reviewViewModel.saveReviewToLocalDB(review = Review(userId = authViewModel.uiState.user?.id?:0, movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, contentId = reviewViewModel.uiState.roomMovieInfoForRetrofit!!.id, rating = reviewViewModel.uiState.rating?.toDouble()?:0.0, notes = reviewViewModel.uiState.reviewText, dateWatched = reviewViewModel.uiState.dateWatched))

            reviewViewModel.deleteLikesFromLocalDB(reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0)
            reviewViewModel.uiState.likesForReview?.forEach{
                reviewViewModel.saveLikesToLocalDB(it)
            }
            reviewViewModel.deleteDislikesFromLocalDB(reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0)
            reviewViewModel.uiState.dislikesForReview?.forEach{
                reviewViewModel.saveDislikesToLocalDB(it)
            }


        }) {
            Text(stringResource(R.string.save))
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                                reviewViewModel.addLike(Likes(userId = authViewModel.uiState.user?.id?:0, movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, description = dialogueText))
                            }else {
                                reviewViewModel.addDislike(Dislikes(userId = authViewModel.uiState.user?.id?:0, movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, description = dialogueText))
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

        MaterialDialog(
            dialogState = dateDialogueState,
            buttons = {
                positiveButton(text = stringResource(R.string.accept)) {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                    //val createNotification = CreateNotification(context)
                    //createNotification.scheduleNotification(2024, Calendar.APRIL, 11, 13, 31)
                }
                negativeButton (text = stringResource(R.string.cancel))
            }
        )
        {
            datepicker (
                initialDate = LocalDate.now(),
                title = stringResource(R.string.select_a_date),
            ){
                reviewViewModel.changeDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(it))
                //pickedDate = it
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialog(){

}

//@Preview(showBackground = true)
//@Composable
//fun ReviewPreview() {
//    AppTheme {
//        ContentReviewScreen(rememberNavController(), ItemDescriptionUiState(
//            title = "Железный человек",
//            englishTitle = "Iron man",
//            year = "2008",
//            score = "8.0",
//            genre = "фантастика, боевик, приключение",
//            time = "2 ч 1 м",
//            age = "12+",
//            budget = "75 000 000 $",
//            worldBoxOffice = "585 366 247 $",
//            description = "Афганским тМиллиардер-изобретатель Тони Старк попадает в плен к террористам, которые пытаются заставить его создать оружие массового поражения. В тайне от своих захватчиков Старк конструирует высокотехнологичную киберброню, которая помогает ему сбежать. Однако по возвращении в США он узнаёт, что в совете директоров его фирмы плетётся заговор, чреватый страшными последствиями. Используя своё последнее изобретение, Старк пытается решить проблемы своей компании радикально...",
//            directors = listOf(
//                ItemRowModel(imageId = R.drawable.jon, title = "Джон Фавро")
//            ),
//            actors = listOf(
//                ItemRowModel(imageId = R.drawable.robert, title = "Роберт Дауни мл."),
//                ItemRowModel(imageId = R.drawable.jeff, title = "Джефф Бриджес"),
//                ItemRowModel(imageId = R.drawable.gwinet, title = "Гвинет Пэлтроу"),
//                ItemRowModel(imageId = R.drawable.jon, title = "Джон Фавро")
//            ),
//            likes = listOf("очень интересный", "увлекательноый"),
//            dislikes = listOf("очень не интересный", "не увлекательноый"),
//        ))
//    }
//}
