package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.repo.JPAContext;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

interface DbOperation {
    void dbWork();
}

public class App {
    private JPAContext context = null;
    private final HashMap<InterfaceOptions, DbOperation> DB_METHODS = new HashMap<>();
    private final Scanner in = new Scanner(System.in);

    public App() {
        DB_METHODS.put(InterfaceOptions.INSERT_CLIENT_PART, () -> Table.createTable(clientInfo(), 1, in));
        DB_METHODS.put(InterfaceOptions.UPDATE_CLIENT_PART, () -> Table.createTable(clientInfo(), 1, in));
        DB_METHODS.put(InterfaceOptions.REMOVE_CLIENT_PART, () -> Table.createTable(removeClient(), 1, in));
        //DB_METHODS.put(InterfaceOptions.TOTAL_ALARMS, () -> Table.createTable(context.getAlarms(), 1, in));
        //DB_METHODS.put(InterfaceOptions.PROCESS_REQUEST, () -> Table.createTable(context.process(), 1, in));
        DB_METHODS.put(InterfaceOptions.CREATE_ALARM, () -> Table.createTable(addBip(), 1, in));
        DB_METHODS.put(InterfaceOptions.CREATE_VEHICLE, () -> Table.createTable(createVehicle(), 1, in));
        /*DB_METHODS.put(InterfaceOptions.SHOW_VIEW, () -> {
            String[][] viewContents = context.showView();
            Table.createTable(viewContents, viewContents[0].length, in);
        });*/
        DB_METHODS.put(InterfaceOptions.INSERT_VIEW, () -> Table.createTable(insertView(), 1, in));
        //DB_METHODS.put(InterfaceOptions.DELETE_INVALID_RES, () -> Table.createTable(context.deleteInvalidRequests(), 1, in));
        DB_METHODS.put(InterfaceOptions.DELETE_CLIENT, () -> Table.createTable(clientInfo(), 1, in));
    }

    private enum InterfaceOptions {
        NONE,
        EXIT,
        INSERT_CLIENT_PART,
        UPDATE_CLIENT_PART,
        REMOVE_CLIENT_PART,
        TOTAL_ALARMS,
        PROCESS_REQUEST,
        CREATE_ALARM,
        CREATE_VEHICLE,
        SHOW_VIEW,
        INSERT_VIEW,
        DELETE_INVALID_RES,
        DELETE_CLIENT
    }

    private String[][] clientInfo() {
        clearConsole();
        System.out.print("Please introduce the client's NIF: ");
        int nif = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client's name: ");
        String name = in.nextLine();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client's local of residence: ");
        String residence = in.nextLine();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client's phone number: ");
        String phone = in.next();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client's referred client: ");
        //TODO: refClient = func(checkNumberInput());
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client's CC: ");
        int cc = checkIntegerInput();
        absorbLines();
        System.out.println();
        return new String[0][0];
    }

    private String[][] removeClient() {
        clearConsole();
        System.out.print("Please introduce the client's NIF: ");
        int nif = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client's CC: ");
        int cc = checkIntegerInput();
        absorbLines();
        System.out.println();
        return new String[0][0];
    }

    private String[][] addBip() {
        clearConsole();
        System.out.print("Please introduce the equipment's ID: ");
        int equipment = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the time and date: ");
        String date = in.nextLine();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the coordinates's ID: ");
        String coordinates = in.nextLine();
        absorbLines();
        System.out.println();
        System.out.print("Is the alarm triggered?: ");
        boolean alarm = checkBooleanInput();
        absorbLines();
        System.out.println();
        return new String[0][0];
    }

    private String[][] createVehicle() {
        clearConsole();
        System.out.print("Please introduce the Vehicle's registration: ");
        String registration = in.next();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the driver's ID: ");
        int driver = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the equipment's ID: ");
        int equipment = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the client the car belongs to: ");
        int client = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the radius: ");
        int radius = checkIntegerInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the latitude: ");
        float latitude = checkFloatInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the longitude: ");
        float longitude = checkFloatInput();
        absorbLines();
        System.out.println();
        return new String[0][0];
    }

    private String[][] insertView() {
        clearConsole();
        System.out.print("Please introduce the Vehicle's registration: ");
        String registration = in.next();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the driver's name: ");
        String driverName = in.nextLine();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the latitude: ");
        float latitude = checkFloatInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the longitude: ");
        float longitude = checkFloatInput();
        absorbLines();
        System.out.println();
        System.out.print("Please introduce the date and time: ");
        String date = in.nextLine();
        absorbLines();
        System.out.println();
        return new String[0][0];
    }

    private int checkIntegerInput() {
        while(true) {
            try {
                return in.nextInt();
            } catch(InputMismatchException e) {
                System.out.println("The input requested should be a number!");
                absorbLines();
            }
        }
    }

    private float checkFloatInput() {
        while(true) {
            try {
                return in.nextFloat();
            } catch(InputMismatchException e) {
                System.out.println("The input requested should be a number!");
                absorbLines();
            }
        }
    }

    private boolean checkBooleanInput() {
        while(true) {
            try {
                return in.nextBoolean();
            } catch(InputMismatchException e) {
                System.out.println("The input requested should be a boolean!");
                absorbLines();
            }
        }
    }

    private void absorbLines() {
        while(in.hasNext()) {
            in.next();
        }
    }

    private void clearConsole() {
        for(int y = 0; y < 25; y++) System.out.println("\n");
    }

    private void displayMenu() {
        System.out.println("Vehicles Control Management");
        System.out.println();
        System.out.println("1. Exit");
        System.out.println("2. Insert Client");
        System.out.println("3. Update Client");
        System.out.println("4. Remove Client");
        System.out.println("5. Show Alarms");
        System.out.println("6. Process Requests");
        System.out.println("7. Create Alarm");
        System.out.println("8. Create Equipment");
        System.out.println("9. Show view data");
        System.out.println("10. Insert data into view");
        System.out.println("11. Delete Invalid Requests");
        System.out.println("12. Deactivate Client");
        System.out.print(">");
    }

    private InterfaceOptions getUserInput() {
        int chosenOption;
        while (true) {
            try {
                chosenOption = in.nextInt();
                break;
            } catch (InputMismatchException inputMismatch) {
                System.out.println("Sorry could not understand the input.");
                absorbLines();
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException interrupted) {
                    return InterfaceOptions.NONE;
                }
                clearConsole();
                displayMenu();
            }
        }
        if(InterfaceOptions.values().length <= chosenOption) return InterfaceOptions.NONE;
        return InterfaceOptions.values()[chosenOption];
    }

    private void executeOperation(InterfaceOptions option) {
        if(option == InterfaceOptions.NONE) System.out.println("That doesn't seem right...");
        try {
            DB_METHODS.get(option).dbWork();
        } catch(NullPointerException ignored) {

        }
    }

    private void getPersistenceName() {
        System.out.print("Please introduce the persistence name: ");
        this.context = new JPAContext(in.next());
        absorbLines();
    }

    public void runApp() {
        getPersistenceName();
        InterfaceOptions userInput;

        do {
            clearConsole();
            displayMenu();
            userInput = getUserInput();
            executeOperation(userInput);
        } while(userInput != InterfaceOptions.EXIT);
        System.out.println("Goodbye!");
        try {
            Thread.sleep(2000);
        } catch(InterruptedException ignored) {

        }
    }
}
