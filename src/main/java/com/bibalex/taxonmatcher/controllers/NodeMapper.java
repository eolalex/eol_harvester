package com.bibalex.taxonmatcher.controllers;

import apoc.coll.Coll;
import com.bibalex.taxonmatcher.handlers.*;
import com.bibalex.taxonmatcher.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.*;

/**
 * Created by Amr.Morad
 */
public class NodeMapper {

    private StrategyHandler strategyHandler;
    private GlobalNamesHandler globalNameHandler;
    private NodeHandler nodeHandler;
    private MatchingScoreHandler matchingScoreHandler;
    private SearchHandler searchHandler;
    private int maxAncestorDepth;
    private static Logger logger;
//    private FileHandler fileHandler;
    private Neo4jHandler neo4jHandler;
    private SolrHandler solrHandler;
    int resourceId;
    private HashMap<Integer, Integer> nodePages;
    private Stack ancestorsStack;

    public NodeMapper(int resourceId){
        strategyHandler = new StrategyHandler();
        nodeHandler = new NodeHandler();
        matchingScoreHandler = new MatchingScoreHandler();
        searchHandler = new SearchHandler();
        maxAncestorDepth = Integer.parseInt(ResourceHandler.getPropertyValue("maxAncestorDepth"));
        logger = LogHandler.getLogger(NodeMapper.class.getName());
//        fileHandler = new FileHandler();
        neo4jHandler = new Neo4jHandler();
        this.resourceId = resourceId;
        this.solrHandler = new SolrHandler();
        this.nodePages = new HashMap<>();
        this.ancestorsStack = new Stack();
    }

    public void mapAllNodesToPages(ArrayList<Node> rootNodes){
        logger.info("before getting root nodes");
        ArrayList<Node> mappedRootNodes= nodeHandler.nodeMapper(rootNodes);
        logger.info("after getting root nodes");
        mapNodes(mappedRootNodes);
        neo4jHandler.assignPageToNodes(nodePages);
    }

    public void mapNodes(ArrayList<Node> rootNodes){
        logger.info("Inside Map Node method");
        System.out.println("mapNodes");
        logger.info("Inside Map Node method");
        for(Node node : rootNodes){
            System.out.println("mapNodes: mapping node: " + node.getScientificName());
            logger.info("mapNodes: mapping node: " + node.getScientificName());
            mapIfNeeded(node);
        }
    }

    public void mapIfNeeded(Node node){
        globalNameHandler = new GlobalNamesHandler(node.getScientificName());
        ancestorsStack.push(node);
        Strategy usedStrategy = strategyHandler.defaultStrategy();
        System.out.println("mapIfNeeded: used strategy is: " + usedStrategy.getAttribute());
        logger.info("mapIfNeeded: used strategy is: " + usedStrategy.getAttribute());
        int usedAncestorDepth = 0;
        if (node.needsToBeMapped()&& globalNameHandler.isParsed()){
            System.out.println("mapIfNeeded: needs to be mapped");
            logger.info("mapIfNeeded: needs to be mapped");
            if(!globalNameHandler.hasAuthority()){
                System.out.println("mapIfNeeded: node does not have authority");
                logger.info("mapIfNeeded: node does not have authority");
                usedStrategy = strategyHandler.firstNonScientificStrategy();
            }
            System.out.println("mapIfNeeded: before mapNode");
            logger.info("mapIfNeeded: before mapNode");
            mapNode(node, usedAncestorDepth, usedStrategy);
        }
//        System.out.println("---------------- has children is ------------- " + node.hasChildren() + " size " + node.getChildren().size());
        logger.info("before get children");
        ArrayList<Node> children = node.getChildren();
        if(children.size() > 0 ){


            logger.info("====================children=================");
            mapNodes(nodeHandler.nodeMapper(children));
        }
        ancestorsStack.pop();
    }

