public class Cat extends Pet {

  private int miceCaught;
  public static final int DEFAULT_MICE_CAUGHT = 0;

  public Cat(String name, double health, int painLevel, int miceCaught) {
    super(name, health, painLevel);
    this.miceCaught = miceCaught < 0 ? 0 : miceCaught;
  }
  
  public Cat(String name, double health, int painLevel) {
    super(name, health, painLevel);
    this.miceCaught = DEFAULT_MICE_CAUGHT;
  }

  public int getMiceCaught() {
    return miceCaught;
  }

  public void setMiceCaught(int miceCaught) {
    this.miceCaught = miceCaught;
  }

  public int treat() {
    double dblMinutes = 0.0;
    int minutes = 0;

    if (miceCaught < 4) {
      dblMinutes = (this.getPainLevel() * 2) / this.getHealth();
    }
    else if (miceCaught >= 4 && miceCaught <= 7) {
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
    String meows = "";
    for (int i = 0; i < miceCaught; i++) {
      meows += "meow ";
    }
    // uppercase or not
    meows = this.getPainLevel() > 5 ? meows.toUpperCase() : meows.toLowerCase();
    // trim trailing whitespaces
    meows = meows.trim();
    System.out.println(meows);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Cat) {
      Cat cat = (Cat) o;
      return super.equals(cat) && this.miceCaught == cat.getMiceCaught();
    }
    return false; 
  }

  public String toString() {
    String output = "Name: " + this.getName() + ", health: " + this.getHealth() + ", painLevel: " + this.getPainLevel() + ", miceCaught: " + miceCaught;
    return output;
  }
}