public class Big extends Person{

	protected int littlesLeft;
	protected int littlesSoFar;
	protected Boolean hasAtLeastOne = false;

	public Big(String[] rawArray, int personID){
		super(rawArray, personID);
		this.littlesSoFar =0;
		this.littlesLeft = setInterest(rawArray[2]);
	}

	private int setInterest(String response) {
		int[] pref3 = {3};
		switch (response) {
			case "Just 1 is enough thank you!":
			int[] pref = {1};
				preferences[0] = pref;
				break;
			case "I can have up to 2":
			int[] pref2 = {2};
				preferences[0] = pref2;
				break;
			default: preferences[0] = pref3;
				break;
		}
		return preferences[0][0];
	}

	public int getLeft(){
		return littlesLeft;
	}

	public void decreaseCapability(){
		hasAtLeastOne = true;
		littlesSoFar++;
		littlesLeft--;
		if(littlesLeft == 0){
			removePerson();
		}
	}

	public Boolean hasLittle(){
		return hasAtLeastOne;
	}
	
}
