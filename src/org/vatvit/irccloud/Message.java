package org.vatvit.irccloud;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	private Connection connection;
	private String from;
	private String chan;
	private String msg;
	private int time;
	private int cid;

	public Message(Connection conn, JSONObject object) {
		this.connection = conn;
		try {
			this.from = object.getString("from");
			this.chan = object.getString("chan");
			this.msg = object.getString("msg");
			this.time = object.getInt("time");
			this.cid = object.getInt("cid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getChan() {
		return chan;
	}

	public void setChan(String chan) {
		this.chan = chan;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
	
	
}
