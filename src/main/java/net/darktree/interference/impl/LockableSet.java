package net.darktree.interference.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class LockableSet<E> {

	private Set<E> type = new HashSet<>();

	public void add(E entry) {
		if(this.type == null) throw new RuntimeException("Unable to access locked type!");
		this.type.add(entry);
	}

	public void consume( Consumer<E> consumer ) {
		this.type.forEach(consumer);
		this.type = null;
	}

}
