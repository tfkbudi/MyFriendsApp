package com.tfkbudi.myFriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friend_fragment.*

/**
 * Created on : 11/05/19
 * Author     : Taufik Budi S
 * GitHub     : https://github.com/tfkbudi
 */

class MyFriendsFragment: Fragment() {

    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_friend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //event klik untuk berpindah ke halaman form add fragment
        fabAdd.setOnClickListener {
            (activity as MainActivity).tampilAddFriendFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}