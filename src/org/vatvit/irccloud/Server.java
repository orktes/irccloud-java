package org.vatvit.irccloud;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.vatvit.irccloud.events.EventListener;

public class Server {
	private Connection connection;
	private String name;
	private String nick;
	private String nickservNick;
	private String nickservPass;
	private String realname;
	private String hostname;
	private int port;
	private String away;
	private boolean disconnected;
	private boolean ssl;
	private String serverPass;
	private int cid;
	
	private ArrayList<Channel> channels = new ArrayList<Channel>();
	private ArrayList<Private> privates = new ArrayList<Private>();
	

	public Server(Connection conn, JSONObject object) {
		this.connection = conn;
		try {
			this.cid = object.getInt("cid");
			this.name = object.getString("name");
			this.nick = object.getString("nick");
			this.nickservNick = object.getString("nickserv_nick");
			this.realname = object.getString("realname");
			this.hostname = object.getString("hostname");
			this.port = object.getInt("port");
			this.away = object.getString("away");
			this.disconnected = object.getBoolean("disconnected");
			this.ssl = object.getBoolean("ssl");
			this.serverPass = object.getString("server_pass");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initListeners();
	}
	
	private void initListeners() {
		this.connection.addEventListener("channel_init", new EventListener(){
			public void onEvent(JSONObject event) {
				try {
					if(event.getInt("cid") == cid) {
						Channel channel = new Channel(connection, event);
						channels.add(channel);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		this.connection.addEventListener("makebuffer", new EventListener(){
			public void onEvent(JSONObject event) {
				
			}
		});
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

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNickservNick() {
		return nickservNick;
	}

	public void setNickservNick(String nickservNick) {
		this.nickservNick = nickservNick;
	}

	public String getNickservPass() {
		return nickservPass;
	}

	public void setNickservPass(String nickservPass) {
		this.nickservPass = nickservPass;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public boolean isDisconnected() {
		return disconnected;
	}

	public void setDisconnected(boolean disconnected) {
		this.disconnected = disconnected;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String getServerPass() {
		return serverPass;
	}

	public void setServerPass(String serverPass) {
		this.serverPass = serverPass;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public ArrayList<Channel> getChannels() {
		return channels;
	}

	public void setChannels(ArrayList<Channel> channels) {
		this.channels = channels;
	}

	public ArrayList<Private> getPrivates() {
		return privates;
	}

	public void setPrivates(ArrayList<Private> privates) {
		this.privates = privates;
	}
	
	
	
}
