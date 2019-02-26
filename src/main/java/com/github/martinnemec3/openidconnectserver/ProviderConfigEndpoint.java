package com.github.martinnemec3.openidconnectserver;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@FrameworkEndpoint
public class ProviderConfigEndpoint {

    private final ProviderConfig providerConfig;

    public ProviderConfigEndpoint() {
        this.providerConfig = createProviderConfig();
    }


    @GetMapping("/.well-known/openid-configuration")
    @ResponseBody
    public ProviderConfig getProviderConfig() {
        return providerConfig;
    }

    private static ProviderConfig createProviderConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setIssuer("http://slimbook:8080");
        providerConfig.setAuthorization_endpoint("http://slimbook:8080/oauth/authorize");
        providerConfig.setToken_endpoint("http://slimbook:8080/oauth/token");
        // providerConfig.setUserinfo_endpoint(); // RECOMMENDED
        providerConfig.setJwks_uri("http://slimbook:8080/oauth/jwk");
        // providerConfig.setRegistration_endpoint(); // RECOMMENDED
        providerConfig.setScopes_supported(List.of("openid")); // RECOMMENDED
        providerConfig.setResponse_types_supported(List.of("code", "id_token", "token id_token"));
        // providerConfig.setResponse_modes_supported(); // OPTIONAL
        providerConfig.setGrant_types_supported(List.of("authorization_code"));
        // providerConfig.setAcr_values_supported(); // OPTIONAL
        providerConfig.setSubject_types_supported(List.of("public"));
        providerConfig.setId_token_signing_alg_values_supported(List.of("RS256"));
        // providerConfig.setId_token_encryption_alg_values_supported(); // OPTIONAL
        // providerConfig.setId_token_encryption_enc_values_supported(); // OPTIONAL
        // providerConfig.setUserinfo_signing_alg_values_supported(); // OPTIONAL
        // providerConfig.setUserinfo_encryption_alg_values_supported(); // OPTIONAL
        // providerConfig.setUserinfo_encryption_enc_values_supported(); // OPTIONAL
        // providerConfig.setRequest_object_signing_alg_values_supported(); // OPTIONAL
        // providerConfig.setRequest_object_encryption_alg_values_supported(); // OPTIONAL
        // providerConfig.setRequest_object_encryption_enc_values_supported(); // OPTIONAL
        // providerConfig.setToken_endpoint_auth_methods_supported(); // OPTIONAL
        // providerConfig.setToken_endpoint_auth_signing_alg_values_supported(); // OPTIONAL
        // providerConfig.setDisplay_values_supported(); // OPTIONAL
        // providerConfig.setClaim_types_supported(); // OPTIONAL
        // providerConfig.setClaims_supported(); // RECOMMENDED
        // providerConfig.setService_documentation(); // OPTIONAL
        // providerConfig.setClaims_locales_supported(); // OPTIONAL
        // providerConfig.setUi_locales_supported(); // OPTIONAL
        // providerConfig.setClaims_parameter_supported(); // OPTIONAL
        // providerConfig.setRequest_parameter_supported(); // OPTIONAL
        // providerConfig.setRequest_uri_parameter_supported(); // OPTIONAL
        // providerConfig.setRequire_request_uri_registration(); // OPTIONAL
        // providerConfig.setOp_policy_uri(); // OPTIONAL
        // providerConfig.setOp_tos_uri(); // OPTIONAL
        return providerConfig;
    }
}