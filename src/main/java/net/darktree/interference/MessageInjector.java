package net.darktree.interference;

import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

public class MessageInjector {

	private static final List<String> messages = new ArrayList<>();

	/**
	 * Used for injecting messages
	 *
	 * @param data encoded massage to inject
	 */
	public static void inject(String data) {
		messages.add(new String(Base64.getDecoder().decode(data)));
	}

	/**
	 * Used for injecting messages
	 *
	 * @param message massage to inject
	 */
	public static void injectPlain(String message) {
		messages.add(message);
	}

	@ApiStatus.Internal
	public static void consume(Consumer<String> consumer) {
		messages.forEach(consumer);
	}

}
