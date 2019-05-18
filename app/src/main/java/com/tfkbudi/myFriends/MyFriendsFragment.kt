package com.tfkbudi.myFriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tfkbudi.myFriends.adapter.MyFriendAdapter
import com.tfkbudi.myFriends.database.AppDatabase
import com.tfkbudi.myFriends.database.MyFriendDao
import com.tfkbudi.myFriends.model.Friend
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.my_friend_fragment.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



/**
 * Created on : 11/05/19
 * Author     : Taufik Budi S
 * GitHub     : https://github.com/tfkbudi
 */

class MyFriendsFragment: Fragment() {

    lateinit var listTeman: MutableList<Friend>
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null
    //tambah
    lateinit var adapter: MyFriendAdapter

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

        listTeman = ArrayList()
        initLocalDB()
        initView()

        ambilDataTeman()
    }

    private fun initView() {
//        simulasiDataTeman()
        initAdapter()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    /** sample data teman */
//    private fun simulasiDataTeman() {
//
//        listTeman = ArrayList()
//
////        listTeman.add(Friend("Muhammad", "Laki-laki", "ade@gmail.com", "085719004268", "Bandung"))
////        listTeman.add(Friend("Al Harits", "Laki-laki", "rifaldi@gmail.com", "081213416171", "Bandung"))
//    }

    /* init recylerview with adapter */
    private fun initAdapter() {
        adapter = MyFriendAdapter(activity!!, listTeman)
        adapter.setMyFriendClickListener(object : MyFriendAdapter.MyFriendClickListener{
            override fun onLongClick(friend: Friend, position:Int) {
                confrimDialog(friend, position)
            }
        })

        rvMyFriends.layoutManager = LinearLayoutManager(activity)
        rvMyFriends.adapter = adapter

    }

    /**
     * get data teman dari database
     */
    private fun ambilDataTeman() {
        //ambil data teman menggunakan livedata
        myFriendDao?.getFriends()?.observe(this, Observer { r ->
            //clear data teman sebelumnya
            if(listTeman.isNotEmpty()) listTeman.clear()
            //tambahkan data teman yg baru
            listTeman.addAll(r)
            when {
                //check jika listnya kosong
                listTeman.size == 0 -> tampilToast("Belum ada data teman")

                else -> {
                    adapter.notifyDataSetChanged()
                }

            }
        })

    }

    //menampilkan toast
    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun confrimDialog(friend: Friend, position: Int) {
        AlertDialog.Builder(activity!!)
            .setTitle("Delete ${friend.nama}")
            .setMessage("Do you really want to delete ${friend.nama} ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    deleteFriend(friend)
                    adapter.notifyItemRemoved(position)
                }).show()
    }

    //delete friend ke database
    private fun deleteFriend(friend: Friend): Job {
        return GlobalScope.launch {
            myFriendDao?.deleteFriend(friend)
        }
    }
}