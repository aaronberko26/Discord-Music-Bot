package DiscordJavaBot.berko.productions.com;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;


public class VoiceChannelListener extends ListenerAdapter{
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		
		if(event.getMessage().getContentRaw().startsWith("/join")) {
			//Creating a guild and getting the voice channel of the guild member
			Guild guild = event.getGuild();
			VoiceChannel channel = guild.getVoiceChannelsByName("music",true).get(0);
			
			//Retrieve the AudioManager
			AudioManager audioManager = guild.getAudioManager();
			
			AudioPlayerManager  playerManager = new DefaultAudioPlayerManager();
			AudioSourceManagers.registerRemoteSources(playerManager);
			
			AudioPlayer audioPlayer = playerManager.createPlayer();
			
			audioManager.setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
			
			
			//Open the audio connection
			audioManager.openAudioConnection(channel);
		}
		
		if(event.getAuthor().isBot()) {
			return;
		}
		
		if(event.getMessage().getContentRaw().equals("/leave")) {
			Guild leaveGuild = event.getGuild();
			//leaveGuild.getAudioManager().setSendingHandler(null);
			leaveGuild.getAudioManager().closeAudioConnection();
		}
		
	}
	
	
}
