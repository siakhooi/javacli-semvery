#!/bin/bash

readonly source_directory=src/deb
readonly target_directory=target/deb
jar_file_path=$(ls target/*-with-dependencies.jar)

mkdir -p $target_directory

# Control File
cp -vr $source_directory/DEBIAN $target_directory

# Binary File
cp -vr $source_directory/usr $target_directory
chmod 755 $target_directory/usr/bin/*

# Jar File
mkdir -p $target_directory/usr/lib/java/siakhooi
cp -v "$jar_file_path" $target_directory/usr/lib/java/siakhooi

# Man Pages
mkdir -p $target_directory/usr/share/man/man1/
files=$(cd $source_directory/md && find ./*.1.md | sed 's/.md//')
for file in $files; do
  pandoc "$source_directory/md/$file.md" -s -t man |
    gzip -9 >"$target_directory/usr/share/man/man1/$file.gz"
done

fakeroot dpkg-deb --build -Zxz $target_directory
dpkg-name ${target_directory}.deb

debian_file_path=$(ls ./target/*.deb)

sha256sum "$debian_file_path" >"$debian_file_path.sha256sum"
sha512sum "$debian_file_path" >"$debian_file_path.sha512sum"

dpkg --contents "$debian_file_path"

cp "$debian_file_path" .
cp "$debian_file_path".sha256sum .
cp "$debian_file_path".sha512sum .
