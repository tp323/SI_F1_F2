package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

interface DbOperation {
    void dbWork();
}

interface ParameterFunction<T> {
    T doSomething();
}

public class App {
    private JPAContext context = null;
    private final HashMap<InterfaceOptions, DbOperation> DB_METHODS = new HashMap<>();
    private final Scanner in = new Scanner(System.in);

    public App() {
        DB_METHODS.put(InterfaceOptions.INSERT_CLIENT_PART, () -> Table.createTable(clientInfo(), in));
        DB_METHODS.put(InterfaceOptions.UPDATE_CLIENT_PART, () -> Table.createTable(clientInfo(), in));
        DB_METHODS.put(InterfaceOptions.REMOVE_CLIENT_PART, () -> Table.createTable(removeClient(), in));
        //DB_METHODS.put(InterfaceOptions.TOTAL_ALARMS, () -> Table.createTable(context.getAlarms(), in));
        //DB_METHODS.put(InterfaceOptions.PROCESS_REQUEST, () -> Table.createTable(context.process(), in));
        DB_METHODS.put(InterfaceOptions.CREATE_ALARM, () -> Table.createTable(addBip(), in));
        DB_METHODS.put(InterfaceOptions.CREATE_VEHICLE, () -> Table.createTable(createVehicle(), in));
        /*DB_METHODS.put(InterfaceOptions.SHOW_VIEW, () -> {
            String[][] viewContents = context.showView();
            Table.createTable(viewContents, in);
        });*/
        DB_METHODS.put(InterfaceOptions.INSERT_VIEW, () -> Table.createTable(insertView(), in));
        //DB_METHODS.put(InterfaceOptions.DELETE_INVALID_RES, () -> Table.createTable(context.deleteInvalidRequests(), in));
        DB_METHODS.put(InterfaceOptions.DEACTIVATE_CLIENT, () -> Table.createTable(deactivateClient(), in));
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
        DEACTIVATE_CLIENT
    }

    private String[][] clientInfo() {
        clearConsole();
        System.out.print("Please introduce the client's NIF: ");
        int nif = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client's name: ");
        String name = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client's local of residence: ");
        String residence = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client's phone number: ");
        String phone = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client's referred client: ");
        int refClient = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client's CC: ");
        int cc = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();

        //return dCreate(context, nif, name, residence, phone, refClient, cc);
        return new String[0][];
    }

    private String[][] removeClient() {
        clearConsole();
        System.out.print("Please introduce the client's NIF: ");
        int nif = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client's CC: ");
        int cc = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        return new String[0][];
    }

    private String[][] addBip() {
        clearConsole();
        System.out.print("Please introduce the equipment's ID: ");
        int equipment = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the time and date: ");
        String date = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the coordinates ID: ");
        int coordinates = checkUserInput(in::nextInt);
        System.out.println();
        System.out.print("Is the alarm triggered?: ");
        boolean alarm = checkUserInput(in::nextBoolean);
        in.nextLine();
        System.out.println();
        return new String[0][];
    }

    private String[][] createVehicle() {
        clearConsole();
        System.out.print("Please introduce the Vehicle's registration: ");
        String registration = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the driver's ID: ");
        int driver = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the equipment's ID: ");
        int equipment = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the client the car belongs to: ");
        int client = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the radius: ");
        int radius = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the latitude: ");
        float latitude = checkUserInput(in::nextFloat);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the longitude: ");
        float longitude = checkUserInput(in::nextFloat);
        in.nextLine();
        System.out.println();
        return new String[0][];
    }

    private String[][] insertView() {
        clearConsole();
        System.out.print("Please introduce the Vehicle's registration: ");
        String registration = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the driver's name: ");
        String driverName = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the latitude: ");
        float latitude = checkUserInput(in::nextFloat);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the longitude: ");
        float longitude = checkUserInput(in::nextFloat);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the date and time: ");
        String date = in.nextLine();
        System.out.println();
        return new String[0][];
    }

    private String[][] deactivateClient() {
        clearConsole();
        System.out.print("Please introduce the Client's NIF: ");
        int nif = checkUserInput(in::nextInt);
        System.out.println();
        return new String[0][];
    }

    private <T> T checkUserInput(ParameterFunction<T> func) {
        while(true) {
            try {
                return func.doSomething();
            } catch(InputMismatchException e) {
                System.out.println("The input requested is not correct!");
                in.nextLine();
            }
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
                in.nextLine();
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException interrupted) {
                    return InterfaceOptions.NONE;
                }
                clearConsole();
                displayMenu();
            }
        }
        in.nextLine();
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
        try {
            //this.context = new JPAContext(in.nextLine());
            this.context = new JPAContext();
        } catch(Exception e) {
            System.out.println("The persistent name given does not have a Persistence provider.");
        }
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
