package com.phrases.dict.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.googlecode.concurrenttrees.common.Iterables;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;
import com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree;
import com.googlecode.concurrenttrees.radixinverted.InvertedRadixTree;
import com.phrases.dict.PhrasesDictionary;

@Component
public class RadixPhrasesDictionaryImpl implements InitializingBean, PhrasesDictionary {
  
  private static final Logger logger = LoggerFactory.getLogger(RadixPhrasesDictionaryImpl.class);

  /**
   * We are using the inverted radix tree implementation for fast and efficient dictionary lookup.
   * Also we dont store node values to save space
   * 
   */
  private InvertedRadixTree<VoidValue> tree = new ConcurrentInvertedRadixTree<VoidValue>(new DefaultCharArrayNodeFactory());


  @Autowired
  private ResourceLoader resourceLoader;

  @Value("${phrasesFile}")
  private String phrasesFile;


  @Value("${phrasesDefaultFile}")
  private String phrasesDefaultFile;



  /**
   * This method will return the matching phrases for the provided input by using the dictionary
   * service built using radix tree The inputs are space padded , since the tree implementation
   * returns partial matches which we dont need
   * 
   * @param inputString
   * @return
   */
  public String[] getMatchingPhrases(String inputString) {
    inputString = inputString.trim().replaceAll(" +", " ");
    inputString = " " + inputString + " ";
    List<CharSequence> results = Iterables.toList(tree.getKeysContainedIn(inputString));
    String[] matchedPhrases = results.toArray(new String[results.size()]);
    for (int i = 0; i < matchedPhrases.length; i++) {
      matchedPhrases[i] = matchedPhrases[i].trim();
    }
    logger.info("For string "+inputString+" found matches "+results);
    return matchedPhrases;
  }


  /**
   * This method looks for the phrase file location provided in the environment if no phrasefile
   * location is provided, it uses the default one
   * 
   * Once phrase file is loaded, it is put in the radix tree for search and match
   * 
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    BufferedReader reader = null;
    try{
      if (phrasesFile != null && !"".equalsIgnoreCase(phrasesFile)) {
        logger.info("phrasesFile is provided. Using that instead of default file");
        Resource resource = resourceLoader.getResource("file:///" + phrasesFile);
        reader = new BufferedReader(new FileReader(resource.getFile()));
      } else {
        logger.info("phrasesFile is not provided. Using the default file");
        Resource resource = resourceLoader.getResource(phrasesDefaultFile);
        reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
      }
      String phrase = null;
      while ((phrase = reader.readLine()) != null) {
        phrase = " " + phrase + " ";
        tree.put(phrase, VoidValue.SINGLETON);
      }
      logger.info("Dictionary has been initialised");
    }finally{
      reader.close(); 
    }    

  }


  public String getPhrasesFile() {
    return phrasesFile;
  }


  public void setPhrasesFile(String phrasesFile) {
    this.phrasesFile = phrasesFile;
  }



  public ResourceLoader getResourceLoader() {
    return resourceLoader;
  }


  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }


  public String getPhrasesDefaultFile() {
    return phrasesDefaultFile;
  }


  public void setPhrasesDefaultFile(String phrasesDefaultFile) {
    this.phrasesDefaultFile = phrasesDefaultFile;
  }

}
