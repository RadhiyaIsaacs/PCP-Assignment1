# Makefile for Dungeon Hunter (parallel) — single folder

JAVAC = javac
JAVA  = java
MAIN  = DungeonHunterParallel
SOURCES = DungeonHunterParallel.java DungeonMapParallel.java HuntParallel.java
CLASSES = $(SOURCES:.java=.class)

# Set default args; override like: make run ARGS="300 0.5 42"
ARGS ?= 100 0.5 42

.PHONY: all run clean

all: $(CLASSES)

%.class: %.java
	$(JAVAC) $<

run: all
	$(JAVA) $(MAIN) $(ARGS)

clean:
	rm -f *.class
