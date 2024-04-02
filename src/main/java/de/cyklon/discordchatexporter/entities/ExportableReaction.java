package de.cyklon.discordchatexporter.entities;

public interface ExportableReaction {

	String getEmoji();

	/**
	 * only available for custom emojis.
	 * <p>
	 * otherwise returns null
	 */
	String getEmojiFile();

	int getReactionCount();
}
