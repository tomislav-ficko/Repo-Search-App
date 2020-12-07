package hr.ficko.reposearch.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ficko.reposearch.data.models.Repository
import hr.ficko.reposearch.databinding.ActivityMainBinding
import hr.ficko.reposearch.other.Constants.FIRST_PAGE
import hr.ficko.reposearch.other.Constants.TOTAL_PAGES_STARTING_VALUE
import hr.ficko.reposearch.other.PaginationListener
import hr.ficko.reposearch.viewModels.GitHubRepositoryViewModel
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: MainAdapter
    private lateinit var searchTerm: String
    private val viewModel by viewModels<GitHubRepositoryViewModel>()
    private var currentPage = FIRST_PAGE
    private var isLoading = false
    private var isLastPage = false
    private var totalPages: Int = TOTAL_PAGES_STARTING_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inflateLayout()
        initializeRecyclerView()
        observeLiveData()
        defineActionsForSearchButton()
        focusOnInputAndShowKeyboard()

        Timber.plant(Timber.DebugTree())
    }

    private fun inflateLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun defineActionsForSearchButton() {
        binding.apply {
            btnSearch.setOnClickListener {
                searchTerm = binding.inputField.text.toString()
                hideKeyboard(inputField)
                searchForRepositories(FIRST_PAGE)
            }
        }
    }

    private fun initializeRecyclerView() {
        listAdapter = MainAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
            addOnScrollListener(createScrollListener())
        }
    }

    private fun observeLiveData() {
        val dataObserver = defineNewDataObserver()
        viewModel.repoLiveData.observe(this, dataObserver)
        val errorObserver = defineNetworkErrorObserver()
        viewModel.networkErrorLiveData.observe(this, errorObserver)
        val numberObserver = defineTotalPagesObserver()
        viewModel.totalPagesLiveData.observe(this, numberObserver)
    }

    private fun searchForRepositories(pageNumber: Int) {
        if (currentPage <= totalPages) {
            changeProgressLoaderVisibility()
            viewModel.getSearchResults(searchTerm, pageNumber)
        } else {
            // A known bug is that if we've come to the last page of a search and try to search
            // for a new term, we cannot enter the if block. In order to get around this, we could
            // add the logic from the ViewModel into this Activity, but that goes against MVVM
            Timber.d("Last page reached")
            isLastPage = true
        }
    }

    private fun defineNewDataObserver() = Observer<List<Repository>> { data ->
        data?.let {
            changeProgressLoaderVisibility()
            isLoading = false
            showData(it)
        }
    }


    private fun defineTotalPagesObserver() = Observer<Int> { numberOfPages ->
        numberOfPages?.let {
            totalPages = it
        }
    }

    private fun showData(data: List<Repository>) {
        listAdapter.apply {
            dataset = sortByDate(data)
            notifyDataSetChanged()
        }
    }

    private fun sortByDate(data: List<Repository>): List<Repository> {
        return data.sortedWith { first, second ->
            if (first.updatedAt > second.updatedAt) -1 else if (first.updatedAt < second.updatedAt) 1 else 0
        }
    }

    private fun defineNetworkErrorObserver() = Observer<Boolean> { data ->
        data?.let { networkErrorOccurred ->
            if (networkErrorOccurred) {
                showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(
            applicationContext,
            "Network not available, please make sure you are connected to the Internet",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun focusOnInputAndShowKeyboard() {
        binding.inputField.requestFocus()
        showKeyboard()
    }

    private fun showKeyboard() {
        val imn: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imn.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard(view: View) {
        val imn: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun changeProgressLoaderVisibility() {
        binding.apply {
            if (progressLoader.isVisible) {
                progressLoader.visibility = ProgressBar.INVISIBLE
            } else {
                progressLoader.visibility = ProgressBar.VISIBLE
            }
        }
    }

    private fun createScrollListener(): PaginationListener {
        return object :
            PaginationListener(binding.recyclerView.layoutManager as LinearLayoutManager) {

            override fun loadMoreItems() {
                Timber.d("Loading more items")
                isLoading = true
                currentPage++
                this@MainActivity.searchForRepositories(currentPage)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        }
    }
}
