package ru.pl.a7minuteworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1,
            "Прыжки вверх",
            R.drawable.ic_jumping_jacks
        ).also { exerciseList.add(it) }

        val abdominalCrunch = ExerciseModel(
            2,
            "Скручивания на пресс",
            R.drawable.ic_abdominal_crunch
        ).also { exerciseList.add(it) }


        val highKneesRunningInPlace = ExerciseModel(
            3,
            "Бег на месте высоко поднимая колени",
            R.drawable.ic_high_knees_running_in_place
        ).also { exerciseList.add(it) }

        val lunge = ExerciseModel(
            4,
            "Выпады",
            R.drawable.ic_lunge
        ).also { exerciseList.add(it) }

        val plank = ExerciseModel(
            5,
            "Планка",
            R.drawable.ic_plank
        ).also { exerciseList.add(it) }

        val pushUp = ExerciseModel(
            6,
            "Отжимания",
            R.drawable.ic_push_up
        ).also { exerciseList.add(it) }

        val pushUpAndRotation = ExerciseModel(
            7,
            "Отжимания с поворотом",
            R.drawable.ic_push_up_and_rotation
        ).also { exerciseList.add(it) }

        val sidePlank = ExerciseModel(
            8,
            "Боковая планка",
            R.drawable.ic_side_plank
        ).also { exerciseList.add(it) }

        val squat = ExerciseModel(
            9,
            "Приседания",
            R.drawable.ic_squat
        ).also { exerciseList.add(it) }

        val stepUpOntoChair = ExerciseModel(
            10,
            "Подъем на стул",
            R.drawable.ic_step_up_onto_chair
        ).also { exerciseList.add(it) }

        val tricepsDipOnChair = ExerciseModel(
            11,
            "Отжимания от стула на трицепс",
            R.drawable.ic_triceps_dip_on_chair
        ).also { exerciseList.add(it) }

        val wallSit = ExerciseModel(
            12,
            "Стульчик у стены",
            R.drawable.ic_wall_sit
        ).also { exerciseList.add(it) }

        return exerciseList
    }
}