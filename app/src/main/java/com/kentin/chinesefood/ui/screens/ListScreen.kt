package com.kentin.chinesefood.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import com.kentin.chinesefood.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kentin.chinesefood.ui.theme.AppTheme
import com.kentin.chinesefood.ui.MyError
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

import com.bumptech.glide.integration.compose.placeholder
import com.kentin.chinesefood.model.PictureFood
import com.kentin.chinesefood.ui.Routes
import com.kentin.chinesefood.ui.Routes.DetailScreen.withObject
import com.kentin.chinesefood.viewmodel.MainViewModel

@Composable
fun ListScreen(mainViewModel: MainViewModel, navHostController: NavHostController?) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.padding(8.dp)) {
                MyError(errorMessage = mainViewModel.errorMessage)

                AnimatedVisibility(
                    visible = mainViewModel.runInProgress,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filterList = mainViewModel.pictureFoodList
                    items(filterList.size) {
                        PictureRowItem(
                            data = filterList[it],
                            onPictureClick = {
                                navHostController?.navigate(Routes.DetailScreen.withObject(filterList[it]))
                            }
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { mainViewModel.loadChineseFood() },
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "coucou",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(modifier: Modifier = Modifier, data: PictureFood, onPictureClick: () -> Unit) {
    var fullText by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .then(modifier)
    ) {
        GlideImage(
            model = data.image,
            contentDescription = "une photo de chat",
            loading = placeholder(R.mipmap.ic_launcher_round),
            failure = placeholder(R.mipmap.ic_launcher),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
                .clickable(onClick = onPictureClick)
        )

        Column(modifier = Modifier.clickable {
            fullText = !fullText
        }) {
            Text(text = data.title, fontSize = 20.sp)
            Spacer(Modifier.size(8.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES,
    locale = "FR"
)
@Composable

fun ListScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val mainViewModel = MainViewModel()
            mainViewModel.loadChineseFood()
            mainViewModel.errorMessage = "Erreur inconnue"
            mainViewModel.runInProgress = true

            ListScreen(mainViewModel, null)
        }
    }
}