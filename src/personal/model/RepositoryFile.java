package personal.model;

import java.util.ArrayList;
import java.util.List;

public class RepositoryFile implements Repository {
    private UserMapper mapper = new UserMapper();
    private FileOperation fileOperation;

    public RepositoryFile(FileOperation fileOperation) {
        this.fileOperation = fileOperation;
    }

    @Override
    public List<User> getAllUsers() {
        List<String> lines = fileOperation.readAllLines();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.map(line));
        }
        return users;
    }

    @Override
    public String CreateUser(User user) {

        List<User> users = getAllUsers();
        int max = 0;
        for (User item : users) {
            int id = Integer.parseInt(item.getId());
            if (max < id){
                max = id;
            }
        }
        int newId = max + 1;
        String id = String.format("%d", newId);
        user.setId(id);
        users.add(user);
        List<String> lines = mapToString(users);
        fileOperation.saveAllLines(lines);
        return id;
    }

    private List<String> mapToString(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User item : users) {
            lines.add(mapper.map(item));
        }
        return lines;
    }

    @Override
    public User updateUser(User user) {
        List<User> users = getAllUsers();                   // возвращает список контактов отредаченых под строку
        for (User currentUser: users) {                     // перебираем фором список контактов, если совпал ID
            if (currentUser.getId().equals(user.getId())){   // сеттером возвращаем контакту ФИО и тел контакта, обрабатываемого методом
                currentUser.setFirstName(user.getFirstName());
                currentUser.setLastName(user.getLastName());
                currentUser.setPhone(user.getPhone());
            }
        }
        fileOperation.saveAllLines(mapToString(users));
        return user;
    }
}
