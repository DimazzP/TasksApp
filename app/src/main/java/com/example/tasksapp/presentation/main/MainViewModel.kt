package com.example.tasksapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasksapp.domain.enums.EnumTask
import com.example.tasksapp.domain.model.GoalInterval
import com.example.tasksapp.domain.model.GoalModel
import com.example.tasksapp.domain.model.GoalTarget
import com.example.tasksapp.domain.model.HabitsTaskModel
import com.example.tasksapp.domain.model.NewTaskModel
import com.example.tasksapp.domain.model.RepetitiveTask
import com.example.tasksapp.domain.model.TaskModel
import com.example.tasksapp.domain.model.TeamTaskModel
import com.example.tasksapp.domain.model.UserProfileModel
import com.example.tasksapp.domain.model.utils.SubTask
import java.time.LocalDate
import java.time.LocalDateTime

class MainViewModel : ViewModel() {

    val isBottomVisible = MutableLiveData(false)

    var listFriends = MutableLiveData<List<UserProfileModel>>()
    var listTask = MutableLiveData<List<TaskModel>>()

    var listRepetitiveTask = MutableLiveData<List<RepetitiveTask>>()
    var listNewTask = MutableLiveData<List<NewTaskModel>>()
    var listHabitsTask = MutableLiveData<List<HabitsTaskModel>>()
    var listGoalTask = MutableLiveData<List<GoalModel>>()
    var listTeamTask = MutableLiveData<List<TeamTaskModel>>()
    var selectedGoal: GoalModel? = null


    fun setBottomVisible(changeVisible: Boolean) {
        isBottomVisible.value = changeVisible
    }

    fun addNewTask(task: NewTaskModel) {
        val newList = listNewTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listNewTask.postValue(newList)
    }

    fun addRepetitive(task: RepetitiveTask) {
        val newList = listRepetitiveTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listRepetitiveTask.postValue(newList)
    }

    fun addGoal(task: GoalModel) {
        val newList = listGoalTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listGoalTask.postValue(newList)
    }

    fun addTeam(task: TeamTaskModel) {
        val newList = listTeamTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listTeamTask.postValue(newList)
    }

    fun findTasksByDate(date: LocalDateTime, listData: List<TaskModel>): List<TaskModel>? {
        val targetDate = date.toLocalDate()
        return listData.filter {
            it.startDate?.toLocalDate() == targetDate
        }
    }

    fun addTaskModel(task: TaskModel) {
        val newList = listTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listTask.value = (newList)
    }

    fun addHabitsTask(task: HabitsTaskModel) {
        val newList = listHabitsTask.value?.toMutableList() ?: mutableListOf()
        newList.add(task)
        listHabitsTask.postValue(newList)
    }

