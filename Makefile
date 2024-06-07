all: clean build

build:
	javac -cp ".;libs\*" CubyzLauncher.java
	java -cp ".;libs\*" CubyzLauncher

clean:
	$(RM) -r *.class
	$(RM) -r */*.class