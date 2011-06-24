package com.tracker;

import java.util.HashMap;
import java.util.Map;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.internal.http.HttpClientImpl;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.http.HttpRequest;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.http.RequestMethod;

public class TwitterHelper {
	
	private String login;
	private String password;
	
	public TwitterHelper(String login, String password)
	{
		this.login = login;
		this.password = password;
	}
	
	public void Post(String Message)
	{
		try{
			HttpClientImpl http;
		    HttpResponse response;
		    String resStr;
		    String authorizeURL;
		    HttpParameter[] params;
		    String cookie;
		    http = new HttpClientImpl();
		    
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("rbTj4kFPVIUg9FWT7usPQ", "i6nrTfROtT6FBic7scSgLUi63W8V8WR0ipSWuVeuE");
			RequestToken requestToken = twitter.getOAuthRequestToken();
			
		    Map<String, String> props = new HashMap<String, String>();
		    response = http.get(requestToken.getAuthorizationURL());
		    cookie = response.getResponseHeader("Set-Cookie");
		    props.put("Cookie", cookie);
			resStr = response.asString();
		    authorizeURL = catchPattern(resStr, "<form action=\"", "\" id=\"oauth_form\"");
		    params = new HttpParameter[4];
		    params[0] = new HttpParameter("authenticity_token"
		            , catchPattern(resStr, "\"authenticity_token\" type=\"hidden\" value=\"", "\" />"));
		    params[1] = new HttpParameter("oauth_token",
		            catchPattern(resStr, "name=\"oauth_token\" type=\"hidden\" value=\"", "\" />"));
		    params[2] = new HttpParameter("session[username_or_email]", login);
		    params[3] = new HttpParameter("session[password]", password);
		    response = http.request(new HttpRequest(RequestMethod.POST, authorizeURL, params, null, props));
		    resStr = response.asString();
		    String pin = catchPattern(resStr, "<kbd aria-labelledby=\"code-desc\"><code>", "</code></kbd>");
		    twitter.getOAuthAccessToken(requestToken, pin);
		    
		    twitter.updateStatus(Message);
		}
		catch (TwitterException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String catchPattern(String body, String before, String after) throws Exception {
        int beforeIndex = body.indexOf(before);
        int afterIndex = body.indexOf(after, beforeIndex);
        return body.substring(beforeIndex + before.length(), afterIndex);
    }
}
