package com.example.features.sharedfinance.home.charts

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.R
import com.example.core.theme.CustomTheme
import com.example.core.ui.SFToolbar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChartsScreen(
    viewModel: ChartViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val dataState = viewModel.chartDataState.value
    val scaffoldState = rememberScaffoldState()
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { viewModel.getNotes() }
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SFToolbar(
                title = stringResource(R.string.title_chart)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullToRefreshState)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 20.dp, bottom = 48.dp)
            ) {
                PieChart(
                    data = dataState.info,
                    radiusOuter = 130.dp,
                    chartBarWidth = 60.dp,
                    animDuration = 3000
                )
                Spacer(modifier = Modifier.height(100.dp))
            }
            PullRefreshIndicator(
                state.isRefreshing, pullToRefreshState, Modifier.align(
                    Alignment.TopCenter
                )
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = CustomTheme.colors.text.contrast
                )
            }
            if (state.errorMessageId != 0) {
                Text(
                    text = stringResource(state.errorMessageId),
                    color = CustomTheme.colors.text.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                        .wrapContentHeight()
                )
            }

        }
    }
}