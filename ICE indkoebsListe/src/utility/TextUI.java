package utility;


import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private Scanner scanner = new Scanner(System.in);

    public String promptText(String msg){
        displayMessage(msg);
        return scanner.nextLine();
    }

    public String promptTextYN(String msg){
        displayMessage(msg);
        String input = scanner.nextLine().toLowerCase();
        switch (input){
            case "y":
                return input;
            case "n":
                return input;
            default:
                displayMessage("Invalid input");
                return promptTextYN(msg);
        }
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



    public int promptNumericTwo(String msg) {
        String input = promptText(msg);
        switch (input) {
            case "1":
                return Integer.parseInt(input);
            case "2":
                return Integer.parseInt(input);
            default:
                displayMessage("ugyldigt input, prøv igen");
                return promptNumericTwo(msg);
        }
    }

    public int promptNumericFive(String msg) {
        String input = promptText(msg);
        switch (input) {
            case "1":
                return Integer.parseInt(input);
            case "2":
                return Integer.parseInt(input);
            case "3":
                return Integer.parseInt(input);
            case "4":
                return Integer.parseInt(input);
            case "5":
                return Integer.parseInt(input);
            default:
                displayMessage("ugyldigt input, prøv igen");
                return promptNumericFive(msg);
        }
    }


    public int promptChoice(ArrayList<String> optionslist, String msg){
        displayMessage(msg);
        displayList(optionslist, "");
        int input = promptNumeric("");
        return input;
    }
    public int promptChoiceLogin(ArrayList<String> optionslist, String msg){
        displayMessage(msg);
        displayList(optionslist, "");
        int input = promptNumericTwo("");
        return input;
    }
    public int promptChoiceMenu(ArrayList<String> optionslist, String msg){
        displayMessage(msg);
        displayList(optionslist, "");
        int input = promptNumericFive("");
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