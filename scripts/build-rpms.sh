#!/bin/bash
set -e

jar_file_path=$(ls target/*-with-dependencies.jar)

rm -f  ~/.rpmmacros
rm -rf ~/rpmbuild
rpmdev-setuptree

working_directory=$(realpath target/rpmbuild)
mkdir -p "$working_directory"

readonly SOURCE=src

# Spec File
cp -v $SOURCE/RPMS/siakhooi-semvery.spec ~/rpmbuild/SPECS

# Binary File
mkdir -p "$working_directory/usr/bin"
cp -vr $SOURCE/deb/usr/bin "$working_directory/usr"
chmod 755 "$working_directory/usr/bin/"*

# Jar file
mkdir -p "$working_directory/usr/lib/java/siakhooi"
cp -v "$jar_file_path" "$working_directory/usr/lib/java/siakhooi"

# Man Pages
mkdir -p "$working_directory/usr/share/man/man1/"
files=$(cd $SOURCE/deb/md && find ./*.1.md | sed 's/.md//')
for file in $files; do
  pandoc "$SOURCE/deb/md/$file.md" -s -t man |
    gzip -9 >"$working_directory/usr/share/man/man1/$file.gz"
done

# License
cp -vf ./LICENSE "$working_directory"

# build rpm file
rpmlint ~/rpmbuild/SPECS/siakhooi-semvery.spec
rpmbuild -bb -vv  --define "_working_directory $working_directory" ~/rpmbuild/SPECS/siakhooi-semvery.spec
cp -vf ~/rpmbuild/RPMS/noarch/siakhooi-semvery-*.rpm .

# query
tree ~/rpmbuild/
rpm -ql ~/rpmbuild/RPMS/noarch/siakhooi-semvery-*.rpm

rpm_file=$(basename "$(ls ./siakhooi-semvery-*.rpm)")

sha256sum "$rpm_file" >"$rpm_file.sha256sum"
sha512sum "$rpm_file" >"$rpm_file.sha512sum"
