package com.example.chatapplication.common.utils.extensions

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Shows and calls composable function inside legacy view
 *
 * A [android.view.View] that can host Jetpack Compose UI content.
 * Use [setContent] to supply the content composable function for the view.
 *
 * By default, the composition is disposed according to [ViewCompositionStrategy.Default].
 * Call [disposeComposition] to dispose of the underlying composition earlier, or if the view is
 * never initially attached to a window. (The requirement to dispose of the composition explicitly
 * in the event that the view is never (re)attached is temporary.)
 */
fun Context.composableView(
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    content: @Composable @UiComposable () -> Unit = {},
): ComposeView = ComposeView(
    context = this,
    attrs = attrs,
    defStyleAttr = defStyleAttr
).apply {
    setContent(content)
}


/**
 * Get and observe activity livecycle events as state in compose
 */
@Composable
@NonRestartableComposable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {
    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}

/**
 * Calls [block] when lifecycle owner calls onResume function (ex. activity's onResume)
 */
@Composable
@NonRestartableComposable
fun OnResume(block: () -> Unit) {
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()
    LaunchedEffect(
        key1 = lifecycleState,
        block = {
            if (lifecycleState == Lifecycle.Event.ON_RESUME) {
                block()
            }
        }
    )
}

@Composable
@NonRestartableComposable
fun OnDestroy(block: () -> Unit) {
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()
    LaunchedEffect(
        key1 = lifecycleState,
        block = {
            if (lifecycleState == Lifecycle.Event.ON_DESTROY) {
                block()
            }
        })
}

@Composable
@NonRestartableComposable
fun Dispose(key: Any? = Unit, block: () -> Unit) {
    DisposableEffect(key) {
        onDispose(block)
    }
}

@Composable
@NonRestartableComposable
fun OnStart(block: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                block()
            }
        })
    }
}

@Composable
@NonRestartableComposable
fun OnStop(block: () -> Unit) {
    val lifecycleState by LocalLifecycleOwner.current.lifecycle.observeAsState()
    LaunchedEffect(
        key1 = lifecycleState,
        block = {
            if (lifecycleState == Lifecycle.Event.ON_STOP) {
                block()
            }
        })
}

@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}

@Composable
fun keyboardState(): State<Boolean> {
    val view = LocalView.current
    var isImeVisible by remember { mutableStateOf(false) }

    DisposableEffect(LocalWindowInfo.current) {
        val listener = ViewTreeObserver.OnPreDrawListener {
            isImeVisible = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) == true
            true
        }
        view.viewTreeObserver.addOnPreDrawListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnPreDrawListener(listener)
        }
    }
    return rememberUpdatedState(isImeVisible)
}

/**
 * @return gradient brush with passed [colors] array
 */
fun getVerticalGradient(
    colors: List<Color>,
    startY: Float = 0f,
    endY: Float = Float.POSITIVE_INFINITY,
): Brush = Brush.verticalGradient(
    colors = colors,
    startY = startY,
    endY = endY
)

/**
 * @return gradient brush with passed [alphas] (shades) float array for the same [color]
 */
fun getVerticalGradient(
    color: Color,
    alphas: FloatArray = floatArrayOf(0f, 1f),
    startY: Float = 0f,
    endY: Float = Float.POSITIVE_INFINITY,
): Brush = getVerticalGradient(
    colors = List(alphas.size) { color.copy(alpha = alphas[it]) },
    startY = startY,
    endY = endY
)

@Composable
fun Modifier.noHighlightClick(
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = this.clickable(
    enabled = enabled,
    onClick = onClick,
    interactionSource = remember { MutableInteractionSource() },
    indication = null,
)