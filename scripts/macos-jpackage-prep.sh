#!/usr/bin/env bash
# Sourced by scripts/build-macos-packages.sh only. Prepares jpackage/input, jpackage/runtime
# (jdeps + jlink), and sets variables for jpackage invocations.
set -euo pipefail

_macos_prep_script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${_macos_prep_script_dir}/.." && pwd)"
cd "$REPO_ROOT"

# shellcheck disable=SC1091
. "${REPO_ROOT}/release.env"

if [[ -z "${RELEASE_VERSION:-}" ]]; then
	echo 'error: RELEASE_VERSION is not set in release.env' >&2
	exit 1
fi
if [[ -z "${RELEASE_VENDOR:-}" ]]; then
	echo 'error: RELEASE_VENDOR is not set in release.env' >&2
	exit 1
fi
if [[ -z "${RELEASE_PACKAGE_NAME:-}" ]]; then
	echo 'error: RELEASE_PACKAGE_NAME is not set in release.env' >&2
	exit 1
fi
if [[ -z "${RELEASE_MAC_PACKAGE_IDENTIFIER:-}" ]]; then
	echo 'error: RELEASE_MAC_PACKAGE_IDENTIFIER is not set in release.env (required for jpackage on macOS)' >&2
	exit 1
fi

VERSION="${RELEASE_VERSION}"
PACKAGE_NAME="${RELEASE_PACKAGE_NAME}"
VENDOR="${RELEASE_VENDOR}"
MAIN_JAR="${RELEASE_PACKAGE_NAME}.jar"

echo "macOS jpackage prep: ${PACKAGE_NAME} ${VERSION} (vendor: ${VENDOR})"

# Maven assembly produces *-jar-with-dependencies.jar (same as scripts/build-deb.sh).
shopt -s nullglob
fat_jars=( "${REPO_ROOT}"/target/*-jar-with-dependencies.jar )
shopt -u nullglob
if [[ ${#fat_jars[@]} -ne 1 ]]; then
	echo "error: expected exactly one *-jar-with-dependencies.jar under target/ (run mvn package|verify from repo root first); found ${#fat_jars[@]}" >&2
	exit 1
fi
jar="${fat_jars[0]}"

JPACKAGE_ROOT="${REPO_ROOT}/jpackage"
input_dir="${JPACKAGE_ROOT}/input"
runtime_dir="${JPACKAGE_ROOT}/runtime"

mkdir -p "$input_dir"
cp -f "$jar" "${input_dir}/${MAIN_JAR}"

if ! deps_raw=$(jdeps --print-module-deps --ignore-missing-deps --multi-release 21 "$jar" 2>/dev/null); then
	echo 'error: jdeps failed (--print-module-deps)' >&2
	exit 1
fi
if [[ -z "$(echo "$deps_raw" | tr -d '[:space:]')" ]]; then
	echo 'error: jdeps returned no module list (--print-module-deps)' >&2
	exit 1
fi
deps=$(echo "$deps_raw" | sed '/^[[:space:]]*$/d' | awk 'NR>1{printf ","}{printf "%s",$0}')

jlink --add-modules "$deps" --output "$runtime_dir" --no-header-files --no-man-pages
