import java.util.ArrayList;
import java.util.Scanner;
// TODO: change all nextInts to input request
public class MediaList extends AppBasic implements AppTemplate {
    // variables:
    //---------------------------------------------------------------------------
    private ArrayList<Media> MediaList = new ArrayList<Media>(); // Create an ArrayList object
    // constructors:
    //---------------------------------------------------------------------------
    public MediaList(ArrayList<Media> c) {
        this.MediaList = c;
    }

    public MediaList() {
    }

    // Adds a Media file to the arrayList
    public void addMedia() {
        int TypeInput;
        Scanner scanner = new Scanner(System.in); // scanner object to scan input from the user.
        System.out.println("\nPlease enter 0 for song and 1 for video");
        TypeInput=scanner.nextInt(); //getting user's input
        while (TypeInput!=1 && TypeInput!=0){//checking if it valid answer
            scanner = new Scanner(System.in); // scanner object to scan input from the user.
            System.out.println("\nPlease enter a valid input");
            System.out.println("\nPlease enter 0 for song and 1 for video");
            TypeInput=scanner.nextInt(); //getting user's new input
        }
        if (TypeInput==0){
            System.out.println("Enter the song's name: ");
            String SongName = scanner.next(); //getting the song name
            System.out.println("Enter the song's length: ");
            String SongL = scanner.next();//getting the song length
            while (!(SongL.matches("[0-9]+"))) { //Check user entered only numbers
                System.out.println("Enter numbers only");
                System.out.println("Enter the song's length: ");
                SongL = scanner.next();
            }
            //creating the song
            String Stype = "song";
            Media Nsong = new Media(SongName,Stype, SongL);
            MediaList.add(Nsong);
            System.out.println("Song added");
        }
        else{//if hee crate a video
            System.out.println("Enter the Video's name: ");
            String VideoName = scanner.next(); //getting the song name
            System.out.println("Enter the Video's length: ");
            String VideoL = scanner.next();//getting the video length
            while (!(VideoL.matches("[0-9]+"))) { //Check user entered only numbers
                System.out.println("Enter numbers only");
                System.out.println("Enter the Video's length: ");
                VideoL = scanner.next();
            }
            //creating the video
            String Stype = "video";
            Media Nsong = new Media(VideoName,Stype, VideoL);
            MediaList.add(Nsong);
            System.out.println("Video added");
        }
    }

    //play a specific media
    public void PlayMedia(String name){
        boolean MediaC = false;
        int i;
        for (i = 0; i < MediaList.size(); i++) {//looking for the media name
            if (MediaList.get(i).getName().equals(name)) {
                MediaC = true;
                break;
            }
        }
        if (!MediaC) {//if it dosent exsist
            System.out.println("\nThis Media doesn't exist");
        }
        else{//playing the media
            if (MediaList.get(i).getType().equals("song"))
                System.out.println("\nThe Song: "+ MediaList.get(i).getName() + " is now playing for: " + MediaList.get(i).getLength() + " minutes");
            else
                System.out.println("\nThe Video: "+ MediaList.get(i).getName() + " is now playing for: " + MediaList.get(i).getLength() + " minutes");
        }
    }

    //play all media
    public void PlayAll (){
        int i;
        for (i = 0; i < MediaList.size(); i++) {
            if (MediaList.get(i).getType().equals("song"))
                System.out.println("\nThe Song: "+ MediaList.get(i).getName() + " is now playing for: " + MediaList.get(i).getLength() + " minutes");
            else
                System.out.println("\nThe Video: "+ MediaList.get(i).getName() + " is now playing for: " + MediaList.get(i).getLength() + " minutes");
        }
    }
    
    @Override
    public void appAction(Book book){
        boolean exit = false;
        int input;
        System.out.println("welcome to the media app");
        do{
            Scanner scanner = new Scanner(System.in); // scanner object to scan input from the user.
            printMenu();
            input = scanner.nextInt(); //getting user's input
            scanner.nextLine(); // consume '\n'
            switch(input){
                case 1:
                    addMedia();
                    scanner.nextLine();
                    break;
                case 2:
                    System.out.println("\nPlease enter a media file name:");
                    String File = scanner.nextLine();
                    PlayMedia(File);
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.println("\nPlaying all media:\n");
                    PlayAll();
                    scanner.nextLine();
                    break;
                case 4:
                    exit = exitApp();
                    break;
            }
        }while (!exit);
    }

    @Override
    public void printMenu() {
        System.out.println("\nPlease Choose an action:");
        System.out.println("1 - To add new media");
        System.out.println("2 - To play an existing media");
        System.out.println("3 - To play all media");
        System.out.println("4 - To go back to the main menu");
    }
}
