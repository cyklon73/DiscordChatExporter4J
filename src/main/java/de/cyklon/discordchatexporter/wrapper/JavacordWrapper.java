package de.cyklon.discordchatexporter.wrapper;

import de.cyklon.discordchatexporter.DiscordExporter;
import de.cyklon.discordchatexporter.entities.*;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.Component;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedAuthor;
import org.javacord.api.entity.message.embed.EmbedField;
import org.javacord.api.entity.message.embed.EmbedFooter;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserFlag;

import java.awt.*;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.*;

final class JavacordWrapper extends Wrapper implements APIWrapper<PrivateChannel, ServerTextChannel, Server, MessageAttachment, Component, Embed, Message, User, Reaction, EmbedAuthor, EmbedField, EmbedFooter> {

	static final JavacordWrapper instance = new JavacordWrapper();

	private static final Set<String> IMAGE_EXTENSIONS = new HashSet(Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "tiff", "svg", "apng"));
	private static final Set<String> VIDEO_EXTENSIONS = new HashSet(Arrays.asList("webm", "flv", "vob", "avi", "mov", "wmv", "amv", "mp4", "mpg", "mpeg", "gifv"));

	@Override
	public ExportableChannel ofPrivateChannel(PrivateChannel channel) {
		Optional<User> user = channel.getRecipient();
		String name = user.map(Nameable::getName).orElse("Private Channel");
		OffsetDateTime createdAt = user.map(u -> asOffset(u.getCreationTimestamp())).orElseGet(OffsetDateTime::now);
		long id = user.map(DiscordEntity::getId).orElseGet(channel::getId);
		String avatarUrl = user.map(u -> u.getAvatar().getUrl().toExternalForm()).orElseGet(() -> DiscordExporter.getAvatarById(id));

		List<ExportableMessage> msg = new ArrayList<>(channel.getMessagesAsStream()
				.map(this::ofMessage)
				.toList());

		Collections.reverse(msg);

		return new ChannelImpl(name, id, "", "", msg, createdAt, new GuildImpl(name, id, avatarUrl));
	}

	@Override
	public ExportableChannel ofGuildChannel(ServerTextChannel channel) {
		String topic = channel.getTopic();
		if (topic==null) topic = "";

		List<ExportableMessage> msg = new ArrayList<>(channel.getMessagesAsStream()
				.map(this::ofMessage)
				.toList());

		Collections.reverse(msg);

		return new ChannelImpl(channel.getName(), channel.getId(), topic, topic, msg, asOffset(channel.getCreationTimestamp()), ofGuild(channel.getServer()));
	}

	@Override
	public ExportableGuild ofGuild(Server guild) {
		return new GuildImpl(guild.getName(), guild.getId(), guild.getIcon().map(i -> i.getUrl().toExternalForm()).orElseGet(() -> DiscordExporter.getAvatarById(guild.getId())));
	}

	@Override
	public ExportableAttachment ofAttachment(MessageAttachment attachment) {
		String ext = attachment.getFileName().substring(attachment.getFileName().lastIndexOf(".")+1).toLowerCase();
		if (attachment.getWidth().isPresent()) {
			if (IMAGE_EXTENSIONS.contains(ext)) return ExportableAttachment.Image.impl(attachment.getUrl().toExternalForm(), attachment.getFileName(), attachment.getSize());
			if (VIDEO_EXTENSIONS.contains(ext)) return ExportableAttachment.Video.impl(attachment.getUrl().toExternalForm(), attachment.getFileName(), attachment.getSize());
		}
		if (ExportableAttachment.Audio.supported(ext)) return ExportableAttachment.Audio.impl(attachment.getUrl().toExternalForm(), attachment.getFileName(), attachment.getSize());
		if (ExportableAttachment.Message.supported(ext)) return ExportableAttachment.Message.impl(attachment.getUrl().toExternalForm(), attachment.getFileName(), attachment.getSize());
		if (ExportableAttachment.Code.supported(ext)) return ExportableAttachment.Code.impl(attachment.getUrl().toExternalForm(), attachment.getFileName(), attachment.getSize());
		return ExportableAttachment.Unknown.impl(attachment.getUrl().toExternalForm(), attachment.getFileName(), attachment.getSize());
	}

	@Override
	public ExportableComponent ofComponent(Component component) {
		if (component instanceof Button btn) {
			ExportableComponent.Button.Style style = switch (btn.getStyle()) {
				case SECONDARY -> ExportableComponent.Button.Style.SECONDARY;
				case SUCCESS -> ExportableComponent.Button.Style.SUCCESS;
				case DANGER -> ExportableComponent.Button.Style.DANGER;
				case LINK -> ExportableComponent.Button.Style.LINK;

				default -> ExportableComponent.Button.Style.PRIMARY;
			};
			return ExportableComponent.Button.impl(style, btn.getCustomId().orElse("btn"), btn.getUrl().orElse(""), btn.getEmoji().map(Mentionable::getMentionTag).orElse(""), btn.getLabel().orElse(""), btn.isDisabled().orElse(false));
		}
		if (component instanceof SelectMenu menu) return ExportableComponent.Menu.impl(menu.getCustomId(), menu.getPlaceholder().orElse(""), "", Collections.emptyList(), menu.isDisabled());
		return null;
	}

	@Override
	public ExportableEmbed ofEmbed(Embed embed) {
		return new ExportableEmbed() {
			@Override
			public Color getColor() {
				return nonNull(embed.getColor());
			}

			@Override
			public Author getAuthor() {
				return embed.getAuthor().map(e -> ofEmbedAuthor(e)).orElseGet(() -> dummyAuthor());
			}

			@Override
			public String getUrl() {
				return embed.getUrl().map(URL::toExternalForm).orElse(null);
			}

			@Override
			public String getTitle() {
				return embed.getTitle().orElse("");
			}

			@Override
			public String getDescription() {
				return embed.getDescription().orElse("");
			}

			@Override
			public List<Field> getFields() {
				return embed.getFields().stream()
						.map(f -> ofEmbedField(f))
						.toList();
			}

			@Override
			public String getImageUrl() {
				return embed.getImage().map(i -> i.getUrl().toExternalForm()).orElse(null);
			}

			@Override
			public String getThumbnailUrl() {
				return embed.getThumbnail().map(t -> t.getUrl().toExternalForm()).orElse(null);
			}

			@Override
			public Footer getFooter() {
				return embed.getFooter().map(f -> ofEmbedFooter(embed, f)).orElseGet(() -> dummyFooter());
			}
		};
	}

	@Override
	public ExportableEmbed.Author ofEmbedAuthor(EmbedAuthor author) {
		return new ExportableEmbed.Author() {
			@Override
			public String getName() {
				return author.getName();
			}

			@Override
			public String getUrl() {
				return author.getUrl().map(URL::toExternalForm).orElse(null);
			}

			@Override
			public String getIconUrl() {
				return author.getIconUrl().map(URL::toExternalForm).orElse(null);
			}
		};
	}

	@Override
	public ExportableEmbed.Field ofEmbedField(EmbedField field) {
		return new ExportableEmbed.Field() {
			@Override
			public String getName() {
				return field.getName();
			}

			@Override
			public String getValue() {
				return field.getValue();
			}

			@Override
			public boolean isInline() {
				return field.isInline();
			}
		};
	}

	@Override
	public ExportableEmbed.Footer ofEmbedFooter(Embed embed, EmbedFooter footer) {
		return new ExportableEmbed.Footer() {
			@Override
			public String getContent() {
				return footer.getText().orElse("");
			}

			@Override
			public OffsetDateTime getTimestamp() {
				return asOffset(embed.getTimestamp());
			}

			@Override
			public String getIconUrl() {
				return footer.getIconUrl().map(URL::toExternalForm).orElse(null);
			}
		};
	}

	@Override
	public ExportableMessage ofMessage(Message message) {
		return new ExportableMessage() {
			@Override
			public long getId() {
				return message.getId();
			}

			@Override
			public OffsetDateTime getCreatedAt() {
				return asOffset(message.getCreationTimestamp());
			}

			@Override
			public String getContent() {
				return message.getContent();
			}

			@Override
			public boolean isEdited() {
				return message.getLastEditTimestamp().isPresent();
			}

			@Override
			public ExportableUser getAuthor() {
				return ofMessageAuthor(message.getAuthor());
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
				return user.getId();
			}

			@Override
			public String getAvatarUrl() {
				return user.getAvatar().getUrl().toExternalForm();
			}

			@Override
			public String getName() {
				return user.getName();
			}

			@Override
			public String getDisplayName() {
				return user.getName();
			}

			@Override
			public Color getColor() {
				return Color.BLACK;
			}

			@Override
			public boolean isBot() {
				return user.isBot();
			}

			@Override
			public boolean isSystem() {
				return false;
			}

			@Override
			public List<Flag> getFlags() {
				return Flag.getFlags(getRaw(user.getUserFlags())).stream().toList();
			}
		};
	}

	private int getRaw(EnumSet<UserFlag> flags){
		int raw = 0;
		for (UserFlag flag : flags)
		{
			if (flag != null && flag != UserFlag.NONE)
				raw |= flag.asInt();
		}

		return raw;
	}

	@Override
	public ExportableReaction ofReaction(Reaction reaction) {
		return new ExportableReaction() {
			@Override
			public String getEmoji() {
				return reaction.getEmoji().getMentionTag();
			}

			@Override
			public String getEmojiFile() {
				return reaction.getEmoji().isCustomEmoji() ? reaction.getEmoji().asCustomEmoji().get().getIdAsString() : null;
			}

			@Override
			public int getReactionCount() {
				return reaction.getCount();
			}
		};
	}

	public ExportableUser ofMessageAuthor(MessageAuthor author) {
		if (author.asUser().isPresent()) return ofUser(author.asUser().get());
		return new ExportableUser() {
			@Override
			public long getId() {
				return author.getId();
			}

			@Override
			public String getAvatarUrl() {
				return author.getAvatar().getUrl().toExternalForm();
			}

			@Override
			public String getName() {
				return author.getName();
			}

			@Override
			public String getDisplayName() {
				return author.getDisplayName();
			}

			@Override
			public Color getColor() {
				return author.getRoleColor().orElse(Color.BLACK);
			}

			@Override
			public boolean isBot() {
				return author.isBotUser();
			}

			@Override
			public boolean isSystem() {
				return false;
			}

			@Override
			public List<Flag> getFlags() {
				return Collections.emptyList();
			}
		};
	}
}
