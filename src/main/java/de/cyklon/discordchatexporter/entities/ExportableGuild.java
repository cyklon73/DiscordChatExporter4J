package de.cyklon.discordchatexporter.entities;

import java.nio.ByteBuffer;

public interface ExportableGuild extends SerializableEntity {

	String getName();

	long getId();

	String getIconUrl();

	@Override
	default long getSerialId() {
		return 16;
	}

	@Override
	default byte[] getBytes() {
		byte[] name = getName().getBytes();
		byte[] iconUrl = getIconUrl().getBytes();

		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + name.length + 8 + 4 + iconUrl.length);

		buffer.putLong(getSerialId());

		put(buffer, name);

		buffer.putLong(getId());

		put(buffer, iconUrl);

		return buffer.array();
	}
}
