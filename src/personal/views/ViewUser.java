package personal.views;

import personal.controllers.UserController;
import personal.model.User;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ViewUser {

    private UserController userController;

    public ViewUser(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com = Commands.NONE;

        while (true) {
            String command = prompt("Введите команду: ");
            try {
                com = Commands.valueOf(command.toUpperCase());
                if (com == Commands.EXIT) return;
                switch (com) {
                    case CREATE:
                        createUser();
                        break;
                    case READ:
                        readUser();
                        break;
                    case LIST:
                        readList();
                        break;
                    case UPDATE:
                        updateUser();
                        break;
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateUser() throws Exception {
        readList();                         // вывели список контактов
        User user = getUser();              // запросили id нужного контакта, вернули юзера с этим id
        User updatedUser = getNewUser();    // создали новый контакт без указания номера id
        updatedUser.setId(user.getId());    // присвоили новому контакту id выбранного для изменения, т.е. новыйЮзер.присвоитьID(первоначальныйЮзер.получитьID)
        User savedUser = userController.updateUserSave(updatedUser);
        System.out.println(savedUser);

    }

    private void readList() {
        List<User> listUsers = userController.readAllUsers();
        for (User user: listUsers){
            System.out.println(user);
        }
    }

    private void readUser() throws Exception {
        User user = getUser();
        System.out.println(user);
    }

    private User getUser() throws Exception {
        String id = prompt("Идентификатор пользователя: ");
        User user = userController.readUser(id);
        return user;
    }

    private void createUser() {
        User user = getNewUser();
        userController.saveUser(user);
    }

    private User getNewUser() {
        String firstName = prompt("Имя: ");
        String lastName = prompt("Фамилия: ");
        String phone = prompt("Номер телефона: ");
        User user = new User(firstName, lastName, phone);
        return user;
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
