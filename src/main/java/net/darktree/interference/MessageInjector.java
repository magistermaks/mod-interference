package net.darktree.interference;

import net.darktree.interference.impl.Lockable;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Base64;
import java.util.function.Consumer;

public class MessageInjector {

	private static final Lockable<String, ArrayList<String>> messages = new Lockable<>(new ArrayList<>());

	/**
	 * Used for injecting messages
	 *
	 * @param data encoded massage to inject
	 */
	public static void inject( String data ) {
		messages.get().add( new String( Base64.getDecoder().decode(data) ) );
	}

	/**
	 * Used for injecting messages
	 *
	 * @param message massage to inject
	 */
	@Deprecated
	public static void injectPlain( String message ) {
		messages.get().add(message);
	}

	@ApiStatus.Internal
	public static void consume( Consumer<String> consumer ) {
		messages.consume(consumer);
	}

}
