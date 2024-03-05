package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cinemajournal.CinemaRowModel
import com.example.cinemajournal.R
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun JournalsScreen(navController: NavController) {

    //val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var selectedTabIndex by rememberSaveable{ mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(innerPadding)
            //.padding(start = 16.dp, end = 16.dp)
            //.verticalScroll(ScrollState(0)),
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        ) {
            Tab(
                text = { Text(
                    text = "Просмотренное",
                    fontSize = 18.sp
                ) },
                selected = selectedTabIndex == 0,
                onClick = {
                    selectedTabIndex = 0
                }
            )
            Tab(
                text = { Text(
                    text = "Хочу посмотреть",
                    fontSize = 18.sp
                ) },
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
        ) {

            if (selectedTabIndex == 111) {
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
            } else {
                Text("Содержимое вкладки 'Просмотренное'")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun JournalsPreview() {
    AppTheme {
        JournalsScreen(rememberNavController())
    }
}

