# Terminal commands to build the AAR

- date: 2026-05-22 15:14:00
- filename: 260522_151400_Terminal_commands_to_build_the_AAR.md

## Report

Question: what is the terminal command to build the AAR in debug or release.

Answer — run from the project root:

- Release AAR:  gradlew :app:assembleRelease
- Debug AAR:    gradlew :app:assembleDebug
- Both:         gradlew :app:assemble

Exact invocation by shell:
- PowerShell or CMD:  .\gradlew.bat :app:assembleRelease
- Git Bash:           ./gradlew :app:assembleRelease

Output location:
- app/build/outputs/aar/app-release.aar
- app/build/outputs/aar/app-debug.aar

Notes:
- The :app: prefix targets the module by name; with only one module the prefix can be dropped (gradlew assembleRelease).
- The task that actually packages the AAR is bundleReleaseAar or bundleDebugAar; assembleRelease and assembleDebug just depend on it.
- Add --console=plain for clean logs, or run clean first for a fresh build (gradlew clean :app:assembleRelease).

## Prompt

what is the terminal command to build aar in debug or release
