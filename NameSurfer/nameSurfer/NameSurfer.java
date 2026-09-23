import java.io.*;
import java.util.*;


/**
 * Driver program for the baby-name project.
 */
public class NameSurfer {


    public static void main(String[] args) throws FileNotFoundException {
        NameRecord[] records = loadRecords("name_data.txt");
        Scanner console = new Scanner(System.in);


        // set up drawing scale once
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);


        boolean done = false;
        while (!done) {
            printMenu();
            System.out.print("Enter your selection: ");


            int choice;
            if (console.hasNextInt()) {
                choice = console.nextInt();
                console.nextLine(); // eat end-of-line
            } else {
                console.nextLine(); // bad input, clear
                System.out.println("Please enter a number 1–5.");
                continue;
            }


            if (choice == 1 || choice == 2 || choice == 3) {
                System.out.print("Enter a name: ");
                String name = console.nextLine().trim();


                NameRecord rec = find(records, name);
                if (rec == null) {
                    System.out.println("Name \"" + name + "\" not found.");
                } else if (choice == 1) {
                    int bestYear = rec.bestYear();
                    System.out.println("Best year for " + rec.getName()
                            + " is " + bestYear + ".");
                } else if (choice == 2) {
                    int bestRank = bestRank(rec);
                    int bestYear = bestYearForRank(rec, bestRank);
                    System.out.println("Best rank for " + rec.getName()
                            + " is " + bestRank + " in " + bestYear + ".");
                } else { // choice == 3
                    rec.plot();
                }


            } else if (choice == 4) {
                StdDraw.clear();
            } else if (choice == 5) {
                done = true;
            } else {
                System.out.println("Invalid selection.");
            }
        }


        console.close();
        // Program ends; the StdDraw window will close when the user closes it.
    }


    // ---------- helper methods ----------


    /** Reads all lines from the data file and builds the array of NameRecord. */
    private static NameRecord[] loadRecords(String filename)
            throws FileNotFoundException {


        ArrayList<NameRecord> list = new ArrayList<>();
        Scanner input = new Scanner(new File(filename));


        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (!line.isEmpty()) {
                list.add(new NameRecord(line));
            }
        }
        input.close();


        return list.toArray(new NameRecord[0]);
    }


    /** Case-insensitive search for a name, returns the record or null. */
    private static NameRecord find(NameRecord[] records, String target) {
        for (NameRecord r : records) {
            if (r.getName().equalsIgnoreCase(target)) {
                return r;
            }
        }
        return null;
    }


    /** Returns the best (lowest non-zero) rank for this record. */
    private static int bestRank(NameRecord rec) {
        int best = 0;
        for (int d = 0; d < NameRecord.DECADES; d++) {
            int r = rec.getRank(d);
            if (r != 0 && (best == 0 || r < best)) {
                best = r;
            }
        }
        return best;
    }


    /** Returns the earliest year that has the given rank for this record. */
    private static int bestYearForRank(NameRecord rec, int targetRank) {
        for (int d = 0; d < NameRecord.DECADES; d++) {
            if (rec.getRank(d) == targetRank) {
                return NameRecord.START + 10 * d;
            }
        }
        // fallback – should never happen if targetRank came from bestRank()
        return NameRecord.START;
    }


    /** Prints the menu. */
    private static void printMenu() {
        System.out.println();
        System.out.println("1 - Find best year for a name");
        System.out.println("2 - Find best rank for a name");
        System.out.println("3 - Plot popularity of a name");
        System.out.println("4 - Clear plot");
        System.out.println("5 - Quit");
    }
}
