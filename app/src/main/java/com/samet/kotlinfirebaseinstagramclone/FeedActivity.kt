package com.samet.kotlinfirebaseinstagramclone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    var userEmailFromFirebase: ArrayList<String> = ArrayList()
    var userCommentFromFirebase: ArrayList<String> = ArrayList()
    var userImageFromFirebase: ArrayList<String> = ArrayList()

    var adapter: FeedRecyclerAdapter? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post) {
            //UPload Activity
            val intent = Intent(applicationContext,UploadActivity::class.java)
            startActivity(intent)
        }else if (item.itemId == R.id.logout){
            //LOgout
            auth.signOut()
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        getDataFromFireStore()

        //RecyclerView Adapter

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = FeedRecyclerAdapter(
            userEmailFromFirebase,
            userCommentFromFirebase,
            userImageFromFirebase
        )
        recyclerView.adapter = adapter

    }

    fun getDataFromFireStore() {

        db.collection("Posts").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(
                    applicationContext,
                    exception.localizedMessage.toString(),
                    Toast.LENGTH_LONG
                ).show()

            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {
                        userImageFromFirebase.clear()
                        userCommentFromFirebase.clear()
                        userEmailFromFirebase.clear()
                        val documents = snapshot.documents

                        for (document in documents) {
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val timestamp = document.get("date") as Timestamp
                            val date = timestamp.toDate()

                            userEmailFromFirebase.add(userEmail)
                            userCommentFromFirebase.add(comment)
                            userImageFromFirebase.add(downloadUrl)

                            adapter!!.notifyDataSetChanged()
                        }
                    }
                }
            }
        }

    }

}