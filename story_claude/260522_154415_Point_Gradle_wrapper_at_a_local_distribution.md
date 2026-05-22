# Point Gradle wrapper at a local distribution

- date: 2026-05-22 15:44:15
- filename: 260522_154415_Point_Gradle_wrapper_at_a_local_distribution.md

## Report

Pointed the Gradle wrapper at a local distribution so no internet is needed for the Gradle engine itself.

- The official gradle-9.5.0-milestone-5-bin.zip was no longer on disk (the wrapper deletes it after extraction), so the already-extracted distribution under the wrapper dists cache was repackaged into a fresh zip with Python zipfile. The .NET zipper was rejected first because it wrote backslash path separators, which is not ZIP-spec compliant.
- The repackaged zip lives at C:/Users/aaa/.gradle/local-distribution/gradle-9.5.0-milestone-5-bin.zip, outside the repo and outside Gradle-managed cache folders.
- gradle-wrapper.properties distributionUrl now uses a file URL to that zip, and distributionSha256Sum was updated to the SHA-256 of the repackaged zip so integrity validation still passes.
- Verified: gradlew --version reads from the local file URL, passes checksum validation, extracts, and reports Gradle 9.5.0-milestone-5.

The file URL is absolute and specific to this machine; a clean checkout elsewhere would need the zip at the same path or a different distributionUrl.

## Prompt

Absolute local path (chosen for how the Gradle distribution should be handled for offline use)
