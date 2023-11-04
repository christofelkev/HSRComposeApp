package org.chrivin.hsrcomposeapp.ui.screen

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.chrivin.hsrcomposeapp.di.Injection
import org.chrivin.hsrcomposeapp.model.HSRCharacter
import org.chrivin.hsrcomposeapp.ui.common.UiState
import org.chrivin.hsrcomposeapp.ui.item.HSRCharacterItem
import org.chrivin.hsrcomposeapp.ui.viewmodel.HomeViewModel
import org.chrivin.hsrcomposeapp.ui.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChanged = viewModel::search,
                    listHSRCharacter = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateHSRChara(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }

}

@Composable
fun HomeContent(
    query: String,
    onQueryChanged: (String) -> Unit,
    listHSRCharacter: List<HSRCharacter>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,

    ) {
    val context = LocalContext.current
    Column {
        org.chrivin.hsrcomposeapp.ui.item.SearchBar(
            query = query,
            onQueryChanged = onQueryChanged,
        )
        if (listHSRCharacter.isNotEmpty()) {
            ListHSRCharacter(
                listHSRCharacter = listHSRCharacter,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            //show toast message if the data is empty
            LaunchedEffect(query) {
                if (query.isNotEmpty()) {
                    Toast.makeText(
                        context,
                        "Sorry, the character is not available yet",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListHSRCharacter(
    listHSRCharacter: List<HSRCharacter>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingOnTop: Dp = 4.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingOnTop
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .testTag("lazy_list_column")
    ) {
        items(listHSRCharacter, key = { it.id }) { item ->
            HSRCharacterItem(
                id = item.id,
                name = item.name,
                photo = item.photo,
                description = item.description,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 300))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}