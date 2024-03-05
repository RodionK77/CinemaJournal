package com.example.cinemajournal.ui.theme.screens

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
import androidx.compose.material.icons.outlined.Contacts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.placeholder
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalsToolbar(scrollBehavior: TopAppBarScrollBehavior){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Журналы",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing= 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Outlined.AddBox,
                    contentDescription = "Localized description"
                )
            }
            IconButton(onClick = { /* do something */ }) {
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
fun GalleryToolbar(scrollBehavior: TopAppBarScrollBehavior, galleryViewModel: GalleryViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if(!galleryViewModel.uiState.isSearch){
                Text(
                    text = "Галерея",
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
                        text = "Поисковой запрос...",
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
                                Text("Поиск", fontSize = 20.sp)
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
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if(galleryViewModel.uiState.isSearch){
                IconButton(onClick = { galleryViewModel.changeDrawerState(!galleryViewModel.uiState.drawerState)
                    Log.d("R", galleryViewModel.uiState.drawerState.toString(), )}) {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = "Localized description"
                    )
                }
            }

            if(!galleryViewModel.uiState.isSearch){
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
fun ContentToolbar(navController: NavController, descriptionViewModel: DescriptionViewModel){

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Column {
                Text(descriptionViewModel.uiState.movieInfo?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
                if(descriptionViewModel.uiState.movieInfo?.enName == "" || descriptionViewModel.uiState.movieInfo?.enName == null){
                    Text(descriptionViewModel.uiState.movieInfo?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                }else Text(descriptionViewModel.uiState.movieInfo?.enName ?: descriptionViewModel.uiState.movieInfo?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            dropDownMenu(descriptionViewModel)
        }
    )
}

@Composable
fun dropDownMenu(descriptionViewModel: DescriptionViewModel) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {if(true) Text("В списке к просмотру") else Text("Хочу посмотреть") },
                onClick = { Toast.makeText(context, "хочу", Toast.LENGTH_SHORT).show() }
            )
            DropdownMenuItem(
                text = {if(true) Text("Просмотрено") else Text("Не просмотрено") },
                onClick = { Toast.makeText(context, "уже", Toast.LENGTH_SHORT).show() }
            )
        }
    }
}