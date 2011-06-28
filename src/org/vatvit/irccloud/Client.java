package org.vatvit.irccloud;


public class Client {
	public Connection connection;
	
	public Client(String email, String password) {
		this.connection = new Connection(email, password);
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
	
}
