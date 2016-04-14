package java_assignments.bookIndex;

import java.util.*;
import java.io.*;

class BookIndex{
	public static void main(String args[])
	{
		Scanner sc;
		int lineNo = 0;
		String line;
		Map<String,String> hash = new TreeMap<String,String>();
		if(args.length <1)
			System.exit(1);
		try
		{
			sc = new Scanner(new FileInputStream(args[0]));
			while((line=sc.nextLine()) != null)
			{
				lineNo = lineNo + 1;
				String words[] = line.replaceAll("[^a-zA-Z0-9]"," ").toLowerCase().split("\\s+");
				for(String word : words)
				{
					if(hash.get(word) == null)
						hash.put(word,Integer.toString(lineNo));
					else
					{
						String current = hash.get(word) + " " + Integer.toString(lineNo);
						hash.put(word,current);
					}
				}
			}
		}
		catch(Exception e)
		{
			//System.out.println(e.toString());
		}	
	
		for(Map.Entry m:hash.entrySet())
		{
			System.out.print(m.getKey()+"\t");
			String s = (String) m.getValue();
			String[] st = s.split("\\s+");
			String prev = "";
			for(String i : st)
			{
				if(!(i.equals(prev)))
					System.out.print(i+" ");
				prev = i;
			}
			System.out.println();
		}
	}
}

