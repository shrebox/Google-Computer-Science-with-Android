/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordlist;
    private HashSet<String> wordset;
    private HashMap<String,ArrayList> letterToWord;
    private HashMap<Integer,ArrayList> sizeToWords;
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordlist = new ArrayList<String>();
        wordset = new HashSet<String>();
        letterToWord = new HashMap<String,ArrayList>();
        sizeToWords = new HashMap<Integer, ArrayList>();
        int length;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            wordlist.add(word);
            length = word.length();

            if(sizeToWords.containsKey(word.length())){
                sizeToWords.get(length).add(word);
            }

            else {
                ArrayList<String > value = new ArrayList<String>();
                value.add(word);
                sizeToWords.put(length,value);
            }

            String sword = sortLetters(word);
            if(letterToWord.containsKey(sword)) {
                letterToWord.get(sword).add(word);
               //ArrayList<String> temp = letterToWord.get(sword);
                //temp.add(word);
                //letterToWord.put(sword,temp);
              //  Log.d("google",letterToWord.toString());
            }

            else {
                ArrayList<String> value = new ArrayList<String>();
                value.add(word);
                letterToWord.put(sword, value);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {

        if(wordset.contains(word)){

            if(word.contains(base)) {
                return false;
            }

            else {
                return true;
            }
        }

        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String check = sortLetters(targetWord);

        for(String temp: wordset)
        {
            if(temp.length() == check.length())
            {
                String temp2 = sortLetters(temp);

                if(temp2.equals(check))
                {
                    result.add(temp);
                }
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> validWord =new  ArrayList<String>();
        String sortedWord = sortLetters(word);

        Map<Character,Integer> wordFrequency =  new HashMap<Character,Integer>();

        for(char i=0;i<sortedWord.length();i++) {

            if(wordFrequency.containsKey(sortedWord.charAt(i))){

                wordFrequency.put(sortedWord.charAt(i), wordFrequency.get(sortedWord.charAt(i))+1);
            }

            else {
                wordFrequency.put(sortedWord.charAt(i), 1);
            }
        }

        boolean flag;

        for(String temp: wordset) {
            flag =true;
            if(isGoodWord(temp,word) && temp.length() == word.length()+1) {
                int counter = 0;
                for(char i=0;i<temp.length();i++) {
                    if ((wordFrequency.containsKey(temp.charAt(i)))) {

                        if(wordFrequency.get(temp.charAt(i))==1){
                            wordFrequency.remove(temp.charAt(i));
                        }

                        else
                        {
                            int tval = wordFrequency.get(temp.charAt(i))-1;
                            wordFrequency.put(temp.charAt(i),tval);
                        }

                    }

                    else
                    {
                        if (counter == 0) {
                            counter++;
                        } else {
                            flag = false;
                            break;
                        }
                    }
                }
                if(flag) {
                    result.add(temp);
                }

                wordFrequency.clear();
                for(char i=0;i<sortedWord.length();i++) {

                    if(wordFrequency.containsKey(sortedWord.charAt(i))){

                        wordFrequency.put(sortedWord.charAt(i), wordFrequency.get(sortedWord.charAt(i))+1);
                    }

                    else {
                        wordFrequency.put(sortedWord.charAt(i), 1);
                    }
                }

            }
        }

        return result;
    }

    public String pickGoodStarterWord() {

        Random randomgen  = new Random();
        boolean check = true;
        ArrayList<String> words = new ArrayList<String>();
        words = sizeToWords.get(wordLength);
        int index = randomgen.nextInt(words.size());
        String item =  words.get(index);

        while(check){

            if(getAnagramsWithOneMoreLetter(item).size()>=MIN_NUM_ANAGRAMS){
                check = false;
            }

            else{
                sizeToWords.get(wordLength).remove(item); // Optimization
                index = randomgen.nextInt(words.size());
                item = words.get(index);
            }
        }

        if(wordLength<MAX_WORD_LENGTH) {
            wordLength++;
        }
      return item;
    }

    public String sortLetters (String input)
    {
        String original = input;
        char[] chars = original.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);

        return sorted;
    }
}
