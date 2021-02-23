package com.appforacademy.serviseAndMeneger

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkMeneger(context: Context, params: WorkerParameters): Worker(context, params) {
    var x = true

    override fun doWork(): Result {
        if (x==true){
            Log.i(TAG, "Запуск работы воркменеджера")
            return Result.success()
        }else{

            return Result.failure()
        }

    }


}