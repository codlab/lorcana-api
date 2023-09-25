package com.germainkevin.collapsingtopbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.max
import kotlin.math.min

/**
 * [CollapsingTopBar] is like a TopAppBar that can collapse and/or expand.
 *
 * This [CollapsingTopBar] has slots to display a navigation icon, a title, a subtitle,
 * a mainAction and actions.
 *
 * @param modifier A modifier that is passed down to the main layer which is a [Surface].
 * @param colors [CollapsingTopBarColors] that will be used to resolve the colors used for this
 * [CollapsingTopBar]. See [CollapsingTopBarDefaults.colors].
 * @param scrollBehavior determines the behavior of the [CollapsingTopBar]. If you want the
 * [CollapsingTopBar] to stay collapsed, you set it there, if you want the [CollapsingTopBar] to
 * have a different [collapsed height][CollapsingTopBarScrollBehavior.collapsedTopBarHeight] or
 * a different [expanded height][CollapsingTopBarScrollBehavior.expandedTopBarMaxHeight], you set it
 * there, if you want the [CollapsingTopBar] to detect when a scroll event has occurred in your UI
 * and you want the [CollapsingTopBar] to collapse or expand, you simply pass
 * [nestedScrollConnection][CollapsingTopBarScrollBehavior.nestedScrollConnection] to
 * your Layout's [Modifier.nestedScroll][androidx.compose.ui.input.nestedscroll.nestedScroll].
 * @author Germain Kevin
 * */
@Composable
fun CollapsingTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: CollapsingTopBarScrollBehavior,
    colors: CollapsingTopBarColors = CollapsingTopBarDefaults.colors(),
    content: @Composable (currentTopBarHeight: Dp, progress: Float) -> Unit
) {
    CollapsingTopBarLayout(
        modifier = modifier,
        content = content,
        scrollBehavior = scrollBehavior,
        colors = colors
    )
}

@Composable
private fun CollapsingTopBarLayout(
    modifier: Modifier,
    content: @Composable (currentTopBarHeight: Dp, progress: Float) -> Unit,
    scrollBehavior: CollapsingTopBarScrollBehavior,
    colors: CollapsingTopBarColors
) = with(scrollBehavior) {
    val surfaceColor by currentBackgroundColor(colors)
    colors.onBackgroundColorChange(surfaceColor)

    val minHeight = scrollBehavior.collapsedTopBarHeight
    val maxHeight = scrollBehavior.expandedTopBarMaxHeight

    val progress = max(
        0f,
        min(
            1f,
            with(LocalDensity.current) {
                val actualMin = minHeight.value
                (currentTopBarHeight.value - actualMin) / (maxHeight.value - actualMin)
            }
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(currentTopBarHeight)
            .verticalScroll(topBarVerticalScrollState.invoke())
    ) {
        content(currentTopBarHeight, progress)
    }
}
