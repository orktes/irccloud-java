import org.json.JSONObject;
import org.vatvit.irccloud.Channel;
import org.vatvit.irccloud.Client;
import org.vatvit.irccloud.Connection;
import org.vatvit.irccloud.Server;
import org.vatvit.irccloud.events.EventListener;
import org.vatvit.irccloud.events.ServerListener;
import org.vatvit.irccloud.events.ServersListener;

public class Test {

	public static void main(String[] args) {
		Client client = new Client();
		client.addServerListener(new ServersListener(){

			@Override
			public void connectedToServer(Server server) {
				System.out.println("Yhdistetty palvelimelle "+server.getHostname());
				server.addServerListener(new ServerListener(){

					@Override
					public void newChannel(Channel channel) {
						if(channel.getName().equalsIgnoreCase("#testi")) {
							channel.sendMessage("Testi");
						}
						
					}

					@Override
					public void channelRemoved(Channel channel) {
						// TODO Auto-generated method stub
						
					}
					
				});
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
		
		if(client.login("address@email.com", "password")) {
			System.out.println("Login successful.");
		} else {
			System.out.println("Login failed.");
		}
		
		
		
	}

}
