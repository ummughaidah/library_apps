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

# keep semua class dalam package 'net.sqlcipher', termasuk deskriptor class
-keep, includedescriptorclasses class net.sqlcipher.** { *; }
# keep semua interface dalam package 'net.sqlcipher', termasuk deskriptor class
-keep, includedescriptorclasses interface net.sqlcipher.** { *; }

# Keep class 'retrofit2.Response', mencegah pengaburan dan penghapusan
-keep class retrofit2.Response { *; }
# Keep class 'kotlin.coroutines.Continuation', tetapi mengizinkan pengaburan dan penghapusan
-keep, allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Keep semua class dalam package 'com.program.library.data.remote.response' dan mencegah penghapusan fields, tetapi mengizinkan anggota lainnya diobfuskasi
-keep class com.program.library.data.remote.response.* { <fields>; }