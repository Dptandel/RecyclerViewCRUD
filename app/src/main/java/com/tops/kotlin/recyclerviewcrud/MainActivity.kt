package com.tops.kotlin.recyclerviewcrud

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tops.kotlin.recyclerviewcrud.adapters.UserListAdapter
import com.tops.kotlin.recyclerviewcrud.databinding.ActivityMainBinding
import com.tops.kotlin.recyclerviewcrud.databinding.UpdateDialogLayoutBinding
import com.tops.kotlin.recyclerviewcrud.models.User
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserListAdapter
    private lateinit var userList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserListAdapter(this, mutableListOf()) { user: User ->
            showUpdateDialog(user)
        }

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = userAdapter

        binding.btnSubmit.setOnClickListener {
            val name = binding.edtName.text.trim().toString()
            val email = binding.edtEmail.text.trim().toString()
            val id = generateUniqueID()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val user = User(id = id, userName = name, userEmail = email)

                //add user
                userAdapter.addUser(user)
                binding.edtName.setText("")
                binding.edtEmail.setText("")

                binding.edtName.requestFocus()
            } else {
                Toast.makeText(this, "Please enter both name and email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showUpdateDialog(user: User) {
        val builder = AlertDialog.Builder(this)
        val customView = LayoutInflater.from(this).inflate(R.layout.update_dialog_layout, null)

        val edtID: EditText = customView.findViewById(R.id.edtID)
        val edtName: EditText = customView.findViewById(R.id.edtName)
        val edtEmail: EditText = customView.findViewById(R.id.edtEmail)

        edtID.setText(user.id)
        edtName.setText(user.userName)
        edtEmail.setText(user.userEmail)

        builder.setView(customView)

        val dialog = builder.create()

        val btnUpdate: Button = customView.findViewById(R.id.btnUpdate)
        val btnCancel: Button = customView.findViewById(R.id.btnCancel)

        btnUpdate.setOnClickListener {
            val updatedUser = User(
                id = user.id,
                userName = edtName.text.trim().toString(),
                userEmail = edtEmail.text.trim().toString()
            )

            userAdapter.updateUser(updatedUser)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun generateUniqueID(): String {
        return UUID.randomUUID().toString()
    }
}