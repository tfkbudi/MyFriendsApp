package com.tfkbudi.myFriends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tampilkan list friend ketika activity di buat
        tampilMyFriendFragment()
    }

    //fungsi untuk berpindah fragment
    private fun gantiFragment(fragmentManager: FragmentManager,
                              fragment:Fragment, frameId:Int){
        val transaction =fragmentManager.beginTransaction()
        transaction.replace(frameId,fragment)
        transaction.commit()
    }

    //untuk menampilkan list friends
    fun tampilMyFriendFragment(){
        gantiFragment(supportFragmentManager,MyFriendsFragment.newInstance(),
            R.id.frameLayout)
    }

    //untuk menampilkan halaman add friend
    fun tampilAddFriendFragment(){
        gantiFragment(supportFragmentManager,AddFriendFragment.newInstance(),
            R.id.frameLayout)
    }
}
