import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomJacksonTest {

    @ParameterizedTest
    @MethodSource("jsonsAndObjects")
    <T> void creationOfDifferentObjectsTest(String json, T userClass, T expectedUser) {
        T actualUser = (T) CustomJackson.jsonToObj(json, userClass.getClass());
        assertEquals(expectedUser, actualUser);
    }

    static Stream<Arguments> jsonsAndObjects() {
        return Stream.of(
                Arguments.of("{\n" +
                                "  \"firstName\": \"Levko\",\n" +
                                "  \"lastName\": \"Yodji\"\n" +
                                "}",
                        new User1(),
                        new User1("Levko", "Yodji")
                ),
                Arguments.of("{\n" +
                                "  \"firstName\": \"Levko\",\n" +
                                "  \"lastName\": \"Yodji\",\n" +
                                "  \"phoneNumber\": \"055-255-255-5\"\n" +
                                "}",
                        new User2(),
                        new User2("Levko", "Yodji", "055-255-255-5")
                ),
                Arguments.of("",
                        new User3(),
                        new User3()
                ),
                Arguments.of("{\n" +
                                "  \"firstName\": \"Levko\",\n" +
                                "  \"lastName\": \"Yodji\",\n" +
                                "  \"email\": \"the.best.user@gmail.com\",\n" +
                                "  \"phoneNumber\": \"055-255-255-5\"\n" +
                                "}",
                        new User4(),
                        new User4("Levko", "Yodji", "the.best.user@gmail.com", "055-255-255-5")
                ),
                Arguments.of("{\n" +
                                "  \"firstName\": \"Levko\",\n" +
                                "  \"lastName\": \"Yodji\",\n" +
                                "  \"phoneNumber\": \"055-255-255-5\"\n" +
                                "}",
                        new User4(),
                        new User4("Levko", "Yodji", null, "055-255-255-5")

                ),
                Arguments.of("{\n" +
                                "  \"firstName\": \"Levko\",\n" +
                                "  \"lastName\": \"Yodji\",\n" +
                                "  \"phoneNumber\": \"055-255-255-5\"\n" +
                                "}",
                        new User1(),
                        new User1("Levko", "Yodji")
                )
        );
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class User1 {
        private String firstName;
        private String lastName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class User2 {
        private String firstName;
        private String lastName;
        private String phoneNumber;
    }

    @Data
    private static class User3 {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class User4 {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
    }
}