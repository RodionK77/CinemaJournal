package com.example.cinemajournal.ui.theme.screens
import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.compose.AppTheme
import com.smarttoolfactory.ratingbar.RatingBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentReviewScreen(navController: NavController, descriptionViewModel: DescriptionViewModel, authViewModel: AuthViewModel) {

    Content(descriptionViewModel, authViewModel)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun Content(descriptionViewModel: DescriptionViewModel, authViewModel: AuthViewModel) {

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

        if(descriptionViewModel.uiState.likesForReview == null){
            descriptionViewModel.changeLikes(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review?.likes?.toMutableList())
        }
        if(descriptionViewModel.uiState.dislikesForReview == null){
            descriptionViewModel.changeDislikes(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review?.dislikes?.toMutableList())
        }

        //var rating: Float by rememberSaveable { mutableStateOf(0.0f) }

        var showDialog by rememberSaveable{ mutableStateOf(false) }
        var dialogueText by rememberSaveable { mutableStateOf("") }
        var reviewText by rememberSaveable { mutableStateOf(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review?.notes?:"") }
        var rating by rememberSaveable { mutableStateOf(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review?.rating?.toFloat()?:0.0f) }

        //var likeText by rememberSaveable { mutableStateOf("") }
        //var dislikeText by rememberSaveable { mutableStateOf("") }

        var dialogueSate by rememberSaveable { mutableStateOf(false) }



        var likes: List<Likes>? = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review?.likes
        var dislikes: List<Dislikes>? = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review?.dislikes

        Log.d(
            "R",
            "Модель: ${descriptionViewModel.uiState.roomMovieInfoForRetrofit?.review}",
        )
        Log.d(
            "R",
            "Лайки: ${likes}",
        )
        Log.d(
            "R",
            "Дизлайки: ${dislikes}",
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GlideImage(
                model = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.posterUrl,
                contentDescription = "Постер фильма",
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
                            append(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.year.toString())
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
                            append("Оценка КП: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.kpRating.toString())
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
                            append("Жанр: \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            var g = ""
                            for (i in descriptionViewModel.uiState.roomMovieInfoForRetrofit?.genres?: emptyList()) {
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
                            append("Время: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.movieLength.toString())
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
                            append("Возраст: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.ageRating + "+")
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
                            append("Бюджет: \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.budget.toString() + "$")
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
                            append("Сборы в мире: \n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        ) {
                            append(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.feesWorld.toString() + "$")
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
                        text = "Описание",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Icon(
                        modifier = Modifier.scale(0.8f),
                        imageVector = if (descriptionIsExpanded.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Развернуть"
                    )

                }
            }
        }

        if (descriptionIsExpanded.value) {
            Text(
                text = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.description?:"",
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        RatingBar(
            rating = rating,
            imageVectorEmpty = Icons.Outlined.Grade,
            imageVectorFilled = Icons.Filled.Grade,
            tintEmpty = MaterialTheme.colorScheme.outline,
            tintFilled = MaterialTheme.colorScheme.onPrimaryContainer,
            itemSize = 36.dp,
            itemCount = 10,

        ) {
            rating = it
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Что понравилось",
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
                contentDescription = "Добавить"
            )
            if(descriptionViewModel.uiState.likesForReview?.isNotEmpty() == true){
                Icon(
                    modifier = Modifier.scale(0.8f).clickable {
                        descriptionViewModel.changeLikes(mutableListOf())
                    },
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить всё"
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
        ) {
            itemsIndexed(
                descriptionViewModel.uiState.likesForReview?: emptyList()
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
                text = "Что не понравилось",
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
                contentDescription = "Добавить"
            )
            if(descriptionViewModel.uiState.dislikesForReview?.isNotEmpty() == true){
                Icon(
                    modifier = Modifier.scale(0.8f).clickable {
                        descriptionViewModel.changeDislikes(mutableListOf())
                    },
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить всё"
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary)
        ) {
            itemsIndexed(
                descriptionViewModel.uiState.dislikesForReview?: emptyList()
            ) { _, item ->
                wordItemRow(text = item.description?:"")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Заметки",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(2.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = reviewText,
            onValueChange = {
                reviewText = it
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            ),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {

            descriptionViewModel.addReviewToDB(ReviewForRetrofit(user = authViewModel.uiState.user, movie = RoomMovieInfoForRetrofit(id = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0), contentId = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, rating = rating.toDouble(), notes = reviewText, likes = descriptionViewModel.uiState.likesForReview, dislikes = descriptionViewModel.uiState.dislikesForReview))
            descriptionViewModel.saveReviewToLocalDB(review = Review(userId = authViewModel.uiState.user?.id?:0, movieId = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, contentId = descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id, rating = rating.toDouble(), notes = reviewText))

            descriptionViewModel.deleteLikesFromLocalDB(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0)
            descriptionViewModel.uiState.likesForReview?.forEach{
                descriptionViewModel.saveLikesToLocalDB(it)
            }
            descriptionViewModel.deleteDislikesFromLocalDB(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0)
            descriptionViewModel.uiState.dislikesForReview?.forEach{
                descriptionViewModel.saveDislikesToLocalDB(it)
            }


        }) {
            Text("Сохранить")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (showDialog) {
            AlertDialog(
                icon = {
                    //Icon(icon, contentDescription = "Example Icon")
                },
                title = {
                    Text("Введите односложный текст")
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
                                descriptionViewModel.addLike(Likes(userId = authViewModel.uiState.user?.id?:0, movieId = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, description = dialogueText))
                            }else {
                                descriptionViewModel.addDislike(Dislikes(userId = authViewModel.uiState.user?.id?:0, movieId = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?:0, description = dialogueText))
                            }
                            dialogueText = ""
                            showDialog = false
                        }
                    ) {
                        Text("Принять")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            dialogueText = ""
                            showDialog = false
                        }
                    ) {
                        Text("Отменить")
                    }
                }
            )
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
