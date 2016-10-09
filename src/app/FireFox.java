package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FireFox extends WebBrowserUtility{

	private final String FOLDER_TYPE="text/x-moz-place-container";
	private final String URL_TYPE="text/x-moz-place";
	private final String TYPE = "type";
	private final String NAME ="title";
	private final String URL = "uri";
	private final String BOOKMARK="C:\\Users\\nabisawa\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\kuilpdc5.default\\bookmarkbackups";
	private final String BOOKMARK_AUTO = "auto";

	/*
	 * ファイヤフォックスのブックマークを取得
	 * @return ブックマークの一覧
	 *
	 */
	public ArrayList<String> actionFireFox() throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		JsonNode root = mapper.readTree(new File(getBookMark()));

		for(Iterator<Map.Entry<String,JsonNode>> fieldNames = root.fields();fieldNames.hasNext();){
			autoBookmarkAct(fieldNames,list,false);
		}
		return list;
	}

	/*
	 * ファイヤフォックスのブックマークファイルを取得
	 * @return ブックマークファイル
	 *
	 */
	public String getBookMark(){
		File[] fileList = statingAPP.getFileList(BOOKMARK);
		ArrayList<String> fileName= new ArrayList<String>();
		for (File file : fileList) {
			fileName.add(file.toString());
		}
		Collections.reverse(fileName);

		return fileName.get(0);

	}

	public String getFolderType(){
		return FOLDER_TYPE;
	}

	public String getUrlType(){
		return URL_TYPE;
	}

	public String getType(){
		return TYPE;
	}

	public String getName(){
		return NAME;
	}

	public String getUrl(){
		return URL;
	}

	public String getBookmarkAuto(){
		return BOOKMARK_AUTO;
	}

}
