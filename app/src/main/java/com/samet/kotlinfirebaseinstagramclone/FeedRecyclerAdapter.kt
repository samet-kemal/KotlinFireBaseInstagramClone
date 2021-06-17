package com.samet.kotlinfirebaseinstagramclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter(
    private val userEmailArray: ArrayList<String>,
    private val userCommentArray: ArrayList<String>,
    private val userImageArray: ArrayList<String>
) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(view: View) : RecyclerView.ViewHolder(view) {

        var recyclerEmailText: TextView? = null
        var recyclerCommentText: TextView? = null
        var recyclerImageView: ImageView

        init {
            recyclerEmailText = view.findViewById(R.id.recyclerEmailText)
            recyclerCommentText = view.findViewById(R.id.recyclerCommentText)
            recyclerImageView = view.findViewById(R.id.recyclerImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyler_view_row, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.recyclerEmailText?.text = userEmailArray[position]
        holder.recyclerCommentText?.text = userCommentArray[position]
        Picasso.get().load(userImageArray[position]).into(holder.recyclerImageView)
    }

    override fun getItemCount(): Int {
        return userCommentArray.size
    }
}