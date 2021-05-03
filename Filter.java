package DiscordJavaBot.berko.productions.com;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Filter extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] BAD_WORDS = {"fuck", "nigger", "nigga" , "shit", "cum", "coon", "chink", "bitch", "shit", "dick head", "bastard", "cunt", "fucking", "ass", "dick", "hell", "whore", "slut", "forskin hunter"};
		if(FilterOnOff.filterOn) {
			String[] message = event.getMessage().getContentRaw().split(" ");
			for(int j = 0; j < message.length; j++) {
				boolean bad = false;

				for(int i = 0; i < BAD_WORDS.length; i++) {
					if(message[j].equalsIgnoreCase(BAD_WORDS[i])) {
						event.getMessage().delete().queue();
						bad = true;
					}
					if(FilterMessage.allowed) {
						event.getChannel().sendMessage("Watch your lip " + event.getMember().getUser().getName()).queue();
					}
				}
				System.out.println(message[j] + " " + bad);
			}			
		}	
	}
}
