import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class BlackBoxTest {

	@Test
	public void test() {
		TextManager tm = new TextManager();
        tm.manageText("/Users/wave/Desktop/SEtest.txt");
        GraphC og = new GraphC(tm.getWordNum());
        List<String> adjacentWordLst = tm.getWordPair();
        for (int i = 0; i < adjacentWordLst.size() - 1; i += 2)
            og.add(adjacentWordLst.get(i), adjacentWordLst.get(i + 1));
        og.getTriple(adjacentWordLst);
        og.generateBridge();
        og.print();
        // case1
        System.out.println("case 1 :");
        System.out.println(og.dijkstra("an"));
        // case2
        System.out.println("case 2 :");
        System.out.println(og.dijkstra("away"));
        // case3
        System.out.println("case 3 :");
        System.out.println(og.dijkstra("an", "away"));
        // case4
        System.out.println("case 4 :");
        System.out.println(og.dijkstra("away", "apple"));
        // case5
        System.out.println("case 5 :");
        System.out.println(og.dijkstra("emmmm", "apple"));
        // case5
        System.out.println("case 6 :");
        System.out.println(og.dijkstra("emmmm"));
		fail("Not yet implemented");
	}

}
