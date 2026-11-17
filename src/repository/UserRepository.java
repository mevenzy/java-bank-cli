package repository;

import entity.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище пользователей в памяти. Отдаёт Optional — null наружу не уходит.
 * LinkedHashMap — предсказуемый порядок обхода при отладке.
 */
public class UserRepository {

    private final Map<String, User> users = new LinkedHashMap<>();

    public Optional<User> findByName(String name) {
        return Optional.ofNullable(users.get(name));
    }

    public boolean existsByName(String name) {
        return users.containsKey(name);
    }

    public void save(User user) {
        users.put(user.getName(), user);
    }
}
