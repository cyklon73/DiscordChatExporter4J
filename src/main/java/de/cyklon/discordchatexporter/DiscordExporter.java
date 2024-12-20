package de.cyklon.discordchatexporter;

import de.cyklon.discordchatexporter.entities.*;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static de.cyklon.discordchatexporter.DiscordHtml.*;

public final class DiscordExporter {

	//0-4
	private static final String defaultAvatar = "https://cdn.discordapp.com/embed/avatars/%s.png";

	private static final DateTimeFormatter simpleFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	private static final DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss");

	static String getAvatar(int i) {
		return String.format(defaultAvatar, i);
	}

	public static String getAvatarById(long id) {
		if (id==0) return getAvatar(0);
		return getAvatar((int) (Math.abs(id%10)%4)+1);
	}

	public static String export(ExportableChannel channel, ZoneId zoneId) {
		return export(channel, OffsetDateTime.now(zoneId));
	}

	private static String export(ExportableChannel channel, OffsetDateTime exportDate) {
		ExportableGuild guild = channel.getGuild();

		String dateTimeTooltip = exportDate.format(fullFormatter);
		String dateTime = exportDate.format(simpleFormatter);
		String createdAt = channel.getCreatedAt().format(simpleFormatter);

		List<ExportableMessage> messages = channel.getMessages();
		StringBuilder messagesHtml = new StringBuilder();

		for (ExportableMessage message : messages) {

			String content = message.getContent().isBlank() ? "" : Message.content(message.getContent(), message.isEdited());

			StringBuilder attachments = new StringBuilder();

			for (ExportableAttachment attachment : message.getAttachments()) {
				String at = null;
				if (attachment instanceof ExportableAttachment.Unknown unknown) at = Attachment.unknown(unknown.getUrl(), unknown.getFileName(), formatSize(unknown.getSize()));
				else if (attachment instanceof ExportableAttachment.Audio audio) at = Attachment.audio(audio.getUrl(), audio.getFileName(), formatSize(audio.getSize()));
				else if (attachment instanceof ExportableAttachment.Image image) at = Attachment.image(image.getUrl(), image.getThumbnailUrl(), image.getFileName());
				else if (attachment instanceof ExportableAttachment.Message msg) at = Attachment.message(msg.getUrl(), msg.getFileName(), formatSize(msg.getSize()));
				else if (attachment instanceof ExportableAttachment.Code code) at = Attachment.code(code.getUrl(), code.getFileName(), formatSize(code.getSize()));
				else if (attachment instanceof ExportableAttachment.Video video) at = Attachment.video(video.getUrl(), video.getFileName());

				if (at!=null) attachments.append(at);
			}

			StringBuilder embeds = new StringBuilder();

			for (ExportableEmbed embed : message.getEmbeds()) {
				String author = Embed.author(embed.getAuthor().getIconUrl(), embed.getAuthor().getName(), embed.getAuthor().getUrl());

				StringBuilder fields = new StringBuilder();

				for (ExportableEmbed.Field field : embed.getFields()) {
					fields.append(Embed.field(field.getName(), field.getValue(), field.isInline()));
				}

				String image = embed.getImageUrl()==null ? "" : Embed.image(embed.getImageUrl());

				String thumbnail = embed.getThumbnailUrl()==null ? "" : Embed.thumbnail(embed.getThumbnailUrl());

				String footerTimestamp = embed.getFooter().getTimestamp()==null ? "" : " • " + Script.timestamp(embed.getFooter().getTimestamp().format(fullFormatter), embed.getFooter().getTimestamp().format(simpleFormatter));

				String footer = embed.getFooter().getIconUrl()==null ? Embed.footer(embed.getFooter().getContent() + footerTimestamp) : Embed.footerImage(embed.getFooter().getIconUrl(), embed.getFooter().getContent() + footerTimestamp);

				embeds.append(Embed.body(
						embed.getColor(),
						author,
						Embed.title(embed.getTitle(), embed.getUrl()),
						Embed.description(embed.getDescription()),
						fields.toString(),
						image,
						thumbnail,
						footer
				));
			}

			StringBuilder components = new StringBuilder();

			for (ExportableComponent component : message.getComponents()) {
				if (component instanceof ExportableComponent.Button button) components.append(Component.button(button.isDisabled(), button.getId(), button.getColor(), button.getHoverColor(), button.getDisabledColor(), button.getUrl(), button.getEmoji(), button.getLabel()));
				else if (component instanceof ExportableComponent.Menu menu) {
					StringBuilder options = new StringBuilder();
					for (ExportableComponent.Menu.Option option : menu.getOptions()) {
						options.append(option.getEmoji()==null ? Component.menuOptions(option.getTitle(), option.getDescription()) : Component.menuOptionsEmoji(option.getEmoji(), option.getTitle(), option.getDescription()));
					}
					components.append(Component.menu(menu.isDisabled(), menu.getId(), menu.getPlaceholder(), menu.getIcon(), options.toString()));
				}
			}

			StringBuilder reactions = new StringBuilder();

			for (ExportableReaction reaction : message.getReactions()) {
				reactions.append(reaction.getEmojiFile()==null ? Reaction.emoji(reaction.getEmoji(), reaction.getReactionCount()) : Reaction.customEmoji(reaction.getEmoji(), reaction.getEmojiFile(), reaction.getReactionCount()));
			}

			String msgContent = Message.message(message.getId(),
					message.getCreatedAt().format(fullFormatter),
					message.getCreatedAt().format(simpleFormatter),
					content,
					attachments.toString(),
					embeds.toString(),
					components.toString(),
					reactions.toString());

			ExportableUser user = message.getAuthor();

			String tag;

			if (user.isSystem()) tag = Message.systemTag();
			else tag = user.isBot() ? Message.botTag(user.getFlags().contains(ExportableUser.Flag.VERIFIED_BOT)) : "";

			String msg = Message.start(message.getId(), Message.referenceUnknown(), user.getAvatarUrl(), user.getId(), Message.referenceUnknown(), user.getDisplayName(),
					tag, message.getCreatedAt().format(fullFormatter), message.getCreatedAt().format(simpleFormatter), content,
					attachments.toString(),
					embeds.toString(),
					components.toString(),
					reactions.toString());


			messagesHtml.append(msg);
		}

		return base(guild.getName(), channel.getName(), channel.getTopic(), channel.getSubject(), messagesHtml.toString(),
				dateTimeTooltip, dateTime, guild.getIconUrl(), guild.getId(), channel.getID(), createdAt,
				messages.size(), "", "", "", "");
	}

	private static String formatSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "bytes", "KB", "MB", "GB", "TB", "PB", "EB" };
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

}
