package com.example.mycommon.utils.event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * @author shiner
 * @param <T>
 *            单例，用于模块间通信，监听者与通知者处于同一线程
 */
public class EventBus {
	final static String TAG = "EventBus";

	private static HashMap<EventObserver, String> observerTypes;

	private static EventBus instance;

	public static EventBus getInstance() {
		synchronized (EventBus.class) {
			if (instance == null) {
				instance = new EventBus();
				observerTypes = new HashMap<EventObserver, String>();
				// new Thread() {
				// public void run() {
				// while (true) {
				// try {
				//
				// try {
				// sleep(2000);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				// Iterator<Map.Entry<String,
				// List<WeakReference<EventObserver>>>> itr =
				// observerTypes.entrySet().iterator();
				// int i = 0;
				// while (itr.hasNext()) {
				// Map.Entry<String, List<WeakReference<EventObserver>>> enr =
				// itr.next();
				// String key = enr.getKey();
				// List<WeakReference<EventObserver>> val = enr.getValue();
				// for (WeakReference<EventObserver> weakReference : val) {
				// System.out.println("ref:key:" + key + ",val:" +
				// weakReference.get());
				// i++;
				// }
				// }
				// System.out.println("ref:::size:" + i);
				//
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }
				// };
				// }.start();
			}
		}
		return instance;
	}

	/**
	 * Adds the specified observer to the list of observers. If it is already
	 * registered, it is not added a second time.
	 * 
	 * @param observer
	 *            the Observer to add.
	 */
	public void addObserver(String action, EventObserver observer) {
		if (observer == null) {
			throw new NullPointerException();
		}
		synchronized (this) {
			observerTypes.put(observer, action);
			ELog.d(TAG, "addObserver:action=" + action + ",observer:"
					+ observer);
		}
	}

	/**
	 * Returns the number of observers registered to this {@code Observable}.
	 * 
	 * @return the number of observers.
	 */
	public int countObservers(String action) {
		synchronized (this) {
			Collection<String> observers = observerTypes.values();
			if (observers != null) {
				return observers.size();
			} else {
				return 0;
			}
		}
	}

	/**
	 * Removes the specified observer from the list of observers. Passing null
	 * won't do anything.
	 * 
	 * @param observer
	 *            the observer to remove.
	 */
	public synchronized void delObserver(EventObserver observer) {
		synchronized (this) {
			String action = observerTypes.remove(observer);
			if (action != null) {
				ELog.d(TAG, "deleteObserver:action=" + action);
			}
		}
	}

	/**
	 * Removes all observers from the list of observers.
	 */
	public synchronized void delObservers(String action) {
		synchronized (this) {
			Iterator<Entry<EventObserver, String>> observers = observerTypes
					.entrySet().iterator();
			while (observers.hasNext()) {
				Entry<EventObserver, String> ent = observers.next();
				if (action.equals(ent.getValue())) {
					observers.remove();
					ELog.d(TAG, "deleteObservers:action=" + ent.getValue()
							+ ",observers:" + ent.getKey());
				}
			}
		}
	}

	/**
	 * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
	 * method for every observer in the list of observers using null as the
	 * argument. Afterwards, calls {@code clearChanged()}.
	 * <p>
	 * Equivalent to calling {@code notifyObservers(null)}.
	 */
	public void notifyObservers(String action) {
		notifyObservers(action, null);
	}

	/**
	 * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
	 * method for every Observer in the list of observers using the specified
	 * argument. Afterwards calls {@code clearChanged()}.
	 * 
	 * @param data
	 *            the argument passed to {@code update()}.
	 */
	public void notifyObservers(String action, Object data) {
		synchronized (this) {
			Iterator<Entry<EventObserver, String>> obs = observerTypes
					.entrySet().iterator();
			while (obs.hasNext()) {
				Entry<EventObserver, String> ent = obs.next();
				if (action.equals(ent.getValue())) {
					try {
						ent.getKey().update(data);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void destory() {
		observerTypes.clear();
	}

}
