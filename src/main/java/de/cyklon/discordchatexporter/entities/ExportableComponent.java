package de.cyklon.discordchatexporter.entities;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.List;

public interface ExportableComponent extends SerializableEntity {

	static ExportableComponent unknown() {
		return new ExportableComponent() {
			@Override
			public boolean isDisabled() {
				return true;
			}

			@Override
			public String getId() {
				return "unknown";
			}
		};
	}

	boolean isDisabled();

	String getId();

	@Override
	default long getSerialId() {
		return 7;
	}

	@Override
	default byte[] getBytes() {
		byte[] id = getId().getBytes();

		ByteBuffer buffer = ByteBuffer.allocate(8 + 1 + 4 + id.length);

		buffer.putLong(getSerialId());

		put(buffer, isDisabled());

		put(buffer, id);

		return buffer.array();
	}

	interface Button extends ExportableComponent {

		static Button impl(Style style, String id, String url, String emoji, String label, boolean disabled) {
			return impl(style.getColor(), style.getHoverColor(), style.getDisabledColor(), id, url, emoji, label, disabled);
		}

		static Button impl(Color color, Color hoverColor, Color disabledColor, String id, String url, String emoji, String label, boolean disabled) {
			return new Button() {

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
		}

		@Override
		default long getSerialId() {
			return 8;
		}

		Color getColor();

		Color getHoverColor();

		Color getDisabledColor();

		String getUrl();

		String getEmoji();

		String getLabel();

		@Override
		default byte[] getBytes() {
			byte[] base = ExportableComponent.super.getBytes();

			int color = getColor().getRGB();
			int hoverColor = getHoverColor().getRGB();
			int disabledColor = getDisabledColor().getRGB();
			byte[] url = getUrl().getBytes();
			byte[] emoji = getEmoji().getBytes();
			byte[] label = getLabel().getBytes();

			ByteBuffer buffer = ByteBuffer.allocate(base.length + 4 + 4 + 4 + 4 + url.length + 4 + emoji.length + 4 + label.length);

			buffer.put(base);

			buffer.putInt(color);

			buffer.putInt(hoverColor);

			buffer.putInt(disabledColor);

			put(buffer, url);

			put(buffer, emoji);

			put(buffer, label);

			return buffer.array();
		}

		enum Style implements SerializableEntity {

			PRIMARY(new Color(88, 101, 242), new Color(71, 82, 196), new Color(61, 66, 99)),
			SECONDARY(new Color(79, 84, 92), new Color(71, 76, 83), new Color(59, 62, 69)),
			SUCCESS(new Color(59, 165, 92), new Color(53, 149, 83), new Color(57, 82, 76)),
			DANGER(new Color(237, 66, 69), new Color(213, 59, 62), new Color(91, 60, 65)),
			LINK(new Color(79, 84, 92), new Color(71, 76, 83), new Color(59, 62, 69));

			private final Color color, hoverColor, disabled;

			Style(Color color, Color hoverColor, Color disabled) {
				this.color = color;
				this.hoverColor = hoverColor;
				this.disabled = disabled;
			}

			@Override
			public long getSerialId() {
				return 9;
			}

			public Color getColor() {
				return color;
			}

			public Color getHoverColor() {
				return hoverColor;
			}

			public Color getDisabledColor() {
				return disabled;
			}

			@Override
			public byte[] getBytes() {
				int color = getColor().getRGB();
				int hoverColor = getHoverColor().getRGB();
				int disabledColor = getDisabledColor().getRGB();

				ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + 4 + 4);

				buffer.putLong(getSerialId());

				buffer.putInt(color);

				buffer.putInt(hoverColor);

				buffer.putInt(disabledColor);

				return buffer.array();
			}
		}
	}

	interface Menu extends ExportableComponent {

		static Menu impl(String id, String placeholder, String icon, List<Option> options, boolean disabled) {
			return new Menu() {

				@Override
				public String getId() {
					return id;
				}

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
			};
		}

		@Override
		default long getSerialId() {
			return 10;
		}

		String getPlaceholder();

		String getIcon();

		List<Option> getOptions();

		@Override
		default byte[] getBytes() {
			List<Option> opt = getOptions();

			byte[] base = ExportableComponent.super.getBytes();

			byte[] placeholder = getPlaceholder().getBytes();
			byte[] icon = getIcon().getBytes();
			byte[][] options = new byte[opt.size()][];

			int len = 0;
			for (int i = 0; i < options.length; i++) {
				options[i] = opt.get(i).getBytes();
				len += 4 + options[i].length;
			}

			ByteBuffer optBuffer = ByteBuffer.allocate(len);
			for (byte[] option : options) {
				put(optBuffer, option);
			}

			optBuffer.flip();

			ByteBuffer buffer = ByteBuffer.allocate(base.length + 4 + placeholder.length + 4 + icon.length + 4 + optBuffer.limit());

			buffer.put(base);

			put(buffer, placeholder);

			put(buffer, icon);

			buffer.putInt(optBuffer.limit());
			buffer.put(optBuffer);

			return buffer.array();
		}

		interface Option extends SerializableEntity {

			static Option impl(String emoji, String title, String description) {
				return new Option() {
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
			default long getSerialId() {
				return 11;
			}

			/**
			 * @return null if not emoji is defined
			 */
			@Nullable
			String getEmoji();

			String getTitle();

			String getDescription();

			@Override
			default byte[] getBytes() {
				byte[] emoji = getEmoji()==null ? new byte[0] : getEmoji().getBytes();
				byte[] title = getTitle().getBytes();
				byte[] description = getDescription().getBytes();

				ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + emoji.length + 4 + title.length + 4 + description.length);

				buffer.putLong(getSerialId());

				put(buffer, emoji);

				put(buffer, title);

				put(buffer, description);

				return buffer.array();
			}
		}
	}
}
