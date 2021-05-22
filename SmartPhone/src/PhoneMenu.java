import java.util.ArrayList;
import java.util.Scanner;

public class PhoneMenu extends AppBasic implements AppTemplate {

    // Attributes:
    //---------------------------------------------------------------------------
    // private Book book;
    private SMSApp sms;
    private MediaList media;
    private Calendar cal;

    // constructors:
    //---------------------------------------------------------------------------
    //  public PhoneMenu() { // might not be used
    //     // book = new Book();
    // }

    public PhoneMenu(ArrayList<Media> media, Calendar calendar){//Book book){
        // this.book = book;
        this.sms = new SMSApp();
        this.media = new MediaList(media);
        this.cal = calendar;
    }

    @Override
    public void printMenu() {
        System.out.println("\nPlease Choose an action:");
        System.out.println("1 - Contacts");
        System.out.println("2 - SMS App");
        System.out.println("3 - Calender App");
        System.out.println("4 - Media Player");
        System.out.println("5 - Close Phone (exit)");
    }


    @Override
    public void appAction(Book book) {
        boolean exit = false;
        int input = 0;
        do {
            printMenu();
            input = inputRequest();
            switch(input) {
                case 1: // contacts app
                    try{
                        book.runMenu();    
                    }
                    catch(Exception e){
                        System.out.println("Caught: " + e);
                    }
                    break;
                case 2:
                    sms.appAction(book);
                    break;
                case 3:
                    cal.appAction(book);
                    break;
                case 4:
                    media.appAction(book);
                    break;
                case 5:
                    exit = exitApp();
            }
        } while (!exit);
       

    }


}
