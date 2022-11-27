package com.ip13.client.test;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.ticket.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class UserRequestReader {
    private final Scanner scanner;
    private String login = "";
    private String password = "";

    public UserRequestReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public Request<?> readRequest() {
        COMMANDS command = readCommand();

        switch (command) {
            case ADD, ADD_IF_MAX -> {
                return new Request<>(login, password, readTicket(), command);
            }
            case UPDATE_BY_ID -> {
                return new Request<>(login, password, readTicketWithId(), command);
            }
            case AVERAGE_OF_DISCOUNT, CLEAR, HELP, HISTORY, INFO, PRINT_DESCENDING, REMOVE_HEAD, SHOW -> {
                return new Request<>(login, password, null, command);
            }
            case LOG_IN -> {
                setLogin(readLogin());
                setPassword(readPassword());
                return new Request<>(login, password, null, command);
            }
            case LOG_OUT -> {
                try {
                    return new Request<>(login, password, null, command);
                } finally {
                    login = "";
                    password = "";
                }
            }
            case FILTER_BY_REFUNDABLE -> {
                return new Request<>(login, password, readRefundable(), command);
            }
            case REMOVE_BY_ID -> {
                return new Request<>(login, password, readTicketId(), command);
            }
            case REGISTRATION -> {
                return new Request<>(readLogin(), readPassword(), readRepeatedPassword(), command);
            }
            default -> throw new RuntimeException("Request Error");
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String readLogin() {
        System.out.println("Enter login: ");
        return scanner.nextLine();
    }

    public String readPassword() {
        System.out.println("Enter password: ");
        char[] charPassword = System.console().readPassword();
        StringBuilder password = new StringBuilder();
        for (char c : charPassword) {
            password.append(c);
        }
        return password.toString();
    }

    private String readRepeatedPassword() {
        System.out.println("Enter your password again: ");
        char[] charPassword = System.console().readPassword();
        StringBuilder password = new StringBuilder();
        for (char c : charPassword) {
            password.append(c);
        }
        return password.toString();
    }

    private Ticket readTicket() {
        System.out.println("Enter ticket: ");
        while (true) {
            try {
                return new Ticket(
                        readTicketName(),
                        readCoordinates(),
                        readCreationDate(),
                        readPrice(),
                        readDiscount(),
                        readRefundable(),
                        readTicketType(),
                        readEvent()
                );
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private Ticket readTicketWithId() {
        System.out.println("Enter ticket: ");
        while (true) {
            try {
                return new Ticket(
                        readTicketId(),
                        readTicketName(),
                        readCoordinates(),
                        readCreationDate(),
                        readPrice(),
                        readDiscount(),
                        readRefundable(),
                        readTicketType(),
                        readEventWithId()
                );
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private int readTicketId() {
        System.out.println("Enter ticket ID: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private String readTicketName() {
        System.out.println("Enter ticket name: ");
        while (true) {
            try {
                return scanner.nextLine();
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private Coordinates readCoordinates() {
        int x;
        double y;

        System.out.println("Enter x coordinate: ");
        while (true) {
            try {
                x = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Throwable e) {
                wrongInput();
            }
        }

        System.out.println("Enter y coordinate: ");
        while (true) {
            try {
                y = Double.parseDouble(scanner.nextLine());
                break;
            } catch (Throwable e) {
                wrongInput();
            }
        }

        return new Coordinates(x, y);
    }

    private LocalDateTime readCreationDate() {
        System.out.println("Enter creation date in format [YYYY-MM-DD-HH-MM-SS]. Or type 'now', if you want to assign current time to your ticket: ");
        while (true) {
            try {
                String creationDateTime = scanner.nextLine();
                if (creationDateTime.equals("now")) {
                    return LocalDateTime.now();
                } else {
                    String[] dateTime = creationDateTime.split("-");
                    int year = Integer.parseInt(dateTime[0]);
                    int month = Integer.parseInt(dateTime[1]);
                    int day = Integer.parseInt(dateTime[2]);
                    int hour = Integer.parseInt(dateTime[3]);
                    int minute = Integer.parseInt(dateTime[4]);
                    int second = Integer.parseInt(dateTime[5]);
                    return LocalDateTime.of(year, month, day, hour, minute, second);
                }
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private double readPrice() {
        System.out.println("Enter ticket price: ");
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private double readDiscount() {
        System.out.println("Enter ticket discount: ");
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private boolean readRefundable() {
        System.out.println("Enter if ticket refundable or not: ");
        while (true) {
            try {
                String isRefundable = scanner.nextLine();
                if (isRefundable.equals("true")) {
                    return true;
                } else if (isRefundable.equals("false")) {
                    return false;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private TicketType readTicketType() {
        System.out.print("Enter ticket type. Available ticket types: ");
        Arrays.stream(TicketType.values()).forEach(ticketType -> System.out.print(ticketType + "   "));
        System.out.println();
        while (true) {
            try {
                return TicketType.valueOf(scanner.nextLine());
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private Event readEvent() {
        System.out.println("Enter event: ");
        while (true) {
            try {
                return new Event(
                        readEventName(),
                        readEventMinAge(),
                        readEventType()
                );
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private Event readEventWithId() {
        System.out.println("Enter event: ");
        while (true) {
            try {
                return new Event(
                        readEventId(),
                        readEventName(),
                        readEventMinAge(),
                        readEventType()
                );
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private int readEventId() {
        System.out.println("Enter event ID: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private String readEventName() {
        System.out.println("Enter event name: ");
        while (true) {
            try {
                return scanner.nextLine();
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private int readEventMinAge() {
        System.out.println("Enter event min age: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private EventType readEventType() {
        System.out.print("Enter event type. Available event types: ");
        Arrays.stream(EventType.values()).forEach(eventType -> System.out.print(eventType + "   "));
        System.out.println();
        while (true) {
            try {
                return EventType.valueOf(scanner.nextLine());
            } catch (Throwable e) {
                wrongInput();
            }
        }
    }

    private COMMANDS readCommand() {
        System.out.println("Enter command: ");
        while (true) {
            try {
                return COMMANDS.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                wrongInput();
            }
        }
    }

    private void wrongInput() {
        System.out.println("You've entered wrong data! Try again.");
    }
}
