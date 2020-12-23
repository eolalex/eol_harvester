package org.bibalex.eol.parser.models;

import java.util.HashMap;

public class GnParser {
        String canonicalName;
        HashMap<String, String> authorshipInfo;
        Object parse_quality;
        boolean hybrid;
        boolean surrogate;
        boolean virus;

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public HashMap<String, String> getAuthorshipInfo() {
        return authorshipInfo;
    }

    public void setAuthorshipInfo(HashMap<String, String> authorshipInfo) {
        this.authorshipInfo = authorshipInfo;
    }

    public Object getParse_quality() {
        return parse_quality;
    }

    public void setParse_quality(Object parse_quality) {
        this.parse_quality = parse_quality;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    public void setHybrid(boolean hybrid) {
        this.hybrid = hybrid;
    }

    public boolean isSurrogate() {
        return surrogate;
    }

    public void setSurrogate(boolean surrogate) {
        this.surrogate = surrogate;
    }

    public boolean isVirus() {
        return virus;
    }

    public void setVirus(boolean virus) {
        this.virus = virus;
    }


    @Override
    public String toString() {
        return "GnParser{" +
                "canonicalName='" + canonicalName + '\'' +
                ", authorshipInfo=" + authorshipInfo +
                ", parse_quality=" + parse_quality +
                ", hybrid=" + hybrid +
                ", surrogate=" + surrogate +
                ", virus=" + virus +
                '}';
    }
}
