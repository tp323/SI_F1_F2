package isel.sisinf.grp02.presentation;

import isel.sisinf.grp02.data_acess.JPAContext;
import isel.sisinf.grp02.orm.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

interface DbOperation {
    void dbWork();
}

interface ParameterFunction<T> {
    T doSomething();
}

interface ClienteFunctionCall<T> {
    T doClienteStuff(int nif, String name, String residence, String phone, int refClient, int cc);
}

public class App {
    private JPAContext context = null;
    private final HashMap<InterfaceOptions, DbOperation> DB_METHODS = new HashMap<>();
    private final Scanner in = new Scanner(System.in);

    public App() {
        DB_METHODS.put(InterfaceOptions.INSERT_CLIENT_PART, () -> Table.createTable(clientInfo((nif, name, residence, phone, refClient, cc) -> context.buildClienteFromInput(nif, name, residence, phone, refClient, cc)), in, ClienteParticular::toArray));
        DB_METHODS.put(InterfaceOptions.UPDATE_CLIENT_PART, () -> Table.createTable(clientInfo((nif, name, residence, phone, refClient, cc) -> context.updateClienteFromInput(nif, name, residence, phone, refClient, cc)), in, Cliente::toArray));
        DB_METHODS.put(InterfaceOptions.REMOVE_CLIENT_PART, () -> Table.createTable(new String[]{"removed_cliente_particular"}, in, removeClient()));
        DB_METHODS.put(InterfaceOptions.TOTAL_ALARMS, () -> Table.createTable(new String[]{"alarm_number"}, in, alarmNumber()));
        DB_METHODS.put(InterfaceOptions.PROCESS_REQUEST, this::requestProcess);
        DB_METHODS.put(InterfaceOptions.CREATE_VEHICLE, () -> Table.createTable(createVehicle(), in, Veiculo::toArray));
        DB_METHODS.put(InterfaceOptions.CREATE_VIEW, () -> Table.createTable(viewCreation(), in, TodosAlarmes::toArray));
        DB_METHODS.put(InterfaceOptions.INSERT_VIEW, () -> Table.createTable(insertView(), in, TodosAlarmes::toArray));
        DB_METHODS.put(InterfaceOptions.DELETE_INVALID_RES, this::invalidDeletion);
        DB_METHODS.put(InterfaceOptions.DEACTIVATE_CLIENT, () -> Table.createTable(new String[]{"removed_cliente"}, in, deactivateClient()));
    }

    private enum InterfaceOptions {
        NONE,
        EXIT,
        INSERT_CLIENT_PART,
        UPDATE_CLIENT_PART,
        REMOVE_CLIENT_PART,
        TOTAL_ALARMS,
        PROCESS_REQUEST,
        CREATE_VEHICLE,
        CREATE_VIEW,
        INSERT_VIEW,
        DELETE_INVALID_RES,
        DEACTIVATE_CLIENT
    }

    private <T> T clientInfo(ClienteFunctionCall<T> func) {
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
        int refClient = CheckRefCliente();
        System.out.println();
        System.out.print("Please introduce the client's CC: ");
        int cc = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();

        try {
            return func.doClienteStuff(nif, name, residence, phone, refClient, cc);
        } catch (Exception e) {
            onError(e);
            return null;
        }
    }

    private String[][] removeClient() {
        clearConsole();
        System.out.print("Please introduce the client's NIF: ");
        int nif = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        try {
            return context.deleteClienteParticularFromInput(nif);
        } catch (Exception e) {
            onError(e);
            return null;
        }
    }

    private String[][] alarmNumber() {
        clearConsole();
        System.out.print("Please introduce the car's registration: ");
        String registration = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the year: ");
        int year = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        try {
            return new String[][]{{Integer.toString(context.procedure_getAlarmNumber(registration, year))}};
        } catch (Exception e) {
            onError(e);
            return null;
        }
    }

