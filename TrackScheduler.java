package DiscordJavaBot.berko.productions.com;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class TrackScheduler extends AudioEventAdapter{
	private final AudioPlayer player;
	final Queue<AudioTrack> queue;

	
	public TrackScheduler(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedList<>();
	}
	
	public void queue(AudioTrack track) {
		//SAudioInfo info = new AudioInfo(track);
		if(!player.startTrack(track, true)) {
			queue.offer(track);
		}
	}
	
	//Still needs to be finished 
	public void onPlayerPause(AudioPlayer player) {
		if(player.isPaused() == true) {
			System.out.println("The player was paused");
		}
	}
	
	public void nextTrack(AudioTrack track) {
		player.playTrack(track);
	}
	
	/*public void onTrackStart(AudioPlayer player, AudioTrack track) {
		AudioInfo info = queue.element();
		VoiceChannel vc = info.getVoiceState().getChannel(); 
		if(vc == null) {
			player.stopTrack();
		}else {
			info.getAuthor().getGuild().getAudioManager().openAudioConnection(vc);
		}
	}
	*/
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext) {
			player.playTrack(track);
		}
	}
	
	public void clearQueue() {
		queue.clear();	
	}
	
	public void remove(AudioInfo info) {
		queue.remove(info);
	}
	
}
