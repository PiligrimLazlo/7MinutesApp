package ru.pl.a7minuteworkout

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ru.pl.a7minuteworkout.databinding.ActivityExerciseBinding
import ru.pl.a7minuteworkout.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private lateinit var restTimer: CountDownTimer
    private var progressRest = 10
    private lateinit var exerciseTimer: CountDownTimer
    private var progressExercise = 30

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExercisePosition = -1

    private lateinit var tts: TextToSpeech
    private lateinit var player: MediaPlayer

    private lateinit var exerciseAdapter: ExerciseStatusAdapter

    private val restTimerDuration: Long = 1
    private val exerciseTimerDuration: Long = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpTextToSpeech()

        setSupportActionBar(binding.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.defaultExerciseList()

        binding.toolbarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.yesButton.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.noButton.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun setUpTextToSpeech() {
        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale("RU"))
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("TTS", "Language problem")
                }
            } else {
                Log.e("TTS", "Initialization problem")
            }
        }
    }

    private fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    private fun setupRestView() {

        try {
            val soundURI =
                Uri.parse("android.resource://ru.pl.a7minuteworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundURI)
            player.isLooping = false
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

        speak(binding.tvExerciseName.text.toString())

        setProgressBarExercise()
    }

    private fun setProgressBarRest() {
        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBarRest.progress = progressRest
                binding.tvTimerRest.text = progressRest.toString()
                progressRest--
            }

            override fun onFinish() {
                progressRest = 10

                currentExercisePosition++

                exerciseList[currentExercisePosition].isSelected = true
                exerciseAdapter.notifyDataSetChanged()
                setupExerciseView()
            }

        }.start()
    }

    private fun setProgressBarExercise() {

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBarExercise.progress = progressExercise
                binding.tvTimerExercise.text = progressExercise.toString()
                progressExercise--
            }

            override fun onFinish() {
                progressExercise = 30

                if (currentExercisePosition < exerciseList.size - 1) {
                    exerciseList[currentExercisePosition].isSelected = false
                    exerciseList[currentExercisePosition].isCompleted = true
                    exerciseAdapter.notifyDataSetChanged()

                    setupRestView()
                } else {
                    val intentFinish = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intentFinish)
                    finish()
                }
            }

        }.start()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
//        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::restTimer.isInitialized)
            restTimer.cancel()
        if (this::exerciseTimer.isInitialized)
            exerciseTimer.cancel()

        tts.stop()
        tts.shutdown()

        if (this::player.isInitialized)
            player.stop()
    }
}