clean:
	mvn clean
	rm -f siakhooi-semvery_*_amd64.deb \
		siakhooi-semvery_*_amd64.deb.sha256sum \
		siakhooi-semvery_*_amd64.deb.sha512sum

set-version:
	scripts/set-version.sh
git-commit-and-push:
	scripts/git-commit-and-push.sh
create-release:
	scripts/create-release.sh

build:
	mvn clean verify
	scripts/shellcheck.sh
	scripts/build-deb.sh

test-man:
	pandoc src/deb/md/semvery.1.md -s -t man | man -l -
