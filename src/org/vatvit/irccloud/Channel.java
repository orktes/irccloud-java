package org.vatvit.irccloud;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.vatvit.irccloud.events.EventListener;
import org.vatvit.irccloud.events.ChannelListener;

public class Channel {
	private Connection connection;
	
	private ArrayList<ChannelListener> listeners = new ArrayList<ChannelListener>();
	
	private String name;
	private String topic;
	private String topicAuthor;
	private int topicTime;
	private int cid;
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	public Channel(Connection conn, JSONObject object) {
		this.connection = conn;
		
		//{"bid":58363,"eid":-1,"type":"channel_init","time":
		//1309465365,"highlight":false,"cid":7464,"chan":"#test",
		//"members":[{"nick":"orktes_","mode":"o","realname":"","away":false,"ircserver":"","user":"","usermask":""}],
		//"topic":{"topic_text":null,"topic_time":-1,"topic_author":null},"mode":"n",
		//"ops":{"add":[{"mode":"n","param":""}],"remove":[]}}
		try {
			this.cid = object.getInt("cid");
			this.name = object.getString("chan");
			JSONObject topicObj = object.getJSONObject("topic");
			if(!topicObj.isNull("topic_text")) {
				this.topic = topicObj.getString("topic_text");
				this.topicAuthor = topicObj.getString("topic_author");
				this.topicTime = topicObj.getInt("topic_time");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initListeners();
	}
	private void initListeners() {
		this.connection.addEventListener("buffer_msg", new EventListener(){
			@Override
			public void onEvent(JSONObject event) {
				int ecid = 0;
				try {
					ecid = event.getInt("cid");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String chan = null;
				try {
					chan = event.getString("chan");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(ecid != cid || !name.equalsIgnoreCase(chan)) {
					return;
				}
				
				Message message = new Message(connection, event);
				messages.add(message);
				newMessage(message);
				
			}
			
		});
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTopicAuthor() {
		return topicAuthor;
	}

	public void setTopicAuthor(String topicAuthor) {
		this.topicAuthor = topicAuthor;
	}

	public int getTopicTime() {
		return topicTime;
	}

	public void setTopicTime(int topicTime) {
		this.topicTime = topicTime;
	}

	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	@Override
	public String toString() {
		return name;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public void addChannelListener(ChannelListener listener) {
		listeners.add(listener);
	}
	
	public void removeChannelListener(ChannelListener listener) {
		listeners.remove(listener);
	}
	
	public ArrayList<ChannelListener> getChannelListeners() {
		return listeners;
	}

	public void setChannelListeners(ArrayList<ChannelListener> serverListeners) {
		listeners = serverListeners;
	}
	
	private void newMessage(Message message) {
		for(ChannelListener listener : this.listeners) {
			listener.newMessage(message);
		}
	}
	
	public void sendMessage(String message) {
		ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
		values.add(new NameValuePair("cid", this.cid+""));
		values.add(new NameValuePair("msg", message));
		values.add(new NameValuePair("to", this.name));
		this.connection.postData("say", values);
	}
	
	
}
