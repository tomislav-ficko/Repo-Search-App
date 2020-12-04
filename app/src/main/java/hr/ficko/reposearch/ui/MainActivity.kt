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
import hr.ficko.reposearch.viewModels.MainActivityViewModel
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: MainAdapter
    private val viewModel by viewModels<MainActivityViewModel>()

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
                hideKeyboard(inputField)
                changeProgressLoaderVisibility()
                searchForRepositories()
            }
        }
    }

    private fun initializeRecyclerView() {
        listAdapter = MainAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
    }

    private fun observeLiveData() {
        val dataObserver = defineDataObserver()
        viewModel.repoLiveData.observe(this, dataObserver)
        val errorObserver = defineNetworkErrorObserver()
        viewModel.networkErrorLiveData.observe(this, errorObserver)
    }

    private fun searchForRepositories() {
        viewModel.getSearchResults(binding.inputField.text.toString())
    }

    private fun defineDataObserver() = Observer<List<Repository>> { data ->
        data?.let {
            changeProgressLoaderVisibility()
            showData(it)
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
}
