package fitness;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Schedule {
    private FitnessClass[] classes;
    private int numClasses;

    public Schedule() {
        this.classes = new FitnessClass[10]; // Assuming an initial capacity of 10 classes
        this.numClasses = 0;
    }

    private void grow() {
        FitnessClass[] newClasses = new FitnessClass[classes.length * 2];
        for (int i = 0; i < numClasses; i++) {
            newClasses[i] = classes[i];
        }
        classes = newClasses;
    }

    public void load(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim(); // Trim leading and trailing spaces
            if (line.isEmpty()) {
                continue; // Skip blank lines
            }
            String[] tokens = line.split("\\s+"); // Split on one or more spaces
            Offer offer = Offer.valueOf(tokens[0].toUpperCase());
            Instructor instructor = Instructor.valueOf(tokens[1].toUpperCase());
            Time time = Time.valueOf(tokens[2].toUpperCase());
            Location location = Location.valueOf(tokens[3].toUpperCase());
            FitnessClass fitnessClass = new FitnessClass(offer, instructor, location, time);
            if (numClasses == classes.length) {
                grow();
            }
            classes[numClasses++] = fitnessClass;
        }
        scanner.close();
    }


    public FitnessClass findClass(String classType, Instructor instructor, Location studio) {
        for (int i = 0; i < numClasses; i++) {
            FitnessClass fitnessClass = classes[i];
            if (fitnessClass.getClassInfo().name().equalsIgnoreCase(classType) &&
                    fitnessClass.getInstructor() == instructor &&
                    fitnessClass.getStudio() == studio) {
                return fitnessClass;
            }
        }
        return null; // No class found
    }

    public String printClasses() {
        StringBuilder sb = new StringBuilder();
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass != null) {
                sb.append(fitnessClass.toString()).append("\n");
            }
        }
        return sb.toString();
    }

}

