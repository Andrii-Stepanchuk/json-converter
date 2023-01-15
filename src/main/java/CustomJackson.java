import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;


/**
 * CustomJackson is a special class that can process JSON
 *
 * @author Andrii Stepanchuk
 */

public class CustomJackson {

    public static void main(String[] args) {
        String json = getJsonFromFile("user.json");
        User user = jsonToObj(json, User.class);
        System.out.println(user);
    }

    /**
     * jsonToObj method that converts a json string into an object of type T
     *
     * @param json json that conforms to the syntax rules in the String format
     * @param someClass a certain class into which data will be entered from JSON
     * @return an object of type T
     */

    public static <T> T jsonToObj(String json, Class<T> someClass) {
        HashMap<String, String> jsonHashMap = jsonToHashMap(json);
        return hashMapToObj(jsonHashMap, someClass);
    }

    /**
     * getJsonFromFile that reads the contents of the file into a string
     *
     * @param file the name of the file contained in the resources
     * @return String that containing JSON
     */

    @SneakyThrows
    public static String getJsonFromFile(String file) {
        Path fileName = Paths.get(CustomJackson.class.getResource(file).toURI());
        return Files.readString(fileName);
    }

    /**
     * hashMapToObj method that fills the object with data from json
     *
     * @param jsonHashMap a map that contains a value key, where the key is the name of the field of some object
     * @param someClass a certain class into which data will be entered from JSON
     * @return String that containing json
     */

    @SneakyThrows
    private static <T> T hashMapToObj(HashMap<String, String> jsonHashMap, Class<T> someClass) {
        T user = someClass.getConstructor().newInstance();
        for (Field field : user.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(user, jsonHashMap.get(field.getName()));
        }
        return user;
    }

    /**
     * hashMapToObj method that fills the object with data from json
     *
     * @param json json that conforms to the syntax rules in the String format
     * @return a map that contains a value key, where the key is the name of the field of some object
     */

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