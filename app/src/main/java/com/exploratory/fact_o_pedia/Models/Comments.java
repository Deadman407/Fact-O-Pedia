package com.exploratory.fact_o_pedia.Models;

public class Comments {
    String claim_text, name, comment;

    public Comments() {
    }

    public Comments(String claim_text, String name, String comment) {
        this.claim_text = claim_text;
        this.name = name;
        this.comment = comment;
    }

    public String getClaim_text() {
        return claim_text;
    }

    public void setClaim_text(String claim_text) {
        this.claim_text = claim_text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
