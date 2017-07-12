import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author PetrerW
 * @version 07-07-2017
 * 
 * A class that generates a request to the translator
 */

public class RequestGenerator {

	/**
	 * a default constructor
	 */
	RequestGenerator(){
		
	}
	
	/**
	 * Generate request to the server
	 * @param translator - an online translator (pons, google)
	 * @param lang1 - original language
	 * @param lang2 - translation language
	 * @param wordToTranslate - word from original language to translate
	 * example:
	 * 		"http://en.pons.com/translate?q=" + wordToTranslate + "&l=enpl&in=&lf=en"
	 */
	public static String generateRequest(String translator, String lang1, String lang2, String wordToTranslate){

		try{
			if(!isWord(wordToTranslate)){
				throw new Exception("wordToTranslate is not a word!");
			}
			switch(translator){
			case "pons":
			{
				//check if languages are correct
				if(TranslationMatcher.checkLanguage(lang1) && TranslationMatcher.checkLanguage(lang2)){
					
					//get language shortcut	
					String lang1short = convertLanguage(lang1);
					String lang2short = convertLanguage(lang2);
					
					String requestPattern ="http://en.pons.com/translate?q=" + wordToTranslate + "&l=" + lang2short + lang1short + "&in=&lf=" + lang2short;
					return requestPattern;
				}else 
					throw new Exception("Wrong language name!");

			}
			case "google translator":
			{
				//TODO: add google translator handling
				return null;
			}
			default:
				throw new Exception("Wrong translator");
		}
			//print what function with parameters and return null
		}catch(Exception e){
			System.out.println("RequestGenerator.generateRequest("+translator+", "+lang1+", " + lang2 + ", \'" + wordToTranslate + "\': " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param lang translation language
	 * @return shortcut used in request
	 */
	private static String convertLanguage(String lang){
		try{
			switch(lang){
			case "english":
				return "en";
			case "german":
				return "ge";
			case "polish":
				return "pl";
			case "russian":
				return "ru";
				
				default:
					throw new Exception ("Wrong language!");
			}
		}catch(Exception e){
			System.err.println("RequestGenerator.convertLang: " + e.getMessage() + " " + lang);
			return null;
		}
	}
	
	/**
	 * @param sentence - words that will be translated
	 * @return true if sentence parameter is combination of words divided by space
	 */
	public static boolean isSentence(String sentence){
		return Pattern.matches("(\\w* )+\\w*", sentence);
	}

	/**
	 * 
	 * @param word - a word that will be translated
	 * @return true if word is really a word
	 */
	public static boolean isWord(String word){
		return Pattern.matches("\\w*", word);
	}
}
