package com.example.cinemajournal.ui.theme.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cinemajournal.R
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import kotlinx.coroutines.launch





@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GalleryScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel,
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel
) {

    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var showGenresDialog by rememberSaveable{ mutableStateOf(false) }
    var dialogueGenresText by rememberSaveable { mutableStateOf("") }
    var showCountriesDialog by rememberSaveable{ mutableStateOf(false) }
    var dialogueCountriesText by rememberSaveable { mutableStateOf("") }


    if (!galleryViewModel.uiState.drawerState) {
        scope.launch {
            drawerState.apply {
                close()
            }
        }
    } else {
        scope.launch {
            drawerState.apply {
                open()
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 24.dp)
                    .width(256.dp),
                drawerContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {

                Column(modifier = Modifier.verticalScroll(ScrollState(0))) {
                    var typeIsExpanded by rememberSaveable { mutableStateOf(false) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable {
                                typeIsExpanded = !typeIsExpanded
                            },
                        //modifier = Modifier.padding(end=8.dp)
                    ){
                        Text(stringResource(R.string.type_of_movie), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        Icon(
                            modifier = Modifier.scale(0.8f),
                            imageVector = if (typeIsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = stringResource(R.string.expand)
                        )
                    }
                    val typeState1 = rememberSaveable { mutableStateOf(false) }
                    val typeState2 = rememberSaveable { mutableStateOf(false) }
                    val typeState3 = rememberSaveable { mutableStateOf(false) }
                    val typeState4 = rememberSaveable { mutableStateOf(false) }
                    val typeState5 = rememberSaveable { mutableStateOf(false) }

                    if(typeIsExpanded){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .horizontalScroll(ScrollState(0))
                                .padding(end = 8.dp)
                        ){
                            Checkbox(
                                checked = typeState1.value,
                                onCheckedChange = { typeState1.value = it },
                                modifier = Modifier.scale(0.7f)
                            )
                            Text("movie", fontSize = 12.sp)

                            Spacer(modifier = Modifier.width(4.dp))

                            Checkbox(
                                checked = typeState2.value,
                                onCheckedChange = { typeState2.value = it },
                                modifier = Modifier.scale(0.7f)
                            )
                            Text("tv-series", fontSize = 12.sp)

                            Spacer(modifier = Modifier.width(4.dp))

                            Checkbox(
                                checked = typeState3.value,
                                onCheckedChange = { typeState3.value = it },
                                modifier = Modifier.scale(0.7f)
                            )
                            Text("cartoon", fontSize = 12.sp)

                            Spacer(modifier = Modifier.width(4.dp))

                            Checkbox(
                                checked = typeState4.value,
                                onCheckedChange = { typeState4.value = it },
                                modifier = Modifier.scale(0.7f)
                            )
                            Text("animated-series", fontSize = 12.sp)

                            Spacer(modifier = Modifier.width(4.dp))

                            Checkbox(
                                checked = typeState5.value,
                                onCheckedChange = { typeState5.value = it },
                                modifier = Modifier.scale(0.7f)
                            )
                            Text("anime", fontSize = 12.sp)
                        }
                    }

                    HorizontalDivider()
                    var yearIsExpanded by rememberSaveable { mutableStateOf(false) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable {
                                yearIsExpanded = !yearIsExpanded
                            },
                        //modifier = Modifier.padding(end=8.dp)
                    ){
                        Text(stringResource(R.string.year), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        Icon(
                            modifier = Modifier.scale(0.8f),
                            imageVector = if (yearIsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = stringResource(R.string.expand)
                        )
                    }
                    var yearState1 by rememberSaveable { mutableStateOf("") }
                    var yearState2 by rememberSaveable { mutableStateOf("") }
                    if(yearIsExpanded){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //modifier = Modifier.padding(end=8.dp)
                        ){
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = yearState1,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {yearState1 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.from))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                            Text("—", fontSize = 16.sp)
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = yearState2,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {yearState2 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.before))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                        }
                    }

                    HorizontalDivider()
                    var ratingIsExpanded by rememberSaveable { mutableStateOf(false) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable {
                                ratingIsExpanded = !ratingIsExpanded
                            },
                        //modifier = Modifier.padding(end=8.dp)
                    ){
                        Text(stringResource(R.string.kp_rating_short), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        Icon(
                            modifier = Modifier.scale(0.8f),
                            imageVector = if (ratingIsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = stringResource(R.string.expand)
                        )
                    }
                    var ratingState1 by rememberSaveable { mutableStateOf("") }
                    var ratingState2 by rememberSaveable { mutableStateOf("") }
                    if(ratingIsExpanded){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //modifier = Modifier.padding(end=8.dp)
                        ){
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = ratingState1,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {ratingState1 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.from))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                            Text("—", fontSize = 16.sp)
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = ratingState2,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {ratingState2 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.before))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                        }
                    }

                    var ageIsExpanded by rememberSaveable { mutableStateOf(false) }
                    var ageState1 by rememberSaveable { mutableStateOf("") }
                    var ageState2 by rememberSaveable { mutableStateOf("") }
                    if(authViewModel.uiState.user?.role?:0 == 0) {
                        HorizontalDivider()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                .clickable {
                                    ageIsExpanded = !ageIsExpanded
                                },
                            //modifier = Modifier.padding(end=8.dp)
                        ) {
                            Text(
                                stringResource(R.string.age_rating),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                            Icon(
                                modifier = Modifier.scale(0.8f),
                                imageVector = if (ageIsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = stringResource(R.string.expand)
                            )
                        }
                    } else {
                        ageState1 = "0"
                        ageState2 = "12"
                    }
                    if(ageIsExpanded){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //modifier = Modifier.padding(end=8.dp)
                        ){
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = ageState1,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {ageState1 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.from))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                            Text("—", fontSize = 16.sp)
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = ageState2,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {ageState2 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.before))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                        }
                    }
                    HorizontalDivider()
                    var timeIsExpanded by rememberSaveable { mutableStateOf(false) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .clickable {
                                timeIsExpanded = !timeIsExpanded
                            },
                        //modifier = Modifier.padding(end=8.dp)
                    ){
                        Text(stringResource(R.string.duration), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        Icon(
                            modifier = Modifier.scale(0.8f),
                            imageVector = if (timeIsExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = stringResource(R.string.expand)
                        )
                    }
                    var timeState1 by rememberSaveable { mutableStateOf("") }
                    var timeState2 by rememberSaveable { mutableStateOf("") }
                    if(timeIsExpanded){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            //modifier = Modifier.padding(end=8.dp)
                        ){
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = timeState1,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {timeState1 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.from))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                            Text("—", fontSize = 16.sp)
                            TextField(
                                modifier = Modifier
                                    .width(120.dp)
                                    .scale(0.7f),
                                value = timeState2,
                                textStyle = TextStyle(fontSize=18.sp),
                                onValueChange = {timeState2 = it},
                                singleLine = true,
                                placeholder = {Text(stringResource(R.string.before))},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            )
                        }
                    }

                    HorizontalDivider()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        //modifier = Modifier.padding(end=8.dp)
                    ){
                        Text("Жанры", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        Icon(
                            modifier = Modifier
                                .scale(0.8f)
                                .clickable {
                                    showGenresDialog = true
                                },
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = stringResource(R.string.add)
                        )
                        if(galleryViewModel.uiState.genresList.isNotEmpty()){
                            Icon(
                                modifier = Modifier
                                    .scale(0.8f)
                                    .clickable {
                                        galleryViewModel.removeAllGenres()
                                    },
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete_all)
                            )
                        }
                    }
                    val padding1 = if (galleryViewModel.uiState.genresList.isNotEmpty()) 4.dp else 0.dp
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp, bottom = padding1)
                    ) {
                        itemsIndexed(
                            galleryViewModel.uiState.genresList
                        ) { _, item ->
                            Text(
                                modifier = Modifier.padding(end = 16.dp),
                                text = item
                            )
                        }
                    }
                    HorizontalDivider()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        //modifier = Modifier.padding(end=8.dp)
                    ){
                        Text(stringResource(R.string.countries), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        Icon(
                            modifier = Modifier
                                .scale(0.8f)
                                .clickable {
                                    showCountriesDialog = true
                                },
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = stringResource(R.string.add)
                        )
                        if(galleryViewModel.uiState.countriesList.isNotEmpty()){
                            Icon(
                                modifier = Modifier
                                    .scale(0.8f)
                                    .clickable {
                                        galleryViewModel.removeAllCountries()
                                    },
                                imageVector = Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete_all)
                            )
                        }
                    }
                    val padding2 = if (galleryViewModel.uiState.countriesList.isNotEmpty()) 4.dp else 0.dp
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp, bottom = padding2)
                    ) {
                        itemsIndexed(
                            galleryViewModel.uiState.countriesList
                        ) { _, item ->
                            Text(
                                modifier = Modifier.padding(end = 16.dp),
                                text = item
                            )
                        }
                    }
                    HorizontalDivider()

                    ElevatedButton(onClick = {
                        var typeList:MutableList<String> = mutableListOf()

                        galleryViewModel.changeSearchState(true)

                        if(typeState1.value){
                            typeList.add("movie")
                        }
                        if(typeState2.value){
                            typeList.add("tv-series")
                        }
                        if(typeState3.value){
                            typeList.add("cartoon")
                        }
                        if(typeState4.value){
                            typeList.add("animated-series")
                        }
                        if(typeState5.value){
                            typeList.add("anime")
                        }

                        //val type = if(typeState1.value) "movie" else ""
                        val year = if(yearState1 != "" && yearState2 != "") "$yearState1-$yearState2" else null
                        val rating = if(ratingState1 != "" && ratingState2 != "") "$ratingState1-$ratingState2" else null
                        val ageRating = if(ageState1 != "" && ageState2 != "") "$ageState1-$ageState2" else null
                        val time = if(timeState1 != "" && timeState2 != "") "$timeState1-$timeState2" else null
                        val genres = galleryViewModel.uiState.genresList.ifEmpty { null }
                        val countries = galleryViewModel.uiState.countriesList.ifEmpty { null }

                        Log.d("R", "type: ${typeList}; year: ${year}; rating: ${rating}; age: ${ageRating}; time: ${time}; genres: ${genres}; countries ${countries}",)

                        galleryViewModel.changeQueryGo(true)
                        galleryViewModel.refreshFilteredMovies(typeList, year, rating, ageRating, time, genres, countries)
                        typeList = mutableListOf()
                    },
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .scale(0.9f)
                            .align(Alignment.CenterHorizontally)) {
                        Text(stringResource(R.string.filtered_search), fontSize = 20.sp)
                    }
                }

            }
        },
    ) {
        if (!galleryViewModel.uiState.isQueryGo) {
            BasicScreen(navController, galleryViewModel, descriptionViewModel, authViewModel)
        } else {
            SearchScreen(navController, galleryViewModel, descriptionViewModel)
        }
    }

    if (showGenresDialog) {
        AlertDialog(
            icon = {
                //Icon(icon, contentDescription = "Example Icon")
            },
            title = {
                Text(stringResource(R.string.enter_a_genre))
            },
            text = {
                TextField(
                    value = dialogueGenresText,
                    onValueChange = {
                        dialogueGenresText = it
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
                showGenresDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(dialogueGenresText != ""){
                            galleryViewModel.addGenre(dialogueGenresText)
                            dialogueGenresText = ""
                            showGenresDialog = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogueGenresText = ""
                        showGenresDialog = false
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    if (showCountriesDialog) {
        AlertDialog(
            icon = {
                //Icon(icon, contentDescription = "Example Icon")
            },
            title = {
                Text(stringResource(R.string.enter_a_country))
            },
            text = {
                TextField(
                    value = dialogueCountriesText,
                    onValueChange = {
                        dialogueCountriesText = it
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
                showCountriesDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(dialogueCountriesText != ""){
                            galleryViewModel.addCountry(dialogueCountriesText)
                            dialogueCountriesText = ""
                            showCountriesDialog = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.accept))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogueCountriesText = ""
                        showCountriesDialog = false
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}



@Composable
fun BasicScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel,
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                //.padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(ScrollState(0)),
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.worth_a_look),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary),
            ) {
                itemsIndexed(
                    galleryViewModel.uiState.topMoviesInfo?.toList()!!
                ) { _, item ->
                    cinemaItemRow(
                        item = item,
                        galleryViewModel = galleryViewModel,
                        descriptionViewModel,
                        navController = navController
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.unknown_and_interesting),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary),
            ) {
                itemsIndexed(
                    galleryViewModel.uiState.topInterestedMoviesInfo?.toList()!!
                ) { _, item ->
                    cinemaItemRow(
                        item = item,
                        galleryViewModel = galleryViewModel,
                        descriptionViewModel,
                        navController = navController
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.best_series),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSecondary),
            ) {
                itemsIndexed(
                    galleryViewModel.uiState.topSeriesInfo?.toList()!!
                ) { _, item ->
                    cinemaItemRow(
                        item = item,
                        galleryViewModel = galleryViewModel,
                        descriptionViewModel,
                        navController = navController
                    )
                }
            }
        }

        if(authViewModel.uiState.user?.role?:0 == 0) {
            nonKidsTops(navController = navController, galleryViewModel = galleryViewModel, descriptionViewModel = descriptionViewModel)
        } else {
            kidsTops(navController = navController, galleryViewModel = galleryViewModel, descriptionViewModel = descriptionViewModel)
        }

        if (galleryViewModel.uiState.isSearch) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .pointerInput(Unit) {}
            )
        }
    }
}

@Composable
fun nonKidsTops(navController: NavController, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel){

}

@Composable
fun kidsTops(navController: NavController, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel){

}

@Composable
fun SearchScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel,
    descriptionViewModel: DescriptionViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                galleryViewModel.uiState.searchMoviesInfo?.toList()!!
            ) { _, item ->
                cinemaItemColumn(
                    item = item,
                    navController = navController,
                    galleryViewModel = galleryViewModel,
                    descriptionViewModel = descriptionViewModel
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GalleryPreview() {
//    AppTheme {
//        GalleryScreen(rememberNavController(), hiltViewModel(), viewModel())
//    }
//}
