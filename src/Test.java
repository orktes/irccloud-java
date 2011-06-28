import org.json.JSONObject;
import org.vatvit.irccloud.Client;
import org.vatvit.irccloud.Connection;
import org.vatvit.irccloud.Server;
import org.vatvit.irccloud.events.EventListener;

public class Test {

	public static void main(String[] args) {
		Client client = new Client("email@address.com", "paasword3");
		
		if(client.login()) {
			System.out.println("Login successful.");
		} else {
			System.out.println("Login failed.");
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(client.getServers().size());
		for(Server server : client.getServers()) {
			System.out.println(server.getName());
		}
		
	}

}
