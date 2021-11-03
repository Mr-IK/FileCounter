package jp.mkserver;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileCounter {

    public static String VERSION = "1.0";

    // 指定フォルダ内フォルダの中にあるファイルもカウントするか
    // これをtrueにするとフォルダ内フォルダ内ファイル(以下略)も自動でtrueとなる
    public static boolean FOLDER_IN_FILE = true;

    // 指定した拡張子のみカウントする
    // 複数指定可能(txt,ymlなど)
    public static List<String> WHITELIST_EXT = new ArrayList<>();

    public static void main(String[] args){
        msg("File Counter v"+VERSION+" Enabled.");
        if(args.length==0){
            error("ERROR: Not Select Target Folder.");
            return;
        }
        String fold = args[0];
        File folder = new File(fold);
        if(!folder.exists()) {
            error("ERROR: Target Is Not Exists.");
            return;
        }else if(!folder.isDirectory()){
            error("ERROR: Target Is Not Folder.");
            return;
        }
        if(args.length>=2){
            WHITELIST_EXT.addAll(Arrays.asList(args[1].split(",")));
        }
        msg("FolderInFile: "+FOLDER_IN_FILE);

        // 処理
        List<String> result = folderInCount(new ArrayList<>(),folder);
        msg("///////////////////////");
        msg("// 結果！！");
        msg("// "+result.size()+"ファイル！！");
        msg("///////////////////////");
    }

    public static List<String> folderInCount(List<String> list, File folder){
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()){  // (c)
                    String filename = f.getName();
                    if(WHITELIST_EXT.size()!=0&&!WHITELIST_EXT.contains(getFileExt(filename))){
                        continue;
                    }
                    list.add(filename);
                    msg("Counted: "+filename);
                }else if(f.isDirectory()&&FOLDER_IN_FILE){
                    folderInCount(list, new File(f.getAbsolutePath()));
                }
            }
        }
        return list;
    }

    public static void msg(String text){
        System.out.println(text);
    }

    public static void error(String text){
        System.out.println(Color.RED+text);
    }

    public static String getFileExt(String file){
        return file.substring(file.lastIndexOf(".") + 1);
    }
}
