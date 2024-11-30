# ProGuard rules:
-keepattributes Signature
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken


-keep class com.kappdev.txteditor.analytics.** { *; }
-keepclassmembers class com.kappdev.txteditor.analytics.** { *; }
-keepclassmembers class com.kappdev.txteditor.analytics.** { public static final <fields>;}


# Firebase Analytics
-keep class com.google.firebase.analytics.** { *; }
-keep interface com.google.firebase.analytics.** { *; }
-keepclassmembers class com.google.firebase.analytics.FirebaseAnalytics { public <methods>; }
-keepclassmembers class * {
    @com.google.firebase.analytics.FirebaseAnalytics$Param <fields>;
}
-keepnames class com.google.firebase.analytics.internal.** { *; }