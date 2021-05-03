package DiscordJavaBot.berko.productions.com;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayerManager extends ListenerAdapter{
	
	private final Map<String, GuildMusicManager> musicManagers;
	private final AudioPlayerManager audioPlayerManager;
	
	//Constructor for PlayerManager
	public PlayerManager() {
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		musicManagers = new HashMap<String, GuildMusicManager>();
		
		audioPlayerManager.registerSourceManager(new YoutubeAudioSourceManager());
    }

    public GuildMusicManager getMusicManager(Guild guild) {
    	 String guildId = guild.getId();
         GuildMusicManager mng = musicManagers.get(guildId);
         if (mng == null)
         {
             synchronized (musicManagers)
             {
                 mng = musicManagers.get(guildId);
                 if (mng == null)
                 {
                     mng = new GuildMusicManager(audioPlayerManager);
                     musicManagers.put(guildId, mng);
                 }
             }
         }
         return mng;
    }

	
public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		//This splits up the what the user entered
		String[] command = event.getMessage().getContentDisplay().split(" ", 2);
		
		 Guild guild = event.getGuild();
	        GuildMusicManager mng = getMusicManager(guild);
	        AudioPlayer player = mng.audioPlayer;
	        TrackScheduler scheduler = mng.trackScheduler;
		
		if(event.getMessage().getContentRaw().startsWith("/join")) {
			//Creating a guild and getting the voice channel of the guild member
			
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
		
		//Use this command to leave the voice channel 
		if(event.getMessage().getContentRaw().equals("/leave")) {
			Guild leaveGuild = event.getGuild();
			//leaveGuild.getAudioManager().setSendingHandler(null);
			leaveGuild.getAudioManager().closeAudioConnection();
		}
		
		//Use this command to actually music (also include the url)
		  if ("/play".equals(command[0]))
	        {
	            if (command.length == 1) //It is only the command to start playback (probably after pause)
	            {
	                if (player.isPaused())
	                {
	                    player.setPaused(false);
	                    event.getChannel().sendMessage("Playback as been resumed.").queue();
	                }
	                else if (player.getPlayingTrack() != null)
	                {
	                    event.getChannel().sendMessage("Player is already playing!").queue();
	                }
	                else if (scheduler.queue.isEmpty())
	                {
	                    event.getChannel().sendMessage("The current audio queue is empty! Add something to the queue first!").queue();
	                }
	            }
	            else    //Commands has 2 parts, .play and url.
	            {
	                System.out.println("it worked");
	            	loadAndPlay(mng, event.getChannel(), command[1], false);
	            }
	        }
		
	}
	
    //loadAndPlay method which I will use to play the music in the voice channel 
    public void loadAndPlay(GuildMusicManager musicManager, final MessageChannel channel, String url, final boolean addPlaylist) {
    	
    	final String trackUrl;

        //Strip <>'s that prevent discord from embedding link resources
        if (url.startsWith("<") && url.endsWith(">"))
            trackUrl = url.substring(1, url.length() - 1);
        else
            trackUrl = url;
        
    	 audioPlayerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler()
         {
             @Override
             public void trackLoaded(AudioTrack track)
             {
                 String msg = "Adding to queue: " + track.getInfo().title;
                 //musicManager.trackScheduler.queue(track);
                 channel.sendMessage(msg).queue();
                 
             }

             @Override
             public void playlistLoaded(AudioPlaylist playlist)
             {
                 AudioTrack firstTrack = playlist.getSelectedTrack();
                 List<AudioTrack> tracks = playlist.getTracks();


                 if (firstTrack == null) {
                     firstTrack = playlist.getTracks().get(0);
                 }

                 if (addPlaylist)
                 {
                     channel.sendMessage("Adding **" + playlist.getTracks().size() +"** tracks to queue from playlist: " + playlist.getName()).queue();
                     tracks.forEach(musicManager.trackScheduler::queue);
                 }
                 else
                 {
                     channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
                     musicManager.trackScheduler.queue(firstTrack);
                 }
             }

             @Override
             public void noMatches()
             {
                 channel.sendMessage("Nothing found by " + trackUrl).queue();
             }

             @Override
             public void loadFailed(FriendlyException exception)
             {
                 channel.sendMessage("Could not play: " + exception.getMessage()).queue();
             }
         });
    	 
    	 
    }

  

}
