package com.example.game1.presentation.model;

/**
 * The model object for the customization choices of a user or game.
 */
public class Customization {

    /* The possible character colour choices */
    public enum CharacterColour {BLUE, RED, YELLOW}

    /* The possible colours scheme choices */
    public enum ColourScheme {DARK, LIGHT}

    /* The possible song choices */
    public enum MusicPath {SONG1, SONG2, SONG3}

    /* The customization choices of this Customization object */
    private CharacterColour characterColour;
    private ColourScheme colourScheme;
    private MusicPath musicPath;

    /**
     * Constructs a new Customization object with default choices.
     */
    public Customization() {
        characterColour = CharacterColour.BLUE;
        colourScheme = ColourScheme.DARK;
        musicPath = MusicPath.SONG1;
    }

    /**
     * Returns the character colour of this Customization
     *
     * @return the character colour of this Customization
     */
    public CharacterColour getCharacterColour() {
        return characterColour;
    }

    /**
     * Sets the character colour of this Customization
     *
     * @param characterColour the new character colour of this Customization
     */
    public void setCharacterColour(CharacterColour characterColour) {
        this.characterColour = characterColour;
    }

    /**
     * Returns the colour scheme of this Customization
     *
     * @return the colour scheme of this Customization
     */
    public ColourScheme getColourScheme() {
        return colourScheme;
    }

    /**
     * Sets the colour scheme of this Customization
     *
     * @param colourScheme the new colour scheme of this Customization
     */
    public void setColourScheme(ColourScheme colourScheme) {
        this.colourScheme = colourScheme;
    }

    /**
     * Returns the music of this Customization
     *
     * @return the music of this Customization
     */
    public MusicPath getMusicPath() {
        return musicPath;
    }

    /**
     * Sets the music of this Customization
     *
     * @param musicPath the new music of this Customization
     */
    public void setMusicPath(MusicPath musicPath) {
        this.musicPath = musicPath;
    }

}
