//import javafx.application.Application; 

public class Application {
	public static String MESSAGE = "This is a secret message! ys$0._mPPz!~``\\\\"; // ys$0._mPPz!~``
	
	//public static String MESSAGE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
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
