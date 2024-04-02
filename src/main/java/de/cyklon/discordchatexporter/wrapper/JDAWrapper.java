package de.cyklon.discordchatexporter.wrapper;

import de.cyklon.discordchatexporter.DiscordExporter;
import de.cyklon.discordchatexporter.entities.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.entities.channel.middleman.StandardGuildMessageChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

final class JDAWrapper implements APIWrapper<PrivateChannel, GuildMessageChannel, Guild, Message.Attachment, Component, MessageEmbed, Message, User, MessageReaction, MessageEmbed.AuthorInfo, MessageEmbed.Field, MessageEmbed.Footer> {

	static final JDAWrapper instance = new JDAWrapper();

	private JDAWrapper() {}

	private String nonNull(String s) {
		return Objects.requireNonNullElse(s, "");
	}

	@Override
	public ExportableChannel ofPrivateChannel(PrivateChannel channel) {
		String name = channel.getName();
		if (name.isBlank()) name = "Private Channel";
		User user = channel.getUser();
		OffsetDateTime createdAt;
		long id;
		String avatarUrl = null;
		if (user==null) {
			createdAt = OffsetDateTime.now();
			id = channel.getIdLong();
		} else {
			createdAt = user.getTimeCreated();
			id = user.getIdLong();
			avatarUrl = user.getAvatarUrl();
		}
		if (avatarUrl==null) avatarUrl = DiscordExporter.getAvatarById(id);
		return new ChannelImpl(name, id, "", "", new ArrayList<>(), createdAt, new GuildImpl(name, id, avatarUrl));
	}

	@Override
	public ExportableChannel ofGuildChannel(GuildMessageChannel channel) {
		String topic = "";
		if (channel instanceof StandardGuildMessageChannel tc) topic = tc.getTopic();
		if (topic==null) topic = "";
		return new ChannelImpl(channel.getName(), channel.getIdLong(), topic, topic, new ArrayList<>(), channel.getTimeCreated(), ofGuild(channel.getGuild()));
	}

	@Override
	public ExportableGuild ofGuild(Guild guild) {
		String iconUrl = guild.getIconUrl();
		if (iconUrl==null) iconUrl = DiscordExporter.getAvatarById(guild.getIdLong());
		return new GuildImpl(guild.getName(), guild.getIdLong(), iconUrl);
	}

	@Override
	public ExportableAttachment ofAttachment(Message.Attachment attachment) {
		if (attachment.isImage()) return ExportableAttachment.Image.impl(attachment.getUrl(), attachment.getFileName(), attachment.getSize());
		if (attachment.isVideo()) return ExportableAttachment.Video.impl(attachment.getUrl(), attachment.getFileName(), attachment.getSize());
		if (ExportableAttachment.Audio.supported(attachment.getFileExtension())) return ExportableAttachment.Audio.impl(attachment.getUrl(), attachment.getFileName(), attachment.getSize());
		if (ExportableAttachment.Message.supported(attachment.getFileExtension())) return ExportableAttachment.Message.impl(attachment.getUrl(), attachment.getFileName(), attachment.getSize());
		if (ExportableAttachment.Code.supported(attachment.getFileExtension())) return ExportableAttachment.Code.impl(attachment.getUrl(), attachment.getFileName(), attachment.getSize());
		return ExportableAttachment.Unknown.impl(attachment.getUrl(), attachment.getFileName(), attachment.getSize());
	}

	@Override
	public ExportableComponent ofComponent(Component component) {
		if (component instanceof Button button) {
			ExportableComponent.Button.Style style = switch (button.getStyle()) {
				case SECONDARY -> ExportableComponent.Button.Style.SECONDARY;
				case SUCCESS -> ExportableComponent.Button.Style.SUCCESS;
				case DANGER -> ExportableComponent.Button.Style.DANGER;
				case LINK -> ExportableComponent.Button.Style.LINK;

				default -> ExportableComponent.Button.Style.PRIMARY;
			};
			return ExportableComponent.Button.impl(style, button.getId(), nonNull(button.getUrl()), button.getEmoji() == null ? "" : button.getEmoji().getFormatted(), button.getLabel(), button.isDisabled());
		} else if (component instanceof SelectMenu menu) {
			return ExportableComponent.Menu.impl(menu.getId(), menu.getPlaceholder(), "", Collections.emptyList(), menu.isDisabled());
		}
		return null;
	}

