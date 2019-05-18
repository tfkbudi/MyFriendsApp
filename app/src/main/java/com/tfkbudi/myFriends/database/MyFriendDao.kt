package com.tfkbudi.myFriends.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM Friend")
    fun getFriends(): LiveData<List<Friend>>
}