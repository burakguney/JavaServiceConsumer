import java.io.OutputStream;

import java.net.HttpURLConnection;
//import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class App {

    //private static final String BASE_URL = "http://localhost:4000/nbi/wdl/ata?imei=356299100013547";
    private static final String BASE_URL = "http://localhost:4000/nbi/device/5ef9a217291ff37768d49ab3/download/file/5ef9a217291ff37768d49ab3";
    private static final String USERNAME = "evam";
    private static final String PASSWORD = "evam";
    private static final String TOKEN = "token";
    private static final String METHOD = "POST";
    private static final String AUTH_TYPE = "Basic";
    private static final String URL_PARAMETER = "";

    //private static String RESULT = "result";
    
    public static void main(String[] args) throws Exception {

        String userPass = USERNAME + ":" + PASSWORD;

        if ("GET".equals(METHOD)) {

            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if ("Basic".equals(AUTH_TYPE)) {

                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
                connection.setRequestProperty("Authorization", basicAuth);

            } else if ("Bearer".equals(AUTH_TYPE)) {

                String bearerAuth = "Bearer " + TOKEN;
                connection.setRequestProperty("Authorization", bearerAuth);

            }

            connection.setRequestMethod(METHOD);

            int responseCode = connection.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //outputActionContext.getReturnMap().put(RESULT, response);

                System.out.println(String.valueOf(response));

                //System.out.println(outputActionContext.getReturnMap().get(RESULT).toString());

                System.out.println("GET request SUCCESS");

            } else {

                System.out.println("GET request FAILED");
            }
        } else if ("POST".equals(METHOD)) {

            URL url = new URL(BASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if ("Basic".equals(AUTH_TYPE)) {

                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
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

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {

                /* BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(String.valueOf(response)); */

                System.out.println("POST request SUCCESS.");

            } else {

                System.out.println("POST request FAILED.");
            }
        }
    }
}