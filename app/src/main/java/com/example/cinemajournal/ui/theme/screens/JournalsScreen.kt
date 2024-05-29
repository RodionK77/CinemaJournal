package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinemajournal.R
import com.example.cinemajournal.data.models.RoomModels.Dislikes
import com.example.cinemajournal.data.models.RoomModels.Likes
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JournalsScreen(navController: NavController, journalsViewModel: JournalsViewModel, reviewViewModel: ReviewViewModel, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel, authViewModel: AuthViewModel) {

    reviewViewModel.changeLikes(null)
    reviewViewModel.changeDislikes(null)
    reviewViewModel.changeReviewText(null)
    reviewViewModel.changeRating(null)
    reviewViewModel.changeDate(null)
    descriptionViewModel.changeTimeToWatch(null, null)
    descriptionViewModel.changeDateToWatch(null)

    val profileDialogueState = rememberMaterialDialogState()

    if(journalsViewModel.uiState.accountDialogState){
        dialog(navController = navController, journalsViewModel = journalsViewModel, authViewModel = authViewModel)
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),

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
                    text = stringResource(R.string.viewed),
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
                    text = stringResource(R.string.want_to_see),
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
                                    reviewViewModel = reviewViewModel, galleryViewModel = galleryViewModel,
                                    descriptionViewModel = descriptionViewModel
                                )
                            }
                        }
                    }else {
                        Text(stringResource(R.string.empty_viewed_journal))
                    }
                } else {
                    Text(stringResource(R.string.empty_viewed_journal))
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
                                    reviewViewModel = reviewViewModel, galleryViewModel = galleryViewModel,
                                    descriptionViewModel = descriptionViewModel
                                )
                            }
                        }
                    }else {
                        Text(stringResource(R.string.empty_wanted_journal))
                    }
                } else {
                    Text(stringResource(R.string.empty_wanted_journal))
                }
            }
        }

        MaterialDialog(
            dialogState = profileDialogueState,
            buttons = {
                positiveButton(
                    text = stringResource(R.string.understand),
                ) {
                    //Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                    //val createNotification = CreateNotification(context)
                    //createNotification.scheduleNotification(2024, Calendar.APRIL, 11, 13, 31)
                    journalsViewModel.changeAccountDialogState(false)
                }
                negativeButton (
                    text = stringResource(R.string.exit_from_account),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.error)
                ) {
                    journalsViewModel.changeAccountDialogState(false)
                }
            }
        )
        {
            Text(text = authViewModel.uiState.user?.email?:"")
            Text(text = if(authViewModel.uiState.user?.role?:0 == 1) stringResource(R.string.kid_account) else stringResource(R.string.common_account))
        }
    }
}

@Composable
fun dialog(navController: NavController, journalsViewModel: JournalsViewModel, authViewModel: AuthViewModel){
    AlertDialog(
        icon = {
            Icons.Default.Info
        },
        title = {
            Text(stringResource(R.string.account_info))
        },
        text = {
            Column() {
                Text(text = authViewModel.uiState.user?.email?:"", fontSize = 20.sp)
                if(authViewModel.uiState.user?.role == 0) {
                    Text(text = stringResource(R.string.common_account), fontSize = 20.sp)
                } else {
                    Text(text = stringResource(id = R.string.kid_account), fontSize = 20.sp )
                }
            }
        },
        onDismissRequest = {
            journalsViewModel.changeAccountDialogState(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    journalsViewModel.changeAccountDialogState(false)
                }
            ) {
                Text(stringResource(R.string.understand))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    journalsViewModel.changeAccountDialogState(false)
                    authViewModel.deleteUserById(authViewModel.uiState.user!!.id)
                    journalsViewModel.deleteAllMoviesFromLocalDB()
                    authViewModel.changeUser(null)
                    journalsViewModel.changeUser(null)
                    authViewModel.signOutUser()
                    navController.navigate("EntranceScreen"){
                        popUpTo(0)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.error,
                )
            ) {
                Text(stringResource(R.string.exit_from_account))
            }
        }
    )
}



//@Preview(showBackground = true)
//@Composable
//fun JournalsPreview() {
//    AppTheme {
//        JournalsScreen(rememberNavController())
//    }
//}

