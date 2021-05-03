package DiscordJavaBot.berko.productions.com;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TimeListeners extends ListenerAdapter{
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().equals("/time")){
			event.getChannel().sendMessage("");
		}
	}
}
