package ru.pl.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.pl.a7minuteworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "История занятий"
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}