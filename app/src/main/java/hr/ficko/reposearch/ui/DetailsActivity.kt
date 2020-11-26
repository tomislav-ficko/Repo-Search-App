package hr.ficko.reposearch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import hr.ficko.reposearch.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_details)
        Timber.d("DetailsActivity -> Creating activity")

        val repoName = intent.getStringExtra(REPO_NAME) ?: ""
        val updateTime = intent.getStringExtra(UPDATE_TIME) ?: ""
        val owner = intent.getStringExtra(OWNER) ?: ""
        val description = intent.getStringExtra(DESCRIPTION) ?: ""

        displayData(repoName, updateTime, owner, description)
    }

    private fun displayData(
        repoName: String,
        updateTime: String,
        owner: String,
        description: String
    ) {
        tvRepoNameValue.text = repoName
        tvUpdateTimeValue.text = updateTime
        tvOwnerValue.text = owner
        tvDescriptionValue.text = description
    }
}