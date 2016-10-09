package app;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class statingAPP {
	final static String APPFOLDER="C:\\Users\\nabisawa\\Desktop\\aa";
	final static String[] EXCLUDE_LIST={"ini"};

	public static void main(String args[])  {
		File[] appList = getFileList(APPFOLDER);
		//指定拡張子の除外
		appList = judgetExclude(appList);
		ProcessBuilder appPb = new ProcessBuilder();
		String fileName = null;
		try {
			for (File app : appList) {
				//ファイル名取得
				fileName = getFileName(app);
				//ブラウザ
				if (WebBrowserUtility.judgeWebBrowser(fileName)) {
					appCommandAct(appPb,app,WebBrowserUtility.getBookmarkList(fileName));
				}else{
					appCommandAct(appPb,app);
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 指定フォルダからファイルを取得
	 * @param String 指定フォルダ
	 * @return 取得したファイル一覧
	 *
	 */
	public static File[] getFileList(final String FOLDER) {
		File folder = new File(FOLDER);
		File[] fileList = folder.listFiles();
		return fileList;
	}

	/*
	 * ファイル名を取得
	 * @param File ファイル名を取得するファイル
	 * @return 取得したファイル名
	 *
	 */
	public static String getExtension(File app){
		String extension = null;
		extension = app.getName();
		int point = extension.lastIndexOf(".");
		if(point != -1){
			extension = extension.substring(point+1);
		}

		return extension;
	}

	/*
	 * 実行しない拡張子を除外する
	 * @param File 除外判定を行うファイル
	 * @return 除外後のファイルリスト
	 *
	 */
	public static File[] judgetExclude(File[] appList){
		boolean judge = false;
		String extension = null;
		ArrayList<File> arrayFile = new ArrayList<File>();
		for(File app : appList){
			extension = getExtension(app);
			for(String exclude : EXCLUDE_LIST){
				if(!extension.equals(exclude)){
					arrayFile.add(app);
				}
			}
		}
		return arrayFile.toArray(new File[0]);
	}


	/*
	 * 拡張子を取得
	 * @param File 拡張子を取得するファイル
	 * @return 取得した拡張子
	 *
	 */
	public static String getFileName(File app){
		String fileName = null;
		fileName = app.getName();
		int point = fileName.lastIndexOf(".");
		if(point != -1){
			fileName = fileName.substring(0,point);
		}

		return fileName;
	}

	/*
	 * 指定アプリを実行
	 * @param ProcessBuilder
	 * @param File 実行するアプリ
	 *
	 */
	public static void appCommandAct(ProcessBuilder appPb,File app) throws IOException, InterruptedException{
		appPb.command("cmd","/c",app.toString());
		Process appProcess = appPb.start();
		appProcess.waitFor();
	}

	/*
	 * 指定アプリを実行
	 * @param ProcessBuilder
	 * @param File 実行するアプリ
	 * @param ArrayList<String> アプリの引数
	 */
	public static void appCommandAct(ProcessBuilder appPb,File app,ArrayList<String> list) throws IOException, InterruptedException{
		if(list.isEmpty()){
			appCommandAct(appPb, app);
		}else{
			for(String str : list){
				appPb.command("cmd","/c",app.toString(),str);
				Process appProcess = appPb.start();
				appProcess.waitFor();
			}
		}
	}

}
