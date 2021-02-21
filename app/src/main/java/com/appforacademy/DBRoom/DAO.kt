package com.appforacademy.DBRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.appforacademy.data.ActorPersistensy
import com.appforacademy.data.GenrePersistensy
import com.appforacademy.data.MoviePersistensy

@Dao
interface DAO {
    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<MoviePersistensy>

  /*  @Insert
    suspend fun insertAllMovies(films: List<MoviePersistensy>)
    @Insert
    suspend fun insertAllGenres(films: List<GenrePersistensy>)
    @Insert
    suspend fun insertAllActor(films: List<ActorPersistensy>)
*/
    @Insert
    fun insertMovies(moviePersistensy: MoviePersistensy)
    @Insert
    fun insertGenre(genrePersistensy: GenrePersistensy)
    @Insert
    fun insertActor(actorPersistensy: ActorPersistensy)



    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovies()
    @Query("DELETE FROM Genre")
    suspend fun deleteAllGenre()
    @Query("DELETE FROM Actor")
    suspend fun deleteAllActor()

    @Query("SELECT * FROM Genre WHERE idFromMovie == :id")
    suspend fun getGenreFromMovie(id: Int):List<GenrePersistensy>
    @Query("SELECT * FROM Actor WHERE idFromMovie == :id")
    suspend fun getActorFromMovie(id: Int):List<ActorPersistensy>
}