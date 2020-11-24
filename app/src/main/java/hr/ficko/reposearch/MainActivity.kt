package hr.ficko.reposearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import hr.ficko.reposearch.ui.MainActivityViewModel
import hr.ficko.reposearch.ui.RepositoryState
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataObserver = defineDataObserver()
        viewModel.getLiveData().observe(this, dataObserver)

        btnSearch.setOnClickListener {
            searchForRepositories()
        }
    }

    private fun searchForRepositories() {
        viewModel.getRepositories(inputField.text.toString())
    }

    private fun defineDataObserver() = Observer<RepositoryState> { data ->
        data?.let {
            TODO("logic when data is returned")
        }
    }
}