	@Override
	public ExportableEmbed ofEmbed(MessageEmbed embed) {
		return new ExportableEmbed() {
			@Override
			public Color getColor() {
				return embed.getColor();
			}

			@Override
			public Author getAuthor() {
				return ofEmbedAuthor(embed.getAuthor());
			}

			@Override
			public String getUrl() {
				return embed.getUrl();
			}

			@Override
			public String getTitle() {
				return embed.getTitle();
			}

			@Override
			public String getDescription() {
				return embed.getDescription();
			}

			@Override
			public List<Field> getFields() {
				return embed.getFields().stream()
						.map(f -> ofEmbedField(f))
						.toList();
			}

			@Override
			public String getImageUrl() {
				return embed.getImage()==null ? null : embed.getImage().getUrl();
			}

			@Override
			public String getThumbnailUrl() {
				return embed.getThumbnail()==null ? null : embed.getThumbnail().getUrl();
			}

			@Override
			public Footer getFooter() {
				return ofEmbedFooter(embed, embed.getFooter());
			}
		};
	}

	@Override
	public ExportableEmbed.Author ofEmbedAuthor(MessageEmbed.AuthorInfo author) {
		return new ExportableEmbed.Author() {
			@Override
			public String getName() {
				return nonNull(author.getName());
			}

			@Override
			public String getUrl() {
				return author.getUrl();
			}

			@Override
			public String getIconUrl() {
				return author.getIconUrl();
			}
		};
	}

	@Override
	public ExportableEmbed.Field ofEmbedField(MessageEmbed.Field field) {
		return new ExportableEmbed.Field() {
			@Override
			public String getName() {
				return nonNull(field.getName());
			}

			@Override
			public String getValue() {
				return nonNull(field.getValue());
			}

			@Override
			public boolean isInline() {
				return field.isInline();
			}
		};
	}

	@Override
	public ExportableEmbed.Footer ofEmbedFooter(MessageEmbed embed, MessageEmbed.Footer footer) {
		return new ExportableEmbed.Footer() {
			@Override
			public String getContent() {
				return nonNull(footer.getText());
			}

			@Override
			public OffsetDateTime getTimestamp() {
				return embed.getTimestamp();
			}

			@Override
			public String getIconUrl() {
				return footer.getIconUrl();
			}
		};
	}

	@Override
	public ExportableMessage ofMessage(Message message) {
		return new ExportableMessage() {
			@Override
			public long getId() {
				return message.getIdLong();
			}

			@Override
			public OffsetDateTime getCreatedAt() {
				return message.getTimeCreated();
			}

			@Override
			public String getContent() {
				return message.getContentRaw();
			}

			@Override
			public boolean isEdited() {
				return message.isEdited();
			}

			@Override
			public ExportableUser getAuthor() {
				return ofUser(message.getAuthor());
			}

			@Override
			public List<ExportableAttachment> getAttachments() {
				return message.getAttachments().stream()
						.map(a -> ofAttachment(a))
						.toList();
			}

			@Override
			public List<ExportableEmbed> getEmbeds() {
				return message.getEmbeds().stream()
						.map(e -> ofEmbed(e))
						.toList();
			}

			@Override
			public List<ExportableComponent> getComponents() {
				return message.getComponents().stream()
						.map(c -> ofComponent(c))
						.toList();
			}

			@Override
			public List<ExportableReaction> getReactions() {
				return message.getReactions().stream()
						.map(r -> ofReaction(r))
						.toList();
			}
		};
	}

	@Override
	public ExportableUser ofUser(User user) {
		return new ExportableUser() {
			@Override
			public long getId() {
				return user.getIdLong();
			}

			@Override
			public String getAvatarUrl() {
				return Objects.requireNonNullElse(user.getAvatarUrl(), DiscordExporter.getAvatarById(getId()));
			}

			@Override
			public String getName() {
				return user.getName();
			}

			@Override
			public String getDisplayName() {
				return user.getEffectiveName();
			}

			@Override
			public Color getColor() {
				return user.retrieveProfile().complete().getAccentColor();
			}

			@Override
			public boolean isBot() {
				return user.isBot();
			}

			@Override
			public boolean isSystem() {
				return user.isSystem();
			}

			@Override
			public List<Flag> getFlags() {
				return Flag.getFlags(user.getFlagsRaw()).stream().toList();
			}
		};
	}

	@Override
	public ExportableReaction ofReaction(MessageReaction reaction) {
		return new ExportableReaction() {
			@Override
			public String getEmoji() {
				return reaction.getEmoji().getName();
			}

			@Override
			public String getEmojiFile() {
				return reaction.getEmoji().getType().equals(Emoji.Type.CUSTOM) ? reaction.getEmoji().asCustom().getId() : null;
			}

			@Override
			public int getReactionCount() {
				return reaction.getCount();
			}
		};
	}
}
