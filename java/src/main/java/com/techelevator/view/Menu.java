package com.techelevator.view;

import com.techelevator.model.MenuItem;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.Attributes;

public class Menu {
    private PrintWriter out;
    private Scanner in;
    private Map<String,String> messages;
    private boolean showAnsi;

    private String message;

    private Map<String, MenuItem> menuOptions;

    public Menu(InputStream input, PrintWriter output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
        setMessages();
        this.showAnsi = false;
    }
    public Menu(InputStream input, OutputStream output, boolean showAnsi) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
        setMessages();
        this.showAnsi = showAnsi;
    }

    private void setMessages(){
        messages = new HashMap<>();
        messages.put("NotValidOption"," is not a valid option ***");
        messages.put("ChooseOption","Please choose an option >>> ");
    }

    private void loadOptions(Object[] options){
        menuOptions = new HashMap<>();
        for (int i=0;i<options.length;i++) {
            menuOptions.put(String.valueOf(i+1),new MenuItem(options[i]));
        }
    }
    private void loadOptions(MenuItem[] options){
        menuOptions = new HashMap<>();
        for (int i=0;i<options.length;i++) {
            menuOptions.put(String.valueOf(i+1),options[i]);
        }
    }

    public MenuItem getChoiceFromOptions(MenuItem[] options, String message) {
        this.message = message;
        loadOptions(options);
        return getChoiceFromOptionsMenuItem();
    }


    private MenuItem getChoiceFromOptionsMenuItem(){
        MenuItem choice = null;
        while (choice == null) {
            displayMenuOptions();
            if (message!=null && !message.isEmpty()) {
                out.println(message);
            }
            choice = getChoiceFromUserInputMenuItem();
        }
        return choice;
    }
    private MenuItem getChoiceFromUserInputMenuItem() {
        String userInput = in.nextLine();
        if (this.menuOptions.containsKey(userInput)){
            MenuItem item = this.menuOptions.get(String.valueOf(userInput));
            return item;
        }
        if (this.showAnsi) {
            out.println(AnsiColors.redValue(System.lineSeparator() + "*** " + userInput + messages.get("NotValidOption") + System.lineSeparator(), true));
        } else {
            out.println();
            out.println("*** " + userInput + messages.get("NotValidOption"));
            out.println();
        }
        return null;
    }

    public Object getChoiceFromOptions(Object[] options, String message) {
        this.message = message;
        loadOptions(options);
        return getChoiceFromOptions();
    }

    private String getChoiceFromOptions(){
        String choice = null;
        while (choice == null) {
            displayMenuOptions();
            if (message!=null && !message.isEmpty()) {
                out.println(message);
            }
            choice = getChoiceFromUserInput();
        }
        return choice;
    }
    private String getChoiceFromUserInput() {
        String userInput = in.nextLine();
        if (this.menuOptions.containsKey(userInput)){
            MenuItem item = this.menuOptions.get(String.valueOf(userInput));
            return item.getMenuCode();
        }
        if (this.showAnsi) {
            out.println(AnsiColors.redValue(System.lineSeparator() + "*** " + userInput + messages.get("NotValidOption") + System.lineSeparator(), true));
        } else {
            out.println();
            out.println("*** " + userInput + messages.get("NotValidOption"));
            out.println();
        }
        return null;
    }

    private void displayMenuOptions() {
        out.println();
        for (int i = 1; i <= this.menuOptions.size(); i++) {
            MenuItem item = this.menuOptions.get(String.valueOf(i));
            if (!item.getHide()) {
                if (this.showAnsi) {
                    out.println(AnsiColors.cyanValue((i) + ") ", true) + AnsiColors.greenValue(item.getMenuText(), true));
                } else {
                    out.println((i) + ") " + item.getMenuText());
                }
            }
        }
        if (this.showAnsi) {
            out.print(AnsiColors.yellowValue(System.lineSeparator() + messages.get("ChooseOption"), true));
        } else {
            out.println(messages.get("ChooseOption"));
        }
        out.flush();
    }
}
