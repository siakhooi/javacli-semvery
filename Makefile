all-deb: clean set-version build build-deb
all-rpm: clean set-version build build-rpms

clean:
	mvn clean
	rm -f siakhooi-semvery_*_amd64.deb \
		siakhooi-semvery_*_amd64.deb.sha256sum \
		siakhooi-semvery_*_amd64.deb.sha512sum \
		siakhooi-semvery-*.rpm \
		siakhooi-semvery-*.rpm.sha256sum \
		siakhooi-semvery-*.rpm.sha512sum \

set-version:
	scripts/set-version.sh
release:
	scripts/create-release.sh

build:
	mvn verify
	scripts/shellcheck.sh
build-deb:
	scripts/build-deb.sh
build-rpms:
	scripts/build-rpms.sh

test-man:
	pandoc src/deb/md/semvery.1.md -s -t man | man -l -

qlty-check:
	qlty check --all

docker-build-rpm:
	docker run --rm -v $(CURDIR):/workspaces docker.io/siakhooi/devcontainer:rpm scripts/build-rpms.sh
docker-build-deb:
	docker run --rm -v $(CURDIR):/workspaces docker.io/siakhooi/devcontainer:deb scripts/build-deb.sh