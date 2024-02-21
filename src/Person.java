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
        final String name = Prompter.promptString("Insert your first name: ");
        final String surname = Prompter.promptString("Insert your surname: ");
        final String email = Prompter.promptString("Insert your email: ");

        return new Person(name, surname, email);
    }

    public String toDisplayString() {
        return String.format("%s %s <%s>", name, surname, email);
    }
}
