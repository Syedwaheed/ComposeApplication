package com.newapp.composeapplicationstart.presentation.ui.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.newapp.composeapplicationstart.BuildConfig
import com.newapp.composeapplicationstart.R
import com.newapp.composeapplicationstart.domain.model.response.MovieResponse
import com.newapp.composeapplicationstart.domain.model.response.TrendingMovieResponse
import com.newapp.composeapplicationstart.presentation.ui.theme.textColor
import com.newapp.composeapplicationstart.presentation.viewmodel.MovieViewModel
import com.newapp.composeapplicationstart.presentation.utils.MovieUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: MovieUiState<List<TrendingMovieResponse?>>
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.background_color)
            )
    ) {
        item {
            StreamEveryWhereText()
        }
        item {
            BannerImage()
        }
        item {
            TexComposable()
        }
        item {
            ImageCarousel(modifier = modifier,
                state)
        }

    }


}

@Composable
fun ImageCarousel(
    modifier: Modifier = Modifier,
    state: MovieUiState<List<TrendingMovieResponse?>>,
    ) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        when (state) {
            is MovieUiState.UILoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .fillMaxHeight(0.1f)
                        .align(Alignment.Center)
                )
            }

            is MovieUiState.UISuccess -> {
                val results = (state as MovieUiState.UISuccess).data
                Log.d("*AccessTokenAPIResponse", "$results")
                if (results.isNotEmpty()) {
                    val pagerState = rememberPagerState {
                        results.size
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 40.dp),
                    ) { page ->
                        val isCurrentPAge = pagerState.currentPage == page
                        val scale = if (isCurrentPAge) 1f else 0.85f
                        val alphas = if (isCurrentPAge) 1f else 0.7f
                        Box(
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                    alpha = alphas
                                }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(BuildConfig.TMDB_IMAGE_BASER_URL + results[page]?.posterPath)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = results[page]?.name,
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .clip(
                                        RoundedCornerShape(30.dp)
                                    )
                                    .fillMaxSize()
                            )
                            Box(modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .fillMaxHeight(0.3f)
                                .align(Alignment.TopEnd)
                            ){
                                BlurComposable(
                                    borderColorId = R.color.white,
                                    backGroundColorId = R.color.gray_alfa)
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ){
                                    Row(modifier = Modifier
                                        .wrapContentSize()
                                        .padding(
                                            start = 20.dp,
                                            top = 20.dp
                                        )
                                        ) {
                                        Text(
                                            text = "IMDB",
                                            modifier = Modifier,
                                            color = colorResource(id = R.color.white))
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(0.3f),
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Image(painter = painterResource(id = R.drawable.rating),
                                                contentDescription = "rating",
                                                modifier = Modifier.width(20.dp)
                                                    .height(20.dp))
                                        }
                                        Column(modifier = Modifier.weight(0.7f)) {
                                            Text(
                                                text = "${results[page]?.rating}",
                                                style = TextStyle(
                                                    fontSize = 24.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontStyle = FontStyle.Italic,
                                                    color = Color.Red,
                                                    letterSpacing = 2.sp,
                                                    textDecoration = TextDecoration.Underline,
                                                    textAlign = TextAlign.Center,
                                                    lineHeight = 28.sp
                                                ),
                                                modifier = Modifier.padding(start = 10.dp),
                                                color = colorResource(id = R.color.white))
                                        }
                                    }

                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .fillMaxHeight(0.34f)
                                    .padding(16.dp)
                                    .align(Alignment.BottomStart) // Align at the bottom of the parent Box
                            ) {
                                BlurComposable(
                                    modifier = Modifier.fillMaxSize(),
                                    borderColorId = R.color.white,
                                    backGroundColorId = R.color.gray_alfa
                                )

                                Text(
                                    text = results[page]?.name ?: "",
                                    modifier = Modifier.align(Alignment.Center),
                                    color = colorResource(id = R.color.white)
                                )
                            }

                        }
                    }
                }else{
                    Text(text = "No Data Available",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .align(Alignment.Center),
                        color = colorResource(id = R.color.white)
                    )
                }

            }

            is MovieUiState.UIError -> {
                val message = (state as MovieUiState.UIError).message.asString()
                Text(
                    text = message,
                    color = colorResource(id = R.color.white)
                )
            }
        }
    }
}

@Composable
fun TexComposable(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = stringResource(id = R.string.trending),
            color = colorResource(id = R.color.white),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun BannerImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Makes the Box take full width
            .height(300.dp) // Set a fixed height for the image banner
            .padding(horizontal = 20.dp) // Padding on sides for a consistent layout
    ) {
        Image(
            painter = painterResource(id = R.drawable.posterimage),
            contentDescription = "Poster Image",
            modifier = Modifier
                .fillMaxSize() // Ensures the Image fits the Box
                .clip(RoundedCornerShape(20.dp)), // Rounds the corners of the Image
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.34f)
                .align(Alignment.BottomStart)
        ) {
            BlurComposable(modifier,
                borderColorId = R.color.white,
                backGroundColorId = R.color.gray_alfa
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),

                ) {
                Column(
                    modifier = Modifier
                        .weight(0.1f)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.icon
                        ),
                        contentDescription = "Play Icon",
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),

                        )
                }

                Column(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            start = 5.dp,
                            top = 5.dp
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Text(
                            text = "Continue Watching",
                            color = colorResource(id = R.color.white)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Text watching",
                            color = colorResource(id = R.color.white)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun StreamEveryWhereText(modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(
                top = 25.dp,
                start = 20.dp,
                bottom = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xFFFF5722)
                )
            ) {
                append(stringResource(R.string.orange_text))
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.white)
                )
            ) {
                append(stringResource(R.string.black_text))
            }
        }
        Text(
            text = annotatedString,
            style = MaterialTheme.typography.bodyLarge
        )
    }

}

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (uiState) {
            is MovieUiState.UILoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }

            is MovieUiState.UISuccess -> {
                val movies = (uiState as MovieUiState.UISuccess).data ?: emptyList()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    items(
                        items = movies,
                        key = { movie ->
                            movie?.id!!
                        }
                    ) { movie ->
                        MovieItem(movie = movie)
                    }
                }
            }

            is MovieUiState.UIError -> {
                val message =
                    (uiState as MovieUiState.UIError).message.asString()
                        ?: "An error occurred"
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieResponse?) {
    Row(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
                .crossfade(true)
                .build(),
            contentDescription = "test",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = movie?.title ?: "", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie?.overview ?: "",
                maxLines = 3,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        val mockTrendingMovies = listOf(
            TrendingMovieResponse(1, "Movie 1","dfa", 1.0),
            TrendingMovieResponse(1, "/path2.jpg","dfg", 1.0)
        )
        val state = MovieUiState.UISuccess(mockTrendingMovies)
        HomeScreen(state = state)
//        MovieListScreen()
    }
}

@Composable
fun BlurComposable(
    modifier: Modifier = Modifier,
    borderColorId: Int,
    backGroundColorId: Int
) {
    val borderColor = colorResource(id = borderColorId)
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .graphicsLayer {
                // Apply blur effect using graphics layer
                alpha = 0.7f
                shadowElevation = 20f // Optional, if you want a shadow effect
            }
            .background(
                color = colorResource(id = backGroundColorId).copy(alpha = 0.3f),
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(id = borderColorId),
                shape = RoundedCornerShape(15.dp)
            )

    )
}

