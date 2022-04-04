package h.w.rmuitool.ktx


import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import h.w.rmuitool.BuildConfig
import h.w.rmuitool.HookInit
import h.w.rmuitool.ktx.GlobalContext.Companion.context
import java.lang.reflect.Field


private const val packageName = BuildConfig.APPLICATION_ID

private typealias MethodHookParam = XC_MethodHook.MethodHookParam
private typealias Replacer = (XC_MethodHook.MethodHookParam) -> Any?
private typealias Hooker = (XC_MethodHook.MethodHookParam) -> Unit

fun String.showToast(length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, length).show()
}

@SuppressLint("WrongConstant")
fun String.makeSP(mode: Int = Application.MODE_APPEND) = context.getSharedPreferences(this, mode)!!


fun String.makeXSP(): XSharedPreferences = XSharedPreferences(packageName, this)
fun XSharedPreferences.getBool(key: String, defValue: Boolean = false): Boolean = this.getBoolean(key, defValue)

fun log(text: String, throwable: Any) {
    when (throwable) {
        is NoSuchFieldError -> XposedBridge.log("$packageName Not such Field:$text")

        is XposedHelpers.ClassNotFoundError -> XposedBridge.log("$packageName  Class:$text not found.")

        is NoSuchMethodError -> XposedBridge.log("$packageName  ")

        else -> XposedBridge.log(text)
    }
}

fun String.findClass() = XposedHelpers.findClass(this, HookInit.classLoader)!!

fun String.findClassIfExists(): Class<*>? =
    XposedHelpers.findClassIfExists(this, HookInit.classLoader)

fun Class<*>.findField(fieldName: String) = XposedHelpers.findField(this, fieldName)!!

fun Class<*>.findFieldIfExists(fieldName: String): Field? = XposedHelpers.findFieldIfExists(this, fieldName)

fun Class<*>.hookMethod(methodName: String?, vararg args: Any?) =
    try {
        XposedHelpers.findAndHookMethod(this, methodName, *args)
    } catch (t: XposedHelpers.ClassNotFoundError) {
        log(text = this.name, throwable = t)
        null
    } catch (t: NoSuchMethodError) {
        methodName?.let { log(text = it, throwable = t) }
        null
    }

fun String.hookMethod(methodName: String?, vararg args: Any?) =
    try {
        XposedHelpers.findAndHookMethod(this.findClass(), methodName, *args)
    } catch (t: XposedHelpers.ClassNotFoundError) {
        log(text = this, throwable = t)
        null
    } catch (t: NoSuchMethodError) {
        methodName?.let { log(text = it, throwable = t) }
        null
    }

inline fun XC_MethodHook.MethodHookParam.callHooker(crossinline hooker: Hooker) = hooker(this)

inline fun Class<*>.beforeHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})

inline fun String.beforeHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})


inline fun Class<*>.afterHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun afterHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})

inline fun String.afterHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})




