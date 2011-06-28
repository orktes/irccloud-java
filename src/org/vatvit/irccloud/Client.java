package org.vatvit.irccloud;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.vatvit.irccloud.events.EventListener;
import org.vatvit.irccloud.events.ServersListener;


public class Client {
	private Connection connection;
	private String name;
	private String email;
	private ArrayList<Server> servers = new ArrayList<Server>();
	private ArrayList<ServersListener> serverListeners = new ArrayList<ServersListener>();
	
	
	public Client(String email, String password) {
		this.connection = new Connection(email, password);
		initListeners();
	}
	
	private void initListeners() {
		final Client self = this;
		this.connection.addEventListener("stat_user", new EventListener(){
			public void onEvent(JSONObject event) {
				//{"bid":-1,"eid":-1,"type":"stat_user","time":1309291645,"highlight":false,"id":2348,"name":"Jaakko Lukkari","email":"jaakko.lukkari@gmail.com","verified":true,"last_selected_bid":57154,"limits_name":"free","limits":{"networks":0,"passworded_servers":false,"zombiehours":48,"download_logs":false,"maxhistorydays":7},"num_connections":2,"num_active_connections":1,"highlights":[],"prefs":"{\"time-24hr\":true,\"time-seconds\":true}","join_date":1308811174,"autoaway":true}
				try {
					self.name = event.getString("name");
					self.email = event.getString("email");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.connection.addEventListener("makeserver", new EventListener(){
			public void onEvent(JSONObject event) {
				Server server = new Server(connection, event);
				servers.add(server);
				connectedToServer(server);
			}
		});
	}
	
	public boolean login() {
		return this.connection.login();
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Server> getServers() {
		return servers;
	}

	public void setServers(ArrayList<Server> servers) {
		this.servers = servers;
	}

	public void addServerListener(ServersListener listener) {
		serverListeners.add(listener);
	}
	
	public void removeServerListener(ServersListener listener) {
		serverListeners.remove(listener);
	}
	
	public ArrayList<ServersListener> getServerListeners() {
		return serverListeners;
	}

	public void setServerListeners(ArrayList<ServersListener> serverListeners) {
		this.serverListeners = serverListeners;
	}
	
	private void connectedToServer(Server server) {
		for(ServersListener listener : this.serverListeners) {
			listener.connectedToServer(server);
		}
	}
	private void disconnectedFromServer(Server server) {
		for(ServersListener listener : this.serverListeners) {
			listener.disconnectedFromServer(server);
		}
	}
	
	
}
