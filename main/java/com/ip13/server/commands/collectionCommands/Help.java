package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;


public class Help extends AbstractCollectionCommand {
    public Help(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<String> execute(Request<?> request) {
        return new Response<>(commandName, "Success", HELP);
    }

    private final String HELP =
            """
                    help : вывести справку по доступным командам
                    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                    add {element} : добавить новый элемент в коллекцию
                    update id {element} : обновить значение элемента коллекции, id которого равен заданному
                    remove_by_id id : удалить элемент из коллекции по его id
                    clear : очистить коллекцию
                    save : сохранить коллекцию в файл
                    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                    exit : завершить программу (без сохранения в файл)
                    remove_head : вывести первый элемент коллекции и удалить его
                    add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                    history : вывести последние 11 команд (без их аргументов)
                    average_of_discount : вывести среднее значение поля discount для всех элементов коллекции
                    filter_by_refundable refundable : вывести элементы, значение поля refundable которых равно заданному
                    print_descending : вывести элементы коллекции в порядке убывания
                    """;

}
