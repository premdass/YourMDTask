package com.phrases.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phrases.controller.PhraseMatchController;
import com.phrases.dict.PhrasesDictionary;


@RestController
public class PhraseMatcherControllerImpl implements PhraseMatchController{
  
  @Autowired
  private PhrasesDictionary phrasesDictionary;
  
  @RequestMapping("/")
  public String index(){
    return "Welcome . Please use /matchphrase route to test the assignment";
  }
  
  @RequestMapping("/matchphrase")
  public String[] getMatchedPhrases(@RequestParam(name="input", required=false )String inputString){
    if(inputString == null ){
      return new String[]{"Please pass the string to be Phrase matched using request parameter named 'input' . Example: '/matchphrase?input=i have headache' or '/matchphrase?input=I%20have%20a%20headache' "};
    }
    if(phrasesDictionary != null){
      return phrasesDictionary.getMatchingPhrases(inputString);
    }
    return null;   
    
  }
  
  

}
