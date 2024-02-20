import java.util.Set;

public class Person {
    public String name;
    public String surname;
    public String email;

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public static Person fromUserInput() {
        final Set<String> emptySet = Set.of();

        final String name = IOUtils.getUserString(emptySet, "Insert your first name: ", "Please insert a valid string!");
        final String surname = IOUtils.getUserString(emptySet, "Insert your surname: ", "Please insert a valid string!");
        final String email = IOUtils.getUserString(emptySet, "Insert your email: ", "Please insert a valid email!");

        return new Person(name, surname, email);
    }

    public String toDisplayString() {
        return String.format("%s %s <%s>", name, surname, email);
    }
}
