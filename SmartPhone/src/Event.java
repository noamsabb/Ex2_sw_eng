import java.util.Date;

public class Event implements Comparable<Event>{

    // variables:
    //---------------------------------------------------------------------------
    private Date date;
    private int length = 0;
    private String details = null;
    private Contact contact = null;
    private int type = 0;

    //Constructor of simple events (no meeting)
    public Event(int type, Date date, int length, String details) {
        this.date=date;
        this.length = length;
        this.details = details;
        this.type = type;
    }

    //Constructor of Meetings
    public Event(int type, Date date, int length, Contact contact)
    {
        this.date = date;
        this.length = length;
        this.contact = contact;
        this.type = type;
    }

    //Constructor of splited events
    public Event(int type, Date date, int length, Contact contact, String details)
    {
        this.date = date;
        this.length = length;
        this.contact = contact;
        this.type = type;
        this.details = details;
    }


    public Date getDate(){return date;};
    public int getLength(){return length;}
    public int getHour(){return date.getHours();}
    public int getMinute(){return date.getMinutes();}
    public int getType(){return  type; }
    public String getDetails(){return details;}
    public void printContact(Book book){book.printSearch(this.contact);}
    public Contact getContact(){return contact;}
    
    @Override public int compareTo(Event e)
    {
        return date.compareTo(e.getDate());
    }

}