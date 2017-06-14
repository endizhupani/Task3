package businesslogiclayer;


/**
 * Quellen: https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
 */
public class MockupOderApiImplementation {
	private MockupOrApiInterface moa;
	public MockupOderApiImplementation(MockupOrApiInterface moa){
		this.moa = moa;
	}
	
	public void laden(){
		moa.objekteLaden();
	}
	
	public void speichern(Object object){
		moa.objektepeichern(object);
	}
}
