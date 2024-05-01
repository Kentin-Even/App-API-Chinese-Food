package com.kentin.chinesefood.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kentin.chinesefood.model.ChineseFood
import com.kentin.chinesefood.model.ChineseFoodAPI
import com.kentin.chinesefood.model.PictureFood
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main() {
    val mainViewModel = MainViewModel()
    println("Loading ...")
    mainViewModel.loadChineseFood()

    while (mainViewModel.runInProgress) {
        println("Waiting ...")
        Thread.sleep(500)
    }
    println("mainViewModel.pictureFoodList: ${mainViewModel.pictureFoodList}")
    println("mainViewModel.pictureFoodList: ${mainViewModel.pictureFoodList}")
    println("Fin")
}

class MainViewModel : ViewModel() {
    var pictureFoodList = mutableStateListOf<PictureFood>()
    var runInProgress by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun loadChineseFood() {
        runInProgress = true
        errorMessage = ""

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val chineseFoods: List<ChineseFood> = ChineseFoodAPI.loadChineseFood()
                val res = chineseFoods.map {
                    PictureFood(
                        it.id,
                        it.title,
                        it.difficulty,
                        it.image,
                    )
                }
                pictureFoodList.clear()
                pictureFoodList.addAll(res)

                runInProgress = false
            }
            catch (e: Exception) {
                e.printStackTrace()
                runInProgress = false
                errorMessage = e.message ?: "Erreur inconnue"
            }
        }
    }
}