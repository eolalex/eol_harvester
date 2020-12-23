package org.bibalex.eol.parser.models;

import java.util.ArrayList;

public class Media {
    int resourceId;
    String taxonId;
    String mediaId;
    String type;
    String subType;
    String format;
    String subject;
    String title;
    String description;
    String accessURI;
    String thumbnailURI;
    String furtherInformationURI;
    String derivedFrom;
    String createDate;
    String modified;
    String language;
    String rating;
    String audience;
    String license;
    String rights;
    String owner;
    String bibliographicCitation;
    String publisher;
    String contributor;
    String creator;
    ArrayList<Agent> agents;
    MediaLocation locationAttributes;
    ArrayList<Reference> references = new ArrayList<>();
    String deltaStatus;
    String storageLayerPath;
    String storageLayerThumbnailPath;

    public Media(int resourceId, String taxonId,String mediaId, String type, String subType, String format, String subject, String title, String description, String accessURI,
                 String thumbnailURI, String furtherInformationURI, String derivedFrom, String createDate, String modified, String language, String rating,
                 String audience, String license, String rights, String owner, String bibliographicCitation, String publisher, String contributor,
                 String creator,String storageLayerPath, String storageLayerThumbnailPath, String deltaStatus) {
        this.resourceId = resourceId;
        this.taxonId = taxonId;
        this.mediaId = mediaId;
        this.type = type;
        this.subType = subType;
        this.format = format;
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.accessURI = accessURI;
        this.thumbnailURI = thumbnailURI;
        this.furtherInformationURI = furtherInformationURI;
        this.derivedFrom = derivedFrom;
        this.createDate = createDate;
        this.modified = modified;
        this.language = language;
        this.rating = rating;
        this.audience = audience;
        this.license = license;
        this.rights = rights;
        this.owner = owner;
        this.bibliographicCitation = bibliographicCitation;
        this.publisher = publisher;
        this.contributor = contributor;
        this.creator = creator;
        this.storageLayerPath = storageLayerPath;
        this.storageLayerThumbnailPath = storageLayerThumbnailPath;
        this.deltaStatus = deltaStatus;
        if(this.format != null) {
            this.format = this.format.replace("/", "_");
            this.format = this.format.replace("+", "$");
            this.format = this.format.replace("-", "_$");

        }
        if(this.subType != null && this.subType.contains("/"))
            this.subType=this.subType.replace("/","_");
    }

    public String getMediaId() {

        return mediaId;
    }

    public String getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(String taxonId) {
        this.taxonId = taxonId;
    }

    public String getDeltaStatus() {
        return deltaStatus;
    }

    public void setDeltaStatus(String deltaStatus) {
        this.deltaStatus = deltaStatus;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessURI() {
        return accessURI;
    }

    public void setAccessURI(String accessURI) {
        this.accessURI = accessURI;
    }

    public String getThumbnailURI() {
        return thumbnailURI;
    }

    public void setThumbnailURI(String thumbnailURI) {
        this.thumbnailURI = thumbnailURI;
    }

    public String getFurtherInformationURI() {
        return furtherInformationURI;
    }

    public void setFurtherInformationURI(String furtherInformationURI) {
        this.furtherInformationURI = furtherInformationURI;
    }

     public ArrayList<Reference> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<Reference> references) {
        this.references = references;
    }

    public String getDerivedFrom() {
        return derivedFrom;
    }

    public void setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBibliographicCitation() {
        return bibliographicCitation;
    }

    public void setBibliographicCitation(String bibliographicCitation) {
        this.bibliographicCitation = bibliographicCitation;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public MediaLocation getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(MediaLocation locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    public String getStorageLayerPath(){
        return storageLayerPath;
    }

    public void setStorageLayerPath(String storageLayerPath) {
        this.storageLayerPath = storageLayerPath;
    }

    public String getStorageLayerThumbnailPath() {
        return storageLayerThumbnailPath;
    }

    public void setStorageLayerThumbnailPath(String storageLayerThumbnailPath) {
        this.storageLayerThumbnailPath = storageLayerThumbnailPath;
    }
}
