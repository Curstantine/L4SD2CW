import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void save() throws IOException {
        final File folder = new File("./tickets");
        final boolean folderCreated = folder.mkdir();
        if (!folderCreated) throw new IOException("Failed to create the tickets folder");

        final String path = String.format("%s%s.txt", folder.getPath() + File.separator, position.toDisplayString());
        final FileWriter writer = new FileWriter(path);

        writer.write(toDisplayString());
        writer.close();
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
