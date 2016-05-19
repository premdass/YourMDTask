package com.phrases.controller;

public interface PhraseMatchController {
  
  public String index();
  
  /**
   * This method accepts string as inpt, and uses dictionary to look up matched words and return the matched words in a json array
   * @param inputString
   * @return
   */
  public String[] getMatchedPhrases(String inputString);

}
