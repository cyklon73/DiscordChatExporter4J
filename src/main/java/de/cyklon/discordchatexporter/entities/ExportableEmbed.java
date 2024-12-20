package de.cyklon.discordchatexporter.entities;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.List;

public interface ExportableEmbed extends SerializableEntity {

	Color getColor();

	Author getAuthor();

	/**
	 * @return null if no url is defined
	 */
	@Nullable
	String getUrl();

	String getTitle();

	String getDescription();

	List<Field> getFields();

	/**
	 * @return null if no image is defined
	 */
	@Nullable
	String getImageUrl();

	/**
	 * @return null if no thumbnail is defined
	 */
	@Nullable
	String getThumbnailUrl();

	Footer getFooter();

	@Override
	default long getSerialId() {
		return 12;
	}

	@Override
	default byte[] getBytes() {
		List<Field> fld = getFields();

		int color = getColor().getRGB();
		byte[] author = getAuthor().getBytes();
		byte[] url = getUrl()==null ? new byte[0] : getUrl().getBytes();
		byte[] title = getTitle().getBytes();
		byte[] description = getDescription().getBytes();
		byte[][] fields = new byte[fld.size()][];
		byte[] imageUrl = getImageUrl()==null ? new byte[0] : getImageUrl().getBytes();
		byte[] thumbnailUrl = getThumbnailUrl()==null ? new byte[0] : getThumbnailUrl().getBytes();
		byte[] footer = getFooter().getBytes();

		int len = 0;
		for (int i = 0; i < fields.length; i++) {
			fields[i] = fld.get(i).getBytes();
			len += 4 + fields[i].length;
		}

		ByteBuffer fieldBuffer = ByteBuffer.allocate(len);
		for (byte[] field : fields) {
			put(fieldBuffer, field);
		}

		fieldBuffer.flip();

		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + 4 + author.length + 4 + url.length + 4 + title.length + 4 + description.length + 4 + fieldBuffer.limit() + 4 + imageUrl.length + 4 + thumbnailUrl.length + 4 + footer.length);

		buffer.putLong(getSerialId());

		buffer.putInt(color);

		put(buffer, author);

		put(buffer, url);

		put(buffer, title);

		put(buffer, description);

		buffer.putInt(fieldBuffer.limit());
		buffer.put(fieldBuffer);

		put(buffer, imageUrl);

		put(buffer, thumbnailUrl);

		put(buffer, footer);

		return buffer.array();
	}

	interface Author extends SerializableEntity {

		String getName();

		/**
		 * @return null if no url is defined
		 */
		@Nullable
		String getUrl();

		/**
		 * @return null if no icon is defined
		 */
		@Nullable
		String getIconUrl();

		@Override
		default long getSerialId() {
			return 13;
		}

		@Override
		default byte[] getBytes() {
			byte[] name = getName().getBytes();
			byte[] url = getUrl()==null ? new byte[0] : getUrl().getBytes();
			byte[] iconUrl = getIconUrl()==null ? new byte[0] : getIconUrl().getBytes();

			ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + name.length + 4 + url.length + 4 + iconUrl.length);

			buffer.putLong(getSerialId());

			put(buffer, name);

			put(buffer, url);

			put(buffer, iconUrl);

			return buffer.array();
		}
	}

	interface Field extends SerializableEntity {

		String getName();

		String getValue();

		boolean isInline();

		@Override
		default long getSerialId() {
			return 14;
		}

		@Override
		default byte[] getBytes() {
			byte[] name = getName().getBytes();
			byte[] value = getValue().getBytes();

			ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + name.length + 4 + value.length + 1);

			buffer.putLong(getSerialId());

			put(buffer, name);

			put(buffer, value);

			put(buffer, isInline());

			return buffer.array();
		}
	}

	interface Footer extends SerializableEntity {

		String getContent();

		/**
		 * @return null if no timestamp is defined
		 */
		@Nullable
		OffsetDateTime getTimestamp();

		/**
		 * @return null if no icon is defined
		 */
		@Nullable
		String getIconUrl();

		@Override
		default long getSerialId() {
			return 15;
		}

		@Override
		default byte[] getBytes() {
			byte[] content = getContent().getBytes();
			byte[] zoneId = getTimestamp()==null ? new byte[0] : getTimestamp().toZonedDateTime().getZone().getId().getBytes();
			long timestamp = getTimestamp()==null ? 0 : getTimestamp().toInstant().toEpochMilli();
			byte[] iconUrl = getIconUrl()==null ? new byte[0] : getIconUrl().getBytes();

			ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + content.length + 4 + zoneId.length + 8 + 4 + iconUrl.length);

			buffer.putLong(getSerialId());

			put(buffer, content);

			put(buffer, zoneId);

			buffer.putLong(timestamp);

			put(buffer, iconUrl);

			return buffer.array();
		}
	}
}
