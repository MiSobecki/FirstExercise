package sobecki.michal.ex1.repository;

import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final Map<String, Integer> userRegisters = new HashMap<>();

    public int register(String user) {
        return userRegisters.merge(user, 1, Integer::sum);
    }

    public void delete(String user) {
        userRegisters.remove(user);
    }

    public Map<String, Integer> getAllUsers() {
        return userRegisters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public Map<String, Integer> getTopUsers() {
        return userRegisters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public Map<String, Integer> getAllUsersInsensitive() {
        return userRegisters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toUpperCase(),
                        Map.Entry::getValue,
                        Integer::sum,
                        LinkedHashMap::new));
    }
}
