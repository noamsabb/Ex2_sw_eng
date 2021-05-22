import java.util.*;
import java.util.Collections;

public class Calendar extends AppBasic implements AppTemplate {


   private LinkedList<Event>[] Month; //Create an array of LinkedList of Events
   private ArrayList<Contact> contacts = new ArrayList<>(); // Create an ArrayList of contact object

    //Set Calendar Month array from main
    public Calendar(LinkedList<Event>[] M){
        this.Month = M;
    }

    private void updateContacts(Book book)
    {
        int existCheck;
        this.contacts = book.getContacts(); //Update Book of contact from main
        for (LinkedList<Event> events : Month) {
            for (int j = 0; j < events.size(); j++) {
                if (events.get(j).getContact() != null) {
                    existCheck=0;
                    for (Contact contact : contacts) //Search and delete events with contact that no longer exist in book
                    {
                        if (contact == events.get(j).getContact()) {
                            existCheck=1;
                            break;
                        }
                    }
                    if(existCheck==0)
                        events.remove(j);
                }
            }
        }

    }

    public void printMenu()
    {
        System.out.println("\nPlease Choose an action:");
        System.out.println("1 - Create a new Event/Meeting");
        System.out.println("2 - Delete an existing Event");
        System.out.println("3 - Print all events of a specific day");
        System.out.println("4 - Print all meetings with a specific Contact");
        System.out.println("5 - Check for events Collision");
        System.out.println("6 - Print all events of the month");
        System.out.println("7 - Exit to phone menu");
    }
    public void appAction(Book book)
    {
        updateContacts(book);
        int input = 0;
        boolean exit = false;

        do{
            printMenu();
            input = inputRequest();

            switch (input) {
                case 1 -> newEvent(book);
                case 2 -> DeleteEvent();
                case 3 -> PrintDaySchedule(book);
                case 4 -> PrintContactSchedule(book);
                case 5 -> CheckForCollision();
                case 6 -> PrintMonthSchedule(book);
                case 7 -> exit = exitApp();
            }
    }while (!exit);
    }

    public void newEvent(Book book) //Method to create of new events and meeting
    {

        java.util.Calendar c = java.util.Calendar.getInstance();
        Scanner scanner = new Scanner(System.in);

        //Get event info from user
        int day = askForDay();
        int hour = askForHour();
        int minute = askForMinute();

        //Set date Object with info
        c.set(2021,4,day,hour,minute);
        Date d = c.getTime();

        System.out.println("Enter length (1-60):");
        int length = inputRequest();
        while(length < 1 || length > 60)
        {
            System.out.println("No such length, try again !");
            length = inputRequest();
        }
        int length2 = length;

        //Set type of event (Meeting of simple event
        System.out.println("Meeting (0) or Event (1) ?");
        int choice = inputRequest();
        Contact contact = null;
        String details = null;

        if (hour == 23 && d.getMinutes() + length > 59)
        {
            length = 59-d.getMinutes();
        }

        //If meeting ask for contact info and create new event to add to Month array on right place
        if (choice == 0)
        {
            System.out.println("Enter contact name:");
            String name = scanner.nextLine();
            contact = book.searchContact2(name);
            if (contact == null)
            {return;}
            Event e = new Event(choice,d, length, contact);
            Month[day].add(e);
        }
        //If simple event ask for details info and create and add new event to Month array on right place
        if(choice ==1)
        {
            System.out.println("Enter Event details:");
            details = scanner.nextLine();
            Event e = new Event(choice,d, length, details);
            Month[day].add(e);
        }


        //Check if the event Start and stop on the same day, if not call EventSplit method
        if (hour == 23 && d.getMinutes() + length2 > 59 && day !=30)
        {
            EventSplit(choice, d,length2,details, contact);
        }

        //After adding new event to specific day LinkedList, sort the event by hour
        Collections.sort(Month[day]);
        System.out.println("New Event created !");
    }

    public void DeleteEvent() //Method to delete a specific event
    {
        //Get event info from user
        int day = askForDay();
        int hour = askForHour();
        int minute = askForMinute();


        boolean found = false;
        for (int i = 0; i<Month[day].size(); i++)
        {
            if (Month[day].get(i).getHour() == hour && Month[day].get(i).getMinute() == minute)
            {
                Month[day].remove(i);
                found = true;
                break;
            }
        }

        //Check if the event got removed or didn't existed
        if (!found) {
            System.out.println("Event doesn't exist");
        } else
            System.out.println("Event removed");
    }

    public void PrintDaySchedule(Book book) //Method to print all events of specific day
    {
        //Get info from user
        int day = askForDay();

        //Check if there is events on this day
        if (Month[day].size()==0)
        {
            System.out.println("No events found on this day");
        }

        //Run on each event of that day and print infos
        if (Month[day].size()!=0)
        {
            System.out.println(day+" May 2021");
            for (int i = 0; i<Month[day].size(); i++)
            {
                System.out.println();
                String EndTime = EndCaculator(Month[day].get(i)); //Calculating event ending time
                String addZero = "";
                if (Month[day].get(i).getMinute() < 10)
                    addZero = "0";
                System.out.println(Month[day].get(i).getHour()+":"+addZero +Month[day].get(i).getMinute()+" - " +EndTime);
                if (Month[day].get(i).getType()==0) //If meeting, print contact Name
                {
                    System.out.print("Meeting with ");
                    Month[day].get(i).printContact(book);
                }
                if (Month[day].get(i).getType()==1) //If simple event, print his details
                {
                    System.out.println(Month[day].get(i).getDetails());
                }
            }
        }
    }

