package de.cyklon.discordchatexporter.entities;

public record GuildImpl(String name, long id, String iconUrl) implements ExportableGuild {
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
}
