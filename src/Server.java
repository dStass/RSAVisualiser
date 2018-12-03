import java.util.ArrayList;

public class Server {
	final String MESSAGE = "hello";
	private ArrayList<User> users;
	
	public Server() {
		// setup server
		// setup 1 user (TODO many users)
		
		users = new ArrayList<User>();
		
		// 1 user setup;
		//users.add(new User(users.size()));
		User user0 = new User(0); // primes will automatically be chosen by user
		users.add(user0);
		
	}
}
