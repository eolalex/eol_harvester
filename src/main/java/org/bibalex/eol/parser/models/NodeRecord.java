package org.bibalex.eol.parser.models;

import com.bibalex.taxonmatcher.handlers.GlobalNamesHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/*
This class will include the needed objects and attributes and is compatible with MongodB
 */
public class NodeRecord {

    int resourceId;
    String nodeId;
    String _id;
    Integer parentGNId; // neo4j
    Boolean preferred;  // taxon matching
    Integer pageId;
    String acceptedNameUsageId;
    String rank;
    String scientificName;
    GnParser gnParserAttributes;
    String taxonomicStatus;
    String landMark;
    ArrayList<Reference> references = new ArrayList<>(); // after parsing referecnes
    String[] identifiers;
    String sourceUrl; //TODO FOUND IN JR'S DOC BUT NOT IN FILE
    String remarks;
    String attribution;
    ArrayList<VernacularName> vernaculars;
    String deltaStatus;

    public NodeRecord(String generatedNodeId, int resourceId) {
        this._id = generatedNodeId;
        this.resourceId = resourceId;
    }

    public NodeRecord(String nodeId, int resourceId, String _id, String acceptedNameUsageId, String rank,
                      String scientificName,String taxonomicStatus, String landMark, String remarks, String attribution,
                       String deltaStatus){
        this.nodeId = nodeId;
        this.resourceId = resourceId;
        this._id = _id;
        this.acceptedNameUsageId = acceptedNameUsageId;
        this.rank = rank;
        this.scientificName = scientificName;
        this.taxonomicStatus = taxonomicStatus;
        this.landMark = landMark;
        this.remarks = remarks;
        this.attribution = attribution;
        this.deltaStatus = deltaStatus;
    }
    public void getGnAttributes(String scientificName) {
        GlobalNamesHandler gnh = new GlobalNamesHandler(scientificName);
        gnParserAttributes = new GnParser();
        gnParserAttributes.canonicalName = gnh.getCanonicalForm();
        gnParserAttributes.authorshipInfo = gnh.getAuthorAttributes();
        gnParserAttributes.parse_quality =  gnh.getAttribute("quality");
        gnParserAttributes.hybrid =(boolean) gnh.getAttribute("hybrid");
        gnParserAttributes.surrogate = (boolean)gnh.getAttribute("surrogate");
        gnParserAttributes.virus = (boolean) gnh.getAttribute("virus");
    }

    public String getDeltaStatus() {
        return deltaStatus;
    }

    public void setDeltaStatus(String deltaStatus) {
        this.deltaStatus = deltaStatus;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<VernacularName> getVernaculars() {
        return vernaculars;
    }

    public void setVernaculars(ArrayList<VernacularName> vernaculars) {
        this.vernaculars = vernaculars;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getParentGNId() {
        return parentGNId;
    }

    public void setParentGNId(Integer parentGNId) {
        this.parentGNId = parentGNId;
    }

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getAcceptedNameUsageId() {
        return acceptedNameUsageId;
    }

    public void setAcceptedNameUsageId(String acceptedNameUsageId) {
        this.acceptedNameUsageId = acceptedNameUsageId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getTaxonomicStatus() {
        return taxonomicStatus;
    }

    public void setTaxonomicStatus(String taxonomicStatus) {
        this.taxonomicStatus = taxonomicStatus;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public ArrayList<Reference> getReferences() {
        return references;
    }

    public void setReferences( ArrayList<Reference> references) {
        this.references = references;
    }

    public String[] getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(String[] identifiers) {
        this.identifiers = identifiers;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public void setGnParserAttributes(GnParser gnParserAttributes) {
       this.gnParserAttributes = gnParserAttributes;
    }

    public GnParser getGnParserAttributes() {
        return gnParserAttributes;
    }

    @Override
    public String toString() {
        return "NodeRecord{" +
                "resourceId=" + resourceId +
                ", nodeId='" + nodeId + '\'' +
                ", _id='" + _id + '\'' +
                ", parentGNId=" + parentGNId +
                ", preferred=" + preferred +
                ", pageId=" + pageId +
                ", acceptedNameUsageId='" + acceptedNameUsageId + '\'' +
                ", rank='" + rank + '\'' +
                ", scientificName='" + scientificName + '\'' +
                ", gnParserAttributes=" + gnParserAttributes +
                ", taxonomicStatus='" + taxonomicStatus + '\'' +
                ", landMark='" + landMark + '\'' +
                ", references=" + references +
                ", identifiers=" + Arrays.toString(identifiers) +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", remarks='" + remarks + '\'' +
                ", attribution='" + attribution + '\'' +
                ", vernaculars=" + vernaculars +
                ", deltaStatus='" + deltaStatus + '\'' +
                '}';
    }
}
