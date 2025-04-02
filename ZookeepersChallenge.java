import java.io.*;
import java.util.*;

public class ZookeepersChallenge {
    public static void main(String[] args) {
        String arrivalDate = "2024-03-26";

        Map<String, Queue<String>> namesMap = loadAnimalNames("C:\\repo\\java-module-06-hoangth30\\animalNames.txt");

        List<Animals> animals = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\repo\\java-module-06-hoangth30\\arrivingAnimals.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip empty lines
                Animals animal = parseAnimalLine(line, namesMap, arrivalDate);
                if (animal != null) {
                    animals.add(animal);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading arrivingAnimals.txt: " + e.getMessage());
            e.printStackTrace();
        }

        Map<String, List<Animals>> habitatMap = new HashMap<>();
        for (Animals animal : animals) {
            String species = animal.getSpecies();
            habitatMap.computeIfAbsent(species, k -> new ArrayList<>()).add(animal);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("zooPopulation.txt"))) {
            writer.println("Zoo Population Report");
            writer.println("=====================");
            writer.println();
            for (String species : habitatMap.keySet()) {
                // Create a header, e.g., "Hyena Habitat:" with species capitalized.
                String habitatHeader = species.substring(0, 1).toUpperCase() + species.substring(1) + " Habitat:";
                writer.println(habitatHeader);
                for (Animals a : habitatMap.get(species)) {
                    writer.println(a.toString());
                }
                writer.println();
            }
            System.out.println("Report generated successfully in zooPopulation.txt.");
        } catch (IOException e) {
            System.err.println("Error writing zooPopulation.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Map<String, Queue<String>> loadAnimalNames(String fileName) {
        Map<String, Queue<String>> namesMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentSpecies = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.toLowerCase().endsWith("names:")) {
                    currentSpecies = line.split(" ")[0].toLowerCase();
                } else {
                    String[] names = line.split(",");
                    Queue<String> queue = new LinkedList<>();
                    for (String name : names) {
                        String trimmedName = name.trim();
                        if (!trimmedName.isEmpty()) {
                            queue.add(trimmedName);
                        }
                    }
                    if (currentSpecies != null) {
                        namesMap.put(currentSpecies, queue);
                        currentSpecies = null;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
        return namesMap;
    }

    public static Animals parseAnimalLine(String line, Map<String, Queue<String>> namesMap, String arrivalDate) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 5) {
                System.err.println("Line format incorrect: " + line);
                return null;
            }
            String part0 = parts[0].trim();
            String[] tokens = part0.split(" ");
            int age = Integer.parseInt(tokens[0]);
            String sex = tokens[3].toLowerCase();
            String species = tokens[4].toLowerCase();

            String part1 = parts[1].trim();
            String birthSeason = null;
            if (part1.toLowerCase().startsWith("born in")) {
                birthSeason = part1.substring("born in".length()).trim();
            } else if (part1.toLowerCase().contains("unknown")) {
                birthSeason = null;
            }

            String part2 = parts[2].trim();
            String color = part2.toLowerCase().replace("color", "").trim();

            String part3 = parts[3].trim();
            int weight = Integer.parseInt(part3.toLowerCase().replace("pounds", "").trim());

            String part4 = parts[4].trim();
            String origin = part4.toLowerCase().startsWith("from") ? part4.substring(4).trim() : part4;

            String name = "Unnamed";
            if (namesMap.containsKey(species)) {
                Queue<String> queue = namesMap.get(species);
                if (!queue.isEmpty()) {
                    name = queue.poll();
                }
            }

            switch (species) {
                case "hyena":
                    return new Hyena(name, age, sex, color, weight, origin, birthSeason, arrivalDate);
                case "lion":
                    return new Lion(name, age, sex, color, weight, origin, birthSeason, arrivalDate);
                case "tiger":
                    return new Tiger(name, age, sex, color, weight, origin, birthSeason, arrivalDate);
                case "bear":
                    return new Bear(name, age, sex, color, weight, origin, birthSeason, arrivalDate);
                default:
                    // If species is not recognized, create a generic Animal.
                    return new Animals(name, age, sex, species, color, weight, origin, Animals.genBirthDay(age, birthSeason), arrivalDate);
            }
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            e.printStackTrace();
            return null;
        }
    }
}
