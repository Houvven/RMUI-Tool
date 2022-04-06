package h.w.rmuitool.ui.tool

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import h.w.rmuitool.R
import h.w.rmuitool.ui.tool.BottomNavigationEnum.*
import h.w.rmuitool.ui.view.AppInfoList

enum class BottomNavigationEnum(
    val title: String,
    @DrawableRes val icon: Int,
    var titleWeight: FontWeight = FontWeight(300),
    var iconColor: Color = Color.DarkGray,
) {
    Home("主页", R.drawable.icon_home, FontWeight(800), Color(0xFF1278FF)),
    Extend("扩展", R.drawable.icon_extend),
    MyLog("日志", R.drawable.icon_log),
    About("关于", R.drawable.icon_about)
}

@Composable
fun Home() {
    AppInfoList()
}

@Composable
fun Extend() {

}

@Composable
fun MyLog() {

}

@Composable
fun About() {

}


@Composable
fun MyBottomNavigation() {
    val tabs = values().toList()
    var position by remember { mutableStateOf(Home) }
    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        bottomBar = {
            BottomNavigation {
                tabs.forEach { tab ->
                    var titleWeight by remember { mutableStateOf(tab.titleWeight) }
                    var iconColor by remember { mutableStateOf(tab.iconColor) }
                    titleWeight = tab.titleWeight
                    iconColor = tab.iconColor
                    BottomNavigationItem(
                        icon = {
                            Image(
                                painter = painterResource(id = tab.icon),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(color = iconColor)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                color = Color(0xFF1278FF),
                                fontWeight = titleWeight
                            )
                        },
                        selected = tab == position,
                        onClick = {
                            position = tab
                            when (tab) {
                                Home -> {
                                    Home.titleWeight = FontWeight(700)
                                    Home.iconColor = Color(0xFF1278FF)
                                    FontWeight(300).also {
                                        Extend.titleWeight = it
                                        MyLog.titleWeight = it
                                        About.titleWeight = it
                                    }
                                    Color.DarkGray.also {
                                        Extend.iconColor = it
                                        MyLog.iconColor = it
                                        About.iconColor = it
                                    }
                                }
                                Extend -> {
                                    Extend.titleWeight = FontWeight(700)
                                    Extend.iconColor = Color(0xFF1278FF)
                                    FontWeight(300).also {
                                        Home.titleWeight = it
                                        MyLog.titleWeight = it
                                        About.titleWeight = it
                                    }
                                    Color.DarkGray.also {
                                        Home.iconColor = it
                                        MyLog.iconColor = it
                                        About.iconColor = it
                                    }
                                }
                                MyLog -> {
                                    MyLog.titleWeight = FontWeight(700)
                                    MyLog.iconColor = Color(0xFF1278FF)
                                    FontWeight(300).also {
                                        Home.titleWeight = it
                                        Extend.titleWeight = it
                                        About.titleWeight = it
                                    }
                                    Color.DarkGray.also {
                                        Home.iconColor = it
                                        Extend.iconColor = it
                                        About.iconColor = it
                                    }
                                }
                                About -> {
                                    About.titleWeight = FontWeight(700)
                                    About.iconColor = Color(0xFF1278FF)
                                    FontWeight(300).also {
                                        Home.titleWeight = it
                                        Extend.titleWeight = it
                                        MyLog.titleWeight = it
                                    }
                                    Color.DarkGray.also {
                                        Home.iconColor = it
                                        Extend.iconColor = it
                                        MyLog.iconColor = it
                                    }
                                }
                            }
                        },
                        alwaysShowLabel = false,
                    )
                }
            }
        }
    ) {
        when (position) {
            Home -> Home()
            Extend -> Extend()
            MyLog -> MyLog()
            About -> About()
        }
    }
}
