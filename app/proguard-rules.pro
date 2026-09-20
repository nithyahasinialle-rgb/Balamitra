# Proguard rules for Balamitra
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.room.* *;
}
