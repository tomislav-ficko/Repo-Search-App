package hr.ficko.reposearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<State> : ViewModel() {
    private val mutableLiveData: MutableLiveData<State> = MutableLiveData()

    val data: LiveData<State>
        get() = mutableLiveData

    fun getLiveData() = mutableLiveData
}