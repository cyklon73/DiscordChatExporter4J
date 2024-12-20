package de.cyklon.discordchatexporter.wrapper;

import de.cyklon.discordchatexporter.entities.*;
import de.cyklon.discordchatexporter.exception.InvalidDataException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

final class DataWrapper extends Wrapper implements APIWrapper<byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte[], byte[]> {

	static final DataWrapper instance = new DataWrapper();

	private ExportableChannel ofChannel(byte[] channel) {
		ByteBuffer buffer = ByteBuffer.wrap(channel);

		if (buffer.getLong()!=6) throw new InvalidDataException("Provided data is no valid channel data");

		String name = readString(buffer);

		long id = buffer.getLong();

		String topic = readString(buffer);

		String subject = readString(buffer);

		byte[] messageData = read(buffer);

		OffsetDateTime createdAt = readTime(buffer);

		ExportableGuild guild = ofGuild(read(buffer));


		//Messages
		ByteBuffer msgBuffer = ByteBuffer.wrap(messageData);
		List<ExportableMessage> messages = new LinkedList<>();

		while (msgBuffer.hasRemaining()) {
			byte[] msg = read(msgBuffer);
			messages.add(ofMessage(msg));
		}

		return new ExportableChannel() {
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
		};
	}

	@Override
	public ExportableChannel ofPrivateChannel(byte[] channel) {
		return ofChannel(channel);
	}

	@Override
	public ExportableChannel ofGuildChannel(byte[] channel) {
		return ofChannel(channel);
	}

