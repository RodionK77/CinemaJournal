package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JournalsScreen(navController: NavController, journalsViewModel: JournalsViewModel, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel) {

    //val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    //var selectedTabIndex by rememberSaveable{ mutableStateOf(0) }

    /*if(journalsViewModel.uiState.user != null){
        journalsViewModel.startUpdateLocalDB(journalsViewModel.uiState.user!!)
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(innerPadding)
            //.padding(start = 16.dp, end = 16.dp)
            //.verticalScroll(ScrollState(0)),
    ) {
        TabRow(
            selectedTabIndex = journalsViewModel.uiState.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[journalsViewModel.uiState.selectedTabIndex]),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        ) {
            Tab(
                text = { Text(
                    text = "Просмотренное",
                    fontSize = 18.sp
                ) },
                selected = journalsViewModel.uiState.selectedTabIndex == 0,
                onClick = {
                    journalsViewModel.changeSelectedTabIndex(0)
                    //selectedTabIndex = 0
                }
            )
            Tab(
                text = { Text(
                    text = "Хочу посмотреть",
                    fontSize = 18.sp
                ) },
                selected = journalsViewModel.uiState.selectedTabIndex == 1,
                onClick = {
                    journalsViewModel.changeSelectedTabIndex(1)
                    //selectedTabIndex = 1
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
        ) {

            if(journalsViewModel.uiState.selectedTabIndex == 0) {
                journalsViewModel.getMoviesFromWatchedFromLocalDB()
                if(journalsViewModel.uiState.watchedMovies != null){
                    if(journalsViewModel.uiState.watchedMovies!!.isNotEmpty()){
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onSecondary),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            itemsIndexed(
                                journalsViewModel.uiState.watchedMovies!!
                            ){ _, item ->
                                cinemaItemColumnLocalDB(
                                    item = item, navController = navController, selectedIndex = journalsViewModel.uiState.selectedTabIndex,
                                    journalsViewModel = journalsViewModel, galleryViewModel = galleryViewModel,
                                    descriptionViewModel = descriptionViewModel
                                )
                            }
                        }
                    }else {
                        Text("Журнал просмотренных фильмов пуст")
                    }
                } else {
                    Text("Журнал просмотренных фильмов пуст")
                }
            }else if (journalsViewModel.uiState.selectedTabIndex == 1) {
                journalsViewModel.getMoviesFromToWatchFromLocalDB()
                if(journalsViewModel.uiState.moviesToWatch != null){
                    if(journalsViewModel.uiState.moviesToWatch!!.isNotEmpty()){
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onSecondary),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            itemsIndexed(
                                journalsViewModel.uiState.moviesToWatch!!
                            ){ _, item ->
                                cinemaItemColumnLocalDB(
                                    item = item, navController = navController, selectedIndex = journalsViewModel.uiState.selectedTabIndex,
                                    journalsViewModel = journalsViewModel, galleryViewModel = galleryViewModel,
                                    descriptionViewModel = descriptionViewModel
                                )
                            }
                        }
                    }else {
                        Text("Журнал фильмов к просмотру пуст")
                    }
                } else {
                    Text("Журнал фильмов к просмотру пуст")
                }
                /*LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    itemsIndexed(
                        listOf(
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"),
                            CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек")
                        )
                    ){ _, item ->
                        cinemaItemColumn(item = item, navController = navController, selectedTabIndex)
                    }
                }*/
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun JournalsPreview() {
//    AppTheme {
//        JournalsScreen(rememberNavController())
//    }
//}

