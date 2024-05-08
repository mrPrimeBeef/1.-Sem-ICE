package utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;


public class TextUI {
    private Scanner scanner = new Scanner(System.in);

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

    public int promptChoice(ArrayList<String> optionslist, String msg){
        displayMessage(msg);
        displayList(optionslist, "");
        int input = promptNumeric("");
        return input;
    }
    // ArrayList options
    // ui.validerInput(5, "Vælg en af valgmulighederne");
    public int validerInput(int min, int max)
    {
        String regex = String.format("[%d-%d]", min, max);

        String valg = promptText("\nDit valg: ");
        if (!valg.matches(regex))
        {
            displayMessage("Ugyldigt input. Prøv igen");
            return promptNumeric(valg);
        } else {
            return Integer.parseInt(valg);
        }
    }
    public int promptChoiceLogin(ArrayList<String> optionslist, String msg){
        displayMessage(msg);
        displayList(optionslist, "");
        int input = validerInput(1, 2);
        return input;
    }


    public void displayList(ArrayList<String> list, String msg){
        System.out.println(msg);
        int counter = 1;
        for (String option : list) {
            System.out.print(counter + ") ");
            counter++;
            System.out.println(option);
        }
    }


    public void displayMessage(String msg){
        System.out.println(msg);
    }
}