	@Override
	public ExportableGuild ofGuild(byte[] guild) {
		ByteBuffer buffer = ByteBuffer.wrap(guild);

		if (buffer.getLong()!=16) throw new InvalidDataException("Provided data is no valid guild data");

		String name = readString(buffer);

		long id = buffer.getLong();

		String iconUrl = readString(buffer);

		return new ExportableGuild() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public long getId() {
				return id;
			}

			@Override
			public String getIconUrl() {
				return iconUrl;
			}
		};
	}

	@Override
	public ExportableAttachment ofAttachment(byte[] attachment) {
		ByteBuffer buffer = ByteBuffer.wrap(attachment);

		long serialId = buffer.getLong();

		if (serialId < 0 || serialId > 5) throw new InvalidDataException("Provided data is no valid attachment data");

		String url = readString(buffer);

		String filename = readString(buffer);

		int size = buffer.getInt();

		return switch ((int) serialId) {
			case 1 -> ExportableAttachment.Audio.impl(url, filename, size);
			case 2 -> ExportableAttachment.Image.impl(url, filename, size);
			case 3 -> ExportableAttachment.Message.impl(url, filename, size);
			case 4 -> ExportableAttachment.Code.impl(url, filename, size);
			case 5 -> ExportableAttachment.Video.impl(url, filename, size);
			default -> ExportableAttachment.Unknown.impl(url, filename, size);
		};
	}

	@Override
	public ExportableComponent ofComponent(byte[] component) {
		ByteBuffer buffer = ByteBuffer.wrap(component);

		long serialId = buffer.getLong();

		if (!(serialId == 7 || serialId == 8 || serialId == 10)) throw new InvalidDataException("Provided data is no valid component data");

		boolean disabled = readBool(buffer);

		String id = readString(buffer);

		if (serialId == 8) {
			Color color = readColor(buffer);

			Color hoverColor = readColor(buffer);

			Color disabledColor = readColor(buffer);

			String url = readString(buffer);

			String emoji = readString(buffer);

			String label = readString(buffer);

			return new ExportableComponent.Button() {
				@Override
				public Color getColor() {
					return color;
				}

				@Override
				public Color getHoverColor() {
					return hoverColor;
				}

				@Override
				public Color getDisabledColor() {
					return disabledColor;
				}

				@Override
				public String getUrl() {
					return url;
				}

				@Override
				public String getEmoji() {
					return emoji;
				}

				@Override
				public String getLabel() {
					return label;
				}

				@Override
				public boolean isDisabled() {
					return disabled;
				}

				@Override
				public String getId() {
					return id;
				}
			};
		}else if (serialId == 10) {
			String placeholder = readString(buffer);

			String icon = readString(buffer);

			byte[] opt = read(buffer);

			//Options
			ByteBuffer optionBuffer = ByteBuffer.wrap(opt);
			List<ExportableComponent.Menu.Option> options = new LinkedList<>();

			while (optionBuffer.hasRemaining()) {
				byte[] option = read(optionBuffer);
				options.add(ofMenuOption(option));
			}

			return new ExportableComponent.Menu() {
				@Override
				public String getPlaceholder() {
					return placeholder;
				}

				@Override
				public String getIcon() {
					return icon;
				}

				@Override
				public List<Option> getOptions() {
					return options;
				}

				@Override
				public boolean isDisabled() {
					return disabled;
				}

				@Override
				public String getId() {
					return id;
				}
			};
		}
		return new ExportableComponent() {
			@Override
			public boolean isDisabled() {
				return disabled;
			}

			@Override
			public String getId() {
				return id;
			}
		};
	}

	public ExportableComponent.Menu.Option ofMenuOption(byte[] option) {
		ByteBuffer buffer = ByteBuffer.wrap(option);

		if (buffer.getLong() != 11) throw new InvalidDataException("Provided data is no valid Menu Option data");

		String emoji = readString(buffer, true);

		String title = readString(buffer);

		String description = readString(buffer);

		return new ExportableComponent.Menu.Option() {
			@Nullable
			@Override
			public String getEmoji() {
				return emoji;
			}

			@Override
			public String getTitle() {
				return title;
			}

			@Override
			public String getDescription() {
				return description;
			}
		};
	}

	@Override
	public ExportableEmbed ofEmbed(byte[] embed) {
		ByteBuffer buffer = ByteBuffer.wrap(embed);

		if (buffer.getLong() != 12) throw new InvalidDataException("Provided data is no valid embed data");

		Color color = readColor(buffer);

		ExportableEmbed.Author author = ofEmbedAuthor(read(buffer));

		String url = readString(buffer, true);

		String title = readString(buffer);

		String description = readString(buffer);

		byte[] fieldData = read(buffer);

		String imageUrl = readString(buffer, true);

		String thumbnailUrl = readString(buffer, true);

		ExportableEmbed.Footer footer = ofEmbedFooter(embed, read(buffer));


		//Fields
		ByteBuffer fieldBuffer = ByteBuffer.wrap(fieldData);
		List<ExportableEmbed.Field> fields = new LinkedList<>();

		while (fieldBuffer.hasRemaining()) {
			byte[] field = read(fieldBuffer);
			fields.add(ofEmbedField(field));
		}

		return new ExportableEmbed() {
			@Override
			public Color getColor() {
				return color;
			}

			@Override
			public Author getAuthor() {
				return author;
			}

			@Nullable
			@Override
			public String getUrl() {
				return url;
			}

			@Override
			public String getTitle() {
				return title;
			}

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public List<Field> getFields() {
				return fields;
			}

			@Nullable
			@Override
			public String getImageUrl() {
				return imageUrl;
			}

			@Nullable
			@Override
			public String getThumbnailUrl() {
				return thumbnailUrl;
			}

			@Override
			public Footer getFooter() {
				return footer;
			}
		};
	}

	@Override
	public ExportableEmbed.Author ofEmbedAuthor(byte[] author) {
		ByteBuffer buffer = ByteBuffer.wrap(author);

		if (buffer.getLong() != 13) throw new InvalidDataException("Provided data is no valid embed author data");

		String name = readString(buffer);

		String url = readString(buffer, true);

		String iconUrl = readString(buffer, true);

		return new ExportableEmbed.Author() {
			@Override
			public String getName() {
				return name;
			}

			@Nullable
			@Override
			public String getUrl() {
				return url;
			}

			@Nullable
			@Override
			public String getIconUrl() {
				return iconUrl;
			}
		};
	}

	@Override
	public ExportableEmbed.Field ofEmbedField(byte[] field) {
		ByteBuffer buffer = ByteBuffer.wrap(field);

		if (buffer.getLong() != 14) throw new InvalidDataException("Provided data is no valid embed field data");

		String name = readString(buffer);

		String value = readString(buffer);

		boolean inline = readBool(buffer);

		return new ExportableEmbed.Field() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getValue() {
				return value;
			}

			@Override
			public boolean isInline() {
				return inline;
			}
		};
	}

	@Override
	public ExportableEmbed.Footer ofEmbedFooter(byte[] embed, byte[] footer) {
		ByteBuffer buffer = ByteBuffer.wrap(footer);

		if (buffer.getLong() != 15) throw new InvalidDataException("Provided data is no valid embed footer data");

		String content = readString(buffer);

		OffsetDateTime timestamp = readTime(buffer);

		String iconUrl = readString(buffer, true);

		return new ExportableEmbed.Footer() {
			@Override
			public String getContent() {
				return content;
			}

			@Nullable
			@Override
			public OffsetDateTime getTimestamp() {
				return timestamp;
			}

			@Nullable
			@Override
			public String getIconUrl() {
				return iconUrl;
			}
		};
	}

	@Override
	public ExportableMessage ofMessage(byte[] message) {
		ByteBuffer buffer = ByteBuffer.wrap(message);

		if (buffer.getLong() != 17) throw new InvalidDataException("Provided data is no valid message data");

		long id = buffer.getLong();

		OffsetDateTime createdAt = readTime(buffer);

		String content = readString(buffer);

		boolean edited = readBool(buffer);

		ExportableUser author = ofUser(read(buffer));

		byte[] attachmentData = read(buffer);

		byte[] embedData = read(buffer);

		byte[] componentData = read(buffer);

		byte[] reactionData = read(buffer);


		//Attachments
		ByteBuffer attachmentBuffer = ByteBuffer.wrap(attachmentData);
		List<ExportableAttachment> attachments = new LinkedList<>();

		while (attachmentBuffer.hasRemaining()) {
			byte[] attachment = read(attachmentBuffer);
			attachments.add(ofAttachment(attachment));
		}


		//Embeds
		ByteBuffer embedBuffer = ByteBuffer.wrap(embedData);
		List<ExportableEmbed> embeds = new LinkedList<>();

		while (embedBuffer.hasRemaining()) {
			byte[] embed = read(embedBuffer);
			embeds.add(ofEmbed(embed));
		}


		//Components
		ByteBuffer componentBuffer = ByteBuffer.wrap(componentData);
		List<ExportableComponent> components = new LinkedList<>();

		while (componentBuffer.hasRemaining()) {
			byte[] component = read(componentBuffer);
			components.add(ofComponent(component));
		}


		//Reactions
		ByteBuffer reactionBuffer = ByteBuffer.wrap(reactionData);
		List<ExportableReaction> reactions = new LinkedList<>();

		while (reactionBuffer.hasRemaining()) {
			byte[] reaction = read(reactionBuffer);
			reactions.add(ofReaction(reaction));
		}

		return new ExportableMessage() {
			@Override
			public long getId() {
				return id;
			}

			@Override
			public OffsetDateTime getCreatedAt() {
				return createdAt;
			}

			@Override
			public String getContent() {
				return content;
			}

			@Override
			public boolean isEdited() {
				return edited;
			}

			@Override
			public ExportableUser getAuthor() {
				return author;
			}

			@Override
			public List<ExportableAttachment> getAttachments() {
				return attachments;
			}

			@Override
			public List<ExportableEmbed> getEmbeds() {
				return embeds;
			}

			@Override
			public List<ExportableComponent> getComponents() {
				return components;
			}

			@Override
			public List<ExportableReaction> getReactions() {
				return reactions;
			}
		};
	}

	@Override
	public ExportableUser ofUser(byte[] user) {
		ByteBuffer buffer = ByteBuffer.wrap(user);

		if (buffer.getLong() != 19) throw new InvalidDataException("Provided data is no valid user data");

		long id = buffer.getLong();

		String avatarUrl = readString(buffer);

		String name = readString(buffer);

		String displayName = readString(buffer);

		Color color = readColor(buffer);

		boolean bot = readBool(buffer);

		boolean system = readBool(buffer);

		byte[] flagData = read(buffer);


		//Flags
		ByteBuffer flagBuffer = ByteBuffer.wrap(flagData);
		List<ExportableUser.Flag> flags = new LinkedList<>();

		while (flagBuffer.hasRemaining()) {
			byte[] flag = read(flagBuffer);
			flags.add(ofUserFlag(flag));
		}

		return new ExportableUser() {
			@Override
			public long getId() {
				return id;
			}

			@Override
			public String getAvatarUrl() {
				return avatarUrl;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDisplayName() {
				return displayName;
			}

			@Override
			public Color getColor() {
				return color;
			}

			@Override
			public boolean isBot() {
				return bot;
			}

			@Override
			public boolean isSystem() {
				return system;
			}

			@Override
			public List<Flag> getFlags() {
				return flags;
			}
		};
	}

	public ExportableUser.Flag ofUserFlag(byte[] flag) {
		ByteBuffer buffer = ByteBuffer.wrap(flag);

		if (buffer.getLong() != 20) throw new InvalidDataException("Provided data is no valid user flag data");

		int offset = buffer.getInt();

		return ExportableUser.Flag.getFromOffset(offset);
	}

	@Override
	public ExportableReaction ofReaction(byte[] reaction) {
		ByteBuffer buffer = ByteBuffer.wrap(reaction);

		if (buffer.getLong() != 18) throw new InvalidDataException("Provided data is no valid reaction data");

		String emoji = readString(buffer);

		String emojiFile = readString(buffer, true);

		int reactionCount = buffer.getInt();

		return new ExportableReaction() {
			@Override
			public String getEmoji() {
				return emoji;
			}

			@Nullable
			@Override
			public String getEmojiFile() {
				return emojiFile;
			}

			@Override
			public int getReactionCount() {
				return reactionCount;
			}
		};
	}


	private byte[] read(ByteBuffer buffer) {
		int len = buffer.getInt();
		byte[] data = new byte[len];
		buffer.get(data);
		return data;
	}

	@NotNull
	private String readString(ByteBuffer buffer) {
		return readString(buffer, false);
	}

	@Nullable
	@Contract("_, false -> !null")
	private String readString(ByteBuffer buffer, boolean nullable) {
		byte[] data = read(buffer);
		return (nullable && data.length==0) ? null : new String(data);
	}

	private boolean readBool(ByteBuffer buffer) {
		return buffer.get()==1;
	}

	private Color readColor(ByteBuffer buffer) {
		return new Color(buffer.getInt(), true);
	}

	@Nullable
	private OffsetDateTime readTime(ByteBuffer buffer) {
		String zoneId = readString(buffer, true);

		if (zoneId==null) return null;

		long time = buffer.getLong();

		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of(zoneId));
	}
}
