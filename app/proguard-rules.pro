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
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Reglas para Google Mobile Ads (AdMob)
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.common.** { *; }
-keep class com.google.android.gms.internal.** { *; }

# Reglas específicas para las clases faltantes
-dontwarn android.media.LoudnessCodecController
-dontwarn android.media.LoudnessCodecController$OnLoudnessCodecUpdateListener

# Reglas para AndroidX
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-keep class com.google.android.material.** { *; }
-keep interface com.google.android.material.** { *; }

# Reglas para mantener las clases del modelo
-keep class com.jorgecruces.metrometro.model.** { *; }

# Reglas para mantener las actividades
-keep class com.jorgecruces.metrometro.activities.** { *; }

# Reglas para mantener las vistas personalizadas
-keep class com.jorgecruces.metrometro.customViews.** { *; }

# Reglas para mantener la lógica
-keep class com.jorgecruces.metrometro.logic.** { *; }

# Reglas para mantener el sonido
-keep class com.jorgecruces.metrometro.sound.** { *; }

# Reglas generales para evitar problemas con R8
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses

# Reglas para evitar problemas con la serialización
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}