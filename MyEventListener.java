package DiscordJavaBot.berko.productions.com;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MyEventListener extends ListenerAdapter{
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().equals("/ping")) {
			long time = System.currentTimeMillis();
			event.getChannel().sendMessage("pong").queue(response ->{
				response.editMessageFormat("pong: %d ms",  System.currentTimeMillis() - time).queue();
			});
		}
	}


}
