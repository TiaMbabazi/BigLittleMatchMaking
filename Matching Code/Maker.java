import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Maker {

	private final Little[] littles;

	private final Big[] bigs;

	private int maxBigCount;

	public Maker() throws IOException{
		this.maxBigCount = 0;
		//create all littles
		File inFile = new File("littleTab.txt");
		Scanner inLittle = new Scanner(inFile);
		int numLittles = Integer.valueOf(inLittle.nextLine());
		this.littles = new Little[numLittles];
		makePeople(inLittle, numLittles, true);
		inLittle.close();
		

		// create all bigs
		inFile = new File("bigsTab.txt");
		Scanner inBigs = new Scanner(inFile);
		int numBigs = Integer.valueOf(inBigs.nextLine());
		this.bigs = new Big[numBigs];
		makePeople(inBigs, numBigs, false);
		inBigs.close();

		makePerfectRatio();

		//begin matching
		Matcher match = new Matcher(littles, bigs);
		match.matchThem();
	}

	private void makePerfectRatio(){
		int numLittles = littles.length;
		int numBigs = 0;
		for (Big big : bigs) {
			numBigs += (big.getPreferences())[0][0];
		}
		if(numLittles<numBigs){
			removeBigs(3);
		}else if(numLittles>numBigs){
			removeLittles(numLittles-numBigs);
		}

	}

	private void removeLittles(int amount){
		int count = 0;
		for (int i = littles.length-1; (count < amount)&& i>=0; i--) {
			if(littles[i].getPreferences()[0][0]==3){
				count ++;
				littles[i].discard();
			}
		}
	}

	private void removeBigs(int max){
		for (Big big : bigs) {
			if(big.getPreferences()[0][0]==max){
				big.updatePreferences(0, 0, 2);
			}
		}
	}

	private void makePeople(Scanner inScanner, int numPeople, Boolean isLittle) throws FileNotFoundException{
		for (int i = 0; i < numPeople; i++) {
			String currentLine = inScanner.nextLine();
			String[] rawArray = currentLine.split("\t");
			// System.out.println(Arrays.toString(rawArray));
			if(isLittle){
				littles[i] = new Little(rawArray,i);
			}else{
				bigs[i] = new Big(rawArray,i);
				maxBigCount += bigs[i].getPreferences()[0][0];
			}
		}
	}

	public int getMaxBigs(){
		return maxBigCount;
	}

	public static void main(String[] args) throws IOException {
		Maker making = new Maker();
		making.makePeople(null, 0, null);
	}
	
}
