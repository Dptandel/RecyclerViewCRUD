package com.tops.kotlin.recyclerviewcrud.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tops.kotlin.recyclerviewcrud.R
import com.tops.kotlin.recyclerviewcrud.databinding.ItemUserLayoutBinding
import com.tops.kotlin.recyclerviewcrud.models.User

class UserListAdapter(
    private val context: Context,
    private val userList: MutableList<User>,
    private var itemClicked: ((user: User) -> Unit)? = null
) :
    Adapter<UserListAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.binding.tvID.text = user.id
        holder.binding.tvUserName.text = user.userName
        holder.binding.tvUserEmail.text = user.userEmail

        holder.binding.ivDelete.setOnClickListener {
            deleteUser(user.id)
        }

        holder.binding.ivUpdate.setOnClickListener {
            itemClicked?.invoke(user)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addUser(user: User) {
        userList.add(user)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteUser(id: String) {
        userList.removeIf {
            it.id == id
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUser(user: User) {
        val index = userList.indexOfFirst {
            it.id == user.id
        }

        userList[index] = user

        notifyDataSetChanged()
    }

}