# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-ignorewarnings
-keepattributes SourceFile, LineNumberTable, *Annotation*, Signature, EnclosingMethod, InnerClasses, EnclosingMethod

# Androidx
-keep class com.google.android.material.** {*;}
 -keep class androidx.** {*;}
 -keep public class * extends androidx.**
 -keep interface androidx.** {*;}
 -dontwarn com.google.android.material.**
 -dontnote com.google.android.material.**
 -dontwarn androidx.**


# 设置SDK
-keep public class com.haiwan.lantian.vhaiw.** {*;}
-keepclassmembers enum com.haiwan.lantian.vhaiw.** {
     *;
}

#********************** 第三方库 *********************
# Google
-keep class com.android.billingclient.api.* {*;}
-keep public class com.google.android.gms.ads.identifier.** { public protected *;
}

# For Helpshift SDK <= 4.8.1
-keepnames class * extends com.helpshift.support.fragments.MainFragment
-keepnames class * extends com.helpshift.campaigns.fragments.MainFragment

# For appsflyer
-keep public class com.android.installreferrer.** { *; }
-keep class com.appsflyer.** { *; }

# For AIHeilp
-keep class net.aihelp.** {*;}

# For Unity
-keep class bitter.jnibridge.* { *; }
-keep class com.unity3d.player.* { *; }
-keep interface com.unity3d.player.IUnityPlayerLifecycleEvents { *; }
-keep class org.fmod.* { *; }
-keep class com.google.androidgamesdk.ChoreographerCallback { *; }
-keep class com.google.androidgamesdk.SwappyDisplayManager { *; }