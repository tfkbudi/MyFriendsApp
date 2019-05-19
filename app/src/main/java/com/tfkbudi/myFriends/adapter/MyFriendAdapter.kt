package com.tfkbudi.myFriends.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tfkbudi.myFriends.R
import com.tfkbudi.myFriends.model.Friend
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.my_friends_item.view.*

/**
 * Created on : 17/05/19
 * Author     : Taufik Budi S
 * GitHub     : https://github.com/tfkbudi
 */
class MyFriendAdapter(private val context: Context, private val items: List<Friend>) :
    RecyclerView.Adapter<MyFriendAdapter.ViewHolder>() {
    //inisiasi listener
    private lateinit var listener: MyFriendClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.my_friends_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //passing item, listener dan adapter position (posisi list di recyclerview)
        holder.bindItem(items.get(position), listener, holder.adapterPosition)
    }

    override fun getItemCount(): Int = items.size

    //set value listener
    fun setMyFriendClickListener(listener: MyFriendClickListener) {
        this.listener = listener
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(item: Friend, listener: MyFriendClickListener, position: Int) {
            //set value ke textview di layout item recyclerview
            containerView.txtFriendName.text = item.nama
            containerView.txtFriendEmail.text = item.email
            containerView.txtFriendTelp.text = item.telp
            //tambah event long klik
            containerView.container.setOnLongClickListener {
                /**
                 * ketika ada event klik, akan mengirim objek item(Friend) dan posisi objek tsb di list.
                 */
                listener.onLongClick(item, position)
                true
            }

            containerView.container.setOnClickListener { listener.onClick(item.id) }
        }

    }

    //interface untuk callback event
    interface MyFriendClickListener {
        fun onLongClick(friend: Friend, position: Int)

        fun onClick(id: Int?)
    }
}