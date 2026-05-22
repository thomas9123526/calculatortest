# Convert app module to an Android library (AAR)

- date: 2026-05-22 12:31:38
- filename: 260522_123138_Convert_app_module_to_an_Android_library_AAR.md

## Report

Converted the app module from an Android application to an Android library that builds an AAR.

- Switched the plugin from com.android.application to com.android.library (added an android-library alias to gradle/libs.versions.toml and updated the root build.gradle).
- Removed applicationId, versionCode and versionName from defaultConfig; these are not valid for a library and AGP rejects applicationId.
- Replaced proguardFiles plus getDefaultProguardFile with consumerProguardFiles, so the rules ship to consumers of the AAR.
- Rewrote AndroidManifest.xml for a library: dropped the app-level application attributes (icon, label, theme, backup rules) so they no longer override a consuming app, and removed the MAIN/LAUNCHER intent-filter from the Calculator activity. The activity stays inside the library with exported set to false and carries its own Theme.MyApplication so it keeps an AppCompat theme.
- The native CMake build and the inlined calculator classes are unchanged; the AAR bundles the nativecalc .so files.

The launcher icons and backup-rule XML are now unused but were left in place.

## Prompt

I want convert whole project into aar , Calculator Activity inside aar
