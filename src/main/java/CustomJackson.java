import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class CustomJackson {

    public static void main(String[] args) {
        String json = getJsonFromFile("user.json");
        User user = jsonToObj(json, User.class);
        System.out.println(user);
    }

    public static <T> T jsonToObj(String json, Class<T> someClass) {
        HashMap<String, String> jsonHash = jsonToHashMap(json);
        return hashMapToObj(jsonHash, someClass);
    }

    @SneakyThrows
    public static String getJsonFromFile(String file) {
        Path fileName = Paths.get(CustomJackson.class.getResource(file).toURI());
        return Files.readString(fileName);
    }

    @SneakyThrows
    private static <T> T hashMapToObj(HashMap<String, String> jsonHash, Class<T> someClass) {
        T user = someClass.getConstructor().newInstance();
        for (Field field : user.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(user, jsonHash.get(field.getName()));
        }
        return user;
    }

    private static HashMap<String, String> jsonToHashMap(String json) {
        if (json.isBlank())
            return new HashMap<>();

        String[] parts = json
                .replace("\"", "")
                .replace("{", "")
                .replace("}", "")
                .split(",");

        return Arrays.stream(parts)
                .map(part -> part.split(":"))
                .collect(Collectors.toMap(
                        subpart -> subpart[0].trim(),
                        subpart -> subpart[1].trim(),
                        (a, b) -> b,
                        HashMap::new)
                );
    }
}