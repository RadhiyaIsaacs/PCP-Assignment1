import java.io.*;

public class ThreadTester {
    private static final int WARMUP_RUNS = 1;      
    private static final int MEASURED_RUNS = 4;    

    public static void main(String[] args) throws Exception {
        int[] gridSizes = {100, 200, 300, 400};         // different dungeon sizes
        double searchFactor = 1.0;                       // example search density
        int seed = 54;                                   // fixed seed
        int[] threadCounts = {1, 2, 4, 8, 16};          // thread counts to test

        String csvFile = "thread_test_results.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile))) {
            pw.println("GridSize,Threads,AverageTime(ms)");

            for (int gridSize : gridSizes) {
                for (int threads : threadCounts) {
                    // Warmup runs
                    for (int i = 0; i < WARMUP_RUNS; i++) {
                        runAndTime("parallel_code.DungeonHunterThread", gridSize, searchFactor, seed, threads);
                    }

                    // Measured runs
                    long totalTime = 0;
                    for (int i = 0; i < MEASURED_RUNS; i++) {
                        totalTime += runAndTime("parallel_code.DungeonHunterThread", gridSize, searchFactor, seed, threads);
                    }

                    long avgTime = totalTime / MEASURED_RUNS;

                    pw.printf("%d,%d,%d%n", gridSize, threads, avgTime);
                    System.out.printf("Grid %d, Threads %d → Average time: %d ms%n", gridSize, threads, avgTime);
                }
            }
        }

        System.out.println("Thread testing complete. Results saved to " + csvFile);
    }

    private static long runAndTime(String mainClass, int gridSize, double factor, int seed, int threads)
            throws IOException, InterruptedException {
        long start = System.currentTimeMillis();

        ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp", ".",
                mainClass,
                String.valueOf(gridSize),
                String.valueOf(factor),
                String.valueOf(seed),
                String.valueOf(threads)
        );

        pb.inheritIO(); 
        Process p = pb.start();
        p.waitFor();

        return System.currentTimeMillis() - start;
    }
}
