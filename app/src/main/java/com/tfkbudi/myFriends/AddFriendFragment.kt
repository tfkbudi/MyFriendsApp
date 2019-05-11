package com.tfkbudi.myFriends

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*

/**
 * Created on : 11/05/19
 * Author     : Taufik Budi S
 * GitHub     : https://github.com/tfkbudi
 */
class AddFriendFragment: Fragment() {

    companion object {
        fun newInstance(): AddFriendFragment {
            return AddFriendFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_friend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}