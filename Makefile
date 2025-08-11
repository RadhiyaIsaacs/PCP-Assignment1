JAVAC = javac
JAVA = java
SRC = SoloLevelling

COMMON = $(SRC)/DungeonMap.java $(SRC)/Hunt.java
SERIAL = $(SRC)/DungeonHunter.java
PARALLEL = $(SRC)/DungeonHunterParallel.java $(SRC)/HuntTask.java

ARGS ?= 20 50 42

serial:
	$(JAVAC) $(COMMON) $(SERIAL)
	$(JAVA) -cp $(SRC) DungeonHunter $(ARGS)

parallel:
	$(JAVAC) $(COMMON) $(PARALLEL)
	$(JAVA) -cp $(SRC) DungeonHunterParallel $(ARGS)

clean:
	rm -f $(SRC)/*.class
all:
	$(JAVAC) $(COMMON) $(SERIAL) $(PARALLEL)

