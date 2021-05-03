package DiscordJavaBot.berko.productions.com;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class GuildMusicManager {
	public final AudioPlayer audioPlayer;
	public final TrackScheduler trackScheduler;
	public final AudioPlayerSendHandler handler;
	
	//Music manager for the guild 
	public GuildMusicManager(AudioPlayerManager manager) {
		this.audioPlayer = manager.createPlayer();
		this.trackScheduler = new TrackScheduler(this.audioPlayer);
		this.audioPlayer.addListener(this.trackScheduler);
		this.handler = new AudioPlayerSendHandler(this.audioPlayer);
	}

	public AudioSendHandler getSendHandler() {
		// TODO Auto-generated method stub
		return handler;
	}
	
	
}
