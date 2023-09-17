package com.github.codlab.lorcana.files

import android.annotation.SuppressLint
import android.content.Context

internal var AndroidRootPath: String = ""

@SuppressLint("StaticFieldLeak")
internal var AndroidContext: Context? = null

actual val RootPath: String
    get() = AndroidRootPath
