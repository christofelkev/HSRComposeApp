package org.chrivin.hsrcomposeapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.chrivin.hsrcomposeapp.ui.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    characterId : Int,
    navigateBack : () -> Unit,
    modifier : Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModel
    )
) {

}