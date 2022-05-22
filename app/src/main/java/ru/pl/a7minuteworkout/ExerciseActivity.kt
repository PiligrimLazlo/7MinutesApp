package ru.pl.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.pl.a7minuteworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private lateinit var restTimer: CountDownTimer
    private var progressRest = 10
    private lateinit var exerciseTimer: CountDownTimer
    private var progressExercise = 30

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbarExercise)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.defaultExerciseList()

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }

    private fun setupRestView() {
        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE

        binding.tvNextExerciseLabel.visibility = View.VISIBLE
        binding.tvNextExerciseName.visibility = View.VISIBLE
        binding.tvNextExerciseName.text = exerciseList[currentExercisePosition + 1].name

        binding.tvExerciseName.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.ivImage.visibility = View.INVISIBLE

        setProgressBarRest()
    }


    private fun setupExerciseView() {
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE

        binding.tvNextExerciseLabel.visibility = View.INVISIBLE
        binding.tvNextExerciseName.visibility = View.INVISIBLE


        binding.tvExerciseName.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE

        binding.ivImage.setImageResource(exerciseList[currentExercisePosition].image)
        binding.tvExerciseName.text = exerciseList[currentExercisePosition].name

        setProgressBarExercise()
    }

    private fun setProgressBarRest() {
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBarRest.progress = progressRest
                binding.tvTimerRest.text = progressRest.toString()
                progressRest--
            }

            override fun onFinish() {
                progressRest = 10

                currentExercisePosition++
                setupExerciseView()
            }

        }.start()
    }

    private fun setProgressBarExercise() {

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBarExercise.progress = progressExercise
                binding.tvTimerExercise.text = progressExercise.toString()
                progressExercise--
            }

            override fun onFinish() {
                progressExercise = 30

                if (currentExercisePosition < exerciseList.size - 1) {
                    setupRestView()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Поздравляем, вы завершили 7 minute workout!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::restTimer.isInitialized)
            restTimer.cancel()
        if (this::exerciseTimer.isInitialized)
            exerciseTimer.cancel()
    }
}