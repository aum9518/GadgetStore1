package peaksoft.service;

import peaksoft.dto.dtoAuthentication.AdminTokenRequest;
import peaksoft.dto.dtoAuthentication.AuthenticationRequest;
import peaksoft.dto.dtoAuthentication.AuthenticationResponse;
import peaksoft.dto.dtoAuthentication.SignIn;

public interface AuthenticationService {
    AuthenticationResponse adminToken(AdminTokenRequest adminTokenResponse);
    AuthenticationResponse signUp(AuthenticationRequest authenticationRequest);
    AuthenticationResponse signIn(SignIn signIn);
}
