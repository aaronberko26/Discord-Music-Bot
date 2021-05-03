package DiscordJavaBot.berko.productions.com;

import java.util.HashSet;
import java.util.Set;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class AudioInfo {
	private final AudioTrack track;
	private final Set<String> skips;

	
	public AudioInfo(AudioTrack track) {
		this.track = track;
		this.skips = new HashSet<>();

	}
	
	public AudioTrack getTrack() {
		return track;
	}
	
	public int getSkips() {
		return skips.size();
	}
	
	public void addSkip(User user) {
		skips.add(user.getId());
	}
	
	public boolean hasVoted(User user) {
		return skips.contains(user.getId());
	}
	
	
}
