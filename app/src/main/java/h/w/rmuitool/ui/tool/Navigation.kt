package h.w.rmuitool.ui.tool

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import h.w.rmuitool.R
import kotlin.math.roundToInt


@Composable
fun ScrollableAppBar(
    title: String = stringResource(id = R.string.app_name), //默认为应用名
    scrollableAppBarHeight: Dp, //ScrollableAppBar高度
    toolbarOffsetHeightPx: MutableState<Float>, //向上偏移量
) {
    // 应用栏最大向上偏移量
    val maxOffsetHeightPx = with(LocalDensity.current) {
        scrollableAppBarHeight.roundToPx().toFloat() - toolBarHeight.roundToPx().toFloat()
    }
    // Title 偏移量参考值

    Box(
        modifier = Modifier
            .height(scrollableAppBarHeight)
            .offset {
                IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) //设置偏移量
            }
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
    ) {
        // 自定义Toolbar
        Row(
            modifier = Modifier
                .height(toolBarHeight)
                .offset {
                    IntOffset(
                        x = 0,
                        y = -toolbarOffsetHeightPx.value.roundToInt() //保证应用栏是始终不动的
                    )
                }
                .height(toolBarHeight)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

        }

        var titleSize by remember { mutableStateOf(20.sp) }
        //自定义title
        Box(
            modifier = Modifier
                .height(toolBarHeight) //和ToolBar同高
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .offset {
                    IntOffset(
                        x = -((toolbarOffsetHeightPx.value / maxOffsetHeightPx)).roundToInt(),
                        y = 0
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(start = 30.dp, bottom = 10.dp)
                    .offset { IntOffset(0, 0) },
                fontSize = titleSize,
            )
        }
    }
}

// 应用栏高度
private val toolBarHeight = 50.dp

// 导航图标大小
//private val navigationIconSize = 50.dp