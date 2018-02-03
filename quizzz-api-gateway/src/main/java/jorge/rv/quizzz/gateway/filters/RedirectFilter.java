package jorge.rv.quizzz.gateway.filters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/*
 * 
 * Zuul filter to modify the host and port of re-directs coming from
 * microservices.
 * 
 * TODO: Whitelist external hosts that may need to be addressed.
 * TODO: Re-visit when the migration reaches Security.
 * 
 */
public class RedirectFilter extends ZuulFilter {
	
	private static final int HTTP_STATUS_REDIRECT = 302;
	private static final String HTTP_HEADER_REDIRECT = "Location";
	
	private RequestContext ctx; 

	@Override
	public Object run() {
		ctx = RequestContext.getCurrentContext();
		
		Pair<String, String> locationHeader = findLocationInHeaders();
		
		if (locationHeader == null)
			return null;
		
		try {
			URL redirectUrl = new URL(locationHeader.second());
			URL newUrl = new URL(redirectUrl.getProtocol(), 
					ctx.getRequest().getServerName(), 
					ctx.getRequest().getServerPort(), 
					redirectUrl.getFile());
			
			locationHeader.setSecond(newUrl.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean shouldFilter() {
		return isRedirect();
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "post";
	}
	
	private Pair<String,String> findLocationInHeaders() {
		Collection<Pair<String, String>> headers = ctx.getZuulResponseHeaders();
		
		for (Pair<String, String> header: headers) {
			if (header.first().equals(HTTP_HEADER_REDIRECT))
				return header;
		}
		
		return null;
	}
	
	private boolean isRedirect() {
		return RequestContext.getCurrentContext().getResponseStatusCode() == HTTP_STATUS_REDIRECT;
	}

}
