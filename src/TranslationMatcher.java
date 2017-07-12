import java.util.regex.Pattern;

/**
 * 
 * @author PetrerW
 * @version 01-07-2017
 * 
 * A class to match translation code
 */
public class TranslationMatcher {
	
	/**
	 * Pattern of an answer from pons.com translator
	 * example:
	 * 	<a href='/translate/(english|german|polish|russian)-(english|german|polish|russian)/\\w*'>\\w*</a> [\\w\\W]*
	 */
	public static String[] ponsAnswerPatterns = {"<a href='/translate/", "-", "/\\w*'>\\w*</a>( [\\w\\W]*||)"};
	
	/**
	 * A default constructor
	 */
	TranslationMatcher(){
		
	}
	
	/**
	 * Function that generates a request pattern on the basis of parameters
	 * @param translator "pons" or "google translator"
	 * @param lang1 : from which language we translate
	 * @param lang2 : to which language we translate 
	 */
	public static String generateAnswerPattern(String translator, String lang1, String lang2){
		
		try{
			switch(translator){
			case "pons":
			{
				if(checkLanguage(lang1) && checkLanguage(lang2)){
					String answerPattern;
					answerPattern = ponsAnswerPatterns[0]+lang1+ponsAnswerPatterns[1]+lang2+ponsAnswerPatterns[2];
					return answerPattern;
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
			System.out.println("TranslationMatcher.generateRequestPattern("+translator+", "+lang1+", "+lang2+"): " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * @param language name
	 * @return true if language is russian, english, german or polish
	 */
	public static boolean checkLanguage(String lang){
		
		switch(lang){
		case "english":
		case "german":
		case "polish":
		case "russian":
			return true;
			
			default:
				return false;
		}
	}
}
