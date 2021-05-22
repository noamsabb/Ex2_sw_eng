import java.util.ArrayList; // import the ArrayList class

public class Media {
    // variables:
    //---------------------------------------------------------------------------
    private String NameM = "---";
    private String TypeM = "---";
    private String LengthM = "---";

    // constructors:
    //---------------------------------------------------------------------------
    public Media(String name, String type,String length) {
        this.NameM = name;
        this.TypeM = type;
        this.LengthM=length;
    }
    // Getters & Setters:
    //---------------------------------------------------------------------------
    public String getName() {
        return NameM;
    }
    public String getType() {
        return TypeM;
    }
    public String getLength() {
        return LengthM;
    }

    public void setName(String name) {
        this.NameM = name;
    }

    public void setType(String type) {
        this.TypeM = type;
    }

    public void setLength(String length) {
        this.LengthM = length;
    }

}



