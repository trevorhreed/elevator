# elevator

## Run

Contrary to best practices, but out of sympathy for those who may not have all the necessary dependencies installed, this program comes pre-compiled, so you can run it without having to build it first!

```
$ java -jar dist/elevator.jar
```

## Build

```
$ javac src/elevator/*.java -d build/classes/elevator
$ jar cmvf manifest.txt dist/elevator.jar -C build/classes .
```
