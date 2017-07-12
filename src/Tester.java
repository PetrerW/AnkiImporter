import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author PetrerW
 * @version 07-07-2017
 * 
 * A class with tests helpful in code development
 */

public class Tester {
	
	Tester(){
		//empty
	}
	
	/**
	 * Carry out all the tests
	 */
	public static void test(){
		//Tester.test1("tapicerka");
		//System.out.println("\'");
		//Tester.test2();
		//TranslationMatcher.generateAnswerPattern("pons", "english", "german");
		//Tester.testIsWord("A word");
		//Tester.testIsSentence("A word");
		//Tester.testIsWord("Sheep");
		//Tester.testIsSentence("Sheep");
		//Tester.testGenerateRequest("pons", "polish", "english", "upholstery");
		//Tester.testAnswerPattern("pons", "polish", "english", "upholstery");
		Tester.testFindAnswerWithPattern("pons", "polish", "english", "upholstery");
	}
	
	//test reading from the webpage
	public static void test1(String wordToTranslate){
		try {
			//create an example url
			URL url = new URL(new String("http://en.pons.com/translate?q=" + wordToTranslate + "&l=enpl&in=&lf=en"));
			
			//download data from the page and save it to the array list
			ArrayList<String> lines = DownloadPage.download(url);
			//String A = "<a href=\'/translate/(english|german|polish|russian)-(english|german|polish|russian)/([a-z]+|[A-Z]+)'>([a-z]+|[A-Z]+)</a>";
			//String S = "<a href=\'/translate/(english|german|polish|russian)-(english|german|polish|russian)/\\w*\'>\\w*</a>";
			//Pattern P = Pattern.compile("<a href=\'\/translate\/(english|german|polish|russian)-(english|german|polish|russian)\/\w*\'>\w*<\/a>");
			//print all lines
			for(String line: lines){
				System.out.println(line);
			}
			
		} catch (Exception e) {
			System.out.println("AnkiImporter.test1("+wordToTranslate+") :" + e.getMessage());
		}
	}
	
	/**
	 * Check if the matcher matches
	 */
	public static void test2(){
		Pattern P = Pattern.compile("<a href='/translate/(english|german|polish|russian)-(english|german|polish|russian)/\\w*'>\\w*</a>");
		Matcher m = P.matcher("<a href='/translate/english-polish/upholstery'>upholstery</a>");
		System.out.println("Matcher matches: " + m.matches());
	}
	
	/**
	 * test RequestGenerator.isWord(String word) function
	 */
	public static void testIsWord(String word){
		System.out.println("\'" + word + "\'" + " is a word: " + RequestGenerator.isWord(word));
	}
	
	/**
	 * test RequestGenerator.isSentence(String sentence) function
	 */
	public static void testIsSentence(String sentence){
		System.out.println("\'" + sentence + "\'" + " is a sentence: " + RequestGenerator.isSentence(sentence));
	}
	
	/**
	 * test DownloadPage.download() request and server's answer
	 * @param translator
	 * @param lang1
	 * @param lang2
	 * @param wordToTranslate
	 */
	public static void testGenerateRequest(String translator, String lang1, String lang2, String wordToTranslate){
		//TODO: Check why an order of language matters
		String request = RequestGenerator.generateRequest(translator, lang1, lang2, wordToTranslate);
		System.out.println("Generated request: " + request);
		ArrayList<String> DownloadedData = new ArrayList<String>();
		try {
			DownloadPage DP = new DownloadPage(new URL(request), translator, lang1, lang2);
			DownloadedData = DP.download(wordToTranslate);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String line: DownloadedData){
			System.out.println(line);
		}
	}
	
	/**
	 * 
	 * @param translator
	 * @param lang1
	 * @param lang2
	 * @param wordToTranslate
	 * 
	 * test if any word matches the answer pattern
	 */
	public static void testAnswerPattern(String translator, String lang1, String lang2, String wordToTranslate){
		String PatternString = TranslationMatcher.generateAnswerPattern(translator, lang1, lang2);
		System.out.println("Answer Pattern: " + PatternString);
		
		String ExampleAnswer = "<a href='/translate/polish-english/tapicerka'>tapicerka</a>";
		System.out.println("Example answer: " + ExampleAnswer);
		
		Pattern P = Pattern.compile(PatternString);
		Matcher M = P.matcher(ExampleAnswer);
		System.out.println("Matcher matches? " + M.matches());
	}
	
	/**
	 * 
	 * @param translator
	 * @param lang1
	 * @param lang2
	 * @param wordToTranslate
	 * Find with matcher an answer in the lines got from server
	 */
	public static void testFindAnswerWithPattern(String translator, String lang1, String lang2, String wordToTranslate){
		
		//TODO: Long line doesn't match!
		String request = RequestGenerator.generateRequest(translator, lang1, lang2, wordToTranslate);
		System.out.println("Generated request: " + request);
		ArrayList<String> DownloadedData = new ArrayList<String>();
		
		String PatternString = TranslationMatcher.generateAnswerPattern(translator, lang1, lang2);
		System.out.println("Answer Pattern: " + PatternString);
		
		Pattern P = Pattern.compile(PatternString);
		Matcher M; 
		
		try {
			DownloadPage DP = new DownloadPage(new URL(request), translator, lang1, lang2);
			DownloadedData = DP.download(wordToTranslate);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String longLine = "<a href='/translate/polish-english/tapicerka'>tapicerka</a> <span class=\"genus\"><acronym title=\"feminine\">f</acronym></span>      </div> matches? ";
		M=P.matcher(longLine);
		System.out.println(longLine + " matches? " + M.matches());
		
		String shortLine = "<a href='/translate/polish-english/tapicerka'>tapicerka</a>";
		M=P.matcher(shortLine);
		System.out.println(shortLine + " matches? " + M.matches());
		
		for(String line: DownloadedData){
			M = P.matcher(line);
			
			if(M.matches())	
						System.out.println(line);
		}
	}
}
