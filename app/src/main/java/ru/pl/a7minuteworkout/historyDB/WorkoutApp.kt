package ru.pl.a7minuteworkout.historyDB

import android.app.Application

class WorkoutApp: Application() {
    val db by lazy {
        HistoryEntryDatabase.getInstance(this)
    }
}