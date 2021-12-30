package net.darktree.interference.impl;

import java.util.function.Consumer;

public class Lockable<E, T extends Iterable<E>> {

	private final T type;
	private boolean lock;

	public Lockable(T type) {
		this.type = type;
		this.lock = false;
	}

	public T get() {
		if(this.lock) throw new RuntimeException("Unable to access locked type!");
		return this.type;
	}

	public void consume( Consumer<E> consumer ) {
		this.lock = true;
		this.type.forEach(consumer);
	}

}
