import java.io.OutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class App {

    private static final String BASE_URL = "https://self-signed.badssl.com";
    private static final int PORT = -1;
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String TOKEN = "";
    private static final String METHOD = "GET";
    private static final String AUTH_TYPE = "";
    private static final String URL_PARAMETER = "";

    public static void main(String[] args) throws Exception {

        String userPass = USERNAME + ":" + PASSWORD;

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        InstallCertBuilder.InstallCert(BASE_URL, PORT);

        if ("GET".equals(METHOD)) {

            URL url = new URL(BASE_URL + ":" + PORT + URL_PARAMETER);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setSSLSocketFactory(SSLContextBuilder.loadSSLContext());

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

            URL url = new URL(BASE_URL + ":" + PORT + URL_PARAMETER);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setSSLSocketFactory(SSLContextBuilder.loadSSLContext());

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