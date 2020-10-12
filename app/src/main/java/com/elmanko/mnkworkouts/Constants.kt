package com.elmanko.mnkworkouts

class Constants {
    companion object{ //like static in java
        fun defaultExerciseList():ArrayList<ExerciseModel>{ // get the list of exercises that will be in our model as an ArrayList
            var exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1,"Jumping Jacks",R.drawable.ic_jumping_jacks,false,false)
            val abdominalCrunch = ExerciseModel(2,"Abdominal Crunch",R.drawable.ic_abdominal_crunch,false,false)
            val highKneesRunningInPlace = ExerciseModel(3,"HighKnees Running In Place",R.drawable.ic_high_knees_running_in_place,false,false)
            val lunge = ExerciseModel(4,"Body Weight Lunge",R.drawable.ic_lunge,false,false)
            val plank = ExerciseModel(5,"Core Plank",R.drawable.ic_plank,false,false)
            val pushUp = ExerciseModel(6,"Push-Up",R.drawable.ic_push_up,false,false)
            val pushUpAndRotation = ExerciseModel(7,"Push-Up And Rotation",R.drawable.ic_push_up_and_rotation,false,false)
            val sidePlank = ExerciseModel(8,"Core Side Plank",R.drawable.ic_side_plank,false,false)
            val stepUpOntoChair = ExerciseModel(9,"Step-Up Onto Chair",R.drawable.ic_step_up_onto_chair,false,false)
            val tricepsDipOnChair = ExerciseModel(10,"Triceps Dip On Chair",R.drawable.ic_triceps_dip_on_chair,false,false)
            val wallSit = ExerciseModel(11,"WallSit",R.drawable.ic_wall_sit,false,false)

            exerciseList.add(jumpingJacks)
            exerciseList.add(abdominalCrunch)
            exerciseList.add(highKneesRunningInPlace)
            exerciseList.add(lunge)
            exerciseList.add(plank)
            exerciseList.add(pushUp)
            exerciseList.add(pushUpAndRotation)
            exerciseList.add(sidePlank)
            exerciseList.add(stepUpOntoChair)
            exerciseList.add(tricepsDipOnChair)
            exerciseList.add(wallSit)

            return exerciseList

        }

    }
}