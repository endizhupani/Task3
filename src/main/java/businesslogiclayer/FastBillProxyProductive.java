package businesslogiclayer;

import data.MokupDaten;

public class FastBillProxyProductive implements FastBillProxyInterface {

	@Override
	public void kundenDatenLaden() {
		MokupDaten mokDaten = new MokupDaten();
		mokDaten.MokupLaden();
	}

}
