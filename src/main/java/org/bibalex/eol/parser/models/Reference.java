package org.bibalex.eol.parser.models;

/**
 * As this is the harvester, it will put all the fields in Mysql. This, in turn, will be transformed to bytes array. No need for casting
 * because of that.
 */
public class Reference {
    String referenceId;
    int resourceId;
    String body;
    String deltaStatus;

    public Reference(String referenceId, String publicationType, String fullReference, String primaryTitle, String secondaryTitle,
                     String pages, String pageStart, String pageEnd, String volume, String edition, String publisher, String authorsList,
                     String editorsList, String dateCreated, String language, String url, String doi, String localityOfPublisher) {
        StringBuilder str = new StringBuilder();
        this.referenceId = referenceId;
        this.resourceId = resourceId;

        if(publicationType != null) {str.append(publicationType).append(" ");}
        if(fullReference != null) {str.append(fullReference).append(" ");}
        if(primaryTitle != null) {str.append(primaryTitle).append(" ");}
        if(secondaryTitle != null) {str.append(secondaryTitle).append(" ");}
        if(pages != null ) {str.append(pages).append(" ");}
        if(pageStart != null){str.append(pageStart).append(" ");}
        if(pageEnd != null) {str.append(pageEnd).append(" ");}
        if(volume != null ) {str.append(volume).append(" ");}
        if(edition != null) {str.append(edition).append(" ");}
        if(publisher != null) {str.append(publisher).append(" ");}
        if(authorsList != null) {str.append(authorsList).append(" ");}
        if(editorsList != null) {str.append(editorsList).append(" ");}
        if(dateCreated != null) {str.append(dateCreated).append(" ");}
        if(language != null) {str.append(language).append(" ");}
        if(url != null) {str.append(url).append(" ");}
        if(doi != null) {str.append(doi).append(" ");}
        if(localityOfPublisher != null) {str.append(localityOfPublisher).append(" ");}

        this.body = str.toString();
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDeltaStatus() {
        return deltaStatus;
    }

    public void setDeltaStatus(String deltaStatus) {
        this.deltaStatus = deltaStatus;
    }

}
