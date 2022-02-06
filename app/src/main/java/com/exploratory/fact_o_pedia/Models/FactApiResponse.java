package com.exploratory.fact_o_pedia.Models;

import java.util.List;

public class FactApiResponse {
    List<Claims> claims;

    public List<Claims> getClaims() {
        return claims;
    }

    public void setClaims(List<Claims> claims) {
        this.claims = claims;
    }
}
