package hr.ficko.reposearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hr.ficko.reposearch.data.models.Repository
import hr.ficko.reposearch.databinding.ListItemBinding
import timber.log.Timber


class MainAdapter(var dataset: List<Repository> = listOf()) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var repoName: TextView = itemBinding.tvRepoName
        var updateTime: TextView = itemBinding.tvUpdateTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataset[position]

        holder.apply {
            repoName.text = data.repoName
            updateTime.text = data.updatedAt

            itemView.setOnClickListener {
                navigateToDetailsActivity(it, data)
            }
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
