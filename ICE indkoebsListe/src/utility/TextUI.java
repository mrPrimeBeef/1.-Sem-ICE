package utility;

import produkt.AProdukt;
import produkt.Vare;

import java.util.*;


public class TextUI {
    private Scanner scanner;
    Map<String, List<Vare>> groupedMap;

    public TextUI() {
        this.scanner = new Scanner(System.in);
        groupedMap = new HashMap<>();
    }

    public String promptText(String msg){
        displayMessage(msg);
        return scanner.nextLine();
    }


    public int promptNumeric(String msg) {
        String input = promptText(msg);
        if (!input.matches("^[0-9]+$")) { // Brug matches() til at sammenligne med regex
            displayMessage("Invalid input, try again");
            return promptNumeric(msg); // Returner resultatet af det rekursive kald
        } else {
            return Integer.parseInt(input);
        }
    }


    // ArrayList options
    // ui.validerInput(5, "Vælg en af valgmulighederne");
    public int validerInput(int min, int max)
    {
        String regex = String.format("[%d-%d]", min, max);

        String valg = promptText("");
        if (!valg.matches(regex))
        {
            displayMessage("Ugyldigt input. Prøv igen");
            return validerInput(min,max);
        } else {
            return Integer.parseInt(valg);
        }
    }
    public int promptChoice(ArrayList<String> optionslist, String msg, int min, int max){
        displayMessage(msg);
        displayList(optionslist, "");
        int input = validerInput(min, max);
        return input;
    }



    public void displayList(ArrayList<String> list, String msg){
       displayMessage(msg);
        int counter = 1;
        for (String option : list) {
            System.out.print(counter + ") ");
            counter++;
            System.out.println(option);
        }
    }

    public void displayListHashMap(HashMap<Vare, String> list) {
        groupedMap.clear();

        int counter = 1;
        // Group the keys by their values
        for (Map.Entry<Vare, String> entry : list.entrySet()) {
            String value = entry.getValue();
            groupedMap.computeIfAbsent(value, k -> new ArrayList<>()).add(entry.getKey());
        }
        // Display the grouped map
        for (Map.Entry<String, List<Vare>> group : groupedMap.entrySet()) {
            System.out.println(group.getKey() + ":");
            for (AProdukt produkt : group.getValue()) {
                System.out.println(counter + ") " + produkt);
                counter++;
            }
        }
    }

    public void printRetterOgIngredienser(HashMap<String, ArrayList<String>> retterOgIngredienser) {
        for (Map.Entry<String, ArrayList<String>> entry : retterOgIngredienser.entrySet()) {
            String retNavn = entry.getKey();
            ArrayList<String> ingredienser = entry.getValue();

            System.out.println("Ret: " + retNavn);
            System.out.println("Ingredienser:");
            for (String ingrediensNavn : ingredienser) {
                System.out.println("- " + ingrediensNavn);
            }
            System.out.println();
        }
    }

    public String søgtekst(String msg){

        String vare = promptText(msg);
        String searchPattern = String.format("[a-z]+n");
        if (!vare.matches(searchPattern)) {
            displayMessage(vare + " blev ikke fundet, desværre - søg igen.");
            søgtekst(msg); // rekursiv
        } else {
            return vare;
        }

        return vare;
    }

    public void displayMessage(String msg){
        System.out.println(msg);
    }
}