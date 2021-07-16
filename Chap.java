package ToolCrawlData.Demoproject;

public class Chap {
	private String url;
	private String chap_number;
	
	public Chap(String url) {
		this.url = url;
		chap_number = chapchuongNumber(url);
	}
	public String getUrl() {
		return url;
	}
	public String getChap_number() {
		return chap_number;
	}
	
	private String chapchuongNumber(String url) {
		int index1 = 0;
		int index2 = 0;
		for (int i = 0; i < url.length(); i++) {
			if(Character.isDigit(url.charAt(i))) {
				index1 = i;
				break;
			}
		}
		for (int i = url.length()-1; i >= 0 ; i--) {
			if(Character.isDigit(url.charAt(i))) {
				index2 = i + 1;
				break;
			}
		}
		return url.substring(index1,index2);
	}
}
