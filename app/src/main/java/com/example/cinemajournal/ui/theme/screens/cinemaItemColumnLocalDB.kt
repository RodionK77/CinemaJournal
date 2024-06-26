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
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfo
import com.example.cinemajournal.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.example.example.MovieInfo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun cinemaItemColumnLocalDB(item: RoomMovieInfoForRetrofit, selectedIndex: Int, navController: NavController, reviewViewModel: ReviewViewModel, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel) {


    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clickable {
                if (selectedIndex == 0){
                    // ревью
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfo(null)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(item)
                    reviewViewModel.refreshCurrentMovieInfoRoom(item)
                    navController.navigate("ContentReviewScreen")
                }
                else if (selectedIndex == 1){
                    // просто просмотр
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfo(null)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(item)
                    navController.navigate("LocalContentDescriptionScreen")
                }
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.tertiaryContainer),

        ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(

        ) {
            GlideImage(
                model = item.posterUrl?:R.drawable.poster_placeholder,
                contentDescription = stringResource(R.string.poster_not_loaded),
                //loading = placeholder(painterResource(id = R.drawable.poster_placeholder)),
                //failure = placeholder(painterResource(id = R.drawable.poster_placeholder)),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .height(112.dp)
            )
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
                    text = "${stringResource(R.string.kp_rating)}: " + item.kpRating.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

}