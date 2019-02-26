package com.github.martinnemec3.openidconnectserver;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@FrameworkEndpoint
public class JwkSetEndpoint {

    private RSAPublicKey publicKey;

    @Autowired
    public JwkSetEndpoint(RSAPublicKey publicKey) throws Exception {
        this.publicKey = publicKey;
    }

    @GetMapping("/oauth/jwk")
    @ResponseBody
    public Map<String, Object> getKey(Principal principal) {
        return new JWKSet(new RSAKey.Builder(publicKey).build()).toJSONObject();
    }
}