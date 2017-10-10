import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class TextManager {
	
	private Scanner txtFile;
	private List<String> wordLst;
	private List<String> adjacentWordLst;
	private int differentNum = 0;
	
	TextManager()
	{
		wordLst = new ArrayList<String>();
		adjacentWordLst = new ArrayList<String>();
	}
	
	public boolean manageText(String filePath)
	{
		wordLst.clear();
		adjacentWordLst.clear();
		try {
			txtFile = new Scanner(Paths.get(filePath), "Unicode");
		} catch (IOException e) {
			System.out.println("file open error");
			e.printStackTrace();
		}
		setWordLst();
		setDifferentNum();
		setAdjacentWordLst();
		txtFile.close();
		if (wordLst.size() == 0) return false;
		return true;
	}
	
	private void setWordLst()
	{
		String line;
		while (txtFile.hasNext())
		{
			line = txtFile.nextLine();
			line = line.replaceAll("[^a-zA-Z]", " ");
			line = line.toLowerCase();
			wordLst.addAll(Arrays.asList(line.split("\\s+")));
		}
	}
	
	private void setAdjacentWordLst()
	{
		for (int i = 0; i < wordLst.size() - 1; i++)
		{
			adjacentWordLst.add(wordLst.get(i));
			adjacentWordLst.add(wordLst.get(i + 1));
		}
	}
	
	private void setDifferentNum()
	{
		Set<String> set=new HashSet<String>();
		set.addAll(wordLst);
		differentNum = set.size();
	}
	
	public int getWordNum() { return differentNum; }
	
	public List<String> getWordPair() { return adjacentWordLst; }
}
