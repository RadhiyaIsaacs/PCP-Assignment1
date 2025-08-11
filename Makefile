JAVAC = javac
JAVA = java

# Folders
SRC = SoloLevelling
PARALLEL_SRC = parallel_code

# Common file used in both versions
COMMON = $(SRC)/DungeonMap.java

# Serial version files
SERIAL = $(SRC)/DungeonHunter.java $(SRC)/Hunt.java

# Parallel version files
PARALLEL = $(PARALLEL_SRC)/DungeonHunter.java $(PARALLEL_SRC)/Hunt.java $(PARALLEL_SRC)/HuntTask.java

# Arguments to main programs
ARGS ?= 50 50 42

# Compile and run serial version
serial:
	$(JAVAC) $(COMMON) $(SERIAL)
	$(JAVA) -cp $(SRC) DungeonHunter $(ARGS)

# Compile and run parallel version
parallel:
	$(JAVAC) $(COMMON) $(PARALLEL)
	$(JAVA) -cp $(PARALLEL_SRC):$(SRC) DungeonHunter $(ARGS)

# Clean all .class files in both folders
clean:
	rm -f $(SRC)/*.class $(PARALLEL_SRC)/*.class

# Compile everything (optional)
all:
	$(JAVAC) $(COMMON) $(SERIAL) $(PARALLEL)
