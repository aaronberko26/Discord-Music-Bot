package DiscordJavaBot.berko.productions.com;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.entities.GuildImpl;



public class Bot extends ListenerAdapter implements EventListener{
	public static AudioPlayer player;
	
	public static void main(String[] args) throws LoginException, InterruptedException{
		List<GatewayIntent> gatewayIntents = new ArrayList<>();
		gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
		
		//JDABuilder creation
		JDABuilder jdaBuilder = JDABuilder.createDefault("Nzg1NzA4ODU5OTQ2ODI3Nzc3.X87yZQ.y_4F9HaUpuDWZZqxvewC8WTiFX4");
		
		jdaBuilder.enableIntents(gatewayIntents);
		
		//Creating PingListener and adding it to the jdaBuilder
		MyEventListener event = new MyEventListener();
		jdaBuilder.addEventListeners(event);	
		
		InviteListener invite = new InviteListener();
		jdaBuilder.addEventListeners(invite);
		
		jdaBuilder.addEventListeners(new WelcomeMessage());
		
		jdaBuilder.addEventListeners(new TimeListeners());	
		
		jdaBuilder.addEventListeners(new Filter());
		
		jdaBuilder.addEventListeners(new FilterMessage());
		
		jdaBuilder.addEventListeners(new FilterOnOff());
		
		AudioPlayerManager  playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		
	//	jdaBuilder.addEventListeners(new VoiceChannelListener());
		
		jdaBuilder.addEventListeners(new PlayerManager());
		
		//AudioManager audioManager = guild.get
		
		
		//Actual bot creation 
		JDA jda = jdaBuilder.build();
		jda.awaitReady();
		
		//Setting online status 
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("Your every move"));
	}
	
	

		
}
