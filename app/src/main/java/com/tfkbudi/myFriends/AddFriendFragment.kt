package com.tfkbudi.myFriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tfkbudi.myFriends.database.AppDatabase
import com.tfkbudi.myFriends.database.MyFriendDao
import com.tfkbudi.myFriends.model.Friend
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.add_friend_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    var nama: String = ""
    var email: String = ""
    var alamat: String = ""
    var gender: String = ""
    var telp: String = ""

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_friend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //event klik untuk berpindah ke halaman list friends
        btnSimpan.setOnClickListener {
            validasi()
        }

        initLocalDb()
    }

    private fun initLocalDb() {
        db = AppDatabase.getAppDataBase(activity!!)
        myFriendDao = db?.myFriendDao()
    }

    //validasi form jika ada yang kosong munculkan error
    private fun validasi() {
        nama = edit_nama.text.toString()
        email = edit_email.text.toString()
        alamat = edit_alamat.text.toString()
        gender = spinner.selectedItem.toString()
        telp = edit_telp.text.toString()

        when {
            nama.isEmpty() -> edit_nama.error = "harap di isi"
            email.isEmpty() -> edit_email.error = "harap di isi"
//            alamat.isEmpty() -> edit_telp.error = "harap di isi"
            telp.isEmpty() -> edit_telp.error = "harap di isi"
            gender.equals("Pilih Jenis Kelamin") -> showToast("gender harap di isi")

            else -> {
                //insert ke db
                val friend = Friend(nama, gender, email, telp, alamat)
                tambahDataTeman(friend)
            }

        }
    }

    //fungsi untuk menambahkan data teman
    private fun tambahDataTeman(friend: Friend) : Job {
        //coroutine
        return GlobalScope.launch {
            myFriendDao?.addFriend(friend)
            (activity as MainActivity).tampilMyFriendFragment()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun showToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }
}