package DiscordJavaBot.berko.productions.com;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FilterMessage extends ListenerAdapter{
	public static boolean allowed = false;
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e){
	        if(FilterOnOff.filterOn) {
	            if (e.getMessage().getContentRaw().equalsIgnoreCase("$filtermessage") && !allowed) {
	                e.getChannel().sendMessage("Filter Response Has Been Enabled.").queue();
	                System.out.println("Enabled");
	                allowed = true;
	            } else if (e.getMessage().getContentRaw().equalsIgnoreCase("$filtermessage") && allowed) {
	                e.getChannel().sendMessage("Filter Response Has Been Disabled.").queue();
	                System.out.println("Disabled");
	                allowed = false;
	            }
	        }else if(e.getMessage().getContentRaw().equalsIgnoreCase("$filtermessage") && !FilterOnOff.filterOn){
	            e.getChannel().sendMessage("You can't toggle filter response while the filter is off. To turn the filter on, run $togglefilter").queue();
	        }
	 
	 
	    }

}
