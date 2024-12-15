package com.example.tasksapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
//    val
    val isBottomVisible = MutableLiveData<Boolean>(false)
    fun setBottomVisible(changeVisible: Boolean){
        isBottomVisible.value = changeVisible
    }
}