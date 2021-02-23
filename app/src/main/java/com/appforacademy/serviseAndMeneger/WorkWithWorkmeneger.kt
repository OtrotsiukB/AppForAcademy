package com.appforacademy.serviseAndMeneger

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import java.util.concurrent.TimeUnit

class WorkWithWorkmeneger {

    val simpleRequest = OneTimeWorkRequest.Builder(WorkMeneger::class.java).build()

    //private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresCharging(true).build()
    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    //private constarin2
    val delayedRequest = OneTimeWorkRequest.Builder(WorkMeneger::class.java)
        .setInitialDelay(15, TimeUnit.SECONDS)
        .build()
    val constrainedRequest = OneTimeWorkRequest.Builder(WorkMeneger::class.java)
        .setConstraints(constraints)
        .build()

}