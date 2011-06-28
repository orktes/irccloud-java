import org.json.JSONObject;
import org.vatvit.irccloud.Connection;
import org.vatvit.irccloud.events.EventListener;

public class Test {

	public static void main(String[] args) {
		Connection ircConn = new Connection("email@address.com", "password");
		
		ircConn.addEventListener(new EventListener(){
			@Override
			public void onEvent(JSONObject event) {
				System.out.println("Event: "+event.toString());
			}	
		});
		
		if(ircConn.login()) {
			System.out.println("Login successful. Session: "+ircConn.getSession());
		} else {
			System.out.println("Login failed.");
		}
		
	}

}
