package com.example.chatapplication.common.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun <T> Fragment.collectWhenStarted(flow: Flow<T>, block: suspend (value: T) -> Unit) =
    flow.flowWithLifecycle(lifecycle)
        .onEach(block)
        .launchIn(viewLifecycleScope)

fun <T> Fragment.collectWhenResumed(flow: Flow<T>, block: suspend (value: T) -> Unit) =
    flow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
        .onEach(block)
        .launchIn(viewLifecycleScope)

fun <T> Fragment.collectLatestWhenStarted(flow: Flow<T>, block: suspend (value: T) -> Unit) {
    viewLifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle).collectLatest { block(it) }
    }
}

fun <T> Fragment.collectLatestWhenResumed(flow: Flow<T>, block: suspend (value: T) -> Unit) {
    viewLifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).collectLatest { block(it) }
    }
}

val Fragment.viewLifecycleScope: CoroutineScope get() = viewLifecycleOwner.lifecycleScope