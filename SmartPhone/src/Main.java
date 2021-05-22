// This class is used as a menu to intact with the phone book. It has two main parts, the Initializations and Menu.
// Initializations is used to create a contact list so we have a book with some contacts in it
// The menu is used to display the user his options and ask for input and call Book methods as necessary
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Initializations:
        //---------------------------------------------------------------------------
        ArrayList<Contact> cList = new ArrayList<Contact>(); // Create an ArrayList object
        ArrayList<Media> mList = new ArrayList<Media>(); // Create an ArrayList object
        // for scanner issues try to implement static scanner for all methods

        // define some contacts (we assume no name duplicates will be entered initially)
        Contact c1 = new Contact("Eden D", "6969");
        Contact c2 = new Contact("Or Mati", "12305");
        Contact c3 = new Contact("Ori Lavi", "45546");
        Contact c4 = new Contact("Noam Saban", "05266887");
        // add some contacts to the phone book
        cList.add(c1);
        cList.add(c2);
        cList.add(c3);
        cList.add(c4);

        Book book1 = new Book(cList); // Making a new phonebook object

        // define some media items
        Media m1 = new Media("Help", "song","4");
        Media m2 = new Media("Here Come The son", "song","3");
        Media m3 = new Media("Star Wars", "video","180");
        Media m4 = new Media("Iron-Man", "video","135");
        // add the media items to the list
        mList.add(m1);
        mList.add(m2);
        mList.add(m3);
        mList.add(m4);
        // MediaList media1 = new MediaList(mList);

        // Create list of empty events
        LinkedList<Event>[] Month = new LinkedList[31];
        for (int j=0; j<31; j++) {Month[j] = new LinkedList<Event>();}
        Calendar cal1 = new Calendar(Month);

        //book1.runMenu();
        PhoneMenu menu = new PhoneMenu(mList, cal1);
        menu.appAction(book1);

    }
}
