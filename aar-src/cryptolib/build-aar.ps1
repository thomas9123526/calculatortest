# Build cryptolib.aar
# An AAR is a zip containing classes.jar + AndroidManifest.xml + R.txt (+ optional res/, jni/, etc.)

$ErrorActionPreference = "Stop"

$root      = $PSScriptRoot
$projectApp= Resolve-Path (Join-Path $root "..\..\app")
$androidJar= "C:\Users\aaa\AppData\Local\Android\Sdk\platforms\android-36\android.jar"
$out       = Join-Path $root "build"
$outAar    = Join-Path $projectApp "libs\cryptolib.aar"

if (Test-Path $out) { Remove-Item -Recurse -Force $out }
New-Item -ItemType Directory -Path $out | Out-Null
New-Item -ItemType Directory -Path (Join-Path $out "classes") | Out-Null

# 1. compile .java -> .class against android.jar
& javac --release 11 -classpath $androidJar -d (Join-Path $out "classes") `
    (Join-Path $root "com\example\cryptolib\SimpleCrypto.java")

# 2. package classes into classes.jar
Push-Location (Join-Path $out "classes")
& jar cf (Join-Path $out "classes.jar") com\example\cryptolib\SimpleCrypto.class
Pop-Location

# 3. stage AAR contents
$stage = Join-Path $out "aar"
New-Item -ItemType Directory -Path $stage | Out-Null
Copy-Item (Join-Path $out "classes.jar") (Join-Path $stage "classes.jar")
Copy-Item (Join-Path $root "AndroidManifest.xml") (Join-Path $stage "AndroidManifest.xml")
New-Item -Path (Join-Path $stage "R.txt") -ItemType File -Force | Out-Null

# 4. zip into .aar using jar (handles file ordering and avoids file-lock issues)
if (Test-Path $outAar) { Remove-Item -Force $outAar }
Push-Location $stage
& jar cf $outAar AndroidManifest.xml classes.jar R.txt
Pop-Location

Write-Output "Built: $outAar"
Get-Item $outAar | Select-Object FullName, Length
