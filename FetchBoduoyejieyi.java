import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class FetchBoduoyejieyi {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws ParserException 
	 */
	public static void main(String[] args) throws Exception {
		HttpClient httpclient = new HttpClient();
		//System.setErr(new PrintStream("d:/err.txt"));
		
		int count = 1;
		List<String> urlList = new ArrayList<String>();
		while(count<=196){
			String url = "http://69.46.75.43/thread-htm-fid-96-page-"+count+".html";
			GetMethod urlGet = new GetMethod(url);
			httpclient.executeMethod(urlGet);
			Parser urlParser = Parser.createParser(urlGet.getResponseBodyAsString(), "GBK");
			NodeFilter urlFilter = new HasAttributeFilter("class", "subject");
			NodeList urlNodeList = urlParser.extractAllNodesThatMatch(urlFilter);
			
			for(int i=0;i<urlNodeList.size();i++){
				String content = urlNodeList.elementAt(i).toHtml();
				
				if(content.contains("≤‘æÆø’")){
					System.out.println(content);
					int beginIndex = content.indexOf("href");
					int endIndex = content.indexOf("id=");
					
					String contentUrl = content.substring(beginIndex+6, endIndex-2);
					urlList.add(contentUrl);
									
					
				}
			}
			count++;
		}
		
		
		for(String line:urlList){
			//<a href="read-htm-tid-3023005-fpage-9.html" id="a_ajax_3023005" class="subject">
			
			
			
			
			
			
			String urlStr = "http://69.46.75.43/"+line;
			GetMethod httpget = new GetMethod(urlStr);
		
			httpclient.executeMethod(httpget);
			Parser parser = Parser.createParser(httpget.getResponseBodyAsString(),"GBK");
			
			
			NodeFilter classFilter = new HasAttributeFilter("onclick", "");
			NodeList nodeList = parser.extractAllNodesThatMatch(classFilter);
		System.out.println(nodeList.size());
			for(int i=0;i<nodeList.size();i++){
				String content = nodeList.elementAt(i).toHtml();
				//System.out.println(nodeList.elementAt(i).);
				//if(content.contains("≤®∂‡“∞ΩY“¬") || content.contains("≤®∂‡“∞Ω·“¬")){
					System.out.println(content);
					int beginIndex = content.indexOf("href");
					int endIndex = content.indexOf("target");
					System.out.println(beginIndex+":"+endIndex);
					String btUrl = content.substring(beginIndex+5, endIndex);
					
					beginIndex = content.indexOf("\"red\">");
					endIndex = content.indexOf("</font>");
					String fileName = content.substring(beginIndex+6,endIndex);
					saveBtFile(btUrl,fileName);
					
				//}
			}
			
//			count++;
		}

	}
	public static void saveBtFile(String btUrl,String btFileName) throws Exception{
		File file = new File("G:/bt/∫£ÃÏ“Ì/"+btFileName);
		System.out.println(file.getAbsolutePath());
		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
		URL url = new URL("http://69.46.75.43/"+btUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStream in = conn.getInputStream();
		
		
		byte[] buffer = new byte[1024];
		int len=0;
		
		while((len=in.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		
		
		
		
		
	}

}
