package de.cyklon.discordchatexporter.entities;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public interface ExportableReaction extends SerializableEntity {

	String getEmoji();

	/**
	 * only available for custom emojis.
	 * <p>
	 * otherwise returns null
	 */
	@Nullable
	String getEmojiFile();

	int getReactionCount();

	@Override
	default long getSerialId() {
		return 18;
	}

	@Override
	default byte[] getBytes() {
		byte[] emoji = getEmoji().getBytes();
		byte[] emojiFile = getEmojiFile()==null ? new byte[0] : getEmojiFile().getBytes();

		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + emoji.length + 4 + emojiFile.length + 4);

		buffer.putLong(getSerialId());

		put(buffer, emoji);

		put(buffer, emojiFile);

		buffer.putInt(getReactionCount());

		return buffer.array();
	}
}
