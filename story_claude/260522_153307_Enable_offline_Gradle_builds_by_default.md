# Enable offline Gradle builds by default

- date: 2026-05-22 15:33:07
- filename: 260522_153307_Enable_offline_Gradle_builds_by_default.md

## Report

Enabled offline-by-default Gradle builds.

- gradle.properties now contains org.gradle.offline=true, so every gradlew invocation resolves dependencies only from the local cache and never contacts google() or mavenCentral(). The line was added directly in the IDE.
- This is safe because the dependency cache (about 17 GB under the Gradle user home caches folder) and the Gradle 9.5.0-milestone-5 distribution are already populated from the earlier successful build.
- To add or update a dependency later, remove this line or run once online with --refresh-dependencies.

gradle-wrapper.properties was left unchanged: distributionUrl still resolves to the already-extracted local Gradle distribution, so no download happens for the Gradle engine either.

## Prompt

yes
