
# Reimu (Programming Language)

This is the source code for the Reimu Interpreter.


## Building

You need [Maven](https://maven.apache.org/) and java 10 or later.

Clone this repo and navigate to the root directory;

Then run:
```sh
mvn package
```

You should then have `./target/reimu-{version}-jar-with-dependencies.jar`. This is your interpreter.

## Running

Once you have the interpreter jar file, simply run it with source files as the input.

```sh
java -jar reimu.jar ./src_files/test.rei
```
