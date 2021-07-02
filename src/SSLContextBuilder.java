import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLContextBuilder {

    public static SSLSocketFactory loadSSLContext() throws Exception {
        KeyStore clientStore = KeyStore.getInstance("JKS");
        clientStore.load(new FileInputStream("/Users/Burak/Desktop/GitRepos/JavaSSLCertificateInstaller/testssl.jks"),
                "changeit".toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(clientStore, "changeit".toCharArray());
        KeyManager[] kms = kmf.getKeyManagers();
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream("/Users/Burak/Desktop/GitRepos/JavaSSLCertificateInstaller/testssl.jks"),
                "changeit".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        TrustManager[] tms = tmf.getTrustManagers();
        SSLContext sslContext = null;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kms, tms, new SecureRandom());

        return sslContext.getSocketFactory();
    }

}
