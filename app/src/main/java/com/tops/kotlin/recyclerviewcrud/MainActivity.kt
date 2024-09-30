package com.tops.kotlin.recyclerviewcrud

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tops.kotlin.recyclerviewcrud.adapters.UserListAdapter
import com.tops.kotlin.recyclerviewcrud.databinding.ActivityMainBinding
import com.tops.kotlin.recyclerviewcrud.models.User
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserListAdapter(this, mutableListOf())
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = userAdapter

        binding.btnSubmit.setOnClickListener {
            var name = binding.edtName.text.trim().toString()
            var email = binding.edtEmail.text.trim().toString()
            var id = generateUniqueID()

            var user = User(id = id, userName = name, userEmail = email)

            //add user
            userAdapter.addUser(user)
            binding.edtName.setText("")
            binding.edtEmail.setText("")
        }
    }

    private fun generateUniqueID(): String {
        return UUID.randomUUID().toString()
    }
}