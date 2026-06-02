$ErrorActionPreference = 'Stop'

$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location -LiteralPath $RepoRoot

$VERSION = '1.1.0'
$PACKAGE_NAME = 'semvery'
$VENDOR = 'Siak Hooi'

$jar = Join-Path (Join-Path $RepoRoot 'target') "${PACKAGE_NAME}-${VERSION}-jar-with-dependencies.jar"
if (-not (Test-Path -LiteralPath $jar)) {
    throw "JAR not found: $jar (run Maven package from repo root first)"
}

$jpackageRoot = Join-Path $RepoRoot 'jpackage'
$inputDir = Join-Path $jpackageRoot 'input'
$runtimeDir = Join-Path $jpackageRoot 'runtime'

New-Item -ItemType Directory -Force -Path $inputDir | Out-Null
Copy-Item -LiteralPath $jar -Destination (Join-Path $inputDir (Split-Path -Leaf $jar)) -Force

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

Push-Location -LiteralPath $jpackageRoot
try {
    $mainJar = "${PACKAGE_NAME}-${VERSION}-jar-with-dependencies.jar"
    & jpackage.exe `
        --type app-image `
        --name $PACKAGE_NAME `
        --input input `
        --main-jar $mainJar `
        --runtime-image runtime `
        --app-version $VERSION `
        --win-console `
        --vendor $VENDOR
    if ($LASTEXITCODE -ne 0) {
        throw "jpackage failed with exit code $LASTEXITCODE"
    }

    $zipName = "${PACKAGE_NAME}-${VERSION}-windows-x64.zip"
    $zipPath = Join-Path $RepoRoot $zipName
    if (Test-Path -LiteralPath $zipPath) {
        Remove-Item -LiteralPath $zipPath -Force
    }
    Compress-Archive -Path $PACKAGE_NAME -DestinationPath $zipPath -CompressionLevel Optimal
}
finally {
    Pop-Location
}
