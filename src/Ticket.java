public class Ticket {
    public Position position;
    public Person person;

    public Ticket(Position position, Person person) {
        this.position = position;
        this.person = person;
    }

    public int getPrice() {
        return position.getPrice();
    }

    public String toDisplayString() {
        return String.format(
                "Ticket %s @ %sLKR of %s",
                position.toDisplayString(),
                position.getPrice(),
                person.toDisplayString()
        );
    }
}
