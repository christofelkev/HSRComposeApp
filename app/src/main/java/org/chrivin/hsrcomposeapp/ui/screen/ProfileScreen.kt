package org.chrivin.hsrcomposeapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.chrivin.hsrcomposeapp.R


@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val profilePhoto = painterResource(id = R.drawable.profile_kevin)
    val background = painterResource(id = R.drawable.hsrbackground)
    val name = "Christofel Kevin Manopo"
    val email = "christofelmanopo3@gmail.com"

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = profilePhoto,
                    contentDescription = "profile_photo",

                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = email,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )


        }
    }
}