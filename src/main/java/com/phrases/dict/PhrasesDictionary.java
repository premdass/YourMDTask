package com.phrases.dict;

public interface PhrasesDictionary {
  
  /**
   * This method will return the matching phrases for the provided input
   * by looking up at the dictionary
   * @param inputString
   * @return
   */
  public String[] getMatchingPhrases(String inputString);

}
