package hr.ficko.reposearch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import hr.ficko.reposearch.R
import hr.ficko.reposearch.databinding.ActivityDetailsBinding
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object {
        private const val REPO_NAME = "REPO_NAME"
        private const val UPDATE_TIME = "UPDATE_TIME"
        private const val OWNER = "OWNER"
        private const val DESCRIPTION = "DESCRIPTION"

        fun buildIntent(
            context: Context,
            repoName: String,
            updateTime: String,
            owner: String,
            description: String
        ) = Intent(context, DetailsActivity::class.java).apply {
            putExtra(REPO_NAME, repoName)
            putExtra(UPDATE_TIME, updateTime)
            putExtra(OWNER, owner)
            putExtra(DESCRIPTION, description)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Creating DetailsActivity")
        inflateLayout()
        addToolbarWithBackButton()

        val repoName = intent.getStringExtra(REPO_NAME) ?: ""
        val updateTime = intent.getStringExtra(UPDATE_TIME) ?: ""
        val owner = intent.getStringExtra(OWNER) ?: ""
        val description = intent.getStringExtra(DESCRIPTION) ?: ""

        displayData(repoName, updateTime, owner, description)
    }

    private fun inflateLayout() {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun displayData(
        repoName: String,
        updateTime: String,
        owner: String,
        description: String
    ) {
        binding.apply {
            tvRepoNameValue.text = repoName
            tvUpdateTimeValue.text = updateTime
            tvOwnerValue.text = owner
            tvDescriptionValue.text = description
        }
    }

    private fun addToolbarWithBackButton() {
        val toolbar = binding.toolbar.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setTitle(R.string.app_name)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.d("The toolbar Back button was pressed")
        onBackPressed()
        return true
    }
}