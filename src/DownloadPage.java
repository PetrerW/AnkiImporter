
import java.net.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author PetrerW
 * @version 01-07-2017
 * 
 * A class that downloads 
 */
public class DownloadPage {
	
	/**
	 * Language from which we translate
	 */
	public String lang1;
	
	/**
	 * destination translation language
	 */
	public String lang2;
	
	/**
	 * translator: "pons" or "google translator"
	 */
	public String translator;
	
	/**
	 * an URL address of the page from which we download the data
	 */
	public URL url;
	
	/**
	 * 
	 * @param translator "pons" or "google translator"
	 * @param lang1 Language from which we translate
	 * @param lang2 destination translation language
	 */
	DownloadPage(String translator, String lang1, String lang2){
		this.translator = translator;
		this.lang1 = lang1;
		this.lang2 = lang2;
	}
	
	/**
	 * @param url an URL address of the page from which we download the data
	 * @param translator "pons" or "google translator"
	 * @param lang1 Language from which we translate
	 * @param lang2 destination translation language
	 */
	DownloadPage(URL url, String translator, String lang1, String lang2){
		this.url = url;
		this.translator = translator;
		this.lang1 = lang1;
		this.lang2 = lang2;
	}
	/**
	 * @param word - word to translate
	 * @param lang1 - language from which we translate
	 * @param lang2 - language, to which we translate
	 * @param url - link of the website
	 * @return list of lines with webpage data
	 */
	public ArrayList<String> download(URL url, String word, String translator, String lang1, String lang2){
		
		try {
			//create a new connection
			URLConnection connection = url.openConnection();
			
			//get the input stream from the connection
			InputStream is = connection.getInputStream();
			
			//create a reader from the input stream
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        String line = null;
	        String line2 = null;
	        
	        //result list
	        ArrayList<String> downloadedLines = new ArrayList<String>();
	        
	        // read each line and add to the result list
	        while ((line = br.readLine()) != null) {
	        	if((line2 = findAnswer(line, word, translator, lang1, lang2)) != null){
		            System.out.println("FOUND!!! " + line2);
	        	}
	        	downloadedLines.add(line);
	        }
	        
	        return downloadedLines;
			
		} catch (Exception e) {
			System.out.println("DownloadPage.download("+url.toString()+") :"+ e.getMessage());
			e.printStackTrace();
		}
		
		//in case of error in the code
		return null;
	}
	
	/**
	 * 
	 * @param word - word that is being translated
	 * @return list of lines with webpage data
	 */
	public ArrayList<String> download(String word){
		return this.download(url, word, translator, lang1, lang2);
	}
	
	/**
	 * 
	 * @param url - link of the website
	 * @return list of lines with webpage data
	 */
	public static ArrayList<String> download(URL url){
		
		try {
			//create a new connection
			URLConnection connection = url.openConnection();
			
			//get the input stream from the connection
			InputStream is = connection.getInputStream();
			
			//create a reader from the input stream
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        String line = null;
	        String line2 = null;
	        
	        //result list
	        ArrayList<String> downloadedLines = new ArrayList<String>();
	        
	        // read each line and add to the result list
	        while ((line = br.readLine()) != null) {
		            downloadedLines.add(line);

	        }
	        
	        return downloadedLines;
			
		} catch (Exception e) {
			System.out.println("DownloadPage.download("+url.toString()+") :"+ e.getMessage());
			e.printStackTrace();
		}	
		
		//in case of error in the code
		return null;
	}
	
	/**
	 * @param line - a line read from website
	 * @param translator - pons or google translator
	 * @lang1 - language from which we translate
	 * @lang2 - language to which we translate
	 * @param word - a word that is being translated
	 * @return String - translated word
	 */
	private static String findAnswer(String line, String word, String translator, String lang1, String lang2){
		Pattern P = Pattern.compile(TranslationMatcher.generateAnswerPattern(translator, lang1, lang2));
		Matcher M = P.matcher(line);
		if(!line.contains(word)){
			if(M.matches())
				return line;
		}
		return null;
	}
}
