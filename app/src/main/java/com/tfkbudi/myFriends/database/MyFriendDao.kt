package com.tfkbudi.myFriends.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tfkbudi.myFriends.model.Friend

/**
 * Created on : 17/05/19
 * Author     : Taufik Budi S
 * GitHub     : https://github.com/tfkbudi
 */
@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFriend(friend: Friend)

    @Update
    fun updateFriend(friend: Friend)

    @Query("SELECT * FROM Friend")
    fun getFriends(): LiveData<List<Friend>>

    //parameter id di query ini
    @Query("SELECT * FROM Friend WHERE id = :id")
    // ngambil dr sini
    fun getFriendById(id: Int): LiveData<Friend>

    @Delete
    fun deleteFriend(friend: Friend)
}