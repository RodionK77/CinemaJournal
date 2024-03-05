package com.example.cinemajournal.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinemajournal.ItemRowModel
import com.example.cinemajournal.R
import com.example.compose.AppTheme

@Composable
fun wordItemRow(text: String) {


    Card(
        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp, end = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        ),
    ) {
        Text(
            modifier = Modifier
                .padding(6.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(6.dp),
            text = text,
            color = Color.Black
        )
    }

    /*Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, end = 12.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentDescription = "Image of man",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 4.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(text = item.title, modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 4.dp) )
    }*/
}

@Preview
@Composable
fun WordItemPreview() {
    AppTheme {
        wordItemRow(text = "очень интересный")
    }
}