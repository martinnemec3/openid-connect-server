package com.github.martinnemec3.openidconnectserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@SpringBootApplication
@EnableAuthorizationServer
public class OpenidConnectServerApplication extends AuthorizationServerConfigurerAdapter {

	@Bean
	public RSAPublicKey publicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = StreamUtils.copyToByteArray(getClass().getResourceAsStream("pubkey.der"));
		KeySpec spec = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return (RSAPublicKey) kf.generatePublic(spec);
	}

	@Bean
	public RSAPrivateKey signingKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = StreamUtils.copyToByteArray(getClass().getResourceAsStream("privkey.der"));
		KeySpec spec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) kf.generatePrivate(spec);
	}

	@Bean
	public OpenIdTokenEnhancer openIdTokenEnhancer() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		return new OpenIdTokenEnhancer(signingKey());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
                .withClient("my-client").secret("{noop}password")
				    .redirectUris("http://localhost:9090/login/oauth2/code/openid-client")
				    .scopes("openid").authorizedGrantTypes("authorization_code")
				    .autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenEnhancer(openIdTokenEnhancer());
	}

	public static void main(String[] args) {
		SpringApplication.run(OpenidConnectServerApplication.class, args);
	}
}
