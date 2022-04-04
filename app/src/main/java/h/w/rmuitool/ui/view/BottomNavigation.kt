package h.w.rmuitool.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import h.w.rmuitool.R

enum class BottomNavigationEnum(
    val title: String,
    @DrawableRes val icon: Int
) {
    Home("dsf", R.drawable.ic_launcher_background),
    Extend("sdf", R.drawable.ic_launcher_background),
    About("sd", R.drawable.ic_launcher_background)
}

@Composable
fun Home() {
    HomeListView()
}

@Composable
fun Extend() {
    AppInfoList()
}

@Composable
fun About() {
    BaseDefault(content = "sjiq")
}

@Composable
fun BaseDefault(content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = content, fontSize = 50.sp)
    }
}

@Composable
fun MyBottomNavigation() {
    val tabs = BottomNavigationEnum.values().toList()
    var position by remember { mutableStateOf(BottomNavigationEnum.Home) }
    Scaffold(
        bottomBar = {
            BottomNavigation {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        modifier = Modifier.background(Color.Blue),
                        icon = { Icon(painterResource(id = tab.icon), contentDescription = null) },
                        label = { Text(text = tab.title) },
                        selected = tab == position,
                        onClick = { position = tab },
                        alwaysShowLabel = false,
                    )
                }
            }
        }
    ) {
        when (position) {
            BottomNavigationEnum.Home -> Home()
            BottomNavigationEnum.Extend -> Extend()
            BottomNavigationEnum.About -> About()
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyBottomNavigation()
}








