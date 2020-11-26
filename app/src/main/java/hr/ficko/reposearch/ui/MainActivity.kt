package hr.ficko.reposearch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.ficko.reposearch.R
import hr.ficko.reposearch.data.models.Repository
import hr.ficko.reposearch.viewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = MainAdapter()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }

        val dataObserver = defineDataObserver()
        viewModel.repoLiveData.observe(this, dataObserver)

        btnSearch.setOnClickListener {
            searchForRepositories()
        }

        Timber.plant(Timber.DebugTree())
    }

    private fun searchForRepositories() {
        viewModel.getSearchResults(inputField.text.toString())
    }

    private fun defineDataObserver() = Observer<List<Repository>> { data ->
        data?.let {
            showData(it)
        }
    }

    private fun showData(data: List<Repository>) {
        listAdapter.apply {
            val sortedData = sortByDate(data)
            dataset = sortedData
            notifyDataSetChanged()
        }
    }

    private fun sortByDate(data: List<Repository>): List<Repository> {
        return data.sortedWith(kotlin.Comparator { first, second ->
            if (first.updatedAt > second.updatedAt) -1 else if (first.updatedAt < second.updatedAt) 1 else 0
        })
    }
}