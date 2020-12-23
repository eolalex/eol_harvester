package com.bibalex.taxonmatcher.handlers;

import org.apache.logging.log4j.Logger;
import org.globalnames.parser.ScientificNameParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import scala.util.parsing.json.JSON;

import java.util.HashMap;

/**
 * Created by Amr.Morad on 4/16/2017.
 */
public class GlobalNamesHandler {

    private JSONParser parser;
    private JSONObject result;
    private static Logger logger;

    public GlobalNamesHandler(String name){
        parser = new JSONParser();
        logger = LogHandler.getLogger(GlobalNamesHandler.class.getName());
        result = getParsedJson(name);
    }
    public GlobalNamesHandler(){
        parser = new JSONParser();
        logger = LogHandler.getLogger(GlobalNamesHandler.class.getName());
    }

    private JSONObject getParsedJson(String name){
        String jsonStr = ScientificNameParser.instance()
                .fromString(name)
                .renderCompactJson();
        try {
            return (JSONObject)parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getAttribute(String attribute){
        Object att = result.get(attribute);
        return att == null ? false : att;
    }

    public String getCanonicalForm(){
        return (Boolean) result.get("parsed") ? (String) ((JSONObject) result.get("canonical_name")).get("value") : "";
    }

    public Boolean isParsed (){
        return  (Boolean) result.get("parsed") ;
    }

    public JSONArray getAuthors(){
        JSONObject authorship = null;
        JSONObject basionym_authorship = null;
        JSONArray infraspecific_epithets = null;
        if ((Boolean) result.get("parsed"))
        {
            JSONArray details= (JSONArray) result.get("details");
            JSONObject  details_first= (JSONObject)details.get(0);

            if((infraspecific_epithets = (JSONArray) details_first.get("infraspecific_epithets"))!= null){
                JSONObject  infraspecific_epithets_first= (JSONObject)infraspecific_epithets.get(0);
                if((authorship = (JSONObject) infraspecific_epithets_first.get("authorship"))!=null) {
                    if ((basionym_authorship = (JSONObject) authorship.get("basionym_authorship")) != null) {
                        JSONArray authors_arrray = (JSONArray) basionym_authorship.get("authors");
                        return authors_arrray;

                    }
                }

            }
            else if(details_first.get("specific_epithet")!=null){
                JSONObject specific_epithet = (JSONObject) details_first.get("specific_epithet");
                if(specific_epithet.get("authorship")!=null){
                  if((authorship = (JSONObject) specific_epithet.get("authorship"))!=null) {
                      if ((basionym_authorship = (JSONObject) authorship.get("basionym_authorship")) != null) {
                          JSONArray authors_arrray = (JSONArray) basionym_authorship.get("authors");
                          return authors_arrray;
                      }
                  }
                }
            }

        }
        return null;


    }
    public HashMap<String, String> getAuthorAttributes(){
        HashMap<String, String> authorInfo = new HashMap<>();
        if ((Boolean) result.get("parsed"))
        {
            if(result.get("normalized") != null) {authorInfo.put("normalized",result.get("normalized").toString());}

            if(result.get("publication") != null) {authorInfo.put("publication",result.get("publication").toString());}

            JSONArray details= (JSONArray) result.get("details");
            JSONObject  details_first = (JSONObject)details.get(0);

            if(details_first.containsKey("genus"))
            {
                authorInfo.put("genus", ((JSONObject)details_first.get("genus")).get("value").toString());
            }

            if(details_first.containsKey("infrageneric_epithet"))
            {
                authorInfo.put("infrageneric_epithet", ((JSONObject)details_first.get("infrageneric_epithet")).get("value").toString());

            }
            if(details_first.containsKey("infraspecific_epithets"))
            {
                JSONArray infrasep = (JSONArray) details_first.get("infraspecific_epithets");
                JSONObject infrasepJson = (JSONObject)infrasep.get(0);
                if(infrasepJson.containsKey("authorship")){
                    authorInfo.put("infraspecificAuthorship", ((JSONObject)infrasepJson.get("authorship")).get("value").toString());
                    if(((JSONObject) infrasepJson.get("authorship")).containsKey("basionym_authorship")){
                        JSONObject basionym = (JSONObject)((JSONObject)infrasepJson.get("authorship")).get("basionym_authorship");
                        if(basionym.containsKey("authors")){authorInfo.put("infraspecificAuthors", basionym.get("authors").toString());}
                        if(basionym.containsKey("combination_authorship")){authorInfo.put("infraspecificCombinationAuthors", ((JSONObject)basionym.get("combination_authorship")).get("authors").toString());}
                        if(basionym.containsKey("ex_authors")){authorInfo.put("infraspecificExAuthors", basionym.get("ex_authors").toString());}
                        if(basionym.containsKey("year")){authorInfo.put("infraspecificYear", ((JSONObject)basionym.get("year")).get("value").toString());}
                    }
                }
                 if(infrasepJson.containsKey("value"))
                    authorInfo.put("specific_epithet", infrasepJson.get("value").toString());
            }


            if(details_first.containsKey("specific_epithet"))
            {
                JSONObject sep= (JSONObject) details_first.get("specific_epithet");
                if(sep.containsKey("authorship")){
                    authorInfo.put("specificAuthorship", ((JSONObject)sep.get("authorship")).get("value").toString());
                    if(((JSONObject) sep.get("authorship")).containsKey("basionym_authorship")){
                        JSONObject basionym = (JSONObject)((JSONObject) sep.get("authorship")).get("basionym_authorship");
                        if(basionym.containsKey("authors")){authorInfo.put("specificAuthors", basionym.get("authors").toString());}
                        if(basionym.containsKey("combination_authorship")){authorInfo.put("specificCombinationAuthors", ((JSONObject)basionym.get("combination_authorship")).get("authors").toString());}
                        if(basionym.containsKey("ex_authors")){authorInfo.put("specificExAuthors", basionym.get("ex_authors").toString());}
                        if(basionym.containsKey("year")){authorInfo.put("specificYear", ((JSONObject)basionym.get("year")).get("value").toString());}
                    }
                }
                if(sep.containsKey("value"))
                    authorInfo.put("specific_epithet", sep.get("value").toString());
            }
            if(details_first.containsKey("uninomial"))
            {
                JSONObject uni= (JSONObject) details_first.get("uninomial");
                if(uni.containsKey("authorship")){
                    authorInfo.put("uninomialAuthorship", ((JSONObject)uni.get("authorship")).get("value").toString());
                    if(((JSONObject) uni.get("authorship")).containsKey("basionym_authorship")){
                        JSONObject basionym = (JSONObject)((JSONObject) uni.get("authorship")).get("basionym_authorship");
                        if(basionym.containsKey("authors")){authorInfo.put("uninomialAuthors", basionym.get("authors").toString());}
                        if(basionym.containsKey("ex_authors")){authorInfo.put("uninomialExAuthors", basionym.get("ex_authors").toString());}
                        if(basionym.containsKey("year")){authorInfo.put("uninomialYear", ((JSONObject)basionym.get("year")).get("value").toString());}
                    }
                }
                if(uni.containsKey("value"))
                    authorInfo.put("uninomial", uni.get("value").toString());
            }
//            if(details_first.get("genus")!=null){
//                authorInfo.put("genus", ((JSONObject)details_first.get("genus")).get("value"));
//                //authorInfo.add(genus);
//            }
//
//            if(details_first.get("infrageneric_epithet")!=null){
//                authorInfo.put("infrageneric_epithet", ((JSONObject)details_first.get("infrageneric_epithet")).get("value"));
//               // authorInfo.add(infrageneric_epithet);
//            }
//            if(details_first.get("infraspecific_epithets")!= null){
//                infraspecific_epithets.add((JSONArray) details_first.get("infraspecific_epithets"));
//                JSONObject  infraspecific_epithets_first= (JSONObject)infraspecific_epithets.get(0);
//                if((JSONObject) infraspecific_epithets_first.get("authorship")!=null) {
//                    JSONObject authorship_data = (JSONObject) infraspecific_epithets_first.get("authorship");
//                    authorInfo.put("authorship", authorship_data);
//
//                    if (( (JSONObject) authorship_data.get("basionym_authorship")) != null)
//                        basionym_authorship_data.add((JSONArray) ((JSONObject)authorship.get("basionym_authorship")).get("authors"));
//                        authorInfo.put("basionym_authorship",basionym_authorship_data);
//                    authorInfo.put ("year",((JSONObject)((JSONObject)authorship_data.get("basionym_authorship")).get("year")).get("value"));
////                    authorInfo.add(authorship);
////                    authorInfo.add(year);
////                    authorInfo.add(basionym_authorship);
//
//                }
//            }
//
//            else if(details_first.get("specific_epithet")!=null){
//                JSONObject  specific_epithet_data =(JSONObject) details_first.get("specific_epithet");
//                authorInfo.put("specific_epithet",specific_epithet_data.get("value"));
//                if(specific_epithet_data.get("authorship")!=null){
//                    if(((JSONObject) specific_epithet_data.get("authorship")).get("value")!=null) {
//                        JSONObject authorship_data =(JSONObject)specific_epithet_data.get("authorship");
//                        authorInfo.put("authorship",authorship_data.get("value"));
//                        if (((JSONObject) authorship_data.get("basionym_authorship")) != null)
//                            basionym_authorship_data.add((JSONArray) ((JSONObject)authorship_data.get("basionym_authorship")).get("authors"));
//                            authorInfo.put("basionym_authorship",basionym_authorship_data);
//                        authorInfo.put("year",((JSONObject)((JSONObject)authorship_data.get("basionym_authorship")).get("year")).get("value"));
////                        authorInfo.add(authorship);
////                        authorInfo.add(specific_epithet);
////                        authorInfo.add(year);
////                        authorInfo.add(basionym_authorship);
//
//                    }
//                }
//            }
//            else if(details_first.get("uninomial")!= null){
//                uninomial.add(details_first.get("uninomial"));
//                JSONObject  uninomail_first= (JSONObject)uninomial.get(0);
//                if((JSONObject) uninomail_first.get("authorship")!=null) {
//                    JSONObject authorship_data = (JSONObject) uninomail_first.get("authorship");
//                    authorInfo.put("authorship", authorship_data.get("value"));
////                    authorInfo.add(authorship);
//
//                    if (( (JSONObject) authorship_data.get("basionym_authorship")) != null)
//                    {
//                        if(((JSONObject)authorship_data.get("basionym_authorship")).get("authors") !=null) {
//                            basionym_authorship_data.add((JSONArray) ((JSONObject) authorship_data.get("basionym_authorship")).get("authors"));
//                            authorInfo.put("basionym_authorship", basionym_authorship_data);
//                        }
//                        if(((JSONObject)authorship_data.get("basionym_authorship")).get("year") != null)
//                            authorInfo.put ("year",((JSONObject)((JSONObject)authorship_data.get("basionym_authorship")).get("year")).get("value"));
//
////                        authorInfo.add(year);
////                        authorInfo.add(basionym_authorship);
//
//                    }
//                }
//            }
        }
        return authorInfo;


    }

    public boolean hasAuthority(){
        JSONArray nameParts = (JSONArray) result.get("positions");
        if (nameParts != null) {
            for (int i = 0; i < nameParts.size(); i++) {
                JSONArray partArray = (JSONArray) nameParts.get(i);
                if (partArray.get(0).toString().contains("author")) {
                    System.out.println("has authority");
                    logger.info("name: " + result.get("canonical_name") + " has authorship");
                    return true;
                }
            }
        }
        logger.info("name: " +  result.get("canonical_name") + " doesnot have authority");
        System.out.println("will return false");
        return false;
    }

    public static void main(String [] args){

//        ResourceHandler.initialize("config.properties");
//        LogHandler.initializeHandler();

        GlobalNamesHandler gnh = new GlobalNamesHandler("Anthocerotophyta Rothm. ex Stotler & Crand.-Stotler");
        HashMap<String, String> arr = gnh.getAuthorAttributes();

            System.out.println(arr);
//        if(arr.containsKey("authorship")) {
//            System.out.print(arr.get("authorship").toString());
//        }

//        gnh.hasAuthority("Parus major Linnaeus, 1788");
//        System.out.println(gnh.getAuthors("Parus major"));
//        System.out.println("================================================");
//        System.out.println(gnh.getAuthors("Parus major Linnaeus, 1788"));
//        System.out.println("================================================");
//        System.out.println(gnh.getAuthors("Globorotalia miocenica subsp. mediterranea Catalano & Sprovieri, 1969"));
//        System.out.println("================================================");
//        System.out.println(gnh.getAuthors("Gymnodiniales s.l."));
//        System.out.println("================================================");
        //System.out.println(gnh.result);
       // System.out.println(gnh.isParsed("Parus major"));
        //System.out.println(gnh.isParsed("unplaced extinct Diptera"));
//        System.out.println(gnh.getAuthors("unplaced extinct Diptera"));
//        gnh.hasAuthority("test");
    }
}
