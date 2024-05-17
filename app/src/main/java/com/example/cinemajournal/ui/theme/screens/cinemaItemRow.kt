package com.example.cinemajournal.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.cinemajournal.R
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.example.MovieInfo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun cinemaItemRow(item: MovieInfo, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            //.padding(top = 6.dp, bottom = 6.dp, end = 12.dp)
            .width(160.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .clickable {
                if (navController.currentDestination?.route == "GalleryScreen"){
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfo(item)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                    //descriptionViewModel.refreshCurrentMovie()
                    navController.navigate("ContentDescriptionScreen")
                }
                else if (navController.currentDestination?.route == "JournalsScreen"){
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfo(item)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                    //descriptionViewModel.refreshCurrentMovie()
                    navController.navigate("ContentReviewScreen")
                }

            }
            //.background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        GlideImage(
            model = item.poster?.url?:R.drawable.poster_placeholder,
            contentDescription = "",
            //loading = placeholder(painterResource(id = R.drawable.poster_placeholder)),
            //failure = placeholder(painterResource(id = R.drawable.poster_placeholder)),
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 8.dp)
                .height(164.dp),
        )
        /*Image(
            painter = AsyncImage(model = item.poster?.url, contentDescription = "image") /*painterResource(id = R.drawable.poster)*/,
            contentDescription = "Poster of cinema",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 8.dp)
                .height(164.dp)
                //.clip(CircleShape)
        )*/
        Text(
            text = item.name ?: stringResource(R.string.no_name),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
            letterSpacing= (-0.8).sp,
            maxLines = 3,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//@Preview
//@Composable
//fun CinemaItemPreview() {
//    AppTheme {
//        cinemaItemRow(CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"), rememberNavController())
//    }
//}