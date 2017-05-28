package com.example.kota.kotlintraining

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.kota.kotlintraining.network.DribbbleApiClient
import com.example.kota.kotlintraining.network.entity.Shot


class MainFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root:  View? = inflater?.inflate(R.layout.fragment_main, container, false)
        val recyclerView = root?.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.adapter = RecyclerAdapter(context, arrayListOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        fetchItems(recyclerView)
        return root
    }

    fun fetchItems(rerecylerView : RecyclerView)  {
        DribbbleApiClient.getShots(0, 20).subscribe(
                {
                    (rerecylerView.adapter as RecyclerAdapter).items.addAll(it.items)
                    rerecylerView.adapter.notifyDataSetChanged()
                },
                { e -> e.printStackTrace() },
                { Log.d("fetch", "completed")}
        )
    }


    }

class RecyclerAdapter(context: Context, val items: ArrayList<Shot>): RecyclerView.Adapter<RecyclerViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(inflater.inflate(R.layout.row_list, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        val item = items.get(position)
        holder?.textView?.setText(item.title)
        Glide.with(holder?.textView?.context)
                .load(item.images.normal)
                .into(holder?.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class RecyclerViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val textView: TextView = v.findViewById(R.id.text) as TextView
    val imageView: ImageView = v.findViewById(R.id.image) as ImageView
}