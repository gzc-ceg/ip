import java.util.Scanner;

public class Bruno {
    public static void main(String[] args) {
        String logo =
                " ____  ____  _   _ _   _  ____  \n" +
                        "| __ )|  _ \\| | | | \\ | |  _  |\n" +
                        "|  _ \\| |_) | | | |  \\| | | | |\n" +
                        "| |_) |  _ <| |_| | |\\  | |_| | \n" +
                        "|____/|_| \\_\\\\___/|_| \\_| ____| \n";
        System.out.println("    Hello from\n" + logo);
        System.out.println("    Hello! I am Bruno, your personl assistant!");
        System.out.println("    What can I do for you?");

        Scanner scanner=new Scanner(System.in);
        boolean isRunning=true;
        while(isRunning){
            String userInput = scanner.nextLine().trim();
            if(userInput.equalsIgnoreCase("bye")){
                System.out.println("    Bye! Hope to see you again!");
                System.out.println("    Remember, Bruno is always here for you!");
                break;
            }else if(userInput.isEmpty()){
                System.out.println("    Please say something, I am listening...");
            }else{
                System.out.println("    "+userInput);
            }
        }
        scanner.close();
    }
}