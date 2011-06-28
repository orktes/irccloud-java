package org.vatvit.irccloud.events;

import org.vatvit.irccloud.Server;

public interface ServersListener {
	public void connectedToServer(Server server);
	public void disconnectedFromServer(Server server);
}
