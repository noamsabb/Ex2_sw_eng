// It is a simple arrayList with a copy constructor and an empty constructor

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Book {

    // Attributes:
    //---------------------------------------------------------------------------
    private ArrayList<Contact> contacts = new ArrayList<Contact>(); // Create an ArrayList object

    // constructors:
    //---------------------------------------------------------------------------
    public Book(ArrayList<Contact> c) {
        this.contacts = c;
    }

    public Book() {
    }

    // Methods:
    //---------------------------------------------------------------------------
    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    // Display menu and ask for input
    public void runMenu() throws FileNotFoundException {
        // Menu:
        //---------------------------------------------------------------------------
        int input = 0;
        Scanner scanner = new Scanner(System.in); // scanner object to scan input from the user.
        //do..while loop - present the menu to the user:
        do {
            System.out.println("\nPlease Choose an action:");
            System.out.println("1 - Add a new contact");
            System.out.println("2 - Delete a contact (by his name)");
            System.out.println("3 - Print all contacts to screen");
            System.out.println("4 - Search a contact by his name");  // in case of several appearance - prints all of them
            System.out.println("5 - Sort the phonebook by name"); // from the smallest to the biggest
            System.out.println("6 - Sort the phonebook by phone");// from the biggest to the smallest
            System.out.println("7 - Removing duplicates from the phonebook"); // of same ID && phones.
            System.out.println("8 - Arrange the phonebook in reverse order");
            System.out.println("9 - Export the phonebook to a text file"); // the text file will received from the user
            System.out.println("10 - Import a phonebook from a text file, and add it to the existing phonebook"); // the text file name will be received from the user
            System.out.println("11 - Quit");
    

            // Make sure input is a number
            while(true) {
                try {
                    //  Block of code to try
                    input = scanner.nextInt(); //getting user's input
                    break;
                } catch (java.util.InputMismatchException e) {
                    //  Block of code to handle errors
                    System.out.println("Enter numbers only!");
                    scanner.next();
                }
            }
            scanner.nextLine(); // consume '\n'
            
            switch (input) {
                case 1:
                    System.out.println("Enter the contact's name: ");
                    String name1 = scanner.nextLine(); //getting user's contact name
                    if (isDuplicate(name1)){
                        System.out.println("This name already exists in the book");
                        break;
                    }
                    System.out.println("Enter the contact's number: ");
                    String num = scanner.next();//getting user's contact number
                    while (!(num.matches("[0-9]+"))) { //Check user entered only numbers
                        System.out.println("Enter numbers only");
                        System.out.println("Enter the contact's number: ");
                        num = scanner.next();
                    }
                    this.addContact(name1, num);
                    scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Enter the contact's name: ");
                    String name2 = scanner.nextLine(); //getting user's contact name
                    this.removeContact(name2);
                    break;
                case 3:
                    this.printBook();
                    break;
                case 4:
                    System.out.println("Enter the contact's name: ");
                    String name3 = scanner.nextLine(); //getting user's contact name
                    this.searchContact(name3);
                    break;
                case 5:
                    this.nameSort();
                    break;
                case 6:
                    this.phoneSort();
                    break;
                case 7:
                    this.removeDup();
                    break;
                case 8:
                    this.reverseBook();
                    break;
                case 9:
                    System.out.println("Enter the file name: ");
                    String filename = scanner.nextLine(); //getting user's file name
                    filename += ".txt";
                    this.saveToTxt(filename);
                    break;
                case 10:
                    System.out.println("Enter the file name: ");
                    String path = scanner.nextLine(); //getting user's file name
                    path += ".txt";
                    this.uploadBook(path);
                    break;
                case 11:
                    System.out.println("Bye bye!");
                    break;
            }
            //scanner.close();
        } while (input != 11);
       // scanner.close();
    }

    private boolean isDuplicate(String name){
        for (Contact c : contacts)
            if (name.equals(c.getName()))
                return true;
        return false;
    }

    // Adds a contact to the arrayList
    public void addContact(String name, String num) {
        Contact c = new Contact(name, num);
        contacts.add(c);
        System.out.println("Contact added");
    }

    // Removes a contact from the arrayList
    public void removeContact(String name) {
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equals(name)) {
                contacts.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Contact doesn't exist");
        } else
            System.out.println("Contact removed");
    }

    // Prints a single contact
    private void printContact(int index) {
        System.out.println(this.contacts.get(index).getName() + " - " + this.contacts.get(index).getNumber());
    }

    public void printSearch(Contact contact)
    {
        System.out.println(contact.getName());
    }

    // Prints the whole phonebook
    public void printBook() {
        for (int i = 0; i < contacts.size(); i++) {
            this.printContact(i);
        }
    }

    // Searches entire book for a contact by name
    public void searchContact(String name) {
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equals(name)) {
                this.printContact(i);
                found = true;
            }
        }
        if (!found)
            System.out.println("Contact doesn't exist");
    }



