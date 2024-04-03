package de.cyklon.discordchatexporter.wrapper;

import de.cyklon.discordchatexporter.entities.*;
import discord4j.core.object.Embed;
import discord4j.core.object.component.MessageComponent;
import discord4j.core.object.entity.Attachment;
import discord4j.core.object.reaction.Reaction;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;
import net.dv8tion.jda.api.interactions.components.Component;

public interface APIWrapper<PC, GC, G, A, C, E, M, U, R, EA, EF, EFO> {

	static APIWrapper<PrivateChannel, GuildMessageChannel, Guild, Message.Attachment, Component, MessageEmbed, Message, User, MessageReaction, MessageEmbed.AuthorInfo, MessageEmbed.Field, MessageEmbed.Footer> JDA() {
		return JDAWrapper.instance;
	}

	static APIWrapper<discord4j.core.object.entity.channel.PrivateChannel, discord4j.core.object.entity.channel.GuildMessageChannel, discord4j.core.object.entity.Guild, Attachment, MessageComponent, Embed, discord4j.core.object.entity.Message, discord4j.core.object.entity.User, Reaction, Embed.Author, Embed.Field, Embed.Footer> discord4j() {
		return Discord4JWrapper.instance;
	}

	ExportableChannel ofPrivateChannel(PC channel);

	ExportableChannel ofGuildChannel(GC channel);

	ExportableGuild ofGuild(G guild);

	ExportableAttachment ofAttachment(A attachment);

	ExportableComponent ofComponent(C component);

	ExportableEmbed ofEmbed(E embed);

	ExportableEmbed.Author ofEmbedAuthor(EA author);

	ExportableEmbed.Field ofEmbedField(EF field);

	ExportableEmbed.Footer ofEmbedFooter(E embed, EFO footer);

	ExportableMessage ofMessage(M message);

	ExportableUser ofUser(U user);

	ExportableReaction ofReaction(R reaction);
}
