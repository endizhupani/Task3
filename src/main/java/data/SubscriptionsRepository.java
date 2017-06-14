package data;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author mersistafa
 * https://www.tutorialspoint.com/design_pattern/iterator_pattern.htm
 */
public class SubscriptionsRepository implements Iterable<Subscription> {

	private Subscription[] subscriptions;
	int index;
	
	public SubscriptionsRepository(){
		subscriptions = new Subscription[10];
		index = 0;
	}
	
	public void addSubscription(Subscription s)
	{
		if (index == subscriptions.length)
		{
		Subscription[] newSubscriptions = new Subscription[subscriptions.length + 5];
		
		/**
		 * https://stackoverflow.com/questions/5785745/make-copy-of-array-java
		 */
		System.arraycopy( subscriptions, 0, newSubscriptions, 0, subscriptions.length );
			
			subscriptions = newSubscriptions;
			newSubscriptions = null;
		}
		subscriptions[index] = s;
		index++;
	}
	
	public ArrayList<String> getStringRepresentations(){
		ArrayList<String> result = new ArrayList<>();
		for (Subscription s: this)
		{
			result.add(s.toString());
		}
		return result;
	}
	/**
	 * mersistafa
	 * get subscriptions as arraylist
	 * @return
	 */
	public ArrayList<Subscription> toArrayList()
	{
		ArrayList<Subscription> result = new ArrayList<>(); 
		for(Subscription s : this)
		{
			result.add(s);
		}
		return result;
	}

	@Override
	public Iterator<Subscription> iterator() {
		return new SubscrictionIterator();
	}
	
	private class SubscrictionIterator implements Iterator<Subscription>{

		private int currIndex = 0;
		
		@Override
		public boolean hasNext() {
			return currIndex < subscriptions.length && subscriptions[currIndex] != null;
		}

		@Override
		public Subscription next() {
			return subscriptions[currIndex++];
		}
		
		
	}


}