    private void mapNode(Node node, int depth, Strategy strategy){
        globalNameHandler = new GlobalNamesHandler(node.getScientificName());
        Node ancestor;
        if((boolean)globalNameHandler.getAttribute("surrogate")){
            System.out.println("map node: surrogate");
            logger.info("map node: surrogate");
            unmappedNode(node);
        }else{
//            ArrayList<Node> ancestors_neo4j = nodeHandler.nodeMapper(node.getAncestors());
            ArrayList<Node> ancestors = new ArrayList<>(ancestorsStack);
            ancestors.remove(ancestors.size()-1);
            Collections.reverse(ancestors);
            if ((boolean)globalNameHandler.getAttribute("virus")){
                System.out.println("map node: virus");
                logger.info("map node: virus");
                //not finalized as we need ancestor to be arraylist
                // we don't know the root node of virus
//                ancestor = nodeHandler.nodeMapper(nodeHandler.nativeVirus()).get(0);
//                ancestor = nodeHandler.matchedAncestor(nodeHandler.nodeMapper(node.getAncestors()), depth);
                ancestor = nodeHandler.matchedAncestor(ancestors, depth, nodePages, resourceId);
            }else{
                System.out.println("map Node : not virus neither surrogate");
                logger.info("map Node : not virus neither surrogate");
                logger.info("before matched ancestors");
//            ancestor = nodeHandler.matchedAncestor(nodeHandler.nodeMapper(node.getAncestors()), depth);
                ancestor = nodeHandler.matchedAncestor(ancestors, depth, nodePages, resourceId);
                logger.info("after matched ancestors");
            }
            mapUnflaggedNode(node, ancestor, depth, strategy,ancestors);
        }
    }

    private void  mapUnflaggedNode(Node node, Node ancestor, int depth, Strategy strategy,ArrayList<Node> ancestors){
        ArrayList<SearchResult> results = searchHandler.getResults(node, strategy, ancestor);
        Strategy nextStrategy;
        if(results.size() == 1){
//            System.out.println("other "+results.get(0).getScientificName());
            logger.info("results returned is one");
            if (results.get(0).getPageId() == 0) {
                unmappedNode(node);
            } else {
                mapToPage(node, results.get(0).getPageId(), results.get(0).getNodeId());
            }


        }else if(results.size() > 1){
            logger.info("results returned is greater than one");
            logger.info("before getting best match");
            MatchingScore matchingScore = findBestMatch(node, results, ancestors);
            logger.info("after getting best match");
            if(matchingScore != null && matchingScore.getScore() >= 0.1){
                mapToPage(node, matchingScore.getPageId(), matchingScore.getNodeId());
            }
            else {
                unmappedNode(node);
            }
        }else{
            logger.info("no result found start node id is "+node.getGeneratedNodeId()+" depth is"+depth);
            nextStrategy = strategyHandler.getNextStrategy(strategy);
            if (nextStrategy == null) {

                nextStrategy = strategyHandler.firstNonScientificStrategy();
                depth++;
                Node prev_ancestor = ancestor;
//                ancestor = nodeHandler.matchedAncestor(nodeHandler.nodeMapper(node.getAncestors()), depth);
                ancestor = nodeHandler.matchedAncestor(ancestors, depth, nodePages, resourceId);
                logger.info("depth is: " + depth);
                if(ancestor!=null)System.out.println("ancestor "+ancestor.getGeneratedNodeId());
                if(prev_ancestor!=null)System.out.println("prev_ancestor "+prev_ancestor.getGeneratedNodeId());
                if (depth > maxAncestorDepth||ancestor == null||ancestor.getGeneratedNodeId() == prev_ancestor.getGeneratedNodeId() ) {
                    logger.info("depth is greater than max depth");
                    unmappedNode(node);
                    return;
                }
                System.out.println("depth is less than max depth and will call recursion");
                logger.info("depth is less than max depth and will call recursion");
            }
            logger.info("Recursive call");
            mapUnflaggedNode(node, ancestor, depth, nextStrategy,ancestors);
        }
    }

