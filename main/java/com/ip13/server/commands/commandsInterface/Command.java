package com.ip13.server.commands.commandsInterface;

import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;

public interface Command {
    Response<?> execute(Request<?> request);
}
