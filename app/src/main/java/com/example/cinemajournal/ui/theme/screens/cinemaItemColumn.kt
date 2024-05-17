package com.example.cinemajournal.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun cinemaItemColumn(item: MovieInfo, navController: NavController, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel) {


    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clickable {
                if (navController.currentDestination?.route == "GalleryScreen"){
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                    descriptionViewModel.refreshCurrentMovieInfo(item)
                    descriptionViewModel.getMovieInfo(item.id!!)
                    //descriptionViewModel.refreshCurrentMovie()
                    navController.navigate("ContentDescriptionScreen")
                }
                /*else if (navController.currentDestination?.route == "JournalsScreen"){
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                    descriptionViewModel.refreshCurrentMovieInfo(item)
                    //descriptionViewModel.refreshCurrentMovie()
                    navController.navigate("ContentReviewScreen")
                }*/
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(4.dp,MaterialTheme.colorScheme.tertiaryContainer),

    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(

        ) {
            GlideImage(
                model = item.poster?.url?:R.drawable.poster_placeholder,
                contentDescription = stringResource(R.string.poster_not_loaded),
                //loading = placeholder(painterResource(id = R.drawable.poster_placeholder)),
                //failure = placeholder(painterResource(id = R.drawable.poster_placeholder)),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .height(112.dp)
            )
            /*Image(
                painter = painterResource(id = item.imageId),
                contentDescription = "Poster of cinema",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .height(112.dp)
                //.clip(CircleShape)
            )*/
            Column(modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)) {
                Text(
                    text = item.name ?: stringResource(R.string.no_name),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                )
                if(item.enName == null || item.enName == "" && item.alternativeName == null || item.alternativeName == ""){

                }else {
                    Text(
                        text = if(item.alternativeName != "") item.alternativeName ?: item.enName ?: "" + ", " + item.year?:"" else item.enName ?: "" + ", " + item.year?:"",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start
                    )
                }

                var g = ""
                for (i in item.genres!!){
                    g += i.name + ", "
                }
                var c = ""
                for (i in item.countries!!){
                    c += i.name + ", "
                }
                Text(
                    text = if(item.enName == null || item.enName == "" && item.alternativeName == null || item.alternativeName == "") c + "• " + g + "• " + item.year?:"" else c + "• " + g,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        //.padding(bottom = 6.dp)
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.tertiary)
                        .padding(2.dp),
                    text = "${stringResource(R.string.kp_rating)}: " + item.rating?.kp.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

}

//@Preview
//@Composable
//fun cinemaItemColumnPreview() {
//    AppTheme {
//        cinemaItemColumn(CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"), rememberNavController(), 0)
//    }
//}