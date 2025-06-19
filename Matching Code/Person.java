public class Person {
	protected final String name;
	protected final Boolean inTeam;
	protected final int PersonID;
	protected static final int NUM_OF_PREFERENCES = 5;
	protected final int[][] preferences = new int[NUM_OF_PREFERENCES][];
	protected Boolean isMatched;
	protected final String[] readablePreferences = new String[3];


	public Person(String[] rawArray, int PersonID){
		this.PersonID = PersonID;
		this.name = rawArray[1]+", "+rawArray[0];
		this.isMatched = false;

		readablePreferences[1] = rawArray[3];
		String[] hobbies = "Exercising, Puzzles/board games, Video Games, DnD, Knitting, Drawing, Painting, Writing, Journaling, Reading, Crafting, Photography, Dancing, Singing, Theater, Theater tech, Makeup, Foreing languages, Listening to Music, Partying, Creating musinc, Fashion, Skating, Cooking, Baking, Travling, Hiking, Biking, Thrifting".split(",");
		setArrayPosition(rawArray[3].split(","), hobbies,1);
		String[] placesOfInterest = "Fimbel lab and makerspace, Clapp Laboratory, Kendall, Kendade, Chapin, Library Stacks (where the books are), Library study rooms/quiet spaces, Library Saw mentor/Lits spaces, Upper Lake, The Horse Barn, Lower Lake, Pratt Library, Old Blanch, The Green, Art Museum, Amphitheater, Thirsty Mind, Village Commons, Mount Holyoke (mountain), North Hampton (the town next to Smith), Smith, Amherst (College), Amherst (the Person town), Umass, Hampshire, Secret/less known corners of campus, Campfire Pit, Delle's Hill, Hampshire (woods)".split(",");
		setArrayPosition(rawArray[4].split(","), placesOfInterest,2);
		
		readablePreferences[0] = rawArray[5];
		String[] majorsMinors = "Africana Studies, Ancient Studies, Anthropology, Arabic, Architectural Studies, Art History, Art Studio, Asian Studies, Astronomy, Biochemistry, Biological Sciences, Chemistry, Chinese, Classics, Computer Science, Critical Social Thought, Dance, Data Science, East Asian Studies, Economics, Education, Educational Studies, English, Entrepreneurship, Organizations, and Society, Environmental Studies, Film, Media, Theater, Film Studies, French, Gender Studies, Geography, Geology, German Studies, Greek, History, Italian, Japanese, Jewish Studies, Latin, Latin American Studies, Latina/o Studies, Mathematics, Music, Neuroscience and Behavior, Philosophy, Physics, Politics, Psychology, Psychology and Education, Religion, Romance Languages and Cultures, Russian and Eurasian Studies, Russian Culture and Literature, Russian Language, Sociology, South Asian Studies, Spanish, Statistics, Theatre Arts".split(",");
		setArrayPosition(rawArray[5].split(","), majorsMinors,3);
		this.inTeam = rawArray[6].equals("No") ? false: true;
		if(inTeam){
			String[] allTeams = "Basketball, Cross Country, Field Hockey, Lacrosse, Riding, Rowing, Soccer, Squash, Swimming and Diving, Tennis, Track and Field, Volleyball, Dressage Team, Fencing Team, Ice Hockey, Rugby Football, Ultimate Frisbee, Western Riding Team".split(",");
			if(rawArray.length<=8){
				setArrayPosition((rawArray[7]).split(","), allTeams, 4);
			}else{
				setArrayPosition((rawArray[7]+","+rawArray[8]).split(","), allTeams, 4);
			}
		}else{
			int[] temp = {0};
			preferences[4] = temp;
		}
		readablePreferences[2] = rawArray[9]+", "+rawArray[10]+", "+rawArray[11];


	}	

	protected void setArrayPosition(String[] allOptions, String[] thisResponse, int prefIndex) {
		int[] responseCurated = new int[thisResponse.length];
		for (int i = 0; i < thisResponse.length; i++) {
			int responseId = whereInArray(thisResponse[i], allOptions);
			if(responseId>0){
				responseCurated[i] = responseId;
			}
		}
		preferences[prefIndex] = responseCurated;
	}

	protected int whereInArray(String target, String[] array){
		int targetID = 1;
		for (String item : array) {
			if(item.equals(target)){
				return targetID;
			}
			targetID++;
		}
		return 0;
	}

	public String getName(){
		return name;
	}

	public Boolean getInTeam(){
		return inTeam;
	}

	public void updatePreferences(int firstIndex, int secondIndex, int newPrefernce){
		preferences[firstIndex][secondIndex] = newPrefernce;
	}

	public int[][] getPreferences(){
		return preferences;
	}

	public int getID(){
		return PersonID;
	}

	public void removePerson(){
		isMatched = true;
	}

	public Boolean checkIsMatched(){
		return isMatched;
	}

	public String[] readPreferences(){
		return readablePreferences;
	}
}
