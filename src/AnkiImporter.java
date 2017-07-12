import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author PetrerW
 * @version 01-07-2017
 * 
 * Import and generate Anki cards on the basis of the word listed in pdf or a sheet
 */		
public class AnkiImporter {
		public static void main(String args[]){
			Tester.test();
		}
		
		public String[] answerParts = {"<a href='/translate/", "-", "/" , "'>", "</a>" };
		/**
		 * 
		 * @param line - line with translation, for example <a href='/translate/polish-english/tapicerka'>tapicerka</a>
		 * @return translated word
		 */
		
		/**
		 * 
		 * @param line
		 * @param translator
		 * @param lang1
		 * @param lang2
		 * @return raw translated word in language 2
		 */
		public String extractTranslation(String line, String translator, String lang1, String lang2){
			try{
				switch(translator){
				case "pons":
				{
					if(TranslationMatcher.checkLanguage(lang1) && TranslationMatcher.checkLanguage(lang2)){
						line = line.replaceFirst(answerParts[0], "");
						line = line.replaceFirst(lang1+answerParts[1]+lang2, "");
						line = line.replaceFirst("/", "");
						line = line.replaceFirst("'>", "");
						line = line.replaceFirst("</a>", "");
						line = line.substring(line.length()/2);
						return line;
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
				System.err.println("AnkiImporter.extractTranslation: " + e.getMessage());
				return null;
			}
		}
}
