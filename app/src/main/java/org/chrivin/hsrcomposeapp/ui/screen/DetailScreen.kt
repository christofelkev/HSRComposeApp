package org.chrivin.hsrcomposeapp.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.chrivin.hsrcomposeapp.R
import org.chrivin.hsrcomposeapp.di.Injection
import org.chrivin.hsrcomposeapp.ui.common.UiState
import org.chrivin.hsrcomposeapp.ui.theme.HSRComposeAppTheme
import org.chrivin.hsrcomposeapp.ui.viewmodel.DetailViewModel
import org.chrivin.hsrcomposeapp.ui.viewmodel.ViewModelFactory

@Composable
fun DetailScreen(
    characterId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getHSRCharaById(characterId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailCharacterInfo(
                    id = data.id,
                    name = data.name,
                    photo = data.photo,
                    description = data.description,
                    backstory = data.backstory,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack,
                    onFavoriteIconClicked = { id, state ->
                        viewModel.updateHSRChara(id, state)
                    }
                )

            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailCharacterInfo(
    id: Int,
    name: String,
    @DrawableRes photo: Int,
    description: String,
    backstory: String,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteIconClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scrollImagetoBottom")
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(
                text = "Description",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(
                text = "Backstory",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = backstory,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .size(40.dp)
                .testTag("backToHome")
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
        IconButton(
            onClick = {
                onFavoriteIconClicked(id, isFavorite)
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 8.dp)
                .align(Alignment.TopEnd)
                .size(40.dp)

                .testTag("favorite_detail_click")
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!isFavorite) stringResource(R.string.find_character) else stringResource(
                    R.string.delete_favorite
                ),
                tint = if (!isFavorite) Color.Black else Color.Blue
            )
        }
    }
}
