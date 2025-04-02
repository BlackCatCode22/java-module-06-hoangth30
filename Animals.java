import java.time.LocalDate;

public class Animals {
    protected String name;
    protected int age;
    protected String sex;
    protected String species;
    protected String color;
    protected int weight;
    protected String origin;
    protected String birthDay;
    protected String uniqueID;
    protected String arrivalDate;

    public Animals(String name, int age, String sex, String species, String color, int weight, String origin, String birthDay, String arrivalDate) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.species = species;
        this.color = color;
        this.weight = weight;
        this.origin = origin;
        this.birthDay = birthDay;
        this.arrivalDate = arrivalDate;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getSex() { return sex; }
    public String getSpecies() { return species; }
    public String getColor() { return color; }
    public int getWeight() { return weight; }
    public String getOrigin() { return origin; }
    public String getBirthDay() { return birthDay; }
    public String getUniqueID() { return uniqueID; }
    public String getArrivalDate() { return arrivalDate; }

    public void setUniqueID(String id) { this.uniqueID = id; }

    // Generates a birthday based on age and birth season.
    public static String genBirthDay(int age, String birthSeason) {
        int arrivalYear = 2024;
        int birthYear = arrivalYear - age;
        String monthDay;
        if (birthSeason == null) {
            monthDay = "01-01";
        } else {
            switch (birthSeason.toLowerCase()) {
                case "spring": monthDay = "03-21"; break;
                case "summer": monthDay = "06-21"; break;
                case "fall":   monthDay = "09-22"; break;
                case "winter": monthDay = "12-21"; break;
                default: monthDay = "01-01"; break;
            }
        }
        return birthYear + "-" + monthDay;
    }

    @Override
    public String toString() {
        return uniqueID + "; " + name + "; birth date: " + birthDay + "; "
                + color + " color; " + sex + "; " + weight + " pounds; from "
                + origin + "; arrived " + arrivalDate;
    }
}

class Hyena extends Animals {
    private static int count = 0;

    public Hyena(String name, int age, String sex, String color, int weight, String origin, String birthSeason, String arrivalDate) {
        super(name, age, sex, "hyena", color, weight, origin, Animals.genBirthDay(age, birthSeason), arrivalDate);
        count++;
        setUniqueID(String.format("Hy%02d", count));
    }
}

class Lion extends Animals {
    private static int count = 0;

    public Lion(String name, int age, String sex, String color, int weight, String origin, String birthSeason, String arrivalDate) {
        super(name, age, sex, "lion", color, weight, origin, Animals.genBirthDay(age, birthSeason), arrivalDate);
        count++;
        setUniqueID(String.format("Li%02d", count));
    }
}

class Tiger extends Animals {
    private static int count = 0;

    public Tiger(String name, int age, String sex, String color, int weight, String origin, String birthSeason, String arrivalDate) {
        super(name, age, sex, "tiger", color, weight, origin, Animals.genBirthDay(age, birthSeason), arrivalDate);
        count++;
        setUniqueID(String.format("Ti%02d", count));
    }
}

class Bear extends Animals {
    private static int count = 0;

    public Bear(String name, int age, String sex, String color, int weight, String origin, String birthSeason, String arrivalDate) {
        super(name, age, sex, "bear", color, weight, origin, Animals.genBirthDay(age, birthSeason), arrivalDate);
        count++;
        setUniqueID(String.format("Be%02d", count));
    }
}
