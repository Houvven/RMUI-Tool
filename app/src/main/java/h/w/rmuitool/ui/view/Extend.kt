//package h.w.rmuitool.ui.view
//
//import android.annotation.SuppressLint
//import android.app.Application
//import android.content.SharedPreferences
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Button
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Switch
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import h.w.rmuitool.logci.ktx.makeSP
//import h.w.rmuitool.logci.ktx.showToast
//import h.w.rmuitool.ui.tool.ScreenProperty
//import kotlin.concurrent.thread
//
//data class XposedFunction(val title: String, val subTitleL: String = "", val key: String)
//
//class Functions {
//    companion object {
//        val functionList = mutableListOf<XposedFunction>()
//    }
//
//    fun putFunction() {
//        functionList.run {
//            add(
//                XposedFunction(
//                    title = "浏览器简洁模式",
//                    subTitleL = "Color os的系统浏览器需要申请才能开启简洁模式，开启该功能无需申请申请即可使用",
//                    key = "home_page"
//                )
//            )
//
//            add(
//                XposedFunction(
//                    title = "隐藏浏览器简洁模式下的footer",
//                    key = "hide_home_page_footer"
//                )
//            )
//
//            add(
//                XposedFunction(
//                    title = "隐藏浏览器下载页面的热门下载",
//                    key = "hide_download_footer"
//                )
//            )
//        }
//    }
//}
//
//object Sp {
//    lateinit var sp: SharedPreferences
//}
//
//
//@OptIn(ExperimentalFoundationApi::class)
//@SuppressLint("WorldReadableFiles")
//@Composable
//fun HomeListView() {
//    try {
//        val sp = "function".makeSP(Application.MODE_WORLD_READABLE)
//        Sp.sp = sp
//    } catch (t: Throwable) {
//        "你好像没有在Xposed激活模块呢".showToast()
//    }
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        stickyHeader {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(40.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center,
//            ) {
////                Spacer(modifier = Modifier.width(20.dp))
//                Text(
//                    text = "Xposed",
//                    color = MaterialTheme.colors.onSurface,
//                    fontSize = 22.sp,
//                    fontWeight = FontWeight(700),
//                )
//            }
//        }
//        items(Functions.functionList) {
//            HomeItems(functionInfo = it)
//        }
//    }
//}
//
//@SuppressLint("CommitPrefEdits")
//@Composable
//fun HomeItems(functionInfo: XposedFunction) {
//    val width = ScreenProperty.screenWidth
//    var jh = true
//    val checked = remember {
//        try {
//            mutableStateOf(Sp.sp.getBoolean(functionInfo.key, false))
//        } catch (t: Throwable) {
//            jh = false
//            mutableStateOf(false)
//        }
//    }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 10.dp, bottom = 10.dp)
//            .clickable {
//                if (!jh) {
//                    "xposed未激活".showToast()
//                } else {
//                    thread {
//                        val checkedValue = !checked.value
//                        checked.value = checkedValue
//                        Sp.sp
//                            .edit()
//                            .run {
//                                putBoolean(functionInfo.key, checkedValue)
//                                apply()
//                            }
//                    }
//                }
//            },
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Spacer(modifier = Modifier.width((width * 0.05).dp))
//        Column(modifier = Modifier.width((width * 0.8).dp)) {
//            Text(
//                text = functionInfo.title,
//                fontFamily = FontFamily.Default,
//                fontWeight = FontWeight(500),
//                color = MaterialTheme.colors.onBackground,
//            )
//            Text(
//                text = functionInfo.subTitleL,
//                fontFamily = FontFamily.Default,
//                fontSize = 12.sp,
//                color = Color.Gray,
//            )
//        }
//        Switch(checked = checked.value, onCheckedChange = null)
//    }
//}