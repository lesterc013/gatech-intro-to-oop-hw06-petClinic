public abstract class Pet {
  
  private String name;
  private double health;
  private int painLevel;

  public Pet(String name, double health, int painLevel) {
    this.name = name;

    if (health > 1.0) {
      this.health = 1.0;
    }
    else if (health < 0.0) {
      this.health = 0.0;
    }
    else {
      this.health = health;
    }

    if (painLevel > 10) {
      this.painLevel = 10;
    }
    else if (painLevel < 1) {
      this.painLevel = 1;
    }
    else {
      this.painLevel = painLevel;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getHealth() {
    return health;
  }

  public void setHealth(double health) {
    this.health = health;
  }

  public int getPainLevel() {
    return painLevel;
  }

  public void setPainLevel(int painLevel) {
    this.painLevel = painLevel;
  }

  public abstract int treat();

  public void speak() {
    String output = "Hello! My name is " + this.name;
    if (this.painLevel <= 5) {
      System.out.println(output);
    }
    else {
      System.out.println(output.toUpperCase());
    }
  }

  public boolean equals(Object o) {
    if (o instanceof Pet) {
      Pet pet = (Pet) o;
      return this.name.equals(pet.getName());
    }
    // if it is not a Pet instance, return false
    return false; 
  }

  protected void heal() {
    this.setHealth(1.0);
    this.setPainLevel(1);
  }
}
