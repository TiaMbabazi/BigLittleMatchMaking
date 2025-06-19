import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Matcher {

	private final Big[] bigs;

	private final Little[] littles;

	private final Little[] discardedLittles;

	private final Integer[][] compatibility;

	public Matcher(Little[] rawLittles, Big[] bigs){
		int nonDisLittles =  getLittleMax(rawLittles);
		this.discardedLittles = new Little[rawLittles.length - nonDisLittles];
		this.littles = discardLittles(rawLittles, nonDisLittles);
		this.bigs = bigs;
		this.compatibility = new Integer[littles.length][bigs.length];
	}

	private int getLittleMax(Little[] rawLittles){
		int count = 0;
		for (Little little : rawLittles) {
			if(!little.isDiscarded()){
				count++;
			}
		}
		return count;
	}

	private Little[] discardLittles(Little[] rawLittles, int nonDiscarded){
		Little[] littles = new Little[nonDiscarded];
		nonDiscarded = 0;
		int discarded = 0;
		for (Little little : rawLittles) {
			if(!little.isDiscarded()){
				littles[nonDiscarded] = little;
				nonDiscarded++;
			}else{
				discardedLittles[discarded] = little;
				discarded++;
			}
		}
		return littles;
	}
	
	/**
	 * Method to match all pairings and send result to a file
	 * @throws IOException
	 */
	public void matchThem() throws IOException{
		//set up the class
		int numberMatched = 0;
		setCompatibility();
		Map<Integer, Integer> pairs = new HashMap<>();
		int bigID = 0;
		int littleID = 0;
		//get each little's ideal score match
		Integer[] maxScores = makeMax(littles);

		while(numberMatched < littles.length){
			int min = Collections.min(Arrays.asList(maxScores));
			littleID = findIndex(maxScores, min);
			bigID = findIndex(compatibility[littleID], min);
			//make sure the big isn't alrady full
			while(bigs[bigID].checkIsMatched()){
				compatibility[littleID][bigID] = 0;
				//find the next highest number
				maxScores[littleID] = getMax(littleID);
				//repeat
				min = Collections.min(Arrays.asList(maxScores));
				littleID = findIndex(maxScores, min);
				bigID = findIndex(compatibility[littleID], min);
			}
			pairs.put(littleID, bigID);

			littles[littleID].removePerson();
			bigs[bigID].decreaseCapability();
			maxScores[littleID] = 10000;

			numberMatched ++;
		}

		if(!checkFinalPairings(pairs)){
			System.out.println("Not all");
		}
		
		Curator.curate(littles, discardedLittles, bigs, pairs);
	}

	private Boolean checkFinalPairings(Map<Integer, Integer> pairs){
		Boolean temp = true;
		for (Big big : bigs) {
			if(!big.hasLittle()){
				System.out.println(big.name);
				temp = false;
			}
		}
		return temp;
	}

	private int findIndex(Integer[] arr, int key){
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == key) {
                return i;
            }
		}
		return -1;
	}


	private Integer[] makeMax(Little[] lits){

		Integer[] maxScore = new Integer[lits.length];

		for (int j = 0; j < lits.length; j++) {
			int maxScoreTemp = getMax(j);
			maxScore[j] = maxScoreTemp;
		}
		System.out.println(Arrays.toString(maxScore));
		return maxScore;
	}

	private int getMax(int littleID){
		int maxScoreTemp = 0;
			for (int compt : compatibility[littleID]) {
				maxScoreTemp = (maxScoreTemp < compt) ? compt : maxScoreTemp;
			}
		return maxScoreTemp;
	}

	private void setCompatibility(){
		Map<Integer, Integer> importance = createImportanceMap();
		for (int i = 0; i < littles.length; i++) {
			compatibilityHelper(littles[i], i, importance);
		}
	}

	private Map<Integer, Integer> createImportanceMap(){
		Map<Integer, Integer> importance = new HashMap<>();
		for (int i = 1; i < 6; i++) {
			int importace = 1;
			if(i==3){
				importace =3;
			}
			importance.put(i, importace);
		}
		return importance;
	}

	/**
	 * Called by setCompatability, gets a little and determines comp with all bigs
	 * updates instance variable map compatability
	 * @param little
	 * @param littleID
	 * @param importance
	 */
	private void compatibilityHelper(Little little, int littleID, Map<Integer, Integer> importance){
		int[][] littlePreferences = little.getPreferences();
		int compt = 0;
		for (Big big : bigs) {
			int[][] currentBigPreferences = big.getPreferences();
			//run through each preferences
			for (int i = 1; i < littlePreferences.length; i++) {
				//run through all responses for that preference
				for (int j = 0; j < littlePreferences[i].length; j++) {
					//check if it matches the big's
					if(inArray(littlePreferences[i][j], currentBigPreferences[i])){
						compt += importance.get(i);
					}
				}
			}
			//if they both are in a sports team null it
			if(currentBigPreferences[4][0] != 0 &&littlePreferences[4][0] != 0){
				if(inSameTeam(big, currentBigPreferences, little, littlePreferences)){
					compt = 0;
				}
			}
			compatibility[littleID][big.getID()] = compt;
			compt = 0;
		}
	}

	private Boolean inArray(int target, int[] array){
		for (int item : array) {
			if(item == target){
				return true;
			}
		}
		return false;
	}

	private Boolean inSameTeam(Big big, int[][] currentBigPreferences, Little little, int[][] littlePreferences){
		for (int i = 0; i < littlePreferences.length; i++) {
			for (int j = 0; j < littlePreferences[4].length; j++) {
				if(inArray(littlePreferences[4][j], currentBigPreferences[i])){
					return true;
				}
			}
		}
		return false;
	}
	
}
