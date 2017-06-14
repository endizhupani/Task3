package data;

public class SuspendedSubscriptionState implements SubscriptionState {

	private Subscription subscription;
	
	
	public SuspendedSubscriptionState(Subscription subscription) {
		super();
		this.subscription = subscription;
	}

	//@Override
	public String getState() {
		// TODO Auto-generated method stub
		return "Suspended";
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
		// TODO Auto-generated method stub
		subscription.setSubscriptionState(new ActiveSubscriptionState(this.subscription));
	}

	@Override
	public void paymentPending() {
		// TODO Auto-generated method stub
		return;
	}

}
