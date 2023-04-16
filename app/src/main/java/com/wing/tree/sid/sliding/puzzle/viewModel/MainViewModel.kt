package com.wing.tree.sid.sliding.puzzle.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.sid.core.extension.isNull
import com.wing.tree.sid.core.useCase.Result
import com.wing.tree.sid.domain.service.AdFreeService
import com.wing.tree.sid.domain.service.FirstLaunchedAtService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val adFreeService: AdFreeService,
    private val firstLaunchedAtService: FirstLaunchedAtService,
) : ViewModel() {
    private val ioDispatcher = Dispatchers.IO

    init {
        viewModelScope.launch(ioDispatcher) {
            firstLaunchedAtService.get().collect {
                if (it is Result.Complete) {
                    val firstLaunchedAt = System.currentTimeMillis()

                    when (it) {
                        is Result.Complete.Success -> if (it.data.isNull()) {
                            firstLaunchedAtService.put(firstLaunchedAt)
                        }

                        is Result.Complete.Failure -> firstLaunchedAtService.put(firstLaunchedAt)
                    }
                }
            }
        }
    }
}
