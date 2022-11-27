package com.ip13.client.test;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.ticket.*;

import java.time.LocalDateTime;

public class RequestFactory {
    private final String login;
    private final String password;

    public RequestFactory(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Request<?> createRequest() {
        int probability = 15;
        int randomNumber = randomInt() % probability + 1;


//
//        if (1 <= randomNumber && randomNumber <= 100) {
//            return addRequest();
//        } else if (101 <= randomNumber && randomNumber <= 150) {
//            return addIfMaxRequest();
//        } else if (151 <= randomNumber && randomNumber <= 200) {
//            return averageOfDiscountRequest();
//        } else if (randomNumber == 201) {
//            return clearRequest();
//        } else if (202 <= randomNumber && randomNumber <= 250) {
//            return filterByRefundableRequest();
//        } else if (251 <= randomNumber && randomNumber <= 300) {
//            return helpRequest();
//        } else if (301 <= randomNumber && randomNumber <= 350) {
//            return historyRequest();
//        } else if (351 <= randomNumber && randomNumber <= 410) {
//            return infoRequest();
//        } else if (411 <= randomNumber && randomNumber <= 453) {
//            return printDescendingRequest();
//        } else if (454 <= randomNumber && randomNumber <= 500) {
//            return removeByIdRequest();
//        } else if (501 <= randomNumber && randomNumber <= 520) {
//            return removeHeadRequest();
//        } else if (520 <= randomNumber && randomNumber <= 580) {
//            return showRequest();
//        } else if (581 <= randomNumber) {
//            return updateByIdRequest();
//        } else {
//            return addRequest();
//        }

        return switch (randomNumber) {
            case 1 -> addRequest();
            case 2 -> addIfMaxRequest();
            case 3 -> averageOfDiscountRequest();
            case 4 -> clearRequest();
            case 5 -> filterByRefundableRequest();
            case 6 -> helpRequest();
            case 7 -> historyRequest();
            case 8 -> infoRequest();
            case 9 -> logInRequest();
            case 10 -> printDescendingRequest();
            case 11 -> registrationRequest();
            case 12 -> removeByIdRequest();
            case 13 -> removeHeadRequest();
            case 14 -> showRequest();
            case 15 -> updateByIdRequest();
            default -> throw new RuntimeException("Cannot create request");
        };
    }

    private int randomInt() {
        return (int) (Math.random() * 100);
    }

    private double randomDouble() {
        return (Math.random() * 100);
    }

    private boolean randomBoolean() {
        return (int) (Math.random() * 100) % 2 == 1;
    }

    public String randomString() {
        StringBuilder random = new StringBuilder();
        random.append(randomUpperCaseLetter());
        for (int i = 0; i < 8; i++) {
            random.append((char) randomLowerCaseLetter());
        }
        return random.toString();
    }

    private char randomLowerCaseLetter() {
        return (char) ((int) (Math.random() * 26) + 'a');
    }

    private char randomUpperCaseLetter() {
        return (char) ((int) (Math.random() * 26) + 'A');
    }


    private TicketType randomTicketType() {
        return TicketType.values()[randomInt() % TicketType.values().length];
    }

    private EventType randomEventType() {
        return EventType.values()[randomInt() % EventType.values().length];
    }

    private Event randomEvent() {
        return new Event(
                randomInt(),
                randomString(),
                randomInt(),
                randomEventType()
        );
    }

    public Ticket randomTicket() {
        return new Ticket(
                randomInt(),
                randomString(),
                new Coordinates(randomInt(), randomDouble()),
                LocalDateTime.now(),
                randomDouble(),
                randomDouble(),
                randomBoolean(),
                randomTicketType(),
                randomEvent()
        );
    }

    private Request<Ticket> addRequest() {
        return new Request<>(login, password, randomTicket(), COMMANDS.ADD);
    }

    private Request<Ticket> addIfMaxRequest() {
        return new Request<>(login, password, randomTicket(), COMMANDS.ADD_IF_MAX);
    }

    private Request<Boolean> averageOfDiscountRequest() {
        return new Request<>(login, password, true, COMMANDS.AVERAGE_OF_DISCOUNT);
    }

    private Request<Boolean> clearRequest() {
        return new Request<>(login, password, true, COMMANDS.CLEAR);
    }

    private Request<Boolean> filterByRefundableRequest() {
        return new Request<>(login, password, randomBoolean(), COMMANDS.FILTER_BY_REFUNDABLE);
    }

    private Request<Boolean> helpRequest() {
        return new Request<>(login, password, true, COMMANDS.HELP);
    }

    private Request<Boolean> historyRequest() {
        return new Request<>(login, password, true, COMMANDS.HISTORY);
    }

    private Request<Boolean> infoRequest() {
        return new Request<>(login, password, true, COMMANDS.INFO);
    }

    private Request<Boolean> logInRequest() {
        return new Request<>(randomString(), randomString(), true, COMMANDS.LOG_IN);
    }

    private Request<Boolean> printDescendingRequest() {
        return new Request<>(login, password, true, COMMANDS.PRINT_DESCENDING);
    }

    private Request<String> registrationRequest() {
        String password = randomString();
        boolean b = randomBoolean();
        String repeatedPassword = b ? password : randomString();
        return new Request<>(randomBoolean() ? login : randomString(), password, repeatedPassword, COMMANDS.REGISTRATION);
    }

    private Request<Integer> removeByIdRequest() {
        return new Request<>(login, password, randomInt(), COMMANDS.REMOVE_BY_ID);
    }

    private Request<Boolean> removeHeadRequest() {
        return new Request<>(login, password, true, COMMANDS.REMOVE_HEAD);
    }

    private Request<Boolean> showRequest() {
        return new Request<>(login, password, true, COMMANDS.SHOW);
    }

    private Request<Ticket> updateByIdRequest() {
        Ticket ticket = randomTicket();
        ticket.setId(randomInt() % 10);
        return new Request<>(login, password, randomTicket(), COMMANDS.UPDATE_BY_ID);
    }

}
