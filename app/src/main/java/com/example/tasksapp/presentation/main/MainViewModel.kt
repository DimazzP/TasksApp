package com.example.tasksapp.presentation.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasksapp.domain.model.HabitsTaskModel
import com.example.tasksapp.domain.model.NewTaskModel
import com.example.tasksapp.domain.model.RepetitiveTask
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.domain.model.UserProfileModel

class MainViewModel : ViewModel() {

    val isBottomVisible = MutableLiveData(false)

    var listFriends = MutableLiveData<List<UserProfileModel>>()
    var listTask = MutableLiveData<List<TaskModel>>()

    var listRepetitiveTask = MutableLiveData<List<RepetitiveTask>>()
    var listNewTask = MutableLiveData<List<NewTaskModel>>()
    var listHabitsTask = MutableLiveData<List<HabitsTaskModel>>()

    fun setBottomVisible(changeVisible: Boolean) {
        isBottomVisible.value = changeVisible
    }

    fun addNewTask(task: NewTaskModel) {
        val newList = listNewTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listNewTask.postValue(newList)
    }

    fun addTaskModel(task: TaskModel) {
        val newList = listTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listTask.postValue(newList)
    }

    fun addHabitsTask(task: HabitsTaskModel) {
        val newList = listHabitsTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listHabitsTask.postValue(newList)
    }

    fun setFriends() {
        listFriends.value = listOf(
            UserProfileModel(
                name = "Renaldi",
                userName = "renal",
                email = "reanaldi@gmail.com",
                profileImage = "https://www.perfocal.com/blog/content/images/2021/01/Perfocal_17-11-2019_TYWFAQ_100_standard-3.jpg",
                telephone = "087463728144",
            ),
            UserProfileModel(
                name = "Retnowati",
                userName = "retnow",
                email = "retnowati@gmail.com",
                profileImage = "https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg",
                telephone = "087463728921",
            ),
            UserProfileModel(
                name = "Budi andriyanto",
                userName = "budianto",
                email = "retnowati@gmail.com",
                profileImage = "https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg",
                telephone = "087493228981",
            )
        )
    }

    init {
        setFriends()
    }
}