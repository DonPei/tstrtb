package edu.uth.kit.security;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;


//https://www.avajava.com/tutorials/lessons/how-do-i-create-a-login-module.html?page=1
//https://docs.oracle.com/javase/1.5.0/docs/guide/security/jaas/JAASRefGuide.html#Sample
//https://docs.oracle.com/javase/8/docs/technotes/guides/security/jaas/JAASLMDevGuide.html

public class JaasAuthenticationTest {

	public static void main(String[] args) {
		System.setProperty("java.security.auth.login.config", "jaas.config");

		String name = "myName";
		String password = "myPassword";

		try {
			LoginContext lc = new LoginContext("Test", new TestCallbackHandler(name, password));
			lc.login();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
}
