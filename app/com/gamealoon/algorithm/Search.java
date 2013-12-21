package com.gamealoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import play.Logger;

/**
 * 
 * @author Partho
 * 
 * First version of Gamealoon Search engine. Goal is to build a basic search engine which returns a list of urls against a keyword requested.
 * The flow of the search engine is as follows:
 * -> Crawl website
 * -> build index
 * -> perform lookups on the index
 *
 */
public class Search {
	public static HashMap<String, ArrayList<String>> index = new HashMap<>();
	ArrayList<String> tokens= new ArrayList<>();
	String seed="";
	
	
	//inits and refreshes the index
	public static void  initAndRefresh(String seed) throws MalformedURLException, IOException
	{	
		if(index.size()==0)
		{
			index= crawlSite(seed);
		}
		
		
	}
	
	private static void union(Stack<String> first, ArrayList<String >second)
	{
		for(int index=0; index<second.size(); ++index)
		{
			if(!first.contains(second.get(index)))
			{
				first.push(second.get(index));
			}
		}
	}

	/**
	 * Crawler for Gamealoon Searchengine. Algorithm for the crawler is as follows:
	 * -> Maintain two stacks called Crawled(to keep all the crawled links of the site) and toCrawl(to maintain what to crawl)
	 * -> Init toCrawl with the seed/homepage link/route
	 * -> loop until toCrawl is not empty:
	 * ->-> pop the link from TOS of toCrawl so that this link can be Crawled.
	 * ->-> if the popped link is not crawled,i.e, not in crawled stack, 
	 * ->->->-> get content from the link
	 * ->->->-> populate index with content
	 * ->->->-> do a union of toCrawl with all links extracted from the link so that toCrawl is populated with new unique links
	 * ->->->-> push the crawled link to Crawled stack
	 * @param seedUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static HashMap<String, ArrayList<String>> crawlSite(String seedUrl) throws MalformedURLException, IOException
	{		
		Stack<String> crawled=new Stack<>();
		Stack<String> toCrawl = new Stack<>();
		toCrawl.push(seedUrl);
		while(!toCrawl.isEmpty())
		{
			String link = toCrawl.pop();
			if(!link.equalsIgnoreCase(seedUrl))
			{
				link=(!link.startsWith("/"))?(seedUrl+"/"+link):(seedUrl+link);				
			}
			
			if(!crawled.contains(link))
			{
				String content = getPage(link);
				addPageToIndex(index,link,content);
				union(toCrawl, getAllLinksFromPage(content));
				crawled.push(link);
			}
		}
		
		
		return index;
	} 
	
	/**
	 * Lookup method for looking up keyword in index 
	 * 
	 * @param keyword
	 * @return
	 * @throws IllegalAccessException
	 */
	public static ArrayList<String> lookup(String keyword) throws IllegalAccessException
	{
		return lookup(index, keyword);
	}
	
	private  static ArrayList<String> lookup(HashMap<String, ArrayList<String>> index, String keyword) throws IllegalAccessException
	{		
		if(index.containsKey(keyword))
		{
			return index.get(keyword);
		}
		else
		{
			throw new IllegalAccessException("Keyword "+keyword+" not found");
		}
	}
	
	/**
	 * Fetch each word from content and make a list out of it so that we can store it in index along with link
	 * 
	 * @param index
	 * @param link
	 * @param content
	 */
	private static void addPageToIndex(HashMap<String, ArrayList<String>> index, String link, String content)
	{
		String[] wordList = content.split("[^A-Za-z0-9]+");
		
		for(String word: wordList)
		{
			addToIndex(index, word, link);
		}
	}
	
	/**
	 * This method actually add the <Keyword, List<URL>> to index
	 * 
	 * @param index
	 * @param keyword
	 * @param url
	 */
	private static void addToIndex(HashMap<String, ArrayList<String>> index, String keyword, String url)
	{
		ArrayList<String> urls=null;
		if(!index.containsKey(keyword))
		{
			urls= new ArrayList<>();					
		}
		else
		{
			urls=index.get(keyword);						
		}
		if(!urls.contains(url))
		{
			urls.add(url);
		}
		
		index.put(keyword, urls);
	}
	
