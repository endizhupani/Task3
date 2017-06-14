package data;

public interface SubscriptionState {
	public void startSubscription();
	public void cancelSubscription();
	public void paymentReceived();
	public void paymentPending();
	public String getState();
}
