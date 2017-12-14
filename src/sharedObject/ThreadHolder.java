package sharedObject;

import java.util.ArrayList;

public class ThreadHolder {
	public static ThreadHolder instance;
	private ArrayList<Thread> threads;

	public ThreadHolder() {
		threads = new ArrayList<>();
	}

	public ArrayList<Thread> getThreads() {
		return threads;
	}
}
