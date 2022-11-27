package com.ip13.forwardedObjects.commandsEnum;

import java.io.Serial;
import java.io.Serializable;

public enum COMMANDS implements Serializable {
    ADD,
    ADD_IF_MAX,
    AVERAGE_OF_DISCOUNT,
    CLEAR,
    EXECUTE_SCRIPT,
    FILTER_BY_REFUNDABLE,
    HELP,
    HISTORY,
    INFO,
    LOG_IN,
    PRINT_DESCENDING,
    REGISTRATION,
    REMOVE_BY_ID,
    REMOVE_HEAD,
    SHOW,
    UPDATE_BY_ID,
    LOG_OUT;

    @Serial
    private static final long serialVersionUID = 3404499;
}
