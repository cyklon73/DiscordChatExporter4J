package de.cyklon.discordchatexporter.entities;

import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.List;

public interface ExportableChannel extends SerializableEntity {

	String getName();

	long getID();

	String getTopic();

	String getSubject();

	List<ExportableMessage> getMessages();

	OffsetDateTime getCreatedAt();

	ExportableGuild getGuild();


	@Override
	default long getSerialId() {
		return 6;
	}

	@Override
	default byte[] getBytes() {
		List<ExportableMessage> msgs = getMessages();

		byte[] name = getName().getBytes();
		byte[] topic = getTopic().getBytes();
		byte[] subject = getSubject().getBytes();
		byte[][] messages = new byte[msgs.size()][];
		byte[] zoneId = getCreatedAt().toZonedDateTime().getZone().getId().getBytes();
		long createdAt = getCreatedAt().toInstant().toEpochMilli();
		byte[] guild = getGuild().getBytes();

		int len = 0;
		for (int i = 0; i < messages.length; i++) {
			messages[i] = msgs.get(i).getBytes();
			len += messages[i].length + 4;
		}

		ByteBuffer msgBuffer = ByteBuffer.allocate(len);
		for (byte[] msg : messages) {
			put(msgBuffer, msg);
		}
		msgBuffer.flip();

		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + name.length + 8 + 4 + topic.length + 4 + subject.length + 4 + msgBuffer.limit() + 4 + zoneId.length + 8 + 4 + guild.length);

		buffer.putLong(getSerialId());

		put(buffer, name);

		buffer.putLong(getID());

		put(buffer, topic);

		put(buffer, subject);

		buffer.putInt(msgBuffer.limit());
		buffer.put(msgBuffer);

		put(buffer, zoneId);

		buffer.putLong(createdAt);

		put(buffer, guild);

		return buffer.array();
	}
}