	private static ArrayList<String> getAllLinksFromPage(String page)
	{
		ArrayList<String> links = new ArrayList<>();
		int startIndex=page.indexOf("<a");		
		
		while(startIndex!=-1)
		{
			int hrefIndex= page.indexOf("href=\"", startIndex);			
			int endIndex=page.indexOf("\"",hrefIndex+6);
			String link =page.substring(hrefIndex+6, endIndex);			
			links.add(link);
			startIndex=page.indexOf("<a", endIndex);			
		}
		return links;
	}
	
	/**
	 * This method is actually scraping the html to fetch important information for news. Open Graph really helped well and eased out the operation.
	 * 
	 * @param link
	 * @return
	 */
	public static HashMap<String, String> scrapePage(String link)
	{
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		String page="";
		try {
			page = getPageForScraping(link);		
			int urlStartIndex=link.indexOf("://");
			String domainUrl=link.substring(urlStartIndex+3, link.indexOf("/",urlStartIndex+3));
			response.put("domainUrl", domainUrl);
			int titleStartIndex=page.indexOf("<meta property=\"og:title\" content=\"");			
			String title="";
			if(titleStartIndex>-1)
			{
				title = page.substring(titleStartIndex+35, page.indexOf("\"",titleStartIndex+35));
			}
			else
			{
				titleStartIndex=page.indexOf("<title>");
				title = page.substring(titleStartIndex+7, page.indexOf("<",titleStartIndex+7));
			}
			
			Logger.debug("title: "+title);			
			response.put("title", title);
			int metaDescStartIndex = page.indexOf("<meta property=\"og:description\" content=\"");						
			String description ="";
			if(metaDescStartIndex>-1)
			{
				description = page.substring(metaDescStartIndex+41, page.indexOf("\"",metaDescStartIndex+41));
			}
			else
			{
				description=title;
			}
			
			Logger.debug("description: "+description);			
			response.put("description", description);
			
			int imageStartIndex=page.indexOf("<meta property=\"og:image\" content=\"");
			String image="";
			if(imageStartIndex>-1)
			{
				image=page.substring(imageStartIndex+35, page.indexOf("\"",imageStartIndex+35));
			}			
			Logger.debug("image: "+image);			
			response.put("image", image);
			response.put("status", "success");
			Logger.debug("Map "+response);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.error("Error in scraping the page ", e.fillInStackTrace());
			e.printStackTrace();			
		}		
		
		return response;
	}		
	
	private static String getPageForScraping(String link) throws MalformedURLException,IOException
	{
		String output="";		
		URL url = new URL(link);		
		HttpURLConnection  urlConn = (HttpURLConnection) url.openConnection();
		BufferedReader read = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line="";
			
		while(((line=read.readLine())!=null)){
				output+=line;
				output+="\n";
		}				
		return output;
				
	}
	
	public static String getPage(String link) throws MalformedURLException,IOException
	{
		String output="";		
		URL url = new URL(link);		
		HttpURLConnection  urlConn = (HttpURLConnection) url.openConnection();
		BufferedReader read = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line="";
			
		while(((line=read.readLine())!=null)){
				output+=line;
				output+="\n";
		}		
		output = output.substring(output.indexOf("<body>")+6, output.indexOf("</body>"));
		return output;
				
	}
	
	public static void main(String[] args)
	{		
		/*Search search = new Search();
		try
		{			
			search.initAndRefresh("http://localhost:8080");
			try {
				String[] keywords={"prince","Superman", "ps3"};
				for(String keyword:keywords)
				{
					
					System.out.println(search.lookup(keyword));
				}
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		}catch(MalformedURLException ex)
		{
			System.out.println(ex);
		}
		catch(IOException ie)
		{
			System.out.println(ie);
		}*/
	}
	

}
