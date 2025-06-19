import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Curator {
	
	public static void curate(Little littles[], Little[] discardedLittles, Big bigs[], Map<Integer, Integer> pairs) throws IOException{
		sendLittleBasedPairings(littles, bigs, pairs);
		sendBigBasedPairings(littles, bigs, pairs);
		sendDiscarded(discardedLittles);
	}

	private static void sendDiscarded(Little discardedLittles[]) throws IOException{
		SendingToFile.appendingFileClearer("Discarded_Data.csv");
		SendingToFile.appendingToFile("Discarded_Data.csv", "First Name, Little Email,\n");
		for (int i = 0; i < discardedLittles.length; i++) {
			SendingToFile.appendingToFile("Discarded_Data.csv", discardedLittles[i].getName()+"\n");
		}
	}

	private static void sendLittleBasedPairings(Little littles[], Big bigs[], Map<Integer, Integer> pairs) throws IOException{
		SendingToFile.appendingFileClearer("Little_Based_Data.csv");
		SendingToFile.appendingToFile("Little_Based_Data.csv", "First Name, Little Email, Big Name, Big Email, MajorMinorList, HobbieList, PreferedContact\n");
		for (int i = 0; i < littles.length; i++) {
			String bigName = bigs[pairs.get(i)].getName();
			String[] bigPreferences =  bigs[pairs.get(i)].readPreferences();
			String littleName = littles[i].getName();
			SendingToFile.appendingToFile("Little_Based_Data.csv", littleName+","+bigName+",\""+bigPreferences[0]+"\",\""+bigPreferences[1]+"\",\""+bigPreferences[2]+"\" \n");
		}

		tempOutTester(littles, bigs, pairs);

	}

	private static void tempOutTester(Little littles[], Big bigs[], Map<Integer, Integer> pairs) throws IOException{
		SendingToFile.appendingFileClearer("Pairings.csv");
		SendingToFile.appendingToFile("Pairings.csv", "First Name, littleMajorMinor, Big Name, MajorMinorList\n");
		for (int i = 0; i < littles.length; i++) {
			String bigName = bigs[pairs.get(i)].getName().split(",")[0];
			String[] bigPreferences =  bigs[pairs.get(i)].readPreferences();
			String littleName = littles[i].getName().split(",")[0];
			String[] littlePreferences =  littles[i].readPreferences();
			SendingToFile.appendingToFile("Pairings.csv", littleName+",\""+littlePreferences[0]+"\","+bigName+",\""+bigPreferences[0]+"\" \n");
		}
	}

	private static void sendBigBasedPairings(Little littles[], Big bigs[], Map<Integer, Integer> pairs) throws IOException{

		SendingToFile.appendingFileClearer("Big_Single_Data.csv");
		SendingToFile.appendingToFile("Big_Single_Data.csv", "First Name, Big Email, Little Name, Little Email, MajorMinorList, PreferedContact\n");

		SendingToFile.appendingFileClearer("Big_Double_Data.csv");
		SendingToFile.appendingToFile("Big_Double_Data.csv", "First Name, Big Email, First Little Name, First Little Email, First MajorMinorList, First PreferedContact, Second Little Name, Second Little Email, Second MajorMinorList, Second PreferedContact\n");

		SendingToFile.appendingFileClearer("Big_Triple_Data.csv");
		SendingToFile.appendingToFile("Big_Triple_Data.csv", "First Name, Big Email, First Little Name, First Little Email, First MajorMinorList, First PreferedContact, Second Little Name, Second Little Email, Second MajorMinorList, Second PreferedContact, Third Little Name, Third Little Email, Third MajorMinorList, Third PreferedContact\n");

		Map<Integer, String> bigBasedPairs = new HashMap<>();
		for (int i = 0; i < littles.length; i++) {
			if(bigBasedPairs.containsKey(pairs.get(i))){
				String currentPair = bigBasedPairs.get(pairs.get(i));
				bigBasedPairs.put(pairs.get(i), currentPair+","+i);
			}else{
				bigBasedPairs.put(pairs.get(i), ""+i);
			}
		}

		for (Big big : bigs) {
			if(big.littlesSoFar==1){
				sendSingleBig(littles, big, bigBasedPairs);
			}else if(big.littlesSoFar==2){
				sendDoubleBig(littles, big, bigBasedPairs);
			}else{
				sendTripleBig(littles, big, bigBasedPairs);
			}
		}

	}

	private static void sendSingleBig(Little littles[], Big big, Map<Integer, String> bigBasedPairs) throws IOException{

		int key = big.getID();
		String pair = bigBasedPairs.get(key);
		String[] littlePreferences =  littles[Integer.valueOf(pair)].readPreferences();
		String littleName = littles[Integer.valueOf(pair)].getName();

		SendingToFile.appendingToFile("Big_Single_Data.csv", big.getName()+","+littleName+",\""+littlePreferences[0]+"\",\""+littlePreferences[2]+"\" \n");
	}

	private static void sendDoubleBig(Little littles[], Big big, Map<Integer, String> bigBasedPairs) throws IOException{
		int key = big.getID();
		String[] pairs = bigBasedPairs.get(key).split(",");

		int firstID = Integer.valueOf(pairs[0]);
		String[] fLittlePreferences =  littles[firstID].readPreferences();
		String fLittleName = littles[firstID].getName();

		int secondID = Integer.valueOf(pairs[1]);
		String[] sLittlePreferences =  littles[secondID].readPreferences();
		String sLittleName = littles[secondID].getName();

		SendingToFile.appendingToFile("Big_Double_Data.csv", big.getName()+","+fLittleName+",\""+fLittlePreferences[0]+"\",\""+fLittlePreferences[2]+"\"");
		SendingToFile.appendingToFile("Big_Double_Data.csv", ","+sLittleName+",\""+sLittlePreferences[0]+"\",\""+sLittlePreferences[2]+"\" \n");

	}

	private static void sendTripleBig(Little littles[], Big big, Map<Integer, String> bigBasedPairs) throws IOException{
		int key = big.getID();
		String[] pairs = bigBasedPairs.get(key).split(",");

		int firstID = Integer.valueOf(pairs[0]);
		String[] fLittlePreferences =  littles[firstID].readPreferences();
		String fLittleName = littles[firstID].getName();

		int secondID = Integer.valueOf(pairs[1]);
		String[] sLittlePreferences =  littles[secondID].readPreferences();
		String sLittleName = littles[secondID].getName();

		int thirdID = Integer.valueOf(pairs[2]);
		String[] tLittlePreferences =  littles[thirdID].readPreferences();
		String tLittleName = littles[thirdID].getName();

		SendingToFile.appendingToFile("Big_Triple_Data.csv", big.getName()+","+fLittleName+",\""+fLittlePreferences[0]+"\",\""+fLittlePreferences[2]+"\"");
		SendingToFile.appendingToFile("Big_Triple_Data.csv", ","+sLittleName+",\""+sLittlePreferences[0]+"\",\""+sLittlePreferences[2]+"\"");
		SendingToFile.appendingToFile("Big_Triple_Data.csv", ","+tLittleName+",\""+tLittlePreferences[0]+"\",\""+tLittlePreferences[2]+"\" \n");

	}
	

}
