package eu.codlab.lorcana.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun <T : ViewModel> rememberViewModel(
    key: String? = null,
    viewModelBlock: () -> T
): T {
    val viewModel: T = if (null != key) {
        remember(key, viewModelBlock)
    } else {
        remember(viewModelBlock)
    }

    DisposableEffect(viewModel) {
        onDispose { viewModel.onCleared() }
    }

    return viewModel
}

interface StateHandler<UiState> {
    val states: StateFlow<UiState>
    fun setState(state: UiState)
    fun updateState(block: UiState.() -> UiState)
}

interface ActionsHandler<UiAction> {
    val actions: Flow<UiAction>
    suspend fun emitAction(action: UiAction)
}

private class StateHandlerImpl<UiState>(defaultState: UiState) : StateHandler<UiState> {
    private val _state = MutableStateFlow(defaultState)
    override val states: StateFlow<UiState> = _state.asStateFlow()

    override fun setState(state: UiState) {
        _state.value = state
    }

    override fun updateState(block: UiState.() -> UiState) {
        _state.value = block(_state.value)
    }
}

private class ActionsHandlerImpl<UiAction> : ActionsHandler<UiAction> {
    private val _actions = Channel<UiAction>(capacity = Channel.BUFFERED)
    override val actions: Flow<UiAction> = _actions.receiveAsFlow()

    override suspend fun emitAction(action: UiAction) {
        _actions.send(action)
    }
}

@Suppress("UnnecessaryAbstractClass")
abstract class StateViewModel<UiState>(defaultState: UiState) :
    ViewModel(),
    StateHandler<UiState> by StateHandlerImpl(defaultState)

@Suppress("UnnecessaryAbstractClass")
abstract class ActionsViewModel<UiAction> :
    ViewModel(),
    ActionsHandler<UiAction> by ActionsHandlerImpl()

@Suppress("UnnecessaryAbstractClass")
abstract class StateActionsViewModel<UiState, UiAction>(defaultState: UiState) :
    ActionsViewModel<UiAction>(),
    StateHandler<UiState> by StateHandlerImpl(defaultState)

fun <UiAction> ActionsViewModel<UiAction>.action(action: UiAction) = viewModelScope.launch {
    emitAction(action)
}

@Suppress("TooGenericExceptionCaught")
fun ViewModel.launch(
    onError: (Throwable) -> Unit = {
        throw IllegalStateException(
            "Error block not implemented",
            it
        )
    },
    onSuccess: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch {
    try {
        onSuccess.invoke(this)
    } catch (err: Throwable) {
        onError.invoke(err)
    }
}
