package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
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
import com.example.cinemajournal.CinemaRowModel
import com.example.cinemajournal.ItemRowModel
import com.example.cinemajournal.R
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ItemDescriptionUiState
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContentDescriptionScreen(
    navController: NavController,
    descriptionViewModel: DescriptionViewModel
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
        if (descriptionViewModel.uiState.movieInfo != null) {
            //descriptionViewModel.refreshCurrentMovie2()
            Content(descriptionViewModel)
        } else
            Text(
                text = "Грузится",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
            )
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
            model = descriptionViewModel.uiState.movieInfo?.poster?.url,
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
                        append(descriptionViewModel.uiState.movieInfo?.year.toString())
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
                        append(descriptionViewModel.uiState.movieInfo?.rating?.kp.toString())
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
                        for (i in descriptionViewModel.uiState.movieInfo?.genres!!) {
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
                        append(descriptionViewModel.uiState.movieInfo?.movieLength.toString() + " мин")
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
                        append("${descriptionViewModel.uiState.movieInfo?.ageRating.toString()}+")
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
                        append("${descriptionViewModel.uiState.movieInfo?.budget?.value} $")
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
                        append("${descriptionViewModel.uiState.movieInfo?.fees?.world?.value} $")
                    }
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = descriptionViewModel.uiState.movieInfo?.description ?: "",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.secondary,
        lineHeight = 16.sp
    )

    if (descriptionViewModel.uiState.movieInfo!!.typeNumber == 2 || descriptionViewModel.uiState.movieInfo!!.typeNumber == 5) {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Сезоны и серии",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Log.d("R", "Сериес: ${descriptionViewModel.uiState.movieInfo?.seasonsInfo}")
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            itemsIndexed(
                descriptionViewModel.uiState.movieInfo?.seasonsInfo ?: emptyList()
            ) { _, item ->
                seriesItemRow(item)
            }
        }
    }


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Режиссёры",
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
            descriptionViewModel.uiState.movieInfo?.persons!!.filter { it.enProfession == "director" }
        ) { _, item ->
            photoItemRow(item = item)
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Актёры",
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
            descriptionViewModel.uiState.movieInfo?.persons!!.filter { it.enProfession == "actor" }
        ) { _, item ->
            photoItemRow(item = item)
        }
    }
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