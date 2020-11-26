package hr.ficko.reposearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.ficko.reposearch.R
import hr.ficko.reposearch.data.models.Repository
import kotlinx.android.synthetic.main.list_item.view.*

class MainAdapter(var dataset: List<Repository> = listOf()) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var repoName: TextView = view.tvRepoName
        var updateTime: TextView = view.tvUpdateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MainAdapter.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.repoName.text = item.repoName
        holder.repoName.text = item.updatedAt
    }

    override fun getItemCount(): Int = dataset.size

}
