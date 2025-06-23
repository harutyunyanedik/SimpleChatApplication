package com.example.chatapplication.presentation.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.UiComposable
import androidx.fragment.app.Fragment
import com.example.chatapplication.common.utils.extensions.composableView
import com.example.chatapplication.presentation.theme.ChatApplicationTheme
import kotlin.let

/**
 * Base compose - views container fragment
 */
abstract class ComposeMainFragment : Fragment() {
    @Composable
    @UiComposable
    @NonRestartableComposable
    abstract fun ComposeFragmentView()

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return requireContext().composableView {
            // todo add theme here
            ChatApplicationTheme {

            }
            ComposeFragmentView()
        }
    }

    protected open fun closeKeyboard() {
        view?.let { closeKeyboard(it) }
    }

    private fun closeKeyboard(view: View) {
        if (isAdded) {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}