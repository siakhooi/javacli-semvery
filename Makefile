clean:
	mvn clean
	rm -f siakhooi-semvery_*_amd64.deb \
		siakhooi-semvery_*_amd64.deb.sha256sum \
		siakhooi-semvery_*_amd64.deb.sha512sum

build:
	mvn clean verify
	scripts/shellcheck.sh
	scripts/build-deb.sh

test-man:
	pandoc src/deb/md/semvery.1.md -s -t man | man -l -