    public void PrintContactSchedule(Book book) //Method to print every exxisting events with a specific contact
    {   Scanner scanner = new Scanner(System.in);
    //Get contact info from User
        System.out.println("Enter contact name:");
        String name = scanner.nextLine();
    //Check if input contact exist in book
        Contact contact = book.searchContact2(name);
        if (contact == null){return;}

        int counter =0;
        System.out.println("Meetings with "+contact.getName() + " :");

        //Check over the entire month for meetings with the inputed contact
        for (int i = 0; i< Month.length; i++)
        {
            for (int j = 0; j<Month[i].size(); j++)
            {
                if (Month[i].get(j).getContact() == contact)
                {   System.out.println(i+" May 2021");
                    String EndTime = EndCaculator(Month[i].get(j));
                    String addZero = "";
                    if (Month[i].get(j).getMinute() < 10)
                        addZero = "0";
                    System.out.println(Month[i].get(j).getHour()+":"+addZero +Month[i].get(j).getMinute()+" - " + EndTime);
                    counter++;
                }
            }
        }
        //Print if no meeting founded
        if (counter == 0){System.out.println("No meetings founded !");}
    }

    private void CheckForCollision() //Method to check for time collision of events
    {
        int counter= 0;
        for(int i =0; i<Month.length; i++)
        {
            for (int j = 0; j < Month[i].size()-1; j++)
            {
                int EndHour= Month[i].get(j).getHour();
                int EndMin=Month[i].get(j).getMinute();
                int lenght= Month[i].get(j).getLength();
                if (EndMin+lenght>=60){
                    EndHour++;
                    EndMin=EndMin+lenght-60;
                }
                else
                {
                    EndMin += lenght;
                }
                int End2 = Month[i].get(j+1).getMinute()+Month[i].get(j+1).getLength();
                if(EndHour==Month[i].get(j+1).getHour() && Month[i].get(j+1).getMinute()<EndMin && EndMin<End2)
                {
                    counter++;
                    Month[i].remove(j+1);

                }
            }
        }
        if (counter == 0)
        {
            System.out.println("No collision detected");
        }
        else{System.out.println(counter +" collisions detected and handled !");}
    }

    public void PrintMonthSchedule(Book book) //Method to print every events of the month
    {
        for (int i = 0; i<Month.length; i++)
        {
            if (Month[i].size()!=0)
            {
                System.out.println(i+" May 2021");
                for (int j = 0; j<Month[i].size(); j++)
                {
                    System.out.println();
                    String EndTime = EndCaculator(Month[i].get(j));
                    String addZero = "";
                    if (Month[i].get(j).getMinute() < 10)
                        addZero = "0";
                    System.out.println(Month[i].get(j).getHour()+":"+addZero+Month[i].get(j).getMinute()+" - " +EndTime);
                    if (Month[i].get(j).getType()==0)
                    {
                        System.out.print("Meeting with ");
                        Month[i].get(j).printContact(book);
                    }
                    if (Month[i].get(j).getType()==1)
                    {

                        System.out.println(Month[i].get(j).getDetails());

                    }
                }
            }
            if (Month[i].size()!=0){System.out.println();}
        }
    }

    private String EndCaculator(Event event) //Method to calculate the ending time of an event
    {
        int EndMin,lenght,EndHour;
        EndHour= event.getHour();
        EndMin=event.getMinute();
        lenght= event.getLength();
        if (EndMin+lenght>=60){
            EndHour++;
            EndMin=EndMin+lenght-60;
        }
        else
            {
                EndMin += lenght;
            }
        String EndHours, EndMins;
        String addZero = "";
        if (EndMin < 10)
            addZero = "0";
        EndHours = Integer.toString(EndHour);
        EndMins = addZero + Integer.toString(EndMin);
        String EndTime;
        EndTime=EndHours+':'+EndMins;
        return EndTime;
    }

    //Method to split an event that start on a specific day and ending on next day
    private void EventSplit(int choice, Date d, int length, String details, Contact contact)
    {
        //If the event end after 23:59 creat a new event on the next day with length rest
        if (d.getMinutes() + length > 59 )
        {
            int newLength = (d.getMinutes() + length ) - 59;
            int newDay = d.getDate()+1;
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.set(2021,4,newDay,0,0);
            Date d1 = c.getTime();
            Event e = new Event(choice, d1, newLength, contact, details );
            Month[newDay].add(e);
            Collections.sort(Month[newDay]);
        }
    }

    private int askForDay() //Method to get day info from user
    {
        System.out.println("Enter day (1-30):");
        int day = inputRequest();
        while(day < 1 || day > 30)
        {
            System.out.println("No such day, try again !");
            day = inputRequest();
        }
        return day;
    }

    private int askForHour() //Method to get hour info from user
    {
        System.out.println("Enter Hour (00-23):");
        int hour = inputRequest();
        while(hour < 0 || hour > 23)
        {
            System.out.println("No such hour, try again !");
            hour = inputRequest();
        }
        return hour;
    }

    private int askForMinute() //Method to get minute info from user
    {
        System.out.println("Enter Minute (00-59):");
        int minute = inputRequest();
        while(minute < 0 || minute > 59)
        {
            System.out.println("No such minute, try again !");
            minute= inputRequest();
        }
        return minute;
    }

}


