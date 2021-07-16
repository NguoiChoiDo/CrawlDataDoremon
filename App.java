package ToolCrawlData.Demoproject;


import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class App 
{
	private static ArrayList<Chap> getAllChapInPage(String urls) throws IOException{
		ArrayList<Chap> listChap = new ArrayList<Chap>();
		Document doc = Jsoup.connect(urls).get();
		//https://jsoup.org/apidocs/org/jsoup/nodes/Element.html#getElementsByClass(java.lang.String)
		Elements elms = doc.getElementsByClass("ebook_row");
		for (int k = 0; k < elms.size();k++) {
			Elements listt = elms.get(k).getElementsByTag("li"); // doremon la "li"
				for(int i = 0; i < listt.size();i++) {
					Elements elm_row = listt.get(i).getElementsByTag("a");
					for (int j = 0; j < elm_row.size();j++) {
						String link_chap = elm_row.first().absUrl("href");
						listChap.add(new Chap(link_chap));
					}
				}
		}
		
		return listChap;
	}
	
	private static ArrayList<String> listImgOnPage(String pageURL) throws IOException{
		Document doc = Jsoup.connect(pageURL).get();
		ArrayList<String> list_img = new ArrayList<String>();
		Elements elms = doc.getElementsByClass("doc-online");
		//Elements e = elms.get(0).getElementsByTag("img"); //elms chi co 1 phan tu 
		Elements e = elms.get(0).getElementsByTag("p"); //elms chi co 1 phan tu
		for (int j = 0; j < e.size();j++) {
			Elements lis = e.get(j).getElementsByTag("img");
			for(int i = 0; i < lis.size()-1;i++) {
				String url = lis.get(i).absUrl("src");
				if(url.equals("")) {
					continue;
				} 
				list_img.add(url);
			}
		}
		return list_img;
	}
	
	private static void saveImg(String src_image,int name,String dir) {
		try {
			URL url = new URL(src_image);
			InputStream in = url.openStream();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(dir + "//" + Integer.toString(name) + ".jpg"));
			for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
			System.out.println("Download complete " + Integer.toString(name) + ".png");
			out.close();
            in.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Can not download file!!");
		}
	}
	
    public static void main( String[] args ) throws Exception
    {
    	String urls = "https://sachvui.com/ebook/truyen-tranh-doremon-full-mau-fujiko-f-fujio.4357.html";
    	String dir = "D://Baitap//Java//Tool//Do-remon";
    	ArrayList<Chap> listChap = new ArrayList<Chap>();
    	listChap = getAllChapInPage(urls);
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Doremon full hd :)) :");
    	System.out.println("crawl sachvui.com");
    	
    	// Test in ra link va ten tat ca chapter
    	for(int i = 0; i < listChap.size();i++) {
    		int stt = listChap.size() - i;
    		System.out.println(stt +": "+listChap.get(i).getUrl());
    		System.out.println("tap va chuong :"+listChap.get(i).getChap_number());
    	}
    	
    	System.out.println("Nhap SO THU TU tap va chuong can tai: ");
    	int chap = sc.nextInt();
    	ArrayList<String> listImg = listImgOnPage(listChap.get(chap-1).getUrl());
    	
    	for(int i = 0; i < listImg.size();i++) {
    		System.out.print("Downloading image "+ (i+1) + ".....");
    		saveImg(listImg.get(i),i,dir);
    	}
    	
  	
    	//In ra full link trang img truyen
    	for(int i = 0; i < listImg.size();i++) {
    		System.out.println(listImg.get(i));
    	}
    	sc.close();
    }
}
