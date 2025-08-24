import java.io.*;
// programme to test the effects of differnt densities on the parallel and serial programme
// radhiya isaacs with assistance from chatGPT
public class DensityTest {
    private static final int WARMUP_RUNS = 1; // Warmup runs to account for JIT compilation
    private static final int MEASURED_RUNS = 4; // Number of measured runs to average

    public static void main(String[] args) throws Exception {
        int[] gridSizes = {50, 100, 150, 200, 250, 300, 400, 500}; // different dungeon sizes
        double[] searchFactors = {0.1, 0.3, 0.5, 0.7, 0.9, 1.0}; // search densities
        int seed = 60; 
        String csvFile = "benchmark_results.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile))) {
            pw.println("GridSize,SearchFactor,SerialTime,ParallelTime");

            for (int grid : gridSizes) {
                for (double factor : searchFactors) {
                    // Warmup runs (discarded)
                    for (int i = 0; i < WARMUP_RUNS; i++) {
                        runAndTime("SoloLevelling.DungeonHunter", grid, factor, seed);
                        runAndTime("parallel_code.DungeonHunter", grid, factor, seed);
                    }

                    // Measure serial time
                    long totalSerialTime = 0;
                    for (int i = 0; i < MEASURED_RUNS; i++) {
                        totalSerialTime += runAndTime("SoloLevelling.DungeonHunter", grid, factor, seed);
                    }

                    long avgSerialTime = totalSerialTime / MEASURED_RUNS;

                    // Measure parallel time
                    long totalParallelTime = 0;
                    for (int i = 0; i < MEASURED_RUNS; i++) {
                        totalParallelTime += runAndTime("parallel_code.DungeonHunter", grid, factor, seed);
                    }
                    long avgParallelTime = totalParallelTime / MEASURED_RUNS;

                    double speedup = (double) avgSerialTime / avgParallelTime;

                    pw.printf("%d,%.2f,%d,%d%n",
                     grid, factor, avgSerialTime, avgParallelTime);

                    System.out.printf("Grid %d, Factor %.2f → Serial %d ms (avg), Parallel %d ms (avg)%n",
                        grid, factor, avgSerialTime, avgParallelTime);

                }
            }
        }

        System.out.println("Benchmark complete. Results saved to " + csvFile);
    }

    private static long runAndTime(String mainClass, int gridSize, double factor, int seed)
            throws IOException, InterruptedException {
        long start = System.currentTimeMillis();

        ProcessBuilder pb = new ProcessBuilder(
                "java", "-cp", ".",
                mainClass,
                String.valueOf(gridSize),
                String.valueOf(factor),
                String.valueOf(seed)
        );

        pb.inheritIO(); // show output from the process
        Process p = pb.start();
        p.waitFor();

        return System.currentTimeMillis() - start;
    }
}