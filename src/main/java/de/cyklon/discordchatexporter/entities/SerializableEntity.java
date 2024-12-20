package de.cyklon.discordchatexporter.entities;

import java.nio.ByteBuffer;

public interface SerializableEntity {

	/**
	 * 0 -> Unknown/Default Attachment
	 * 1 -> Audio Attachment
	 * 2 -> Image Attachment
	 * 3 -> Message Attachment
	 * 4 -> Code Attachment
	 * 5 -> Video Attachment
	 * 6 -> Channel
	 * 7 -> Default Component
	 * 8 -> Button Component
	 * 9 -> Button Component Style
	 * 10 -> Menu Component
	 * 11 -> Menu Component Option
	 * 12 -> Embed
	 * 13 -> Embed Author
	 * 14 -> Embed Field
	 * 15 -> Embed Footer
	 * 16 -> Guild
	 * 17 -> Message
	 * 18 -> Reaction
	 * 19 -> User
	 * 20 -> User Flag
	 */
	long getSerialId();

	byte[] getBytes();

	default void put(ByteBuffer buffer, byte[] data) {
		buffer.putInt(data.length);
		buffer.put(data);
	}

	default void put(ByteBuffer buffer, boolean b) {
		buffer.put((byte) (b ? 1 : 0));
	}

}
