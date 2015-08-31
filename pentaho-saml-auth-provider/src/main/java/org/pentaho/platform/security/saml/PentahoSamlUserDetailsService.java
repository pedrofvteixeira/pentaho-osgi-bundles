package org.pentaho.platform.security.saml;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

/**
 * Pentaho's implementation of SAMLUserDetailsService interface
 * @ see https://github.com/spring-projects/spring-security-saml/blob/1.0.1.RELEASE/core/src/main/java/org/springframework/security/saml/userdetails/SAMLUserDetailsService.java
 */
public class PentahoSamlUserDetailsService implements SAMLUserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger( PentahoSamlUserDetailsService.class );

  /**
   * The method is supposed to identify local account of user referenced by data in the SAML assertion
   * and return UserDetails object describing the user. In case the user has no local account, implementation
   * may decide to create one or just populate UserDetails object with data from assertion.
   * <p>
   * Returned object should correctly implement the getAuthorities method as it will be used to populate
   * entitlements inside the Authentication object.
   *
   * @param credential data populated from SAML message used to validate the user
   *
   * @return a fully populated user record (never <code>null</code>)
   *
   * @throws UsernameNotFoundException if the user details object can't be populated
   */
  @Override
  public Object loadUserBySAML( SAMLCredential credential ) throws UsernameNotFoundException {

    if( credential == null || credential.getNameID() == null || credential.getNameID().getValue() == null ){
      throw new UsernameNotFoundException( "invalid/null SAMLCredential" );
    }

    String username = credential.getNameID().getValue();

    return new User( username
        , "ignored" /* password */
        , true /* accountEnabled */
        , true /* accountNonExpired */
        , true /* credentialsNonExpired */
        , true /* accountNonLocked */
        , getAuthorities( username ) );
  }


  private List<GrantedAuthority> getAuthorities( String username ) throws UsernameNotFoundException {

    // TODO REMOVE THIS; THIS IS FOR TESTING PURPOSES ONLY
    GrantedAuthority authority1 = new SimpleGrantedAuthority( "Authenticated" );
    GrantedAuthority authority2 = new SimpleGrantedAuthority( "Administrator" );

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    authorities.add( authority1 );
    authorities.add( authority2 );

    return authorities;
  }
}