    private void requestProcess() {
        boolean worked = false;
        try {
            worked = context.procedure_fetchRequests();
        } catch (Exception e) {
            onError(e);
        }
        if (worked) System.out.println("Request process completed successfully!");
        else System.out.println("Request process was not successful.");
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    private List<Veiculo> createVehicle() {
        clearConsole();
        System.out.println("Would you like to use the procedure?");
        String procedure = checkAnswer(in.nextLine());


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

        System.out.print("Would you like to input the coordinates and radius? ");
        String answer = checkAnswer(in.nextLine());
        if(answer.equalsIgnoreCase("no")) {
            try {
                if(procedure.equals("yes")) {
                    return context.procedure_createVehicle(registration, driver, equipment, client, null, null, null);
                }
                return context.procedure_createVehicle(registration, driver, equipment, client, null, null, null);
            } catch (Exception e) {
                onError(e);
                return null;
            }
        }

        System.out.println();
        System.out.print("Please introduce the radius: ");
        int radius = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the latitude: ");
        BigDecimal latitude = checkUserInput(in::nextBigDecimal);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the longitude: ");
        BigDecimal longitude = checkUserInput(in::nextBigDecimal);
        in.nextLine();
        System.out.println();
        try {
            if(procedure.equals("yes")) {
                return context.procedure_createVehicle(registration, driver, equipment, client, radius, latitude, longitude);
            }
            return context.procedure_createVehicle(registration, driver, equipment, client, radius, latitude, longitude);
        } catch (Exception e) {
            onError(e);
            return null;
        }
    }

    private List<TodosAlarmes> viewCreation() {
        try {
            return context.createView();
        } catch (Exception e) {
            onError(e);
            return null;
        }
    }

    private List<TodosAlarmes> insertView() {
        clearConsole();
        System.out.print("Please introduce the Vehicle's registration: ");
        String registration = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the driver's name: ");
        String driverName = in.nextLine();
        System.out.println();
        System.out.print("Please introduce the latitude: ");
        int latitude = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the longitude: ");
        int longitude = checkUserInput(in::nextInt);
        in.nextLine();
        System.out.println();
        System.out.print("Please introduce the date and time: ");
        String date = in.nextLine();
        System.out.println();
        try {
            return context.insertView(registration, driverName, latitude, longitude, Timestamp.valueOf(date));
        } catch (Exception e) {
            onError(e);
            return null;
        }
    }

    private void invalidDeletion() {
        boolean worked = false;
        try {
            worked = context.procedure_clearRequests();
        } catch (Exception e) {
            onError(e);
        }
        if(worked) System.out.println("Invalid deletion was successful!");
        else System.out.println("Invalid deletion was not successful.");
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    private String[][] deactivateClient() {
        clearConsole();
        System.out.print("Please introduce the Client's NIF: ");
        int nif = checkUserInput(in::nextInt);
        System.out.println();
        try {
            return context.deleteClienteFromInput(nif);
        } catch (Exception e) {
            onError(e);
            return null;
        }
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

    private int CheckRefCliente() {
        while(true) {
            try {
                System.out.println();
                System.out.print("Would you like to add a Client as a reference to this client? ");
                String answer = checkAnswer(in.nextLine());
                if(answer.equalsIgnoreCase("yes")) {
                    System.out.println();
                    System.out.print("Please introduce the client's CC of who referred the client: ");
                    int refCliente = in.nextInt();
                    in.nextLine();
                    return refCliente;
                }
                return 0;
            } catch(InputMismatchException e) {
                System.out.println("The input requested is not correct!");
                in.nextLine();
            }
        }
    }

    private String checkAnswer(String answer) {
        String newAnswer = answer;
        while(!newAnswer.equalsIgnoreCase("yes") && !newAnswer.equalsIgnoreCase("no")) {
            System.out.print("The answer should either be yes or no! ");
            newAnswer = in.nextLine();
        }
        return newAnswer;
    }

    private void clearConsole() {
        for(int y = 0; y < 25; y++) System.out.println("\n");
    }

    private void displayMenu() {
        int i = 0;
        System.out.println("Vehicles Control Management");
        System.out.println();
        System.out.println(++i + ". Exit");
        System.out.println(++i + ". Insert Client");
        System.out.println(++i + ". Update Client");
        System.out.println(++i + ". Remove Client");
        System.out.println(++i + ". Show Alarms");
        System.out.println(++i + ". Process Requests");
        System.out.println(++i + ". Create Vehicle");
        System.out.println(++i + ". Show view data");
        System.out.println(++i + ". Insert data into view");
        System.out.println(++i + ". Delete Invalid Requests");
        System.out.println(++i + ". Deactivate Client");
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
        //System.out.print("Please introduce the persistence name: ");
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

    private void onError(Exception e) {
        clearConsole();
        System.out.println("There seems to have been an error!");
        System.out.println(e.getMessage());
        System.out.println("Press enter to continue...");
        in.nextLine();
    }
}
