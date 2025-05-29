package com.example.chatapplication.common.utils.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun Context.restartTheApp() {
    packageManager?.getLaunchIntentForPackage(packageName)?.let { current ->
        Intent.makeRestartActivityTask(current.component).let {
            it.setPackage(packageName)
            startActivity(it)
            Runtime.getRuntime().exit(0)
        }
    }
}

/**
 * Checks and finishes activity if not in finishing process currently
 */
fun Context?.tryToFinish() {
    this.asActivity()?.tryToFinish()
}

fun Activity.tryToFinish() {
    if (!isFinishing) {
        finish()
    }
}

fun Activity.closeApp() {
    finishAffinity()
}

fun Context?.asActivity(): Activity? = this?.activity()

fun Context?.asFragmentActivity(): FragmentActivity? = this?.activity()

val Context?.lifeCycleOwner: LifecycleOwner?
    get() = this?.activity() as? LifecycleOwner

val Context?.viewModelStoreOwner: ViewModelStoreOwner?
    get() = this?.activity() as? ViewModelStoreOwner


private tailrec fun Context.activity(): FragmentActivity? = when (this) {
    is AppCompatActivity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

fun Context?.isDarkMode(): Boolean {
    if (this == null) return true
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

@JvmOverloads
fun Activity?.changeStatusBarColor(
    @ColorInt color: Int,
    isDark: Boolean = isDarkMode(),
) {
    if (this == null) return

    if (window?.statusBarColor == color) return

    window?.apply {// TODO: SDK version check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!isDark) {
                insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )

                insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
        WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = !isDark
    }
}

fun Activity?.changeStatusBarColor(
    color: Color,
    isDark: Boolean = isDarkMode(),
) =
    changeStatusBarColor(
        color = color.toArgb(),
        isDark = isDark
    )

fun Context?.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    if (this == null) return

    Toast.makeText(this, message, length).show()
}

fun Context?.toast(@StringRes messageResId: Int, length: Int = Toast.LENGTH_SHORT) {
    if (this == null) return

    Toast.makeText(this, getString(messageResId), length).show()
}

/**
 * Will open [link] with system default browser
 *
 * @param link url/link to open
 */
fun Context?.openLink(link: String?) {
    if (this == null || link.isNullOrEmpty() || !link.isValidUrl()) return

    val browserIntent = Intent(Intent.ACTION_VIEW, link.toUri())
    ContextCompat.startActivity(this, browserIntent, null)
}


/**
 * Will launch [Activity] by class [clazz]
 *
 * @param clazz class (NOT INSTANCE) of activity
 */
fun <T : Activity> Context?.launchActivity(clazz: Class<T>) {
    if (this == null) return

    startActivity(Intent(this, clazz))
}

/**
 * Will launch [Activity] by class [clazz] name
 *
 * @param clazz class name as string (NOT INSTANCE OR CLASS) of activity
 */
fun Context?.launchActivity(clazz: String?, isLogout: Boolean = true) {
    if (this == null || clazz.isNullOrEmpty()) return

    try {
        val forName: Class<*> = Class.forName(clazz)
        this.startActivity(Intent(this, forName).also {
            it.setClassName(this, clazz)
            it.putExtra("logout", isLogout)
        })
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    }
}

val Activity.isFullScreen: Boolean
    get() = window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != 0


fun Activity.makeFullScreen(full: Boolean) {
    if (full == isFullScreen) {
        return
    }
    WindowCompat.setDecorFitsSystemWindows(window, full)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val uiTypes: Int =
            WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars() or WindowInsets.Type.systemBars()
        if (full) {
            window.insetsController?.hide(uiTypes)
        } else {
            window.insetsController?.show(uiTypes)
        }
    } else {
        if (full) {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        }
    }
    if (full) {
        actionBar?.hide()
    } else {
        actionBar?.show()
    }
}

val Context.isWritePermissionGranted: Boolean
    get() = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

val Context.isReadPermissionGranted: Boolean
    get() = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

val Context.isCameraPermissionsGranted: Boolean
    get() = checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

fun Context.openPermissionsSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = "package:$packageName".toUri()
    startActivity(intent)
}

fun Activity.finishWithoutAnimations() {
    tryToFinish()
    noCloseAnimations()
}

@Suppress("DEPRECATION")
fun Activity.noCloseAnimations() {
    this.overridePendingTransition(
        0,
        0,
    )
}

private const val REQUEST_PERMISSIONS: Int = 123
fun Activity.checkNotificationPermission() {
    if (Build.VERSION.SDK_INT >= TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_PERMISSIONS
                )
            }
        }
    }
}

fun <T> AppCompatActivity.collectWhenStarted(flow: Flow<T>, block: suspend (value: T) -> Unit) =
    flow.flowWithLifecycle(lifecycle)
        .onEach(block)
        .launchIn(lifecycleScope)

fun <T> AppCompatActivity.collectWhenResumed(flow: Flow<T>, block: suspend (value: T) -> Unit) =
    flow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
        .onEach(block)
        .launchIn(lifecycleScope)