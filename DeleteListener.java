package DiscordJavaBot.berko.productions.com;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteListener extends ListenerAdapter{
	@Override 
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().toLowerCase().contains("fuck")) {
			event.getChannel().sendMessage("Swear words are not allowed in this server");		
			}
	}
}
