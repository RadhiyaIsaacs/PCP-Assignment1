import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        int gridSize = 10;
        double searchFactor = 0.5; // same factor used in DungeonHunter to compute numSearches
        String imgFolder = "images/"; // make sure this folder exists

        for (int test = 1; test <= 10; test++) {
            int randomSeed = test * 42; // different seed each time

            System.out.println("=== Test " + test + " (Seed " + randomSeed + ") ===");

            // --- Run Serial ---
            runProcess("java", "-cp", ".", "SoloLevelling.DungeonHunter",
                    String.valueOf(gridSize),
                    String.valueOf(searchFactor),
                    String.valueOf(randomSeed));

            renameFile("visualiseSearch.png", imgFolder + "serial_search_" + test + ".png");
            renameFile("visualiseSearchPath.png", imgFolder + "serial_searchPath_" + test + ".png");

            // --- Run Parallel ---
           runProcess("java", "-cp", ".", "parallel_code.DungeonHunter",
            String.valueOf(gridSize),
            String.valueOf(searchFactor),
            String.valueOf(randomSeed));

            renameFile("visualiseSearch.png", imgFolder + "parallel_search_" + test + ".png");
            renameFile("visualiseSearchPath.png", imgFolder + "parallel_searchPath_" + test + ".png");

            // --- Compare Outputs ---
            boolean searchIdentical = imagesIdentical(imgFolder + "serial_search_" + test + ".png",
                                          imgFolder + "parallel_search_" + test + ".png");
            boolean pathIdentical = imagesIdentical(imgFolder + "serial_searchPath_" + test + ".png",
                                        imgFolder + "parallel_searchPath_" + test + ".png");

            System.out.println("Search image identical (pixel-by-pixel)? " + searchIdentical);
            System.out.println("Search path image identical (pixel-by-pixel)? " + pathIdentical);
            System.out.println();
        }
    }

    private static void runProcess(String... command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        Process p = pb.start();
        p.waitFor();
    }

    private static void renameFile(String oldName, String newName) throws IOException {
        File oldFile = new File(oldName);
        if (oldFile.exists()) {
            java.nio.file.Files.move(oldFile.toPath(), new File(newName).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } else {
            System.err.println("File not found: " + oldName);
        }
    }

    private static boolean imagesIdentical(String file1, String file2) throws IOException {
        File f1 = new File(file1);
        File f2 = new File(file2);
        if (!f1.exists() || !f2.exists()) return false;

        BufferedImage img1 = ImageIO.read(f1);
        BufferedImage img2 = ImageIO.read(f2);

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) return false;

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) return false;
            }
        }
        return true;
    }
}
