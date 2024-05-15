public class Dog extends Pet {

  private double droolRate;
  public static final double DEFAULT_DROOL_RATE = 5.0;

  public Dog(String name, double health, int painLevel, double droolRate) {
    super(name, health, painLevel);
    this.droolRate = droolRate <= 0 ? 0.5 : droolRate;
  }
  
  public Dog(String name, double health, int painLevel) {
    super(name, health, painLevel);
    this.droolRate = DEFAULT_DROOL_RATE;
  }

  public double getDroolRate() {
    return droolRate;
  }

  public void setDroolRate(double droolRate) {
    this.droolRate = droolRate;
  }

  public int treat() {
    double dblMinutes = 0.0;
    int minutes = 0;

    if (droolRate < 3.5) {
      dblMinutes = (this.getPainLevel() * 2) / this.getHealth();
    }
    else if (droolRate >= 3.5 && droolRate <= 7.5) {
      dblMinutes = this.getPainLevel() / this.getHealth();
    }
    else {
      dblMinutes = this.getPainLevel() / (this.getHealth() * 2);
    }
    // need to do the minutes calculation before healing
    this.heal();
    minutes = (int) (Math.ceil(dblMinutes));
    return minutes;
  }

  public void speak() {
    super.speak();
    int thisPainLevel = this.getPainLevel();
    // get the correct number of barks
    String barks = "";
    for (int i = 0; i < thisPainLevel; i++) {
      barks += "bark ";
    }
    // uppercase or not
    barks = thisPainLevel > 5 ? barks.toUpperCase() : barks.toLowerCase();
    // trim trailing whitespaces
    barks = barks.trim();
    System.out.println(barks);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Dog) {
      Dog dog = (Dog) o;
      return super.equals(dog) && this.droolRate == dog.getDroolRate();
    }
    return false; 
  }

  public String toString() {
    String output = "Name: " + this.getName() + ", health: " + this.getHealth() + ", painLevel: " + this.getPainLevel() + ", droolRate: " + droolRate;
    return output;
  }
}