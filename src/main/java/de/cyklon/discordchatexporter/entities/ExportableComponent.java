package de.cyklon.discordchatexporter.entities;

import java.awt.*;
import java.util.List;

public interface ExportableComponent {

	boolean isDisabled();

	String getId();


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

		Color getColor();

		Color getHoverColor();

		Color getDisabledColor();

		String getUrl();

		String getEmoji();

		String getLabel();

		enum Style {

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

			public Color getColor() {
				return color;
			}

			public Color getHoverColor() {
				return hoverColor;
			}

			public Color getDisabledColor() {
				return disabled;
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

		String getPlaceholder();

		String getIcon();

		List<Option> getOptions();

		interface Option {

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

			/**
			 * @return null if not emoji is defined
			 */
			String getEmoji();

			String getTitle();

			String getDescription();
		}
	}
}
