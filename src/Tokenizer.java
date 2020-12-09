import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;

/**
  * This class takes a text file as input and tokenize sentences and tokens.
  * The output is a stream of tokens, with <s> and </s> indicating sentence boundaries.
  */
public class Tokenizer {

	/**
	  * args[0]: source text file
	  * args[1]: output tokenized file
	  */
	public static void main(String[] args) throws IOException {
		String inputFileName = "./data/train_reviews.txt";//args[0];
		String outputFileName = "./data/train_tokens.txt";//args[1];

		FileWriter fw = new FileWriter(outputFileName);
		BufferedWriter bw = new BufferedWriter(fw);

		DocumentPreprocessor dp = new DocumentPreprocessor(inputFileName);
		// for each sentence
		for (List<HasWord> sentence : dp) {
			// beginning of a sentence
			bw.write("<s>\r\n");
			// for each word in the sentence
			for (HasWord w : sentence) {
				bw.write(w.word().toLowerCase() + "\r\n");
			}
			// end of a sentence
			bw.write("</s>\r\n");
		}

		bw.close();
		fw.close();
	}
}
