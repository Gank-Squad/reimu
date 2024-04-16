
# Reimu (Programming Language)

This is the source code for the Reimu Interpreter.

A brief summary of the language can be found [here](https://docs.google.com/presentation/d/15e1qHos6hoTqXgYGeagFJkQ_4VnzNW78qeBvsubOacI/edit?usp=sharing)

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
java -jar ./target/reimu-{version}-jar-with-dependencies.jar ./src_files/test.rei
```

Or you can run it without source files, and access an interactive shell

```sh
java -jar ./target/reimu-{version}-jar-with-dependencies.jar
```

## Features
 - [x] variables
 - [x] functions
 - [x] c style for loops
 - [x] for each loop
 - [x] arrays
 - [x] int
 - [x] float/double
 - [x] string
 - [x] boolean
 - [x] casting
 - [x] type system
 - [ ] threading
 - [x] math casting (in cast)/(out cast)
