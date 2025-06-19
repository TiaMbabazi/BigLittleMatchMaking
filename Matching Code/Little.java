public class Little extends Person{
	private Boolean isDiscarded = false;

	public Little(String[] rawArray, int personID){
		super(rawArray, personID);
		setInterest(rawArray[2]);
	}

	private void setInterest(String response) {
		int[] pref3 = {3};
		switch (response) {
			case "If I'm not a Little I might perish from sadness":
				int[] pref = {1};
				preferences[0] = pref;
				break;
			case "I would love to be a Little":
				int[] pref2 = {2};
				preferences[0] = pref2;
				break;
			default: preferences[0] = pref3;
				break;
		}
	}

	public Boolean isDiscarded(){
		return isDiscarded;
	}

	public void discard(){
		isDiscarded = true;
	}
}
