package parallel_code;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

class DungeonHunterThread {
    static final boolean DEBUG = false;

    static long startTime = 0;
    static long endTime = 0;
    private static void tick() { startTime = System.currentTimeMillis(); }
    private static void tock() { endTime = System.currentTimeMillis(); }

    public static void main(String[] args) {
        double xmin, xmax, ymin, ymax;
        DungeonMap dungeon;

        int numSearches = 10, gateSize = 10;
        int numThreads = Runtime.getRuntime().availableProcessors(); // default = cores

        Random rand = new Random();
        int randomSeed = 0;

        if (args.length < 3 || args.length > 4) {
            System.out.println("Usage: java DungeonHunter <gridSize> <searchFactor> <randomSeed> [numThreads]");
            System.exit(0);
        }

        try {
            gateSize = Integer.parseInt(args[0]);
            if (gateSize <= 0) throw new IllegalArgumentException("Grid size must be > 0.");

            numSearches = (int) (Double.parseDouble(args[1]) * (gateSize*2) * (gateSize*2) * DungeonMap.RESOLUTION);

            randomSeed = Integer.parseInt(args[2]);
            if (randomSeed < 0) throw new IllegalArgumentException("Seed must be >= 0.");
            else if (randomSeed > 0) rand = new Random(randomSeed);

            if (args.length == 4) {
                numThreads = Integer.parseInt(args[3]);
                if (numThreads <= 0) throw new IllegalArgumentException("Number of threads must be > 0.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }

        xmin = -gateSize;
        xmax = gateSize;
        ymin = -gateSize;
        ymax = gateSize;
        dungeon = new DungeonMap(xmin, xmax, ymin, ymax, randomSeed);

        int dungeonRows = dungeon.getRows();
        int dungeonColumns = dungeon.getColumns();

            Hunt[] searches = new Hunt[numSearches];
        for (int i = 0; i < numSearches; i++) {
            searches[i] = new Hunt(
                i + 1,
                rand.nextInt(dungeonRows),
                rand.nextInt(dungeonColumns),
                dungeon
            );
        }

        ForkJoinPool pool = new ForkJoinPool(numThreads);  // specify thread count

        tick();

        // Convert array to a list for easier iteration
        List<Hunt> tasks = Arrays.asList(searches);

        // Fork all tasks (start asynchronously)
        for (Hunt hunt : tasks) {
            pool.submit(hunt);   // submit to ForkJoinPool
        }

        // Wait for all tasks to complete and find the maximum
        int max = Integer.MIN_VALUE;
        int finder = -1;

        for (int i = 0; i < tasks.size(); i++) {
            int localMax = tasks.get(i).join();   // blocks until the task finishes
            if (localMax > max) {
                max = localMax;
                finder = i;
            }
        }

        tock();
        pool.shutdown();   // shutdown the pool after all tasks are done



       

        System.out.printf("\tDungeon size: %d\n", gateSize);
        System.out.printf("\tRows: %d, Columns: %d\n", dungeonRows, dungeonColumns);
        System.out.printf("\tSearch factor: %d searches\n", numSearches);
        System.out.printf("\tThreads used: %d\n", numThreads);
        System.out.printf("\tTime: %d ms\n", endTime - startTime);

        int tmp = dungeon.getGridPointsEvaluated();
        System.out.printf("\tGrid points evaluated: %d (%.2f%%)\n", tmp,
                (tmp * 1.0 / (dungeonRows * dungeonColumns)) * 100.0);

        System.out.printf("Dungeon Master (mana %d) found at: ", max);
        System.out.printf("x=%.1f y=%.1f\n\n",
                dungeon.getXcoord(searches[finder].getPosRow()),
                dungeon.getYcoord(searches[finder].getPosCol()));

        //dungeon.visualisePowerMap("visualiseSearch.png", false);
        //dungeon.visualisePowerMap("visualiseSearchPath.png", true);
    }
}
