package com.tfkbudi.myFriends.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created on : 17/05/19
 * Author     : Taufik Budi S
 * GitHub     : https://github.com/tfkbudi
 */
@Entity
data class Friend(
    val nama: String,
    val kelamin: String,
    val email: String,
    val telp: String,
    val alamat: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)