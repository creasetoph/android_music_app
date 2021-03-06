package com.creasetoph.music.controller;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.creasetoph.music.object.Playlist;
import com.creasetoph.music.object.Sound;
import com.creasetoph.music.object.Track;
import com.creasetoph.music.util.Logger;

public class PlaylistController {

    private static PlaylistController _instance = null;
    private static boolean _repeat_album = true;

    private Playlist _playlist;
    private Sound    _sound;
    private Boolean  _playing;
    private Boolean  _paused;
    private boolean _prepared = false;
    private MediaPlayer.OnPreparedListener _onPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mediaPlayer) {
            _sound.play();
            _prepared = true;
            _paused = false;
            _playing = true;
        }
    };

    public static PlaylistController getInstance() {
        if (_instance == null) {
            _instance = new PlaylistController();
        }
        return _instance;
    }

    private PlaylistController() {
        _playlist = new Playlist();
        _sound = new Sound(_onPreparedListener);
        _playing = false;
        _paused = false;
        registerSoundListeners();
    }

    private void registerSoundListeners() {
        _sound.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
        _sound.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                bufferUpdate(i);
            }
        });
    }

    private void bufferUpdate(int i) {
        Logger.info("BufferingUpdate: " + i);
    }

    public void addToPlaylist(Track track) {
        _playlist.addToPlaylist(track);
    }

    public void clearPlaylist() {
        _playlist.clearPlaylist();
    }

    public void playPause() {
        if (_playing) {
            pause();
        } else {
            play();
        }
    }

    private void play() {
        if (_paused) {
            _sound.resume();
            _paused = false;
            _playing = true;
        } else {
            String path = _playlist.getCurrentPlaylistTrackPath();
            Logger.info("Loading: " + path);
            if (path != null) {
                _prepared = false;
                _sound.load(path);
            }
        }
    }

    private void pause() {
        if (_playing) {
            _sound.pause();
            _paused = true;
            _playing = false;
        }
    }

    public void stop() {
        if (_playing) {
            _sound.stop();
            _playing = false;
            _paused = false;
        }
    }

    public void next() {
        stop();
        if (_playlist.getCurrentTrackIndex() < _playlist.size() - 1) {
            _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() + 1);
            play();
        } else if (_repeat_album) {
            _playlist.setCurrentTrackIndex(0);
            play();
        }
    }

    public void prev() {
        stop();
        if (_playlist.getCurrentTrackIndex() > 0) {
            _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() - 1);
            play();
        } else if (_repeat_album) {
            _playlist.setCurrentTrackIndex(_playlist.getCurrentTrackIndex() - 1);
            play();
        }
    }

    public boolean isPlaying() {
        return _playing;
    }

    public int getCurrentTrack() {
        return _playlist.getCurrentTrackIndex();
    }

    public void selectTrack(int track) {
        stop();
        _playlist.setCurrentTrackIndex(track);
        play();
    }

    public ArrayList<Track> getPlaylistItems() {
        return _playlist.getPlaylistTracks();
    }

    public int getCurrentPosition() {
        if(_prepared) {
            return _sound.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if(_prepared) {
            return _sound.getDuration();
        }
        return 0;
    }
}
