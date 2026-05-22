# Rename Calculator activity to WrappedActivity

- date: 2026-05-22 17:14:25
- filename: 260522_171425_Rename_Calculator_activity_to_WrappedActivity.md

## Report

Renamed the activity class Calculator to WrappedActivity.

- Renamed the source file Calculator.java to WrappedActivity.java with git mv (preserved as a rename in history) and changed the declaration to public class WrappedActivity.
- Updated the activity android:name in AndroidManifest.xml to .WrappedActivity.
- Updated tools:context in res/layout/activity_main.xml to .WrappedActivity.
- Left the on-screen android:text label Calculator (JNI + JAR) in activity_main.xml unchanged, since that is display text and not a class reference.

The fully qualified name is now com.ryongma.calculator.WrappedActivity. The AAR must be rebuilt for this to take effect, and any consumer that launches the activity must reference the new name.

## Prompt

change name from Calculator to WrappedActivity
