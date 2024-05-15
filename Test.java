import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
  public static void main(String[] args) {
    File f = new File("Appointments.csv");
    Scanner fileScanner = null;
    String records = "";
    int day = 1;

    try {
      fileScanner = new Scanner(f);
      String[] tokens = null;
      while (fileScanner.hasNextLine()) {
        // Reading the input
        Scanner inputScanner = new Scanner(System.in);
        String line = "";
        line = fileScanner.nextLine();
        tokens = line.split(",");
        String name = tokens[0];
        String petType = tokens[1];
        String timeIn = tokens[3];
        // Exception check and getting health and painLevel
        if (!petType.equals("Dog") && !petType.equals("Cat")) {
          throw new InvalidPetException();
        }

        System.out.printf("Consultation for %s the %s at %s.\nWhat is the health of %s?\n", name, petType, timeIn, name);
        while (!inputScanner.hasNextDouble()) {
          System.out.println("Please enter a number");
          System.out.printf("Consultation for %s the %s at %s.\nWhat is the health of %s?\n", name, petType, timeIn, name);
          inputScanner.next();
        }
        double health = inputScanner.nextDouble();
        
        System.out.printf("On a scale of 1 to 10, how much pain is %s in right now?\n", name);
        while (!inputScanner.hasNextInt()) {
          System.out.println("Please enter a number");
          System.out.printf("On a scale of 1 to 10, how much pain is %s in right now?\n", name);
          inputScanner.next();
        }
        int painLevel = inputScanner.nextInt();     

        if (petType.equals("Dog")) {
          double droolRate = Double.parseDouble(tokens[2]);
          Dog pet = new Dog(name, health, painLevel, droolRate);
          pet.speak();
          int minutesRequired = pet.treat();
          System.out.println("Mins Required: " + minutesRequired);
          String timeOut = addTime(timeIn, minutesRequired);
          records += String.format("%s,%s,%.1f,Day %d,%s,%s,%.1f,%d\n", name, petType, droolRate, day, timeIn, timeOut, health, painLevel);
        }
        
        else {
          int miceCaught = Integer.parseInt(tokens[2]);
          Cat pet = new Cat(name, health, painLevel, miceCaught);
          pet.speak();
          int minutesRequired = pet.treat();
          System.out.println("Mins Required: " + minutesRequired);
          String timeOut = addTime(timeIn, minutesRequired);
          records += String.format("%s,%s,%d,Day %d,%s,%s,%.1f,%d\n", name, petType, miceCaught, day, timeIn, timeOut, health, painLevel);
        }
      }
     }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());;
    }
    finally {
      if (fileScanner != null) {
        fileScanner.close();
      }
    }
    day += 1;
    System.out.println(records);
    System.out.println(day);
    System.out.println("Time in: 1839, Timeout: " + addTime("1839", 175));
  }

  public static String addTime(String timeIn, int treatmentTime) {
    int intTimeIn = Integer.parseInt(timeIn);
    int treatmentHours = (treatmentTime / 60) * 100;
    int treatmentMinutes = treatmentTime % 60;
    int intTimeOut = intTimeIn + treatmentHours + treatmentMinutes;

    int onesPlace = intTimeOut % 10;
    int hundredAndOnesPlace = intTimeOut % 100;
    // situation when the minutes are >= 60 
    if (hundredAndOnesPlace >= 60) {
      intTimeOut += 100;
      intTimeOut -= hundredAndOnesPlace;
      intTimeOut += onesPlace;
    }

    String timeOut = Integer.toString(intTimeOut);
    return timeOut;
  }
}