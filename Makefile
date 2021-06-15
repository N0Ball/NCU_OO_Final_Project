# Clear Before and Build

final: build
	cd src;java Main;

build: clean

	javac -d ./src Main.java

clean:

	find . -name "*.class" -type f | xargs rm -rf