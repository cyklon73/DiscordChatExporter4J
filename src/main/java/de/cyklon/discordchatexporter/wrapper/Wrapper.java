package de.cyklon.discordchatexporter.wrapper;

import de.cyklon.discordchatexporter.entities.ExportableEmbed;

import java.awt.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

abstract class Wrapper {

	protected String nonNull(String s) {
		return Objects.requireNonNullElse(s, "");
	}

	protected Color nonNull(Color c) {
		return Objects.requireNonNullElse(c, Color.BLACK);
	}

	protected Color nonNull(Optional<Color> c) {
		return c.orElse(Color.BLACK);
	}

	protected OffsetDateTime asOffset(Instant instant) {
		return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
	}

	protected OffsetDateTime asOffset(Optional<Instant> instant) {
		return instant.map(this::asOffset).orElseGet(OffsetDateTime::now);
	}

	protected ExportableEmbed.Author dummyAuthor() {
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

	protected ExportableEmbed.Footer dummyFooter() {
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
}
