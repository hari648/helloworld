package com.coveros.demo.helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test
{
  public static void main(String[] args)
    throws IOException, ParseException
  {
    String listofM = "A,B";
    LinkedHashSet<String> mset = new LinkedHashSet();
    String[] arr = "A,B".split(",");
    String[] var7 = arr;
    int var6 = arr.length;
    for (int var5 = 0; var5 < var6; var5++)
    {
      String ipadd = var7[var5];
      mset.add(ipadd);
    }
    System.out.println(mset);
    CloseableHttpClient clientcon = HttpClients.createDefault();
    String ipadd = null;
    String hostname = null;
    try
    {
      Map<String, String> environ = System.getenv();
      if (environ.containsKey("IP"))
      {
        ipadd = (String)environ.get("IP");
        hostname = ipadd + ":80" + "/test";
      }
      URL uriadd = new URL("http://" + hostname);
      HttpPut httpput = new HttpPut(uriadd.toString());
      httpput.addHeader("Content", "application/json");
      httpput.addHeader("content-language", "en-US");
      JSONParser parser = new JSONParser();
      String jsn = null;
      Iterator var13 = mset.iterator();
      while (var13.hasNext())
      {
        String m = (String)var13.next();
        Class<Test> classload = Test.class;
        InputStream input = classload.getClassLoader().getResourceAsStream("test.json");
        InputStreamReader streamread = new InputStreamReader(input);
        BufferedReader buff = new BufferedReader(streamread);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = buff.readLine()) != null)
        {
          String line;
          result.append(line).append("\n").toString();
        }
        JSONObject jsonob = (JSONObject)parser.parse(result.toString());
        jsn = jsonob.get("path").toString().replaceAll("LN", m);
        if ((!m.contains("O")) && (!m.contains("BLN")))
        {
          jsonob.put("path", jsn);
          jsonob.put("fP", "Co");
          
          System.out.println(jsonob);
        }
        else
        {
          jsonob.put("path", jsn);
        }
        String str = jsonob.toString();
        StringEntity entity = new StringEntity(str, ContentType.APPLICATION_JSON);
        httpput.setEntity(entity);
        CloseableHttpResponse res = clientcon.execute(httpput);
        try
        {
          if (res.getStatusLine().getStatusCode() > 200) {
            throw new ClientProtocolException("unexpected" + res.getStatusLine().getStatusCode());
          }
          System.out.println(res.getStatusLine().getStatusCode());
        }
        finally
        {
          res.close();
        }
      }
    }
    finally
    {
      clientcon.close();
    }
  }
}
