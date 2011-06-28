import java.io.IOException;

import org.json.JSONObject;
import org.vatvit.irccloud.Connection;
import org.vatvit.irccloud.events.EventListener;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection ircConn = new Connection("email@address.com", "password");
		
		ircConn.addEventListener(new EventListener(){
			public void onEvent(JSONObject event) {
				System.out.println("Event: "+event.toString());
			}	
		});
		
		if(ircConn.login()) {
			System.out.println("Kirjautuminen onnistui "+ircConn.getSession());
		} else {
			System.out.println("Kirjautuminen ei onnistu");
		}
		
	}

}
