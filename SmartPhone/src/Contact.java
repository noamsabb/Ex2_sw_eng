// Contact class which acts as a structure to save contacts in the phonebook. Consists of 2 string fields, name and number
// Has getters and setters as well as an override to the equals method


public class Contact {

    // variables:
    //---------------------------------------------------------------------------
    private String name = "---";
    private String number = "0";

    // constructors:
    //---------------------------------------------------------------------------
    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    // Getters & Setters:
    //---------------------------------------------------------------------------
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.name) && number.equals(contact.number);
    }

    public String toString(){
        return name + " - " + number;
    }
}
