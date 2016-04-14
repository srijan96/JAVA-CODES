package java_assignments.listDir;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class ListDir{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static void main(String args[])
	{
		File  f;
		if(args.length > 0 && args[args.length-1].charAt(0) != '-')
			f = new File(args[args.length-1]);
		else
			f = new File(".");
		File[] files = f.listFiles();
		if(args.length > 0 && args[0].equals("-s")){
			Arrays.sort(files,new Comparator<File>(){
				public int compare(File file1,File file2) {
					return Long.compare(file1.length(), file2.length());
				}
			});
		}
		else if(args.length > 0 && args[0].equals("-d")){
			Arrays.sort(files,new Comparator<File>(){
				public int compare(File file1,File file2) {
					return Long.compare(file1.lastModified(), file2.lastModified());
				}
			});
		}
		else
			Arrays.sort(files);
		for (File file : files)
		{
			if(file.isDirectory())
				System.out.println(ANSI_BLUE+file.getName()+ANSI_RESET);
			else 
				System.out.println(file.getName());
		}	
	}
}
