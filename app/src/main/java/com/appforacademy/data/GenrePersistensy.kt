package com.appforacademy.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*@Entity(tableName = "Genre",
    foreignKeys = [ForeignKey(
        entity = MoviePersistensy::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idFromMovie"),
        onDelete = ForeignKey.CASCADE
    )])*/
@Entity(tableName = "Genre")
data class GenrePersistensy(@PrimaryKey(autoGenerate = true)
                            var idKey:Int?,var id: Int, var name: String,var idFromMovie:Int)
