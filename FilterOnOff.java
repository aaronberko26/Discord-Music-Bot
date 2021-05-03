package DiscordJavaBot.berko.productions.com;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FilterOnOff extends ListenerAdapter{
	public static boolean filterOn = true;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		 if (event.getMessage().getContentRaw().equalsIgnoreCase("/togglefilter") && filterOn){
	            filterOn = false;
	            event.getChannel().sendMessage("The Curse-Filter has been disabled by " + event.getMember().getUser().getName()).queue();
	        }else if(event.getMessage().getContentRaw().equalsIgnoreCase("/togglefilter") && !filterOn){
	            filterOn = true;
	            event.getChannel().sendMessage("The Curse-Filter has been enabled by " + event.getMember().getUser().getName()).queue();
	        }
	}
}
