package h.w.rmuitool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowCompat
import androidx.palette.graphics.Palette
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import h.w.rmuitool.ktx.*
import h.w.rmuitool.pullxml.PullTest
import h.w.rmuitool.ui.theme.RMUIToolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        try {
            process()
        } catch (t: Throwable) {
            "未获取到Root权限".showToast()
        }
        setContent {
            RMUIToolTheme {
                rememberSystemUiController().setStatusBarColor(
                    color = MaterialTheme.colors.primary,
                    darkIcons = MaterialTheme.colors.isLight
                )

                ListView(GetAppList().init(
                    context = GlobalContext.context, hideSystemApp = true
                ))
            }
        }
    }
}

@Composable
fun AppItem(appInfo: AppInfo) {
    cp()
    val supportPkgList = PullTest().parseXMLWithPull()
    val value = remember {
        mutableStateOf(supportPkgList.indexOf(appInfo.packageName) != -1)
    }
    Row(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .clickable {
                value.value = !value.value
                if (value.value) appInfo.packageName.addXMLTag() else appInfo.packageName.deleteXMLTag()
            }
            .padding(all = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Spacer(modifier = Modifier.width(15.dp))

        val icon = appInfo.icon.toBitmap()
        val palette = Palette.from(icon).generate()
        val color = Color(palette.getDominantColor(0x00FFFFFF))
        Image(
            bitmap = icon.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = color, shape = CircleShape)
        )

        Column(
            modifier = Modifier
                .width(190.dp)
                .fillMaxHeight()
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = appInfo.label,
                fontWeight = FontWeight(500),
                maxLines = 1,
                fontFamily = FontFamily.Default,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.secondary
            )

            Text(
                text = appInfo.packageName,
                fontSize = 12.sp,
                maxLines = 1,
                fontFamily = FontFamily.Default,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.secondary
            )
        }

        Spacer(modifier = Modifier.width(70.dp))

        Checkbox(
            checked = value.value,
            onCheckedChange = {
                value.value = it
                if (value.value) appInfo.packageName.addXMLTag() else appInfo.packageName.deleteXMLTag()
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF7FFF00),
                checkmarkColor = Color.White,
            ),
            enabled = true,
        )


    }

//    Divider(color = Color(0x0D000000), thickness = 1.dp)

}

@Composable
fun Test() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
    ) {}
}

@Composable
fun ListView(appInfoList: ArrayList<AppInfo>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .width(300.dp)
                .border(shape = RoundedCornerShape(10.dp), width = 1.dp, color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RMUI Tool",
                fontSize = 18.sp,
                fontWeight = FontWeight(700)
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(appInfoList) {
                AppItem(appInfo = it)
            }
        }
    }
}

@Composable
fun BaseDefault(content: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = content, fontSize = 50.sp)
    }
}
