# Dot-source only from scripts/build-windows-*.ps1 (same directory).
# Prepares jpackage/input, jpackage/runtime (jlink), and sets variables for jpackage invocations.
$ErrorActionPreference = 'Stop'

$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location -LiteralPath $RepoRoot

$releasePs1 = Join-Path $RepoRoot 'release.ps1'
if (-not (Test-Path -LiteralPath $releasePs1)) {
    throw "release.ps1 not found: $releasePs1"
}
. $releasePs1
if ([string]::IsNullOrWhiteSpace($ReleaseVersion)) {
    throw '$ReleaseVersion is not set or empty in release.ps1'
}
if ([string]::IsNullOrWhiteSpace($ReleasePackageName)) {
    throw '$ReleasePackageName is not set or empty in release.ps1'
}
if ([string]::IsNullOrWhiteSpace($ReleaseVendor)) {
    throw '$ReleaseVendor is not set or empty in release.ps1'
}

$VERSION = $ReleaseVersion
$PACKAGE_NAME = $ReleasePackageName
$VENDOR = $ReleaseVendor
$mainJar = "${PACKAGE_NAME}.jar"

# Maven assembly produces *-jar-with-dependencies.jar (same as scripts/build-deb.sh).
$targetDir = Join-Path $RepoRoot 'target'
$fatJars = @(Get-ChildItem -LiteralPath $targetDir -Filter '*-jar-with-dependencies.jar' -File -ErrorAction SilentlyContinue)
if ($fatJars.Count -ne 1) {
    throw "Expected exactly one *-jar-with-dependencies.jar under $targetDir (run mvn package|verify from repo root first); found $($fatJars.Count)"
}
$jar = $fatJars[0].FullName

$jpackageRoot = Join-Path $RepoRoot 'jpackage'
$inputDir = Join-Path $jpackageRoot 'input'
$runtimeDir = Join-Path $jpackageRoot 'runtime'

New-Item -ItemType Directory -Force -Path $inputDir | Out-Null
Copy-Item -LiteralPath $jar -Destination (Join-Path $inputDir $mainJar) -Force

$depsLines = & jdeps.exe --print-module-deps --ignore-missing-deps --multi-release 21 $jar
if ($LASTEXITCODE -ne 0) {
    throw "jdeps failed with exit code $LASTEXITCODE"
}
$deps = (($depsLines | ForEach-Object { $_.ToString().Trim() }) | Where-Object { $_ }) -join ','
if ([string]::IsNullOrWhiteSpace($deps)) {
    throw 'jdeps returned no module list (--print-module-deps)'
}

& jlink.exe --add-modules $deps --output $runtimeDir --no-header-files --no-man-pages
if ($LASTEXITCODE -ne 0) {
    throw "jlink failed with exit code $LASTEXITCODE"
}
