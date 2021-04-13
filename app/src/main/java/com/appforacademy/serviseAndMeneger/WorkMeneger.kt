package com.appforacademy.serviseAndMeneger

import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkMeneger(context: Context, params: WorkerParameters): Worker(context, params) {

    private var isBound = false
    private var service: UpdateBoundService? = null
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            isBound = true

            service = (binder as? UpdateBoundService.UpdateBoundServiceBinder)?.getService()
            service?.updateInfoViaServise()

            Log.i(TAG, "Запуск работы сервиса")

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i(TAG, "Прекращение работы сервиса")
            isBound = false
             service = null
        }
    }

    override fun doWork(): Result {
            val intent = Intent(applicationContext,UpdateBoundService::class.java)
            applicationContext.bindService(intent,serviceConnection,BIND_AUTO_CREATE)

            Log.i(TAG, "Запуск работы сервиса")
            return Result.success()


    }


}