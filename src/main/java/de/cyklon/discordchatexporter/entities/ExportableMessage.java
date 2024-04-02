package de.cyklon.discordchatexporter.entities;

import java.time.OffsetDateTime;
import java.util.List;

public interface ExportableMessage {

	long getId();

	OffsetDateTime getCreatedAt();

	String getContent();

	boolean isEdited();

	ExportableUser getAuthor();

	List<ExportableAttachment> getAttachments();

	List<ExportableEmbed> getEmbeds();

	List<ExportableComponent> getComponents();

	List<ExportableReaction> getReactions();

}