    private MatchingScore findBestMatch(Node node, ArrayList<SearchResult> results, ArrayList<Node> ancestors){
        NodeHandler nodeHandler = new NodeHandler();
        ArrayList<MatchingScore> scores = new ArrayList<MatchingScore>();
        ArrayList<Integer> nodeAncestorsPages = nodeHandler.getPagesOfAncestors(ancestors, nodePages);
        for(SearchResult result : results){

            logger.info("before getting matched children count");
//            System.out.println(neo4jHandler.getNodesFromIds(result.getChildren()));
            int matchedChildrenCount = matchingScoreHandler.countMatches(nodeHandler.nodeMapper(neo4jHandler.getNodesFromIds(result.getChildren())),nodeHandler.nodeMapper(node.getChildren()));
            System.out.println("**********************************************************************************");
            logger.info("after getting matched children count");
            logger.info("matched children count " + matchedChildrenCount);
            logger.info("before getting matched ancestors count");
            int matchedAncestorsCount = matchingScoreHandler.countAncestors(nodeHandler.nodeMapper(neo4jHandler.getNodesFromIds(result.getAncestors())), nodePages, nodeAncestorsPages,ancestors.size());
//            int matchedAncestorsCount = matchingScoreHandler.countAncestors(node);
            logger.info("after getting matched ancestors count");
            logger.info("matched Ancestors count " + matchedAncestorsCount);
            logger.info("before sameness of names ");
            logger.info("nm node scientific_name "+node.getScientificName());
            logger.info("nm result scientific_name "+result.getScientificName());
            double sameness_of_names = matchingScoreHandler.samenessOfNames(node.getScientificName(),result.getScientificName());
            logger.info("after sameness of names "+ sameness_of_names);
            logger.info("before calculating score");
            double overallScore = matchingScoreHandler.calculateScore(matchedChildrenCount, matchedAncestorsCount);
            overallScore *= sameness_of_names;
            logger.info("after calculating score");
            logger.info("overall score: "+overallScore);
            MatchingScore score = new MatchingScore(matchedChildrenCount,
                    matchedAncestorsCount, overallScore, result.getPageId(), result.getNodeId());
            logger.info("score: "+score.getScore() + " of page: "+score.getPageId());
            System.out.println("**********************************************************************************");
            scores.add(score);
        }
        logger.info("before sorting and reversing score");
        Collections.sort(scores,new Comparator<MatchingScore>(){
            public int compare(MatchingScore score1, MatchingScore score2)
            {
                return  Double.compare(score1.getScore(), score2.getScore());
            }
        });
//        Collections.reverse(scores);

        logger.info("after sorting and reversing score");

        for( int i = scores.size()-1 ; i >= 0 ;i--)
        {
            if(scores.get(i).getPageId()!=0)
                return scores.get(i) ;
        }

        return null;

    }

    private Node mapToPage(Node node, int pageId, int nodeId){
        try {
            solrHandler.updateRecord(nodeId, node);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
//        boolean response =neo4jHandler.assignPageToNode(node.getGeneratedNodeId(), pageId);
//        if (response ==true){
        nodePages.put(node.getGeneratedNodeId(),pageId);
        node.setPageId(pageId);
//        }
        System.out.println("Node with name " + node.getScientificName() + " is mapped to page "+node.getPageId());
        logger.info("Node with name " + node.getScientificName() + " is mapped to page "+node.getPageId());
//        fileHandler.writeToFile("Node with name " + node.getScientificName() + " is mapped to page "+node.getPageId());
        return node;
    }

    private void unmappedNode(Node node){
        System.out.println("New page is created for node named: "+node.getScientificName());
        logger.info("New page is created for node named: "+node.getScientificName());
//        fileHandler.writeToFile("New page is created for node named: "+node.getScientificName());
        logger.info(" before calling neo4j function to create and assign page to node");
        int page_id = neo4jHandler.assignPageToNode(node.getGeneratedNodeId());
        nodePages.put(node.getGeneratedNodeId(),page_id);
        logger.info("after calling neo4j function to create and assign page to node");
        logger.info("map Node : not virus neither surrogate");
        try {
            logger.info("before adding document in solr");
//            long startTime = System.nanoTime();
            solrHandler.addDocument(node, page_id);
//            long endTime = System.nanoTime();
//
//            long duration = (endTime - startTime);
//            System.out.println("duration of adding document in solr: "+ duration);
            logger.info("after adding document in solr");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        //  Page newPage = new Page();
       // node.setPageId(newPage.getId());
    }



}
