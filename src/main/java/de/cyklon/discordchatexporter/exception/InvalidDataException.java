package de.cyklon.discordchatexporter.exception;

public class InvalidDataException extends IllegalArgumentException {

	public InvalidDataException() {
	}

	public InvalidDataException(String s) {
		super(s);
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDataException(Throwable cause) {
		super(cause);
	}
}
