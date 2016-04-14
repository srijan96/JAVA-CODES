/*Program to zip a file and a directory*/
package java_assignments.zipFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.GZIPOutputStream;
 
public class ZipFile {
     
    List<String> filesInList = new ArrayList<String>(); //list to store the absolute paths of the files inside a directory
 
    public static void main(String[] args) {
        
        ZipFile file = new ZipFile();
        File files = new File("/home/srijan/text1.txt");
        String zipFileName = "/home/srijan/text1.gz";
        file.zipSingleFile(files, zipFileName);
         
        File dir = new File("/home/srijan/srijan_testZip");
        String zipDirName = "/home/srijan/srijan_testZip.zip";
   
        file.zipDirectory(dir, zipDirName);
    }
 
 /*method to zip a directory*/
   public void zipDirectory(File dir, String zipDirName) {
        try {
            FilesList(dir);
            FileOutputStream fos = new FileOutputStream(zipDirName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesInList){
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length()+1, filePath.length()));
                zos.putNextEntry(ze);
                FileInputStream fis = new FileInputStream(filePath);
                byte[] b = new byte[1024];
                int len;
                while ((len = fis.read(b)) > 0) {
                    zos.write(b, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
  /*method to recursively call a function to store it's absolute paths in the ArrayList*/ 
    public void FilesList(File dir) throws IOException {
        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isFile()) filesInList.add(file.getAbsolutePath());
            else FilesList(file);
        }
    }
    
  /*method to zip a single file*/
    public void zipSingleFile(File files, String zipFileName) {
        try {
            
            FileOutputStream fos = new FileOutputStream(zipFileName);
            GZIPOutputStream zos = new GZIPOutputStream(fos);
            FileInputStream fis = new FileInputStream(files);
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) > 0) {
                zos.write(b, 0, len);
            }
            zos.close();
            fis.close();
            fos.close();
             
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
 
}

