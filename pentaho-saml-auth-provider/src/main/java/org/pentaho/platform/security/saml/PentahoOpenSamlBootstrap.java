package org.pentaho.platform.security.saml;

import java.lang.Exception;

import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;

public class PentahoOpenSamlBootstrap {

  public PentahoOpenSamlBootstrap() throws Exception {

    try {

      DefaultBootstrap.bootstrap();

    } catch ( ConfigurationException ce ) {
      throw new Exception( ce );
    }
  }
}