    fun setDummy() {
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
        listGoalTask.value = listOf(
            GoalModel(
                id = 1,
                title = "Learn Kotlin",
                description = "Complete the Kotlin basics course and create a small project.",
                startDate = LocalDateTime.of(2023, 12, 1, 10, 0),
                endDate = LocalDateTime.of(2023, 12, 31, 18, 0),
                remember = 3,
                priority = 1,
                goalTarget = listOf(
                    GoalTarget(
                        selectFreq = 2,
                        interval = GoalInterval(start = 1, end = 7),
                        alreadyFinish = false,
                        currency = GoalInterval(start = 0, end = 10),
                        titleTarget = "Baca Buku"
                    )
                ),
                progress = 50,
                teams = null
            ),
            GoalModel(
                id = 2,
                title = "Fitness Goal",
                description = "Work out 4 times a week and track progress.",
                startDate = LocalDateTime.of(2023, 11, 1, 6, 0),
                endDate = LocalDateTime.of(2024, 1, 31, 6, 0),
                remember = 5,
                priority = 2,
                goalTarget = listOf(
                    GoalTarget(
                        selectFreq = 4,
                        interval = GoalInterval(start = 1, end = 30),
                        alreadyFinish = false,
                        currency = GoalInterval(start = 0, end = 50),
                        titleTarget = "Baca buku"
                    )
                ),
                progress = 25,
                teams = null
            ),
            GoalModel(
                id = 3,
                title = "Read Books",
                description = "Read 2 books by the end of the month.",
                startDate = LocalDateTime.of(2023, 12, 5, 9, 0),
                endDate = LocalDateTime.of(2023, 12, 31, 21, 0),
                remember = 1,
                priority = 3,
                goalTarget = listOf(
                    GoalTarget(
                        selectFreq = 1,
                        interval = GoalInterval(start = 1, end = 30),
                        alreadyFinish = true,
                        currency = GoalInterval(start = 0, end = 2),
                        titleTarget = "Baca buku"
                    )
                ),
                progress = 100,
                teams = null
            )
        )
        listRepetitiveTask.value = listOf(
            RepetitiveTask(
                id = 1,
                title = "Rapat Harian Stand-up",
                description = "Menghadiri rapat harian dengan tim untuk membahas perkembangan pekerjaan.",
                subTask = listOf(
                    SubTask(name = "Membahas tugas kemarin"),
                    SubTask(name = "Merencanakan tugas hari ini"),
                    SubTask(name = "Mengatasi hambatan")
                ),
                freqTask = 1, // Harian
                freqWeekly = null,
                freqMontly = null,
                freqYearly = null,
                freqActivityRest = null,
                startDate = LocalDateTime.of(2023, 12, 1, 9, 0),
                endDate = LocalDateTime.of(2023, 12, 31, 9, 15),
                reminder = 10, // Ingatkan 10 menit sebelum acara
                priority = 2, // Prioritas sedang
                postpone = false // Tidak dapat ditunda
            ),
            RepetitiveTask(
                id = 2,
                title = "Pengumpulan Laporan Mingguan",
                description = "Mempersiapkan dan mengirimkan laporan proyek mingguan kepada manajer.",
                subTask = listOf(
                    SubTask(name = "Mengumpulkan pembaruan dari tim"),
                    SubTask(name = "Menyusun laporan"),
                    SubTask(name = "\"Mengirimkan ke manajer\"")
                ),
                freqTask = 1,
                freqWeekly = listOf(2), // Setiap hari Selasa
                freqMontly = null,
                freqYearly = null,
                freqActivityRest = null,
                startDate = LocalDateTime.of(2023, 12, 5, 14, 0),
                endDate = LocalDateTime.of(2024, 1, 30, 14, 0),
                reminder = 15, // Ingatkan 15 menit sebelum acara
                priority = 3, // Prioritas tinggi
                postpone = false // Tidak dapat ditunda
            ),
            RepetitiveTask(
                id = 3,
                title = "Review Anggaran Bulanan",
                description = "Melakukan review anggaran departemen dan menyusun rencana anggaran untuk bulan berikutnya.",
                subTask = listOf(
                    SubTask(name = "Menganalisis pengeluaran"),
                    SubTask(name = "Merencanakan anggaran berikutnya"),
                    SubTask(name = "Menyusun laporan")
                ),
                freqTask = 1,
                freqWeekly = null,
                freqMontly = listOf(1, 15), // Setiap tanggal 1 dan 15 setiap bulan
                freqYearly = null,
                freqActivityRest = null,
                startDate = LocalDateTime.of(2023, 12, 1, 10, 0),
                endDate = LocalDateTime.of(2024, 12, 1, 12, 0),
                reminder = 30, // Ingatkan 30 menit sebelum acara
                priority = 1, // Prioritas sangat tinggi
                postpone = true // Dapat ditunda
            )
        )

        listHabitsTask.value = listOf(
            HabitsTaskModel(
                id = 1,
                title = "Meditasi Harian",
                description = "Lakukan meditasi selama 10 menit setiap hari.",
                evaluation = 4,
                frequencyWork = 1, // Harian
                evaluationNumeric = 8, // Tingkat evaluasi: 8/10
                frequencyTask = 1, // Harian
                frequencyEveryWeek = null,
                frequencyActivityRest = null,
                startDate = LocalDate.of(2023, 12, 1),
                endDate = LocalDate.of(2023, 12, 31),
                remember = 15, // Ingatkan 15 menit sebelum
                priority = 3 // Prioritas tinggi
            ),
            HabitsTaskModel(
                id = 2,
                title = "Olahraga Mingguan",
                description = "Berolahraga minimal 3 kali seminggu untuk menjaga kesehatan.",
                evaluation = 5,
                frequencyWork = 3, // 3 kali seminggu
                evaluationNumeric = 7, // Tingkat evaluasi: 7/10
                frequencyTask = 3, // 3 kali seminggu
                frequencyEveryWeek = listOf(2, 4, 6), // Selasa, Kamis, Sabtu
                frequencyActivityRest = null,
                startDate = LocalDate.of(2023, 11, 20),
                endDate = LocalDate.of(2024, 1, 20),
                remember = 10, // Ingatkan 10 menit sebelum
                priority = 2 // Prioritas sedang
            ),
            HabitsTaskModel(
                id = 3,
                title = "Membaca Buku",
                description = "Membaca minimal 20 halaman setiap hari.",
                evaluation = 3,
                frequencyWork = 1, // Harian
                evaluationNumeric = 6, // Tingkat evaluasi: 6/10
                frequencyTask = 1, // Harian
                frequencyEveryWeek = null,
                frequencyActivityRest = null,
                startDate = LocalDate.of(2023, 12, 5),
                endDate = LocalDate.of(2023, 12, 31),
                remember = 30, // Ingatkan 30 menit sebelum
                priority = 1 // Prioritas sangat tinggi
            )
        )

        listNewTask.value = listOf(
            NewTaskModel(
                id = 101,
                title = "Complete Project Proposal",
                description = "Draft and finalize the proposal for the new project.",
                startDate = LocalDateTime.now().plusDays(3),
                priority = 1,
                reminder = 15, // Reminder 15 minutes before
                postpone = false
            ),
            NewTaskModel(
                id = 102,
                title = "Weekly Team Sync-Up",
                description = "Regular meeting to align on weekly tasks and goals.",
                startDate = LocalDateTime.now().plusHours(5),
                priority = 2,
                reminder = 30, // Reminder 30 minutes before
                postpone = true // Task has been postponed
            ),
            NewTaskModel(
                id = 103,
                title = "Submit Expense Report",
                description = "Compile and submit the expense report for the last quarter.",
                startDate = LocalDateTime.now().plusDays(7),
                priority = 3,
                reminder = 60, // Reminder 1 hour before
                postpone = false
            )
        )

        listNewTask.value?.forEach { newTask ->
            val lastTaskModel = listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1
            addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    enumTask = EnumTask.NEW,
                    idKeyTask = newTask.id,
                    titleTask = newTask.title,
                    subTask = null, // Konversi ke List<SubTask>
                    time = null,
                    repetitive = true,
                    startDate = newTask.startDate,
                    teams = null // Tidak ada teams
                )
            )
        }

        listRepetitiveTask.value?.forEach { repetitiveTask ->
            val lastTaskModel = listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1
            addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    enumTask = EnumTask.REPETITIVE,
                    idKeyTask = repetitiveTask.id,
                    titleTask = repetitiveTask.title,
                    subTask = null, // Konversi ke List<SubTask>
                    time = null,
                    repetitive = true,
                    startDate = repetitiveTask.startDate,
                    teams = null // Tidak ada teams
                )
            )
        }


// Konversi dari HabitsTaskModel
        listHabitsTask.value?.forEach { habitsTask ->
            val lastTaskModel = listTask.value?.last()
            val finalIdTaskModel = lastTaskModel?.idTask?.plus(1) ?: 1
            addTaskModel(
                TaskModel(
                    idTask = finalIdTaskModel,
                    enumTask = EnumTask.HABITS,
                    idKeyTask = habitsTask.id,
                    titleTask = habitsTask.title,
                    subTask = null, // HabitsTask tidak memiliki subTask langsung
                    time = null,
                    repetitive = false,
                    startDate = habitsTask.startDate.atStartOfDay(), // Konversi LocalDate ke LocalDateTime
                    teams = null // Tidak ada teams
                )
            )
        }
    }

    init {
        setDummy()
    }
}