package ru.pl.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.pl.a7minuteworkout.historyDB.HistoryEntryEntity
import ru.pl.a7minuteworkout.historyDB.HistoryEntryEntityAdapter
import ru.pl.a7minuteworkout.historyDB.WorkoutApp
import ru.pl.a7minuteworkout.historyDB.HistoryEntryEntityDAO
import ru.pl.a7minuteworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = (application as WorkoutApp).db.historyEntryEntityDao()

        setSupportActionBar(binding.toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "История занятий"
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        lifecycleScope.launch {
            dao.fetchAllItems().collect {
                setupListOfItemsIntoRV(it, dao)
            }
        }

        binding.clearButton.setOnClickListener {
            clearAllItemsDialog(dao)
        }
    }

    private fun setupListOfItemsIntoRV(
        entityList: List<HistoryEntryEntity>,
        dao: HistoryEntryEntityDAO
    ) {
        val entityAdapter = HistoryEntryEntityAdapter(entityList)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = entityAdapter
    }

    private fun clearAllItemsDialog(dao: HistoryEntryEntityDAO) {
        AlertDialog.Builder(this)
            .setTitle("Очистить историю?")
            .setCancelable(false)
            .setPositiveButton("Да") { dialogInterface, _ ->
                lifecycleScope.launch {
                    dao.deleteAll()
                    Toast.makeText(applicationContext, "Данные удалены", Toast.LENGTH_SHORT).show()
                }
                dialogInterface.dismiss()
            }
            .setNegativeButton("Нет") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create().show()
    }
}