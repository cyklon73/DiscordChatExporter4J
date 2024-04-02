package de.cyklon.discordchatexporter.entities;

import java.time.OffsetDateTime;
import java.util.List;

public record ChannelImpl(String name, long id, String topic, String subject, List<ExportableMessage> messages, OffsetDateTime createdAt, ExportableGuild guild) implements ExportableChannel {
	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public List<ExportableMessage> getMessages() {
		return messages;
	}

	@Override
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public ExportableGuild getGuild() {
		return guild;
	}
}
