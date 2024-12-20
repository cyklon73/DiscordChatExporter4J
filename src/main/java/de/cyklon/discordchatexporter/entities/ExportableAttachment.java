package de.cyklon.discordchatexporter.entities;

import java.nio.ByteBuffer;
import java.util.Set;

public interface ExportableAttachment extends SerializableEntity {

	String getUrl();

	String getFileName();

	int getSize(); //in bytes

	@Override
	default long getSerialId() {
		return 0;
	}

	@Override
	default byte[] getBytes() {
		byte[] url = getUrl().getBytes();
		byte[] filename = getFileName().getBytes();

		ByteBuffer buffer = ByteBuffer.allocate(8 + 4 + url.length + 4 + filename.length + 4);

		buffer.putLong(getSerialId());

		put(buffer, url);

		put(buffer, filename);

		buffer.putInt(getSize());
		return buffer.array();
	}

	interface Unknown extends ExportableAttachment {
		static Unknown impl(String url, String fileName, int size) {
			return new Unknown() {

				@Override
				public String getFileName() {
					return fileName;
				}

				@Override
				public int getSize() {
					return size;
				}

				@Override
				public String getUrl() {
					return url;
				}
			};
		}
	}

	interface Audio extends ExportableAttachment {

		Set<String> FORMATS = Set.of("mp3", "wav", "ogg");

		static boolean supported(String extension) {
			return extension!=null && FORMATS.contains(extension.toLowerCase());
		}

		static Audio impl(String url, String fileName, int size) {
			return new Audio() {

				@Override
				public long getSerialId() {
					return 1;
				}

				@Override
				public String getUrl() {
					return url;
				}

				@Override
				public String getFileName() {
					return fileName;
				}

				@Override
				public int getSize() {
					return size;
				}
			};
		}

	}

	interface Image extends ExportableAttachment {

		static Image impl(String url, String filename, int size) {
			return new Image() {
				@Override
				public long getSerialId() {
					return 2;
				}

				@Override
				public String getFileName() {
					return filename;
				}

				@Override
				public int getSize() {
					return size;
				}

				@Override
				public String getThumbnailUrl() {
					return url;
				}

				@Override
				public String getUrl() {
					return url;
				}
			};
		}

		String getThumbnailUrl();
	}

	interface Message extends ExportableAttachment {

		Set<String> FORMATS = Set.of("txt", "log");

		static boolean supported(String extension) {
			return extension!=null && FORMATS.contains(extension.toLowerCase());
		}

		static Message impl(String url, String fileName, int size) {
			return new Message() {
				@Override
				public long getSerialId() {
					return 3;
				}

				@Override
				public String getFileName() {
					return fileName;
				}

				@Override
				public int getSize() {
					return size;
				}

				@Override
				public String getUrl() {
					return url;
				}
			};
		}
	}

	interface Code extends ExportableAttachment {
		Set<String> FORMATS = Set.of("html", "htm", "css", "properties", "gradle", "kt", "xml", "bat",
				"sh", "yml", "yaml", "cs", "c", "cpp", "java", "js", "py", "rs", "cmake", "json", "ts",
				"php", "vbs", "vb", "rb", "ps", "conf", "lua", "toml", "go", "h", "cc", "bf");

		static boolean supported(String extension) {
			return extension!=null && FORMATS.contains(extension.toLowerCase());
		}

		static Code impl(String url, String fileName, int size) {
			return new Code() {
				@Override
				public long getSerialId() {
					return 4;
				}

				@Override
				public String getFileName() {
					return fileName;
				}

				@Override
				public int getSize() {
					return size;
				}

				@Override
				public String getUrl() {
					return url;
				}
			};
		}
	}

	interface Video extends ExportableAttachment {

		static Video impl(String url, String filename, int size) {
			return new Video() {
				@Override
				public long getSerialId() {
					return 5;
				}

				@Override
				public String getUrl() {
					return url;
				}

				@Override
				public String getFileName() {
					return filename;
				}

				@Override
				public int getSize() {
					return size;
				}
			};
		}
	}
}
