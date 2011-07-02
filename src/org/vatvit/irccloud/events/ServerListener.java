package org.vatvit.irccloud.events;

import org.vatvit.irccloud.Channel;

public interface ServerListener {
	public void newChannel(Channel channel);

	public void channelRemoved(Channel channel);
}
