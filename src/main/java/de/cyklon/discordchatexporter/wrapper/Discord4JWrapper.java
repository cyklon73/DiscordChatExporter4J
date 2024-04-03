package de.cyklon.discordchatexporter.wrapper;

import de.cyklon.discordchatexporter.DiscordExporter;
import de.cyklon.discordchatexporter.entities.*;
import discord4j.common.util.Snowflake;
import discord4j.core.object.Embed;
import discord4j.core.object.component.MessageComponent;
import discord4j.core.object.entity.Attachment;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.object.entity.channel.PrivateChannel;
import discord4j.core.object.entity.channel.TopLevelGuildMessageChannel;
import discord4j.core.object.reaction.Reaction;
import discord4j.rest.util.Image;

import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

final class Discord4JWrapper implements APIWrapper<PrivateChannel, GuildMessageChannel, Guild, Attachment, MessageComponent, Embed, Message, User, Reaction, Embed.Author, Embed.Field, Embed.Footer> {

	static final Discord4JWrapper instance = new Discord4JWrapper();

	@Override
	public ExportableChannel ofPrivateChannel(PrivateChannel channel) {
		User user = channel.getRecipients().stream().findFirst().get();
		return new ChannelImpl(user.getUsername(), channel.getId().asLong(), "", "", channel.getMessagesBefore(Snowflake.of(Instant.now())).collectSortedList().block().stream()
				.map(this::ofMessage)
				.toList(), OffsetDateTime.now(), new GuildImpl(user.getUsername(), user.getId().asLong(), user.getAvatarUrl()));
	}

	@Override
	public ExportableChannel ofGuildChannel(GuildMessageChannel channel) {
		String topic = "";
		if (channel instanceof TopLevelGuildMessageChannel tc) topic = tc.getTopic().orElse("");
		return new ChannelImpl(channel.getName(), channel.getId().asLong(), topic, topic, channel.getMessagesBefore(Snowflake.of(Instant.now())).collectSortedList().block().stream()
				.map(this::ofMessage)
				.toList(), OffsetDateTime.now(), ofGuild(channel.getGuild().block()));
	}

	@Override
	public ExportableGuild ofGuild(Guild guild) {
		return new GuildImpl(guild.getName(), guild.getId().asLong(), guild.getIconUrl(Image.Format.UNKNOWN).orElseGet(() -> DiscordExporter.getAvatarById(guild.getId().asLong())));
	}

	@Override
	public ExportableAttachment ofAttachment(Attachment attachment) {
		return null;
	}

	@Override
	public ExportableComponent ofComponent(MessageComponent component) {
		return null;
	}

	@Override
	public ExportableEmbed ofEmbed(Embed embed) {
		return new ExportableEmbed() {
			@Override
			public Color getColor() {
				return new Color(embed.getColor().orElse(discord4j.rest.util.Color.BLACK).getRGB());
			}

			@Override
			public Author getAuthor() {
				return embed.getAuthor().isPresent() ? ofEmbedAuthor(embed.getAuthor().get()) : dummyAuthor();
			}

			@Override
			public String getUrl() {
				return embed.getUrl().orElse(null);
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
				return embed.getImage().isPresent() ? embed.getImage().get().getUrl() : null;
			}

			@Override
			public String getThumbnailUrl() {
				return embed.getThumbnail().isPresent() ? embed.getThumbnail().get().getUrl() : null;
			}

			@Override
			public Footer getFooter() {
				return embed.getFooter().isPresent() ? ofEmbedFooter(embed, embed.getFooter().get()) : dummyFooter();
			}
		};
	}

	private ExportableEmbed.Author dummyAuthor() {
		return new ExportableEmbed.Author() {
			@Override
			public String getName() {
				return "";
			}

			@Override
			public String getUrl() {
				return null;
			}

			@Override
			public String getIconUrl() {
				return null;
			}
		};
	}

	@Override
	public ExportableEmbed.Author ofEmbedAuthor(Embed.Author author) {
		return new ExportableEmbed.Author() {
			@Override
			public String getName() {
				return author.getName().orElse("");
			}

			@Override
			public String getUrl() {
				return author.getUrl().orElse(null);
			}

			@Override
			public String getIconUrl() {
				return author.getIconUrl().orElse(null);
			}
		};
	}

	@Override
	public ExportableEmbed.Field ofEmbedField(Embed.Field field) {
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

	private ExportableEmbed.Footer dummyFooter() {
		return new ExportableEmbed.Footer() {
			@Override
			public String getContent() {
				return "";
			}

			@Override
			public OffsetDateTime getTimestamp() {
				return null;
			}

			@Override
			public String getIconUrl() {
				return null;
			}
		};
	}

	@Override
	public ExportableEmbed.Footer ofEmbedFooter(Embed embed, Embed.Footer footer) {
		return new ExportableEmbed.Footer() {
			@Override
			public String getContent() {
				return footer.getText();
			}

			@Override
			public OffsetDateTime getTimestamp() {
				return embed.getTimestamp().orElse(Instant.now()).atOffset(ZoneOffset.ofHours(0));
			}

			@Override
			public String getIconUrl() {
				return footer.getIconUrl().orElseGet(null);
			}
		};
	}

	@Override
	public ExportableMessage ofMessage(Message message) {
		return new ExportableMessage() {
			@Override
			public long getId() {
				return message.getId().asLong();
			}

			@Override
			public OffsetDateTime getCreatedAt() {
				return message.getTimestamp().atOffset(ZoneOffset.ofHours(0));
			}

			@Override
			public String getContent() {
				return message.getContent();
			}

			@Override
			public boolean isEdited() {
				return message.getEditedTimestamp().isPresent();
			}

			@Override
			public ExportableUser getAuthor() {
				return ofUser(message.getAuthor().get());
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
				return user.getId().asLong();
			}

			@Override
			public String getAvatarUrl() {
				return user.getAvatarUrl();
			}

			@Override
			public String getName() {
				return user.getUsername();
			}

			@Override
			public String getDisplayName() {
				return user.getGlobalName().orElse(getName());
			}

			@Override
			public Color getColor() {
				return new Color(user.getAccentColor().orElse(discord4j.rest.util.Color.BLACK).getRGB());
			}

			@Override
			public boolean isBot() {
				return user.isBot();
			}

			@Override
			public boolean isSystem() {
				return user.getUserData().system().toOptional().orElse(false);
			}

			@Override
			public List<Flag> getFlags() {
				return Flag.getFlags(user.getUserData().publicFlags().toOptional().orElse(0L).intValue()).stream().toList();
			}
		};
	}

	@Override
	public ExportableReaction ofReaction(Reaction reaction) {
		return new ExportableReaction() {
			@Override
			public String getEmoji() {
				return reaction.getEmoji().asEmojiData().name().orElse("");
			}

			@Override
			public String getEmojiFile() {
				return reaction.getEmoji().asCustomEmoji().isPresent() ? reaction.getEmoji().asCustomEmoji().get().getId().asString() : null;
			}

			@Override
			public int getReactionCount() {
				return reaction.getCount();
			}
		};
	}
}
