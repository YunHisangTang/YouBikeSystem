package com.youbike.YouBikeSystemBackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.CollectingAuthenticationErrorCallback;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.AuthenticationException;

@Service
public class LdapAuthService {

    @Value("${ldap.urls}")
    private String ldapUrls;

    @Value("${ldap.base}")
    private String ldapBase;

    @Value("${ldap.pool.enabled}")
    private boolean ldapPoolEnabled;

    private LdapTemplate createLdapTemplate(String loginId, String password) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrls);
        contextSource.setBase(ldapBase);
        contextSource.setUserDn(String.format("cn=%s,%s", loginId, ldapBase));
        contextSource.setPassword(password);
        contextSource.setPooled(ldapPoolEnabled);
        contextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
        ldapTemplate.setIgnorePartialResultException(true);

        return ldapTemplate;
    }

    public boolean authenticateWithLdap(String loginId, String password) {
        LdapTemplate ldapTemplate = createLdapTemplate(loginId, password);
        String base = "";
        String filter = String.format("(cn=%s)", loginId);

        boolean ldapLoginStatus = false;
        CollectingAuthenticationErrorCallback errorCallback = new CollectingAuthenticationErrorCallback();
        try {
            ldapLoginStatus = ldapTemplate.authenticate(base, filter, password, errorCallback);
            if (!ldapLoginStatus) {
                if (errorCallback.getError() != null) {
                    System.err.println("LDAP Login Error: " + errorCallback.getError().toString());
                } else {
                    System.err.println("LDAP Login Error: errorCallback is null");
                }
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            System.err.println("LDAP Multiple Accounts Error: " + e.getMessage());
            throw new RuntimeException("Multiple LDAP accounts found");
        } catch (AuthenticationException e) {
            System.err.println("LDAP Authentication Error: " + e.getMessage());
            throw new RuntimeException("LDAP Authentication Failed. Please Contact Administrator.");
        } catch (Exception e) {
            System.err.println("LDAP Operation Error: " + e.getMessage());
            throw new RuntimeException("LDAP Operation Failed. Please Contact Administrator.");
        }

        return ldapLoginStatus;
    }
}
