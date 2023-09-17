package androidx.compose.ui.tooling.preview

import kotlin.reflect.KClass

interface PreviewParameterProvider<T> {
    /**
     * [Sequence] of values of type [T] to be passed as @[Preview] parameter.
     */
    val values: Sequence<T>

    /**
     * Returns the number of elements in the [values] [Sequence].
     */
    val count get() = values.count()
}

annotation class PreviewParameter(
    val provider: KClass<out PreviewParameterProvider<*>>,
    val limit: Int = Int.MAX_VALUE
)