//TODO: to add
    public Contact searchContact2(String name) {
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equals(name)) {
                found = true;
                return contacts.get(i);
            }
        }
        if (!found)
            System.out.println("Contact doesn't exist");
        return null;
    }

    //sort the Phonebook by the ABC order
    public void nameSort()
    {
        //using collection to sort the contacts by abc
        Collections.sort(this.contacts,new Comparator<Contact>(){//what we sort and to what to compare and sort it
            public int compare(Contact c1,Contact c2){//the example to how to sort it
                return String.valueOf(c1.getName()).compareTo(c2.getName());
            }
        });
        System.out.println("PhoneBook sorted");
    }

    //Sort PhoneBook by phone number from the largest to the smallest
    public void phoneSort()
    {
        int i = 0, size = contacts.size();//parameters for the recursive sort to work with
        numSort(i, size-1);//because size give the actual number but we start at 0
        System.out.println("PhoneBook sorted");
    }

    //The sorting function by phone number by using a pivot to sort by him
    private int BtoS(int start, int end)
    {
        // pivot
        String pivot = contacts.get(end).getNumber();
        int i = (start - 1);//i indicates the smaller num of the pivot
        for (int j = start; j <= end - 1; j++)
        {
            //check if the number is bigger then the pivot
            if (((contacts.get(j).getNumber().compareTo(pivot) > 0)))
            {
                // Increment index of the contact with bigger number
                i++;
                Collections.swap(contacts, i, j);
            }
        }
        Collections.swap(contacts, i + 1, end);
        return (i + 1);
    }

    //a quicksort function to sort the numbers
    private void numSort( int start, int end)
    {
        if (start < end)
        {
            // pi is partitioning index
            int pi = BtoS( start, end);
            // sorting the contacts before and after the partition
            numSort( start, pi - 1);
            numSort( pi + 1, end);
        }
    }

    // Removes duplicated contacts by name and number
    public void removeDup() {
        for (int j = 0; j < contacts.size()-1; j++) {
            for (int i = j+1; i < contacts.size(); i++) {
                if (contacts.get(i).equals(contacts.get(j))) {
                    contacts.remove(i);
                    i--;
                }
            }
        }
    }

    // Reverses current order of the book
    public void reverseBook(){
        ArrayList<Contact> tempList = new ArrayList<Contact>(contacts);
        for (int i = 0; i < contacts.size(); i++){
            contacts.set(i,tempList.get(tempList.size() - 1 - i));
        }
    }

    //Save PhoneBook to txt file
    public void saveToTxt(String filename) {
        try {
            File newFile = new File(filename);

            //Check if file already exist, if yes ask for new file name
            while (!(newFile.createNewFile()))
            {
                System.out.println("File already exist");
                System.out.println("Enter the file name: ");
                Scanner scanner = new Scanner(System.in);
                String newName = scanner.nextLine();
                newName += ".txt";
                File newFile2 = new File(newName);
                newFile.renameTo(newFile2);
            }
            FileWriter myWriter = new FileWriter(filename);
            //Write to File
            for (Contact contact : contacts) {
                myWriter.write(contact.getName() + " - " + contact.getNumber() + System.lineSeparator());
            }
            myWriter.close();
            System.out.println("PhoneBook saved into " + filename +" file successfully.");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    //Upload PhoneBook from txt file
    public void uploadBook(String filename) throws FileNotFoundException {
        try {
            int oldSize = contacts.size();
            BufferedReader newFile = new BufferedReader(new FileReader(filename), 16 * 1024);
            Scanner reader = new Scanner(newFile);
            reader.useDelimiter(" - ");
            //Read from file and create new contacts
            while (reader.hasNext()) {
                String name = reader.next();
                String number = reader.nextLine();
                number = number.replace(" - ", "");
                Contact c = new Contact(name, number);
                contacts.add(c);
            }
            reader.close();
            //Calculating how many contacts were added
            int added = (contacts.size() - oldSize);
            System.out.println(added + " contacts added to PhoneBook from " + filename);
        } catch(FileNotFoundException e) {
            System.out.println(filename + " Not found");
        }
    }
}

