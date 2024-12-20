package de.cyklon.discordchatexporter.entities;

import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.List;

public interface ExportableMessage extends SerializableEntity {

	long getId();

	OffsetDateTime getCreatedAt();

	String getContent();

	boolean isEdited();

	ExportableUser getAuthor();

	List<ExportableAttachment> getAttachments();

	List<ExportableEmbed> getEmbeds();

	List<ExportableComponent> getComponents();

	List<ExportableReaction> getReactions();

	@Override
	default long getSerialId() {
		return 17;
	}

	@Override
	default byte[] getBytes() {
		List<ExportableAttachment> attach = getAttachments();
		List<ExportableEmbed> emb = getEmbeds();
		List<ExportableComponent> comp = getComponents();
		List<ExportableReaction> reac = getReactions();

		byte[] zoneId = getCreatedAt().toZonedDateTime().getZone().getId().getBytes();
		long createdAt = getCreatedAt().toInstant().toEpochMilli();
		byte[] content = getContent().getBytes();
		byte[] author = getAuthor().getBytes();
		byte[][] attachments = new byte[attach.size()][];
		byte[][] embeds = new byte[emb.size()][];
		byte[][] components = new byte[comp.size()][];
		byte[][] reactions = new byte[reac.size()][];

		int attachmentsLen = 0;
		for (int i = 0; i < attachments.length; i++) {
			attachments[i] = attach.get(i).getBytes();
			attachmentsLen += 4 + attachments[i].length;
		}

		ByteBuffer attachmentBuffer = ByteBuffer.allocate(attachmentsLen);
		for (byte[] attachment : attachments) {
			put(attachmentBuffer, attachment);
		}

		attachmentBuffer.flip();


		int embedsLen = 0;
		for (int i = 0; i < embeds.length; i++) {
			embeds[i] = emb.get(i).getBytes();
			embedsLen += 4 + embeds[i].length;
		}

		ByteBuffer embedBuffer = ByteBuffer.allocate(embedsLen);
		for (byte[] embed : embeds) {
			put(embedBuffer, embed);
		}

		embedBuffer.flip();


		int componentsLen = 0;
		for (int i = 0; i < components.length; i++) {
			components[i] = comp.get(i).getBytes();
			componentsLen += 4 + components[i].length;
		}

		ByteBuffer componentBuffer = ByteBuffer.allocate(componentsLen);
		for (byte[] component : components) {
			put(componentBuffer, component);
		}

		componentBuffer.flip();


		int reactionsLen = 0;
		for (int i = 0; i < reactions.length; i++) {
			reactions[i] = reac.get(i).getBytes();
			reactionsLen += 4 + reactions[i].length;
		}

		ByteBuffer reactionBuffer = ByteBuffer.allocate(reactionsLen);
		for (byte[] reaction : reactions) {
			put(reactionBuffer, reaction);
		}

		reactionBuffer.flip();


		ByteBuffer buffer = ByteBuffer.allocate(8 + 8 + 4 + zoneId.length + 8 + 4 + content.length + 1 + 4 + author.length + 4 + attachmentBuffer.limit() + 4 + embedBuffer.limit() + 4 + componentBuffer.limit() + 4 + reactionBuffer.limit());

		buffer.putLong(getSerialId());

		buffer.putLong(getId());

		put(buffer, zoneId);

		buffer.putLong(createdAt);

		put(buffer, content);

		put(buffer, isEdited());

		put(buffer, author);

		buffer.putInt(attachmentBuffer.limit());
		buffer.put(attachmentBuffer);

		buffer.putInt(embedBuffer.limit());
		buffer.put(embedBuffer);

		buffer.putInt(componentBuffer.limit());
		buffer.put(componentBuffer);

		buffer.putInt(reactionBuffer.limit());
		buffer.put(reactionBuffer);

		return buffer.array();
	}
}
