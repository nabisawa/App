package app;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleChrome extends WebBrowserUtility {

	private final String FOLDER_TYPE="folder";
	private final String URL_TYPE="url";
	private final String URL="url";
	private final String TYPE = "type";
	private final String NAME ="name";
	private final String BOOKMARK = "";
	private final String BOOKMARK_AUTO = "auto";

	/*
	 * グーグルクロームのブックマークを取得
	 * @return ブックマークの一覧
	 *
	 */
	public ArrayList<String> actionGooglechorome() throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		JsonNode root = mapper.readTree(new File(BOOKMARK)).path("roots").path("other");

		for(Iterator<Map.Entry<String,JsonNode>> fieldNames = root.fields();fieldNames.hasNext();){
			autoBookmarkAct(fieldNames,list,false);

		}
		return list;
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
