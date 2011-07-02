import org.json.JSONObject;
import org.vatvit.irccloud.Client;
import org.vatvit.irccloud.Connection;
import org.vatvit.irccloud.Server;
import org.vatvit.irccloud.events.EventListener;
import org.vatvit.irccloud.events.ServersListener;

public class Test {

	public static void main(String[] args) {
		Client client = new Client();
		client.addServerListener(new ServersListener(){

			@Override
			public void connectedToServer(Server server) {
				System.out.println("Yhdistetty palvelimelle "+server.getHostname());
				
			}

			@Override
			public void disconnectedFromServer(Server server) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void update() {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		if(client.login("email@address.com", "password")) {
			System.out.println("Login successful.");
		} else {
			System.out.println("Login failed.");
		}
		
		
		
	}

}
