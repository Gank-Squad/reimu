
.PHONY: get generate test clean

FILE := "tree-sitter-linux-x64"
EXPECTED_HASH := "457eb788a80c27dd2b4f63146e97b6704162d18b7b83a9e73e4af18ff58b2da5"

all: generate test build

get:

	wget https://github.com/tree-sitter/tree-sitter/releases/download/v0.22.2/${FILE}.gz

	echo "457eb788a80c27dd2b4f63146e97b6704162d18b7b83a9e73e4af18ff58b2da5  ${FILE}.gz" > ${FILE}.gz.sha256

	sha256sum --check ${FILE}.gz.sha256 || exit 1

	gzip -d ${FILE}.gz

	chmod +x ${FILE}


generate:
	./${FILE} generate

test:
	./${FILE} test

build:
	./${FILE} build

clean:
	rm ./Cargo.toml ./setup.py ./binding.gyp ./Package.swift ./package.json ./pyproject.toml
	rm -r ./bindings

