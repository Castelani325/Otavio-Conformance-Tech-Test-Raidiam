package com.raidiam.auth.model;

import com.raidiam.auth.services.AuthService;

public class AccessTokenResponse {

    private AuthService.RequestStatus requestStatus;
    private AccessToken accessToken;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public AuthService.RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(AuthService.RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
