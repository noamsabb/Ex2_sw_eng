import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SMSApp extends AppBasic implements AppTemplate{

    // Attributes:
    //---------------------------------------------------------------------------
    private ArrayList<Contact> contacts = new ArrayList<Contact>(); // Create an ArrayList object

    // constructors:
    //---------------------------------------------------------------------------
    public SMSApp(){
        createSMSAppDir("SMSApp Chats");
    }

    // create a directory to store chats
    private void createSMSAppDir(String folder){
        File directory = new File(folder);
        directory.mkdir();
    }
    
    // Update the local contacts array attributes from the main book
    // Delete chats from deleted contacts
    private void updateContacts(Book book){
        this.contacts = book.getContacts();
        //System.out.println(contacts);
        String[] pathnames; // array to store all files in a directory
        File f = new File("SMSApp Chats");  // open directory
        pathnames = f.list();
        Boolean exists = false;
        for (String pathname : pathnames) {
            String chatName = pathname.substring(0, pathname.length() - 4); // remove .txt extension
            //System.out.println(chatName);
            for (Contact c : contacts)  // check the chat name is there exists a matching contact
                if (chatName.equals(c.getName()))
                    exists = true;
            if (!exists){
                File file = new File("SMSApp Chats/" + pathname);
                if (file.delete()) //delete the txt file
                    System.out.println("Conversation with " + chatName + " been deleted.");
                else
                    System.out.println("Conversation with " + chatName + " isn't found.");
            }
        }
    }

    @Override
    public void printMenu() {
        System.out.println("\nPlease Choose an action:");
        System.out.println("1 - Start conversation with a contact");
        System.out.println("2 - Delete conversation with a contact");
        System.out.println("3 - Print conversation with a contact");
        System.out.println("4 - Search for a sentence in all converstations");
        System.out.println("5 - Print all converstations");
        System.out.println("6 - Exit to phone menu");
    }

    @Override
    public void appAction(Book book){
        updateContacts(book);
        boolean exit = false;
        int input = 0;
        do {
            printMenu();
            input = inputRequest();
            switch(input) {
                case 1:
                    startChat();
                    break;
                case 2:
                    this.deleteChat(); 
                    break;
                case 3:
                    this.printChat();
                    break;
                case 4:
                    this.searchChatPhrase();
                    break;
                case 5:
                    this.printAllChats();
                    break;

                case 6:
                    exit = exitApp();
            }
        } while (!exit);
    }

    // Displays a numbered list of all the contacts
    private void displayContacts(){
        System.out.println();
        for (int i = 1; i <= contacts.size(); i++){
            System.out.println(i + ". " + contacts.get(i-1).getName());
        }
    }

    // Return if a contact choice is valid , avoid out of range array error 
    private boolean contactIsValid(int num){
        if (num > 0 && num <= contacts.size()){
            return true;
        }
        return false;
    }
    
    public void startChat(){
        Scanner scanner = new Scanner(System.in); // scanner object to scan input from the user.
        displayContacts();
        System.out.println("Choose a contact from the list: ");
        int contactNum = inputRequest();
        // Make sure contact number is valid
        if (contactIsValid(contactNum)){
            String contactName = contacts.get(contactNum-1).getName();
            // We need to check if the contact chat exists
            File tmpDir = new File("SMSApp Chats/" + contactName +".txt");
            boolean exists = tmpDir.exists();
            if (!exists){
                try{
                    tmpDir.createNewFile();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }

            // Write message to contact sms file
            System.out.print("Type your message: ");
            String msg = scanner.nextLine(); // getting sms message
            try
            {
                String filename= ("SMSApp Chats/" + contactName +".txt");
                FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                fw.write("me:\n");      //appends the string to the file
                fw.write(msg + "\n");   //appends the string to the file
                fw.close();
            }
            catch(Exception ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
        }
        else{
            System.out.println("Invalid contact number.");
        }
    }


    public void deleteChat(){
        Scanner scanner = new Scanner(System.in);
        displayContacts();
        System.out.println("Choose a contact from the list: ");
        int contactNum = inputRequest(); //getting user's contact index
        // TODO: Add valid number check
        if (contactIsValid(contactNum)){ //check for validation
            String contactName = contacts.get(contactNum-1).getName(); //getting the name from the index submitted
            //System.out.println("trying to delete " + contactName);
            String path = "SMSApp Chats/" + contactName +".txt";
            File file = new File(path);
            System.out.println(contactName + " exists? : " + file.exists());
            if (file.delete()) //delete the txt file
            {
                System.out.println("Conversation with " + contactName + " been deleted.");
            }
            else
            {
                System.out.println("Conversation with " + contactName + " isn't found.");
            }
        }
        else{
            System.out.println("Invalid contact number.");
        }
    }

    public void printChat(){
        Scanner scanner = new Scanner(System.in);
        displayContacts();
        System.out.println("Choose a contact from the list: ");
        int contactNum = inputRequest(); //getting user's contact index
        if(contactIsValid(contactNum)){ //check for validation

            String contactName = contacts.get(contactNum-1).getName();
                File file = new File("SMSApp Chats/" + contactName +".txt");
            try {
                BufferedReader fileBuffer = new BufferedReader(new FileReader(file));
                String line;
                while ((line = fileBuffer.readLine()) != null)
                    System.out.println(line);
                fileBuffer.close();
            }
            catch(Exception e)
            {
                System.out.println("No chat found");
            }
        }
    }

    public void searchChatPhrase(){
        Scanner scanner = new Scanner(System.in);
        String contactName;
        File file;
        Scanner txtScan;
        String str;
        System.out.println("Enter the phrase you're searching for: ");
        String phrase = scanner.nextLine(); //getting phrase from the user
        
        System.out.println();

        for (int i = 1; i <= contacts.size(); i++){
            contactName = contacts.get(i-1).getName();
            try{            
                file = new File("SMSApp Chats/" + contactName +".txt");  
                txtScan = new Scanner(file);
                while(txtScan.hasNextLine()){
                    str = txtScan.nextLine();
                    if(str.equals(phrase))
                        System.out.println("Found phrase in " + contactName + "'s chat");
                }
                txtScan.close();
            }
            catch(Exception e)
            {
                System.out.println();
            }
        }
    }


    public void printAllChats(){
        String contactName;
        File file;
        System.out.println();
        for (int i = 1; i <= contacts.size(); i++){
            System.out.println("\n"+i + ". " + contacts.get(i-1).getName() + ":");
            contactName = contacts.get(i-1).getName();
            try{
                file = new File("SMSApp Chats/" + contactName +".txt");
                BufferedReader fileBuffer = new BufferedReader(new FileReader(file));
                String line;
                while ((line = fileBuffer.readLine()) != null)
                    System.out.println(line);
            fileBuffer.close();
            }
            catch(Exception e)
            {
                System.out.println("No chat found");
            }
    }
    }

}
