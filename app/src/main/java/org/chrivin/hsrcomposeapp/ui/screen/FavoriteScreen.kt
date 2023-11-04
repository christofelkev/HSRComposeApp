package org.chrivin.hsrcomposeapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import org.chrivin.hsrcomposeapp.di.Injection
import org.chrivin.hsrcomposeapp.model.HSRCharacter
import org.chrivin.hsrcomposeapp.ui.common.UiState
import org.chrivin.hsrcomposeapp.ui.viewmodel.FavoriteViewModel
import org.chrivin.hsrcomposeapp.ui.viewmodel.ViewModelFactory

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewmodel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewmodel.getFavoriteHSRChara()
            }

            is UiState.Success -> {
                FavoriteCharacterInfo(
                    listHSRCharacter = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewmodel.updateHSRChara(id, newState)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteCharacterInfo(
    listHSRCharacter: List<HSRCharacter>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val context = LocalContext.current
        if (listHSRCharacter.isNotEmpty()) {
            ListHSRCharacter(
                listHSRCharacter = listHSRCharacter,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            LaunchedEffect(Unit) {
                Toast.makeText(
                    context,
                    "No Favorite Character",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}