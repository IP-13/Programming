package com.ip13.client.network;

import com.ip13.forwardedObjects.response.Response;

import java.util.Arrays;
import java.util.Collection;

public class ResponseHandler {
    public void handleResponse(Response<?> response) {
        switch (response.getCommandName()) {
            case ADD, ADD_IF_MAX, AVERAGE_OF_DISCOUNT, CLEAR, HELP, HISTORY, INFO, REMOVE_BY_ID, REMOVE_HEAD, UPDATE_BY_ID, LOG_IN, LOG_OUT, REGISTRATION ->
                    showWithData(response);
            case FILTER_BY_REFUNDABLE, PRINT_DESCENDING, SHOW -> showWithCollection(response);
        }
    }

    private void showMessage(Response<?> response) {
        System.out.print("Message: " + response.getMessage() + "\t\t");
    }

    private void showData(Response<?> response) {
        if (response.getData() != null) {
            System.out.println("Data: " + response.getData());
        } else {
            System.out.println("Data: no data in this response");
        }
    }

    private void showCollection(Response<?> response) {
        System.out.println();
        if (response.getData() != null) {
            System.out.println(Arrays.toString(Arrays.stream(((Collection) response.getData()).toArray()).toArray()));
//            ((Collection<Ticket>) response.getData()).forEach(ticket -> System.out.println(ticket.toString()));
        } else {
            System.out.println("Nothing to show");
        }
    }

    private void showWithData(Response<?> response) {
        showMessage(response);
        showData(response);
    }

    private void showWithCollection(Response<?> response) {
        showMessage(response);
        showCollection(response);
    }
}
