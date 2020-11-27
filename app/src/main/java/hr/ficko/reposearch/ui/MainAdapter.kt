package hr.ficko.reposearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hr.ficko.reposearch.R
import hr.ficko.reposearch.data.models.Repository
import kotlinx.android.synthetic.main.list_item.view.*
import timber.log.Timber


class MainAdapter(var dataset: List<Repository> = listOf()) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var repoName: TextView = view.tvRepoName
        var updateTime: TextView = view.tvUpdateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]
        holder.repoName.text = data.repoName
        holder.updateTime.text = data.updatedAt

        holder.itemView.setOnClickListener {
            navigateToDetailsActivity(it, data)
        }
    }

    override fun getItemCount(): Int = dataset.size

    private fun navigateToDetailsActivity(it: View, data: Repository) {
        Timber.d("Navigating to DetailsActivity")
        startActivity(
            it.context,
            DetailsActivity.buildIntent(
                context = it.context,
                repoName = data.repoName,
                updateTime = data.updatedAt,
                owner = data.owner.ownerName,
                description = data.description ?: ""
            ),
            null
        )
    }

}
