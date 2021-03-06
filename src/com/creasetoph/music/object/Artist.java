package com.creasetoph.music.object;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Artist object has a name and holds albums
 */
public class Artist extends MusicItem implements Comparable<Artist>{

    //Albums for this artist
    private ArrayList<Album> _albums = new ArrayList<Album>();
    private Library _library;

    /**
     * Constructor sets name for artist
     *
     * @param name Artist name
     */
    public Artist(Library library,String name) {
        _library = library;
        setName(name);
    }

    public Library getLibrary() {
        return _library;
    }

    /**
     * Adds an album to this artist
     *
     * @param album Album to add to artist's album list
     */
    public void addAlbum(Album album) {
        _albums.add(album);
    }

    /**
     * Gets all the albums for this artist
     *
     * @return List of albums
     */
    public ArrayList<Album> getAlbums() {
        return _albums;
    }

    /**
     * Sorts the albums for an artist in alphabetic order
     */
    public void sortAlbums() {
        Collections.sort(_albums);
    }

    /**
     * Searches the list of albums for specified album name
     *
     * @param albumName Album to find in artist's album list
     * @return Album object representing the album name
     */
    public Album getAlbum(String albumName) {
        for (Album album : _albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Comparator so we can sort artist
     * @param another Other Artist to compare to
     * @return 0 if the strings are equal, a negative integer if this string is
     *         before the specified string, or a positive integer if this string is
     *         after the specified string.
     */
    public int compareTo(Artist another) {
        return _name.compareTo(another.getName());
    }
}