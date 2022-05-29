package ru.pl.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.pl.a7minuteworkout.historyDB.HistoryEntryEntity
import ru.pl.a7minuteworkout.historyDB.WorkoutApp
import ru.pl.a7minuteworkout.historyDB.HistoryEntryEntityDAO
import ru.pl.a7minuteworkout.databinding.ActivityFinishBinding
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = (application as WorkoutApp).db.historyEntryEntityDao()

        binding.buttonFinish.setOnClickListener {
            addExerciseIntoDB(dao)

            val intentEnd = Intent(this, MainActivity::class.java)
            intentEnd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            finish()
        }
    }

    private fun addExerciseIntoDB(dao: HistoryEntryEntityDAO) {
        val curDate = Date()

        lifecycleScope.launch {
            dao.insert(HistoryEntryEntity(finishDate = curDate))
        }
    }


}