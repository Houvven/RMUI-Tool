package h.w.rmuitool.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import h.w.rmuitool.AppInfo
import h.w.rmuitool.GetAppList
import h.w.rmuitool.ui.tool.ScreenProperty
import kotlin.concurrent.thread

@Composable
fun NotRoot() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Root, Not for you",
            fontSize = 21.sp,
            fontWeight = FontWeight(800)
        )
    }
}

@Composable
fun AppInfoList() {
    val appInfoList = GetAppList().selectAppInfo(false)
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(appInfoList) {
            AppInfoItem(appInfo = it)
        }
    }
}

@Composable
fun AppInfoItem(appInfo: AppInfo) {
    val width = ScreenProperty.screenWidth
    val icon = appInfo.icon
    val palette = Palette.from(icon).generate()
    val color = Color(palette.getDominantColor(0x00FFFFFF))
    val checked = remember { mutableStateOf(GetAppList().selectEnableInfo(appInfo.packageName)) }
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable {
                thread(start = true) {
                    val temp = !checked.value
                    checked.value = temp
                    GetAppList().updateAppInfo(temp, appInfo.packageName)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width((width * 0.05).dp))
        Image(
            bitmap = icon.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width((width * 0.025).dp))
        Column(
            modifier = Modifier.width((width * 0.7).dp)
        ) {
            Text(
                text = appInfo.label,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = appInfo.packageName,
                fontFamily = FontFamily.Default,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Checkbox(
            checked = checked.value,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(checkedColor = color)
        )
    }
}

