package DiscordJavaBot.berko.productions.com;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InviteListener extends ListenerAdapter{
	
	String url = "https://discord.gg/kdY7PJqEnB";
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().equals("/invite")) {
			event.getChannel().sendMessage(String.format(url,event.getJDA().getSelfUser().getId())).queue();
		}
	}
}
