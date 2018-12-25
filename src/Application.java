//import javafx.application.Application; 

public class Application {
	final static String MESSAGE = "This is a secret message!"; // ys$0._mPPz!~``\\
	/*
	 * 
	 * 
	 * 
	 */
	
    public static void main(String[] args)  {
    	System.out.println("start application");
    	Server s = new Server();
    	s.sendMessageToAllUsers(MESSAGE);
    }
	
}
