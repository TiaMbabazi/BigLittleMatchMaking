/**
 * This class is parent to {@QueryWriter}, {@ScribeCaller}, {@Scribe}.
 * It contains methods to send text to file by appending it to exisitng onformaiton or overwriting
 * the text into the file.
 * It can also clear the data in a specified file.
 * 
 * @version 8/4/23
 * @author Tia Mbabazi
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class SendingToFile{

	/**
	 * Method to clear specified append-type file
	 * @param fileToClear
	 * @throws IOException
	 */
	public static void appendingFileClearer(String filepath) throws IOException{
		File clearFile = new File(System.getProperty("user.dir")+"\\"+filepath);
		FileWriter writer = new FileWriter(clearFile);
		writer.write("");
		writer.close();
	}
	
	/**
	 * Method to append current text to file
	 * @param queryString
	 * @param classType
	 * @throws IOException
	 */
	public static void appendingToFile(String filepath, String textToAppend) throws IOException{
		File outfile = new File(System.getProperty("user.dir")+"\\"+filepath);
		//we are appending each query instead of overwriting the file each time
		FileWriter fileWriter = new FileWriter(outfile, true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(textToAppend);
		bufferedWriter.close();
	}

	/**
	 * Method to overwrite current text to file
	 * @throws IOException
	 */
	public static void overwriteToFile(String filepath, String textToSend) throws IOException{
		File outFile= new File(System.getProperty("user.dir")+"\\"+filepath); 	
		outFile.createNewFile();		
		//Instantiating the PrintStream class
		PrintStream stream = new PrintStream(outFile);
		System.setOut(stream);
		//Printing values to file
		System.out.println(textToSend);
		stream.close(); 
	}
}
