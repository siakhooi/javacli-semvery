#!/bin/bash

set -ex
curl https://qlty.sh | bash

# shellcheck disable=SC1091
source "$HOME/.bashrc"

qlty coverage publish --override-branch=main  \
--format=jacoco target/site/jacoco/jacoco.xml \
--add-prefix src/main/java/

