package com.waigo.nowcoder;

import java.io.*;

/**
 * author waigo
 * create 2021-08-06 23:00
 */
public class PrintFilesInTree {
    public static final String STEP = "____";
    public static void print(String path) throws IOException {
        File folder = new File(path);
        if(!folder.isDirectory()) return;
        File tree = new File("tree");
        tree.mkdir();
        File f = new File("tree/files.txt");
        f.createNewFile();
        File[] files = folder.listFiles();
        assert files!=null&&files.length>0;
        for (File file : files) {
            String name = file.getName();
            if(file.isDirectory()&& name.contains("-")&&(!name.contains("ui"))){
                printFormat(file,0,new PrintWriter(new FileOutputStream(f)));
            }
        }
    }

    private static void printFormat(File folder, int floor, PrintWriter printWriter) {
        if((!"target".equals(folder.getName()))&&
                (!folder.getName().contains(".xml"))&&(!folder.getName().contains("banner"))
                &&(!"resources".equals(folder.getName()))){
            if(floor!=0)
                System.out.print("|");
            for(int i = 0;i<floor;i++){
                System.out.print(STEP);
            }
            System.out.println("|"+folder.getName());
            File[] files = folder.listFiles();
            if(files!=null){
                for (File file : files) {
                    if(!"mapper".equals(file.getName())){
                        printFormat(file,floor+1,printWriter);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        print("F:\\后端技术学习\\我的后端代码\\05-开源项目\\01-若依\\RuoYi-Cloud");
    }
}
