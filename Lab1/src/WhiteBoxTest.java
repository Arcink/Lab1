import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class WhiteBoxTest {
    
    @Test
    public void test()
    {
        TextManager tm = new TextManager();
        tm.manageText("C:\\Users\\skill\\Documents\\Lab6_test.txt");
        OrientedGraph og = new OrientedGraph(tm.getWordNum());
        List<String> adjacentWordLst = tm.getWordPair();
        for (int i = 0; i < adjacentWordLst.size() - 1; i += 2)
            og.add(adjacentWordLst.get(i), adjacentWordLst.get(i + 1));
        og.getTriple(adjacentWordLst);
        og.generateBridge();
        //og.print();
        // case1
        System.out.println("case 1 :");
        System.out.println(og.queryBridgeWord("hehe", "hehe"));
        // case2
        System.out.println("case 2 :");
        System.out.println(og.queryBridgeWord("and", "hehe"));
        // case3
        System.out.println("case 3 :");
        System.out.println(og.queryBridgeWord("and", "return"));
        // case4
        System.out.println("case 4 :");
        System.out.println(og.queryBridgeWord("and", "or"));
        
    }
}
