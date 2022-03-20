import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.regex.*;

public class Main {
	public static String convertStreamToString(InputStream stream) {
		try{
			
		 int bufferSize = 1024;
		char[] buffer = new char[bufferSize];
		 StringBuilder out = new StringBuilder();
		 Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
		 for (int numRead; (numRead = in.read(buffer, 0, buffer.length)) > 0; ) {
			 out.append(buffer, 0, numRead);
		 }
		 return out.toString();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
    public static void main(String[] args) {

	HttpURLConnection connection = null;
	HttpURLConnection connection2 = null;
	HttpURLConnection connection3 = null;

	  try {
		// Create connection
		URL url = new URL("http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/Login.aspx");
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0");

		connection.setUseCaches(false);
		connection.setDoOutput(true);
		
		// Cookie
		String cookie = connection.getHeaderField("Set-Cookie").split(";", -1)[0];

		// Get Response
		InputStream is = connection.getInputStream();
		String content = convertStreamToString(is);
		

		Pattern pattern = Pattern.compile("<input[^>]+?name=\"([^>]+?)\"[^>]+?value=\"([^>]*?)\"");
		Matcher m = pattern.matcher(content);

		String reqBody = "txtUserName=AT13CLC0130&txtPassword=pass ne`";

		while (m.find()) {
		   reqBody +="&"+URLEncoder.encode(m.group(1), StandardCharsets.UTF_8.toString())+"="+ URLEncoder.encode(m.group(2), StandardCharsets.UTF_8.toString());
		}


		// POST
		url = new URL("http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/Login.aspx");
		connection2 = (HttpURLConnection) url.openConnection();
		connection2.setInstanceFollowRedirects(false);
		connection2.setRequestMethod("POST");
		connection2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection2.setRequestProperty("Accept", "*/*");
		connection2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0");
		connection2.setRequestProperty("Cookie", cookie);
		connection2.setRequestProperty("Content-Length", 
			Integer.toString(reqBody.getBytes().length));

		connection2.setUseCaches(false);
		connection2.setDoOutput(true);

		//Send request
		DataOutputStream wr = new DataOutputStream (
			connection2.getOutputStream());
		wr.writeBytes(reqBody);
		wr.close();

		cookie += "; "+connection2.getHeaderField("Set-Cookie").split(";", -1)[0];
		is = connection2.getInputStream();
		content = convertStreamToString(is);

        // Follows redirect
		url = new URL("http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/StudyRegister/StudyRegister.aspx");
		connection3 = (HttpURLConnection) url.openConnection();
		connection3.setRequestMethod("GET");
		connection3.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0");
		connection3.setRequestProperty("Cookie", cookie);

		connection3.setUseCaches(false);
		connection3.setDoOutput(true);

		is = connection3.getInputStream();
		content = convertStreamToString(is);

        System.out.println(content);
		
	  } catch (Exception e) {
		e.printStackTrace();
	  } finally {
		if (connection != null) {
		  connection.disconnect();
		}
		if (connection3 != null) {
          connection3.disconnect();
        }
		if (connection2 != null) {
          connection2.disconnect();
        }
	  }

    }
}