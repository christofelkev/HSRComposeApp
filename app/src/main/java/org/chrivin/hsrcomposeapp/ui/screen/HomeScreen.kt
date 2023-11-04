package org.chrivin.hsrcomposeapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.chrivin.hsrcomposeapp.di.Injection
import org.chrivin.hsrcomposeapp.ui.viewmodel.HomeViewModel
import org.chrivin.hsrcomposeapp.ui.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail : (Int) -> Unit,
) {

}