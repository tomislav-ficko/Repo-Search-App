package hr.ficko.reposearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.ficko.reposearch.R

class ListAdapter(mainActivityViewModel: MainActivityViewModel) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var viewModel = mainActivityViewModel

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var repoName: TextView? = null
        var updateTime: TextView? = null

        init {
            repoName = itemView.findViewById(R.id.tvRepoName)
            updateTime = itemView.findViewById(R.id.tvUpdateTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO()
    }
}