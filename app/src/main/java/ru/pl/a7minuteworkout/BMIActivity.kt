package ru.pl.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.pl.a7minuteworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBMIActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Расчет ИМТ"
        binding.toolbarBMIActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricsUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricsUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding.buttonCalculateUnits.setOnClickListener {
            calculateUnits()
        }

    }

    private fun makeVisibleMetricsUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE

        binding.tilUsUnitWeight.visibility = View.INVISIBLE
        binding.tilUsUnitHeightFeet.visibility = View.INVISIBLE
        binding.tilUsUnitHeightInch.visibility = View.INVISIBLE

        binding.etMetricUnitWeight.text.clear()
        binding.etMetricUnitHeight.text.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilMetricUnitHeight.visibility = View.INVISIBLE

        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.tilUsUnitHeightFeet.visibility = View.VISIBLE
        binding.tilUsUnitHeightInch.visibility = View.VISIBLE

        binding.etUsUnitWeight.text.clear()
        binding.etUsUnitHeightFeet.text.clear()
        binding.etUsUnitHeightInch.text.clear()

        binding.llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi <= 15f) {
            bmiLabel = "Очень сильный недостаток веса"
            bmiDescription = "Упс! Вам нужно привести себя в порядок! Ешьте больше!"
        } else if (bmi > 15f && bmi <= 16) {
            bmiLabel = "Средний недостаток веса"
            bmiDescription = "Упс! Вам нужно привести себя в порядок! Ешьте больше!"
        } else if (bmi > 16f && bmi <= 18.5) {
            bmiLabel = "Легкий недостаток веса"
            bmiDescription = "Упс! Вам нужно привести себя в порядок! Ешьте больше!"
        } else if (bmi > 18.5f && bmi <= 25) {
            bmiLabel = "Нормальный вес"
            bmiDescription = "Подздравляем! Вы в хорошей форме!"
        } else if (bmi > 25f && bmi <= 30) {
            bmiLabel = "Легкая полнота"
            bmiDescription = "Упс! Вам нужно привести себя в порядок! Ешьте меньше!"
        } else if (bmi > 30f && bmi <= 35) {
            bmiLabel = "Ожирение 1 степени (легкое)"
            bmiDescription = "Упс! Вам нужно привести себя в порядок! Ешьте меньше!"
        } else if (bmi > 35f && bmi <= 40) {
            bmiLabel = "Ожирение 2 степени (среднее)"
            bmiDescription = "Вы в очень плохой форме! Приведите себя в порядок"
        } else {
            bmiLabel = "Ожирение 3 степени (сильное)"
            bmiDescription = "Вы в очень плохой форме! Приведите себя в порядок"
        }

        val bmiValue =
            BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        if (binding.etMetricUnitWeight.text.toString().isEmpty() ||
            binding.etMetricUnitHeight.text.toString().isEmpty()
        )
            isValid = false
        return isValid
    }

    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue = binding.etMetricUnitWeight.text.toString().toFloat()
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (validateUsUnits()) {
                val usUnitHeightValueFeet = binding.etUsUnitHeightFeet.text.toString()
                val usUnitHeightValueInch = binding.etUsUnitHeightInch.text.toString()
                val usUnitHeightValueWeight = binding.etUsUnitWeight.text.toString().toFloat()

                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12
                val bmi = 703 * (usUnitHeightValueWeight / (heightValue * heightValue))

                displayBMIResult(bmi)
            }  else {
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        if (binding.etUsUnitWeight.text.toString().isEmpty() ||
            binding.etUsUnitHeightFeet.text.toString().isEmpty() ||
            binding.etUsUnitHeightInch.text.toString().isEmpty()
        )
            isValid = false
        return isValid
    }
}