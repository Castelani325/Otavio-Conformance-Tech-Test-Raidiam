package com.raidiam.auth.model;

import java.time.Instant;

public class AccessTokenInfo {

    private AccessToken accessToken;
    private OAuthClient oAuthClient;
    private Instant iat = Instant.now();

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public OAuthClient getClient() {
        return oAuthClient;
    }

    public void setClient(OAuthClient oAuthClient) {
        this.oAuthClient = oAuthClient;
    }

    public Instant getIat() {
        return iat;
    }
}
