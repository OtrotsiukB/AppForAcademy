package com.appforacademy.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


/*@Entity(tableName = "Actor",
    foreignKeys = [ForeignKey(
        entity = MoviePersistensy::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idFromMovie"),
        onDelete = CASCADE
    )]
)*/
@Entity(tableName = "Actor")
data class ActorPersistensy(
                            @PrimaryKey(autoGenerate = true)
                            var idKey:Int?,
                            var id: Int,
                            var name: String,
                            var picture: String,
var idFromMovie: Int)
