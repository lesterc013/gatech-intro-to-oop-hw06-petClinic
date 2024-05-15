import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Clinic {
  
  private File patientFile;
  private int day;

  public Clinic(File file) {
    this.patientFile = file;
    this.day = 1;
  }

  public Clinic(String fileName) {
    this(new File(fileName));
  }

  public String nextDay(File f) throws FileNotFoundException {
    // Create fileScanner to iterate through each appointment in the file
    // Need another Scanner to ask for user input -- System.in
    
    Scanner fileScanner = null;
    String records = "";
    try {
      fileScanner = new Scanner(f);
      Scanner inputScanner = new Scanner(System.in);
      String[] tokens = null;

      while (fileScanner.hasNextLine()) {
        // Reading the input
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
          inputScanner.nextLine();
        }
        double health = inputScanner.nextDouble();
        
        System.out.printf("On a scale of 1 to 10, how much pain is %s in right now?\n", name);
        while (!inputScanner.hasNextInt()) {
          System.out.println("Please enter a number");
          System.out.printf("On a scale of 1 to 10, how much pain is %s in right now?\n", name);
          inputScanner.nextLine();
        }
        int painLevel = inputScanner.nextInt();     

        if (petType.equals("Dog")) {
          double droolRate = Double.parseDouble(tokens[2]);
          Dog pet = new Dog(name, health, painLevel, droolRate);
          pet.speak();
          int minutesRequired = pet.treat();
          // need use the addTime method
          String timeOut = addTime(timeIn, minutesRequired);
          records += String.format("%s,%s,%.1f,Day %d,%s,%s,%.1f,%d\n", name, petType, droolRate, day, timeIn, timeOut, health, painLevel);
        }
        
        else {
          int miceCaught = Integer.parseInt(tokens[2]);
          Cat pet = new Cat(name, health, painLevel, miceCaught);
          pet.speak();
          int minutesRequired = pet.treat();
          String timeOut = addTime(timeIn, minutesRequired);
          records += String.format("%s,%s,%d,Day %d,%s,%s,%.1f,%d\n", name, petType, miceCaught, day, timeIn, timeOut, health, painLevel);
        }
      }
      // Increment day after all the appts are seen
      day += 1;
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
    finally {
      if (fileScanner != null) {
        fileScanner.close();
      }
    }
    return records;
  }

  public String nextDay(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    return nextDay(file);
  }

  public boolean addToFile(String patientInfo) {
    String toAdd = "";
    String[] patientInfoTokens = patientInfo.split(",");
    String patientInfoName = patientInfoTokens[0];
    Scanner fileScanner = null;
    PrintWriter fileWriter = null;
    boolean newPatient = true;
    
    try {
      fileScanner = new Scanner(patientFile);
      while (fileScanner.hasNextLine()) {
        String patientFileLine = fileScanner.nextLine();
        // Populate toAdd with this old patient so we can maintain this record
        toAdd += patientFileLine;
        String[] patientFileTokens = patientFileLine.split(",");
        String patientFileName = patientFileTokens[0];

        // if there is a repeat patient, then we append the patientInfo subsequence
        if (patientInfoName.equals(patientFileName)) {
          int firstCommaIdx = patientInfo.indexOf(",");
          int secondCommaIdx = patientInfo.indexOf(",", firstCommaIdx+1);
          int thirdCommaIdx = patientInfo.indexOf(",", secondCommaIdx+1);
          toAdd += "," + patientInfo.subSequence(thirdCommaIdx+1, patientInfo.length()) + "\n";
          newPatient = false;
        }

        // else if no repeat patient BUT still a record of a patient, need to add a "\n"
        else {
          toAdd += "\n";
        }
      }
      if (newPatient) {
        toAdd += patientInfo + "\n";
      }
      fileScanner.close();
      fileWriter = new PrintWriter(patientFile);
      fileWriter.print(toAdd);
      return true;
    }
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      return false;
    }
    finally {
      if (fileScanner != null) {
        fileScanner.close();
      }
      if (fileWriter != null) {
        fileWriter.close();
      }
    }
  }

  private String addTime(String timeIn, int treatmentTime) {
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