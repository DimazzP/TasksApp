package com.example.tasksapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val isBottomVisible = MutableLiveData<Boolean>(false)
    fun setBottomVisible(changeVisible: Boolean){
        isBottomVisible.value = changeVisible
    }
}