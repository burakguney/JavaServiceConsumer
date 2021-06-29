import java.io.OutputStream;

//import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class App {

    // private static final String BASE_URL = "https://www.google.com/";
    //private static final String BASE_URL = "https://localhost:8000/nbi/wdl/ata";
    private static final String BASE_URL = "https://self-signed.badssl.com/";
    //private static final String BASE_URL = "https://localhost:8000/nbi/device/5ef9a217291ff37768d49ab3/download/file/5ef9a217291ff37768d49ab3";
    private static final String USERNAME = "evam";
    private static final String PASSWORD = "evam";
    private static final String TOKEN = "";
    private static final String METHOD = "GET";
    private static final String AUTH_TYPE = "Basic";
    private static final String URL_PARAMETER = "";

    public static void main(String[] args) throws Exception {

        /* System.setProperty("javax.net.debug", "SSL");

        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", "%JAVA_HOME%/jre/lib/security/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.keyStore", "/Users/Burak/Desktop/Badssl.cer");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456"); */

        String userPass = USERNAME + ":" + PASSWORD;

        /* javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return hostname.equals("localhost");
            }

        }); */


        if ("GET".equals(METHOD)) {

            URL url = new URL(BASE_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if ("Basic".equals(AUTH_TYPE)) {

                String basicAuth = "Basic "
                        + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
                connection.setRequestProperty("Authorization", basicAuth);

            } else if ("Bearer".equals(AUTH_TYPE)) {

                String bearerAuth = "Bearer " + TOKEN;
                connection.setRequestProperty("Authorization", bearerAuth);

            }

            connection.setRequestMethod(METHOD);

            int responseCode = connection.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(String.valueOf(response));

                System.out.println("GET request SUCCESS");

            } else {

                System.out.println("GET request FAILED");
            }
        } else if ("POST".equals(METHOD)) {

            URL url = new URL(BASE_URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if ("Basic".equals(AUTH_TYPE)) {

                String basicAuth = "Basic "
                        + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
                connection.setRequestProperty("Authorization", basicAuth);

            } else if ("Bearer".equals(AUTH_TYPE)) {

                String bearerAuth = "Bearer " + TOKEN;
                connection.setRequestProperty("Authorization", bearerAuth);

            }

            connection.setRequestMethod(METHOD);

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(URL_PARAMETER.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(String.valueOf(response));

                System.out.println("POST request SUCCESS.");

            } else {

                System.out.println("POST request FAILED.");
            }
        }
    }
}