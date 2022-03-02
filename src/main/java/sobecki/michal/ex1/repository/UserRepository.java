package sobecki.michal.ex1.repository;

import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final HashMap<String, Integer> userRegisters = new HashMap<>();

    public int register(String user) {
        Integer returnedValue =
                userRegisters.put(user, userRegisters.get(user) != null ? userRegisters.get(user) + 1 : 1);

        if (returnedValue != null) return returnedValue + 1;
        else return 1;
    }

    public void delete(String user) {
        userRegisters.remove(user);
    }

    public LinkedHashMap<String, Integer> getAllUsers() {
        return userRegisters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    public LinkedHashMap<String, Integer> getTopUsers() {
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

    public LinkedHashMap<String, Integer> getAllUsersInsensitive() {
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
