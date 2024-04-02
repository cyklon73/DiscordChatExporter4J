package de.cyklon.discordchatexporter.entities;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

public interface ExportableEmbed {

	Color getColor();

	Author getAuthor();

	/**
	 * @return null if no url is defined
	 */
	String getUrl();

	String getTitle();

	String getDescription();

	List<Field> getFields();

	/**
	 * @return null if no image is defined
	 */
	String getImageUrl();

	/**
	 * @return null if no thumbnail is defined
	 */
	String getThumbnailUrl();

	Footer getFooter();

	interface Author {

		String getName();

		/**
		 * @return null if no url is defined
		 */
		String getUrl();

		/**
		 * @return null if no icon is defined
		 */
		String getIconUrl();
	}

	interface Field {

		String getName();

		String getValue();

		boolean isInline();
	}

	interface Footer {

		String getContent();

		OffsetDateTime getTimestamp();

		/**
		 * @return null if no icon is defined
		 */
		String getIconUrl();
	}
}
