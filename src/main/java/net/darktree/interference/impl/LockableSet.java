package net.darktree.interference.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class LockableSet<E> {

	private final Set<E> type = new HashSet<>();
	private boolean lock = false;

	public void add(E entry) {
		if(this.lock) throw new RuntimeException("Unable to append to a locked set!");
		this.type.add(entry);
	}

	public void consume( Consumer<E> consumer ) {
		this.type.forEach(consumer);
		this.lock = true;
	}

	public void clear() {
		this.type.clear();
	}

}
