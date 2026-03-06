import java.awt.Color;
import java.util.Random;
//Ethan Daniels ; CSC220-05 ; Project 04: Surfer ; 05 December 2025//

public class NameRecord {
    // Constants
    public static final int START = 1900;   // First year in the dataset
    public static final int DECADES = 11;   // Number of decades in the dataset

    // Instance variables
    private String name;        // The name being tracked
    private int[] ranks;        // Popularity ranks across decades

    /**
     * Constructor: Parses a line from the data file and initializes the NameRecord.
     * @param line A line from the file, e.g. "Sam 58 69 99 ..."
     */
    public NameRecord(String line) {
        String[] parts = line.trim().split("\\s+");
        name = parts[0];
        ranks = new int[DECADES];
        for (int i = 0; i < DECADES; i++) {
            ranks[i] = Integer.parseInt(parts[i + 1]);
        }
    }

    /**
     * Returns the name.
     * @return the name string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the rank of the name in the given decade.
     * @param decade index (0 = 1900, 1 = 1910, ..., 10 = 2000)
     * @return rank value (1 = most popular, 0 = not ranked)
     */
    public int getRank(int decade) {
        if (decade < 0 || decade >= DECADES) {
            throw new IllegalArgumentException("Invalid decade index: " + decade);
        }
        return ranks[decade];
    }

    /**
     * Returns the year where the name was most popular.
     * If tied, returns the earliest year.
     * @return year of best popularity
     */
    public int bestYear() {
        int bestRank = Integer.MAX_VALUE;
        int bestDecade = 0;
        for (int i = 0; i < DECADES; i++) {
            if (ranks[i] != 0 && ranks[i] < bestRank) {
                bestRank = ranks[i];
                bestDecade = i;
            }
        }
        return START + bestDecade * 10;
    }

    /**
     * Plots the popularity of the name across decades using StdDraw.
     * Each point is scaled between 0 and 1.
     */
    public void plot() {
        Random rand = new Random();
        Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        StdDraw.setPenColor(color);

        double xStep = 1.0 / (DECADES - 1);

        for (int i = 0; i < DECADES - 1; i++) {
            double x1 = i * xStep;
            double x2 = (i + 1) * xStep;

            double y1 = (ranks[i] == 0 ? 1100 : ranks[i]) / 1100.0;
            double y2 = (ranks[i + 1] == 0 ? 1100 : ranks[i + 1]) / 1100.0;

            StdDraw.line(x1, y1, x2, y2);
            StdDraw.filledCircle(x1, y1, 0.005);
        }
        // Draw last point
        double xLast = (DECADES - 1) * xStep;
        double yLast = (ranks[DECADES - 1] == 0 ? 1100 : ranks[DECADES - 1]) / 1100.0;
        StdDraw.filledCircle(xLast, yLast, 0.005);
    }
}