package data;

public class InactiveSubscriptionState implements SubscriptionState {

	private Subscription subscription;
	
	public InactiveSubscriptionState(Subscription subscription) {
		
		this.subscription = subscription;
	}

	//@Override
	public String getState() {
		// TODO Auto-generated method stub
		return "Inactive";
	}

	public void startSubscription() {
		// TODO Auto-generated method stub
		subscription.setSubscriptionState(new ActiveSubscriptionState(this.subscription));
	}

	//@Override
	public void cancelSubscription() {
		// TODO Auto-generated method stub
		return;
	}

	//@Override
	public void paymentReceived() {
		// TODO Auto-generated method stub
		return;
	}

	//@Override
	public void paymentPending() {
		// TODO Auto-generated method stub
		return;
	}

}
