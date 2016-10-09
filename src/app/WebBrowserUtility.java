package app;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

abstract class WebBrowserUtility {
	final static String GOOGLECHROME_LINK_NAME = "chrome";
	final static String FIREFOX_LINK_NAME = "firefox";
	final static String IE_LINK_NAME ="";

	abstract String getFolderType();
	abstract String getUrlType();
	abstract String getType();
	abstract String getName();
	abstract String getUrl();
	abstract String getBookmarkAuto();


	/*
	 * 指定アプリがウェブブラウザか判定
	 * @param String ファイル名
	 * @return 判定結果
	 *
	 */
	public static boolean judgeWebBrowser(String fileName){
		boolean webBrowser = false;
		if(fileName.equals(GOOGLECHROME_LINK_NAME)){
			webBrowser = true;
		}else if(fileName.equals(FIREFOX_LINK_NAME)){
			webBrowser = true;
		}else if(fileName.equals(IE_LINK_NAME)){
			webBrowser = true;
		}
		return webBrowser;
	}

	/*
	 * ウェブブラウザのブックマークを取得
	 * @param String ファイル名
	 * @return ブックマークの一覧
	 *
	 */
	public static ArrayList<String> getBookmarkList(String fileName) throws JsonProcessingException, IOException{

		ArrayList<String> bookmarkList= new ArrayList<String>();
		if(fileName.equals(GOOGLECHROME_LINK_NAME)){
			GoogleChrome gc = new GoogleChrome();
			bookmarkList = gc.actionGooglechorome();
		}else if(fileName.equals(FIREFOX_LINK_NAME)){
			FireFox ff = new FireFox();
			bookmarkList = ff.actionFireFox();

		}else if(fileName.equals(IE_LINK_NAME)){

		}
		return bookmarkList;

	}

	/*
	 *
	 * @param String ファイル名
	 * @return ブックマークの一覧
	 *
	 */
	public void autoBookmarkAct(Iterator<Map.Entry<String,JsonNode>> fieldNames,ArrayList<String> list,boolean flg){
		String fieldKey = null;
		JsonNode fieldValue = null;
		Entry<String, JsonNode> field = null;
		String folderType = getFolderType();
		String urlType = getUrlType();
		String url = getUrl();
		String type = getType();
		String name = getName();
		String bookmarkAuto = getBookmarkAuto();

		for(Iterator<Map.Entry<String,JsonNode>> en = fieldNames;en.hasNext();){
			field = en.next();
			if("children".equals(field.getKey())){
				fieldKey = field.getKey();
				fieldValue = field.getValue();
			}
		}

		if(fieldValue.isArray()){
			for(int i=0 ;i < fieldValue.size();i++){
				if(folderType.equals(fieldValue.path(i).path(type).asText())){
					if(bookmarkAuto.equals(fieldValue.path(i).path(name).asText())){
						autoBookmarkAct(fieldValue.path(i).fields(),list,true);
					}else{
						autoBookmarkAct(fieldValue.path(i).fields(),list,flg);
					}
				}else{
					if(flg){
						if(urlType.equals(fieldValue.path(i).path(type).asText())){
							list.add(fieldValue.path(i).path(url).asText());
						}
					}
				}

			}
		}
	}


}
