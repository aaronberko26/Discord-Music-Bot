package DiscordJavaBot.berko.productions.com;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class WelcomeMessage extends ListenerAdapter{
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		System.out.println("Welcome Test");
		Member member = event.getMember();
		member.getUser().openPrivateChannel().queue(channel -> {
			channel.sendMessage("Welcome to my bot testing server").queue();
		});
	}
}
