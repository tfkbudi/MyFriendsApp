package com.tfkbudi.myFriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
        fun newInstance(id: Int? = null): AddFriendFragment {
            val addFriendFragment = AddFriendFragment()
            id?.let {
                val bundle = Bundle()
                /**
                 *di sini keyword it = id jadi bisa di isi id atau cukup it
                 *
                 * bundle di gunakan untuk mengirim data antar fragment
                 */
                bundle.putInt("id", it)
                addFriendFragment.arguments = bundle
            }
            return addFriendFragment
        }
    }

    private var nama: String = ""
    private var email: String = ""
    private var alamat: String = ""
    private var gender: String = ""
    private var telp: String = ""
    private var idFriend: Int? = null

    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_friend_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ambil id yang di kirim, jika tidak ada nilainya null
        idFriend = arguments?.getInt("id")

        //event klik jika button simpan di klik user
        btnSimpan.setOnClickListener {
            validasi()
        }

        initLocalDb()
        //jika id nya ada nilainya. akan mengambil data di db
        idFriend?.let {
            getFriendById(it)
        }
    }

    /**
     * inisiasi database
     */
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
            alamat.isEmpty() -> edit_telp.error = "harap di isi"
            telp.isEmpty() -> edit_telp.error = "harap di isi"
            gender == "Pilih Jenis Kelamin" -> showToast("gender harap di isi")

            else -> {
                /**
                 * asumsi nya jika idFriend null maka proses selanjutnya adalah menyimpan
                 * data friend baru
                 * tapi jika idFriend ada (tidak null) maka proses selanjutnya adalah update data friend
                 */
                val friend = Friend(nama, gender, email, telp, alamat, idFriend)
                if(idFriend == null) {
                    //insert ke db
                    addFriend(friend)
                } else {
                    //update ke db
                    updateFriend(friend)
                }
            }
        }
    }

    //fungsi untuk menambahkan data teman
    private fun addFriend(friend: Friend) : Job {
        //coroutine
        return GlobalScope.launch {
            myFriendDao?.addFriend(friend)
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }

    /**
     * fungsi untuk mengupdate data teman
     */
    private fun updateFriend(friend: Friend): Job {
        return GlobalScope.launch {
            myFriendDao?.updateFriend(friend)
            (activity as MainActivity).tampilMyFriendFragment()
        }
    }

    /**
     * fungsi untuk mendapatkan data friend dari database berdasarkan id nya
     * kemudian di tampilkan di form
     */
    private fun getFriendById(id: Int) {
        myFriendDao?.getFriendById(id)?.observe(this, Observer { friend ->
            edit_nama.setText(friend.nama)
            edit_telp.setText(friend.telp)
            edit_email.setText(friend.email)
            edit_alamat.setText(friend.alamat)
            spinner.setSelection(getIndexSpinner(friend.kelamin))
        })
    }

    /**
     * fungsi untuk mendapatkan index array spinner
     */
    private fun getIndexSpinner(value: String): Int {
        val arrayGender = resources.getStringArray(R.array.jenis_kelamin)
        return arrayGender.indexOf(value)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun showToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }
}