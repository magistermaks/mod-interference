package net.darktree.interference;

import java.util.ArrayList;
import java.util.Base64;
import java.util.function.Consumer;

public class MessageInjector {

	private static final ArrayList<String> messages = new ArrayList<>();
	private static boolean mutable = true;

	public static void supply( String data ) {
		plain( new String( Base64.getDecoder().decode(data) ) );
	}

	public static void plain( String message ) {
		if( mutable ) {
			messages.add( message );
		}else{
			throw new RuntimeException("Unable to add new message after the consumer has run!");
		}
	}

	public static void consume( Consumer<String> consumer ) {
		mutable = false;
		messages.forEach(consumer);
	}

}
