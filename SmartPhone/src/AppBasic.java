import java.util.Scanner;

abstract class AppBasic{

    // This method is used when we want to request a number for any menu. It makes sure the user enters a number
    public int inputRequest() {
        Scanner scanner = new Scanner(System.in); // scanner object to scan input from the user.
        int input = 0;
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
            //scanner.close();
        }
        scanner.nextLine(); // consume '\n'
        //scanner.close();
        return input;
    }
    // try to use one close see if it fucks shit up


    public boolean exitApp() {
        System.out.println("\nAre you sure you want to exit? (1 - Yes, 2+ - No)");
        int a = inputRequest();
        switch(a) {
            case 1: // contacts app
                return true;   
            case 2:
                return false;
        }
        return false;
    }

}