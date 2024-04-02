package de.cyklon.discordchatexporter.entities;

import java.time.OffsetDateTime;
import java.util.List;

public interface ExportableChannel {

	String getName();

	long getID();

	String getTopic();

	String getSubject();

	List<ExportableMessage> getMessages();

	OffsetDateTime getCreatedAt();

	ExportableGuild getGuild();

}
