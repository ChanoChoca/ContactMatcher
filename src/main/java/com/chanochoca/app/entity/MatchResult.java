package com.chanochoca.app.entity;

public class MatchResult {
    private String sourceContactId;
    private String matchedContactId;
    private String accuracy;

    public MatchResult(String sourceContactId, String matchedContactId, String accuracy) {
        this.sourceContactId = sourceContactId;
        this.matchedContactId = matchedContactId;
        this.accuracy = accuracy;
    }

    public String getSourceContactId() {
        return sourceContactId;
    }

    public void setSourceContactId(String sourceContactId) {
        this.sourceContactId = sourceContactId;
    }

    public String getMatchedContactId() {
        return matchedContactId;
    }

    public void setMatchedContactId(String matchedContactId) {
        this.matchedContactId = matchedContactId;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
