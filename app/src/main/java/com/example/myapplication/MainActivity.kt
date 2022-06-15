package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User


class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth=FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().getReference()
        userList= ArrayList()

        adapter= UserAdapter(this,userList)
        userRecyclerView=findViewById(R.id.rv)
        userRecyclerView.layoutManager= LinearLayoutManager(this)
        userRecyclerView.adapter=adapter
        userRecyclerView.setHasFixedSize(true)
        userList= arrayListOf()
        getUserData()

    }

    private fun getUserData() {
       mDbRef=FirebaseDatabase.getInstance().getReference("Users")
        mDbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                     val user = snapshot.getValue(User::class.java)
                    userList.add(user!!)
                }
                userRecyclerView.adapter=UserAdapter(userList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.action_logout){
            //write logic for logout
            mAuth.signOut()
            val intent= Intent(this@MainActivity,login::class.java)
            finish()
            startActivity(intent)
            return true

        }
        return true
    }
}


