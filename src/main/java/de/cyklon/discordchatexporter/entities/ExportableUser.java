package de.cyklon.discordchatexporter.entities;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

public interface ExportableUser extends SerializableEntity {

	long getId();

	String getAvatarUrl();

	String getName();

	String getDisplayName();

	Color getColor();

	boolean isBot();

	boolean isSystem();

	List<Flag> getFlags();

	@Override
	default long getSerialId() {
		return 19;
	}

	@Override
	default byte[] getBytes() {
		List<Flag> fl = getFlags();

		byte[] avatarUrl = getAvatarUrl().getBytes();
		byte[] name = getName().getBytes();
		byte[] displayName = getDisplayName().getBytes();
		int color = getColor().getRGB();
		byte[][] flags = new byte[fl.size()][];

		int flagsLen = 0;
		for (int i = 0; i < flags.length; i++) {
			flags[i] = fl.get(i).getBytes();
			flagsLen += 4 + flags[i].length;
		}

		ByteBuffer flagBuffer = ByteBuffer.allocate(flagsLen);
		for (byte[] flag : flags) {
			put(flagBuffer, flag);
		}

		flagBuffer.flip();


		ByteBuffer buffer = ByteBuffer.allocate(8 + 8 + 4 + avatarUrl.length + 4 + name.length + 4 + displayName.length + 4 + 1 + 1 + 4 + flagBuffer.limit());

		buffer.putLong(getSerialId());

		buffer.putLong(getId());

		put(buffer, avatarUrl);

		put(buffer, name);

		put(buffer, displayName);

		buffer.putInt(color);

		put(buffer, isBot());

		put(buffer, isSystem());

		buffer.putInt(flagBuffer.limit());
		buffer.put(flagBuffer);

		return buffer.array();
	}

	/**
	 * This enum was implemented by <a href="https://github.com/discord-jda/JDA">JDA</a>
	 * <p>
	 * Represents the bit offsets used by Discord for public flags
	 */
	enum Flag implements SerializableEntity
	{
		STAFF(0, "Discord Employee"),
		PARTNER(1, "Partnered Server Owner"),
		HYPESQUAD(2, "HypeSquad Events"),
		BUG_HUNTER_LEVEL_1(3, "Bug Hunter Level 1"),

		// HypeSquad
		HYPESQUAD_BRAVERY(6, "HypeSquad Bravery"),
		HYPESQUAD_BRILLIANCE(7, "HypeSquad Brilliance"),
		HYPESQUAD_BALANCE(8, "HypeSquad Balance"),

		EARLY_SUPPORTER(9, "Early Supporter"),

		TEAM_USER(10, "Team User"),
		BUG_HUNTER_LEVEL_2(14, "Bug Hunter Level 2"),
		VERIFIED_BOT(16, "Verified Bot"),
		VERIFIED_DEVELOPER(17, "Early Verified Bot Developer"),
		CERTIFIED_MODERATOR(18, "Discord Certified Moderator"),
		/**
		 * Bot uses only HTTP interactions and is shown in the online member list
		 */
		BOT_HTTP_INTERACTIONS(19, "HTTP Interactions Bot"),
		/**
		 * User is an <a href="https://support-dev.discord.com/hc/articles/10113997751447">Active Developer</a>
		 */
		ACTIVE_DEVELOPER(22, "Active Developer"),

		UNKNOWN(-1, "Unknown");

		/**
		 * Empty array of UserFlag enum, useful for optimized use in {@link java.util.Collection#toArray(Object[])}.
		 */
		public static final Flag[] EMPTY_FLAGS = new Flag[0];

		private final int offset;
		private final int raw;
		private final String name;

		Flag(int offset, String name)
		{
			this.offset = offset;
			this.raw = 1 << offset;
			this.name = name;
		}

		@Override
		public long getSerialId() {
			return 20;
		}

		@Override
		public byte[] getBytes() {
			ByteBuffer buffer = ByteBuffer.allocate(8 + 4);

			buffer.putLong(getSerialId());

			buffer.putInt(offset);

			return buffer.array();
		}

		/**
		 * The readable name as used in the Discord Client.
		 *
		 * @return The readable name of this UserFlag.
		 */
		public String getName()
		{
			return this.name;
		}

		/**
		 * The binary offset of the flag.
		 *
		 * @return The offset that represents this UserFlag.
		 */
		public int getOffset()
		{
			return offset;
		}

		/**
		 * The value of this flag when viewed as raw value.
		 * <br>This is equivalent to: <code>1 {@literal <<} {@link #getOffset()}</code>
		 *
		 * @return The raw value of this specific flag.
		 */
		public int getRawValue()
		{
			return raw;
		}

		/**
		 * Gets the first UserFlag relating to the provided offset.
		 * <br>If there is no UserFlag that matches the provided offset,
		 * {@link #UNKNOWN} is returned.
		 *
		 * @param  offset
		 *         The offset to match a UserFlag to.
		 *
		 * @return UserFlag relating to the provided offset.
		 */
		public static Flag getFromOffset(int offset)
		{
			for (Flag flag : values())
			{
				if (flag.offset == offset)
					return flag;
			}
			return UNKNOWN;
		}

		/**
		 * A set of all UserFlags that are specified by this raw int representation of
		 * flags.
		 *
		 * @param  flags
		 *         The raw {@code int} representation if flags.
		 *
		 * @return Possibly-empty EnumSet of UserFlags.
		 */
		public static EnumSet<Flag> getFlags(int flags)
		{
			final EnumSet<Flag> foundFlags = EnumSet.noneOf(Flag.class);

			if (flags == 0)
				return foundFlags; //empty

			for (Flag flag : values())
			{
				if (flag != UNKNOWN && (flags & flag.raw) == flag.raw)
					foundFlags.add(flag);
			}

			return foundFlags;
		}

		/**
		 * This is effectively the opposite of {@link #getFlags(int)}, this takes 1 or more UserFlags
		 * and returns the bitmask representation of the flags.
		 *
		 * @param  flags
		 *         The array of flags of which to form into the raw int representation.
		 *
		 * @throws java.lang.IllegalArgumentException
		 *         When the provided UserFlags are null.
		 *
		 * @return bitmask representing the provided flags.
		 */
		public static int getRaw(Flag... flags){
			int raw = 0;
			for (Flag flag : flags)
			{
				if (flag != null && flag != UNKNOWN)
					raw |= flag.raw;
			}

			return raw;
		}

		/**
		 * This is effectively the opposite of {@link #getFlags(int)}. This takes a collection of UserFlags
		 * and returns the bitmask representation of the flags.
		 * <br>Example: {@code getRaw(EnumSet.of(UserFlag.STAFF, UserFlag.HYPESQUAD))}
		 *
		 * @param  flags
		 *         The flags to convert
		 *
		 * @throws java.lang.IllegalArgumentException
		 *         When the provided UserFLags are null.
		 *
		 * @return bitmask representing the provided flags.
		 *
		 * @see java.util.EnumSet EnumSet
		 */
		public static int getRaw(Collection<Flag> flags)
		{
			return getRaw(flags.toArray(EMPTY_FLAGS));
		}
	}
}
