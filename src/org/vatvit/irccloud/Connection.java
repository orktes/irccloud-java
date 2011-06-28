package org.vatvit.irccloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.vatvit.irccloud.events.EventListener;

public class Connection {
	private String username;
	private String password;
	private boolean connected;
	private String session;
	private ArrayList<EventListener> eventListeners = new ArrayList<EventListener>();

	private String loginUrl = "https://irccloud.com/chat/login";
	private String streamUrl = "https://irccloud.com/chat/stream";

	public Connection(String username, String password) {
		this.username = username;
		this.password = password;
		this.connected = false;
	}

	public boolean login() {
		this.session = null;
		this.connected = false;
		String data = "";
		try {
			data = URLEncoder.encode("email", "UTF-8") + "="
					+ URLEncoder.encode(this.username, "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "="
					+ URLEncoder.encode(this.password, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URL loginURL = null;
		try {
			loginURL = new URL(loginUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpURLConnection loginConn = null;
		try {
			loginConn = (HttpURLConnection) loginURL.openConnection();

			loginConn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					loginConn.getOutputStream());
			wr.write(data);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					loginConn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				JSONObject response = new JSONObject(line);
				String session = response.getString("session");
				if (session != null) {
					this.session = session;
				}
			}

			wr.close();
			rd.close();

		} catch (IOException e) {
			try {
				if (loginConn.getResponseCode() == 400) {
					return false;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		if (this.session != null) {
			this.connected = true;
		}

		// Start reading stream.
		readStream();
		
		return this.connected;
	}

	private void readStream() {
		final Connection self = this;
		if (this.connected) {
			(new Thread() {
				public void run() {
					URL streamURL = null;
					try {
						streamURL = new URL(self.streamUrl);
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						URLConnection streamConn = streamURL.openConnection();
						streamConn.addRequestProperty("Cookie", "session="+self.session);
						streamConn.connect();
						BufferedReader rd = new BufferedReader(new InputStreamReader(
								streamConn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) {
							try {
								JSONObject response = new JSONObject(line);
								self.onEvent(response);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						rd.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}).start();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void addEventListener(EventListener listener) {
		this.eventListeners.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		this.eventListeners.remove(listener);
	}

	public ArrayList<EventListener> getEventListeners() {
		return eventListeners;
	}

	public void setEventListeners(ArrayList<EventListener> eventListeners) {
		this.eventListeners = eventListeners;
	}

	private void onEvent(JSONObject event) {
		for (EventListener listener : this.eventListeners) {
			listener.onEvent(event);
		}
	}
}
