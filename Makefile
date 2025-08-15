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
PARALLEL = $(PARALLEL_SRC)/DungeonHunter.java $(PARALLEL_SRC)/Hunt.java 

# Test runner
TESTRUNNER = TestRunner.java

# Arguments to main programs
ARGS ?= 300 0.5 42 #20 0.2 0

# Compile and run serial version
serial:
	$(JAVAC) $(COMMON) $(SERIAL)
	$(JAVA) -cp . SoloLevelling.DungeonHunter $(ARGS)

# Compile and run parallel version
parallel:
	$(JAVAC) $(COMMON) $(PARALLEL)
	$(JAVA) -cp . parallel_code.DungeonHunter $(ARGS)

# Compile and run TestRunner for comparison
compare: all
	$(JAVAC) $(TESTRUNNER)
	$(JAVA) -cp . TestRunner

# Clean all .class files in both folders
clean:
	rm -f $(SRC)/*.class $(PARALLEL_SRC)/*.class

# Compile everything (optional)
all:
	$(JAVAC) $(COMMON) $(SERIAL) $(PARALLEL)
	
