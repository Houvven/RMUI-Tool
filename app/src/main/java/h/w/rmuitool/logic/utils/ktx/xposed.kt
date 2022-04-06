package h.w.rmuitool.logic.utils.ktx


import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedHelpers
import h.w.rmuitool.BuildConfig
import h.w.rmuitool.HookInit
import java.lang.reflect.Field


//软件包名
private const val packageName = BuildConfig.APPLICATION_ID

private typealias MethodHookParam = XC_MethodHook.MethodHookParam
private typealias Replacer = (XC_MethodHook.MethodHookParam) -> Any?
private typealias Hooker = (XC_MethodHook.MethodHookParam) -> Unit


fun String.makeXSharedPreferences() = XSharedPreferences(packageName, this)



fun String.findClass() = XposedHelpers.findClass(this, HookInit.classLoader)!!

fun String.findClassIfExists(): Class<*>? =
    XposedHelpers.findClassIfExists(this, HookInit.classLoader)

fun Class<*>.findField(fieldName: String) = XposedHelpers.findField(this, fieldName)!!

fun Class<*>.findFieldIfExists(fieldName: String): Field? =
    XposedHelpers.findFieldIfExists(this, fieldName)

fun Class<*>.hookMethod(methodName: String?, vararg args: Any?) =
    try {
        XposedHelpers.findAndHookMethod(this, methodName, *args)
    } catch (t: XposedHelpers.ClassNotFoundError) {
        null
    } catch (t: NoSuchMethodError) {

        null
    }

fun String.hookMethod(methodName: String?, vararg args: Any?) =
    try {
        XposedHelpers.findAndHookMethod(this.findClass(), methodName, *args)
    } catch (t: XposedHelpers.ClassNotFoundError) {

        null
    } catch (t: NoSuchMethodError) {

        null
    }

inline fun XC_MethodHook.MethodHookParam.callHooker(crossinline hooker: Hooker) = hooker(this)

inline fun Class<*>.beforeHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker,
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})

inline fun String.beforeHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker,
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})


inline fun Class<*>.afterHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker,
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun afterHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})

inline fun String.afterHookMethod(
    methodName: String?,
    vararg args: Any?,
    crossinline hooker: Hooker,
) = hookMethod(methodName, *args, object : XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = param.callHooker(hooker)
})




