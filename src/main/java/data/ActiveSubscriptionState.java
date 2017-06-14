package data;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ActiveSubscriptionState implements SubscriptionState {
	
	private Subscription subscription;
	
	
	
	public ActiveSubscriptionState(Subscription subscription) {
		
		this.subscription = subscription;
		this.subscription.setValidUntil(ZonedDateTime.now(ZoneOffset.UTC).plusMonths(1));
	}

	//@Override
	public String getState() {
		// TODO Auto-generated method stub
		return "Active";
	}

	@Override
	public void startSubscription() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void cancelSubscription() {
		// TODO Auto-generated method stub
		subscription.setSubscriptionState(new InactiveSubscriptionState(this.subscription));
	}

	@Override
	public void paymentReceived() {
		subscription.setSubscriptionState(new ActiveSubscriptionState(this.subscription));
		return;
	}

	@Override
	public void paymentPending() {
		// TODO Auto-generated method stub
		subscription.setSubscriptionState(new SuspendedSubscriptionState(this.subscription));
	}

}
