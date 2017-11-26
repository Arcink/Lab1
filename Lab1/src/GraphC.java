import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;

// change
public class GraphC {
    private HashMap<String, Integer> map = new HashMap<>();
    public String[][][] bridge;
    private GraphE graphE;

    public HashMap<String, Boolean> tripleMap = new HashMap<String, Boolean>();

    GraphC(int numberOfVex) {
        graphE = new GraphE(numberOfVex);
        bridge = new String[numberOfVex][numberOfVex][numberOfVex];
    }

    public Node[] getmVex() {
        return graphE.getmVex();
    }

    public int getAddNow() {
        return graphE.getAddNow();
    }

    public void getTriple(List<String> wordLst) {
        for (int i = 0; i < wordLst.size() - 3; i += 2)
            if (wordLst.get(i).equals(wordLst.get(i + 1)) && wordLst.get(i + 2).equals(wordLst.get(i + 3)))
                tripleMap.put(wordLst.get(i), true);
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        for (int i = 0; i < addNow; i++)
            if (tripleMap.get(mVex[i].ivex) != null && !tripleMap.get(mVex[i].ivex).equals(true))
                tripleMap.put(mVex[i].ivex, false);
    }

    public void add(String word1, String word2) {
        graphE.add(word1, word2);
    }

    public void print() {
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        for (int i = 0; i < addNow; i++) {
            Node tmp = mVex[i];
            System.out.print(tmp.ivex + ":\t\t");
            while (tmp.nextEdge != null && tmp.nextEdge.ivex != null) {
                System.out.print(tmp.nextEdge.ivex + "\t:" + Integer.toString(tmp.nextEdge.weight) + "\t\t");
                tmp = tmp.nextEdge;
            }
            System.out.print("\n");
        }
        System.out.println("");
    }

    public void generateBridge() {
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        String last = graphE.getLast();
        for (int i = 0; i < addNow; i++)
            for (int j = 0; j < addNow; j++)
                for (int k = 0; k < addNow; k++)
                    bridge[i][j][k] = "0";
        for (int i = 0; i < addNow; i++)
            map.put(mVex[i].ivex, i); // Accelerate search process by using
                                      // advanced data structure; ____ Liu and
                                      // Li
        String word1;

        for (int i = 0; i < addNow; i++) {
            word1 = mVex[i].ivex;
            if (i == addNow - 1 && word1.equals(last) && mVex[addNow - 1].nextEdge.nextEdge == null) {
                for (int m = 0; m < addNow; m++) {
                    for (int j = 0; j < addNow; j++) {
                        if (!bridge[m][j][0].equals("0")) {
                            System.out.println(mVex[m].ivex + "\t" + mVex[j].ivex + ":\t");
                            for (int k = 0; k < addNow; k++) {
                                if (!bridge[m][j][k].equals("0")) {
                                    System.out.println("\t" + bridge[m][j][k]);
                                }
                            }
                            System.out.println("");
                        }
                    }
                }
                return;
            }
            Node tmp = mVex[i].nextEdge;
            while (tmp != null) {
                if (tmp.ivex.equals(last) && mVex[(int) map.get(last)].nextEdge == null) {
                    tmp = tmp.nextEdge;
                    continue;
                }
                Node tmptmp = mVex[(int) map.get(tmp.ivex)].nextEdge;
                while (tmptmp != null) {
                    for (int j = 0; j < addNow; j++) {
                        if (tmptmp.ivex == null)
                            continue;
                        if (bridge[(int) map.get(word1)][(int) map.get(tmptmp.ivex)][j].equals("0")) {
                            if (!word1.equals(tmptmp.ivex))
                                bridge[(int) map.get(word1)][(int) map.get(tmptmp.ivex)][j] = tmp.ivex;
                            else if (word1.equals(tmptmp.ivex) && tripleMap.get(word1) != null && tripleMap.get(word1))
                                bridge[(int) map.get(word1)][(int) map.get(tmptmp.ivex)][j] = tmp.ivex;
                            break;
                        }
                    }
                    tmptmp = tmptmp.nextEdge;
                }
                tmp = tmp.nextEdge;
            }
        }
    }

    public String queryBridgeWord(String word1, String word2) {
        int addNow = graphE.getAddNow();
        if (map.get(word1) == null || map.get(word2) == null)
            return null;
        int i = (int) map.get(word1), j = (int) map.get(word2), k;
        for (k = 0; k < addNow; k++)
            if (bridge[i][j][k].equals("0"))
                break;
        if (k == 0)
            return null;
        Random rand = new Random();
        return bridge[i][j][(int) Math.floor(rand.nextInt(k))];
    }

    public String generateNewString(String inputText) throws IOException {
        List<String> wordLst = new ArrayList<String>();
        List<String> adjacentWordLst = new ArrayList<String>();
        inputText = inputText.replaceAll("[^a-zA-Z]", " "); // delete non-word
        inputText = inputText.toLowerCase();
        wordLst.addAll(Arrays.asList(inputText.split("\\s+")));
        for (int i = 0; i < wordLst.size() - 1; i++) {
            adjacentWordLst.add(wordLst.get(i));
            adjacentWordLst.add(wordLst.get(i + 1));
        }
        String NewString = "", tmp;
        for (int i = 0; i < wordLst.size() - 1; i++) {
            tmp = queryBridgeWord(wordLst.get(i), wordLst.get(i + 1));
            if (tmp != null && tmp != "")
                NewString += wordLst.get(i) + " " + tmp + " ";
            else
                NewString += wordLst.get(i) + " ";
        }
        NewString += wordLst.get(wordLst.size() - 1);
        return NewString;
    }

    public String randomWalk() // Can't be used be map is completed (function
                               // generateBridge)
    {
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        String result = "";
        boolean[][] marks = new boolean[addNow][addNow];
        for (int p = 0; p < addNow; p++)
            for (int q = 0; q < addNow; q++)
                marks[p][q] = false;
        Random random = new Random();
        Node tmp, tmp2;
        int i, j;
        int numOut; // number of out edges of a word
        // random select a node to begin
        i = random.nextInt(addNow);
        tmp = mVex[i];
        tmp2 = mVex[i];
        result += tmp.ivex + " -";
        // loop to walk next word
        while (true) {
            numOut = 0;
            while (tmp2.nextEdge != null && tmp2.nextEdge.ivex != null) {
                tmp2 = tmp2.nextEdge;
                numOut++;
            }
            if (numOut == 0)
                return result; // no out edge, end
            j = random.nextInt(numOut); // random select a edge
            for (int k = 0; k <= j; k++)
                tmp = tmp.nextEdge; // find the end point of the out edge
            j = map.get(tmp.ivex);
            if (marks[i][j] == false) {
                result += "> " + tmp.ivex + " -";
                marks[i][j] = true;
            } else
                return result; // visited edge, end
            i = j;
            tmp = mVex[i];
            tmp2 = tmp;
        }
    }

    private boolean isInWordLst(String word1) {
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        boolean result = false;
        for (int i = 0; i < addNow; i++) {
            if (mVex[i].ivex.equals(word1))
                result = true;
        }
        return result;
    }

    public String dijkstra(String word1) {
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        word1 = word1.toLowerCase();
        if (!isInWordLst(word1))
            return "Word is not in the word list.";
        String result = "\n" + word1 + ":\n";
        Integer[][] toShowPath = new Integer[addNow][addNow];
        Integer[][] graph = new Integer[addNow][addNow];
        for (int i = 0; i < addNow; i++)
            for (int j = 0; j < addNow; j++)
                toShowPath[i][j] = 0;
        for (int i = 0; i < addNow; i++)
            for (int j = 0; j < addNow; j++)
                graph[i][j] = Integer.MAX_VALUE / 2;
        for (int i = 0; i < addNow; i++) {
            Node tmp = mVex[i];
            while (tmp.nextEdge != null && tmp.nextEdge.ivex != null) {
                graph[(int) map.get(mVex[i].ivex)][(int) map.get(tmp.nextEdge.ivex)] = tmp.nextEdge.weight;
                tmp = tmp.nextEdge;
            }
        }
        for (int i = 0; i < addNow; i++)
            graph[i][i] = 0;
        int[] mindist = new int[addNow];
        // �ýڵ��Ƿ��Ѿ��ҵ����·��
        int vs = (int) map.get(word1);
        Integer nearest = Integer.MAX_VALUE / 2;
        Integer min = Integer.MAX_VALUE / 2;
        Integer[] tmpDist = new Integer[addNow];
        boolean[] find = new boolean[addNow];
        for (int i = 0; i < addNow; i++) {
            mindist[i] = graph[vs][i];
            find[i] = false;
            tmpDist[i] = mindist[i];
        }
        find[vs] = true;
        for (int i = 1; i < addNow; i++) // ѭ����չn-1��
        {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < addNow; j++) // Ѱ��δ����չ��Ȩֵ��С�Ķ���
            {
                if (find[j] == false && mindist[j] < min) {
                    min = mindist[j];
                    nearest = j;
                }
            }
            find[nearest] = true;
            for (int k = 0; k < addNow; k++) // ����dist�����ֵ��·����ֵ
            {
                if (find[k] == false && graph[nearest][k] > 0 && min + graph[nearest][k] < mindist[k]) {
                    mindist[k] = min + graph[nearest][k];
                    toShowPath[nearest][k] = 1;
                }
            }
        }
        for (int i = 0; i < addNow; i++)
            if (mindist[i] == tmpDist[i] && (int) map.get(word1) != i)
                toShowPath[(int) map.get(word1)][i] = 1;
        System.out.println("");
        /*
         * for (int i = 0; i < addNow; i++) { if (mindist[i] !=
         * Integer.MAX_VALUE/2) System.out.println(mindist[i]); else
         * System.out.println("Can't Reach"); }
         */
        HashMap<Integer, String> reverseMap = new HashMap<>();
        for (int i = 0; i < addNow; i++)
            reverseMap.put(i, mVex[i].ivex);
        Stack<Integer> path = new Stack<Integer>();
        Integer[] vistedIf = new Integer[addNow];
        for (int i = 0; i < addNow; i++) // ��ÿһ����Ѱ��·��
        {
            if (i != (int) map.get(word1)) // ��㲻Ϊ�յ�
            {
                for (int k = 0; k < addNow; k++)
                    vistedIf[k] = 0;
                path.push((int) map.get(word1));
                vistedIf[(int) map.get(word1)] = 1;
                while (!path.isEmpty()) {
                    for (int x = 0; x < addNow; x++) {
                        if (toShowPath[path.peek()][x] != 0 && vistedIf[x] == 0) {
                            if (x == i) // �ҵ���
                            {
                                result += word1 + "\t->\t" + mVex[i].ivex + ": \t";
                                for (int m = 0; m < path.size(); m++)
                                    result += mVex[(int) path.get(m)].ivex + "\t";
                                result += mVex[i].ivex + "\n";
                                path.clear();
                                break;
                            } else {
                                path.push(x);
                                vistedIf[x] = 1;
                                break;
                            }
                        } else if (x == addNow - 1)
                            path.pop();
                        if (path.isEmpty())
                            result += word1 + "\t->\t" + mVex[i].ivex + ": \tNo Way";
                    }
                }
            }
        }
        return result;
    }

    public String dijkstra(String word1, String word2) {
        int addNow = graphE.getAddNow();
        Node[] mVex = graphE.getmVex();
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        if (!isInWordLst(word1) || !isInWordLst(word2))
            return "Word is not in the word list.";
        String result = "\n" + word1 + ":\n";
        Integer[][] toShowPath = new Integer[addNow][addNow];
        Integer[][] graph = new Integer[addNow][addNow];
        for (int i = 0; i < addNow; i++)
            for (int j = 0; j < addNow; j++)
                toShowPath[i][j] = 0;
        for (int i = 0; i < addNow; i++)
            for (int j = 0; j < addNow; j++)
                graph[i][j] = Integer.MAX_VALUE / 2;
        for (int i = 0; i < addNow; i++) {
            Node tmp = mVex[i];
            while (tmp.nextEdge != null && tmp.nextEdge.ivex != null) {
                graph[(int) map.get(mVex[i].ivex)][(int) map.get(tmp.nextEdge.ivex)] = tmp.nextEdge.weight;
                tmp = tmp.nextEdge;
            }
        }
        for (int i = 0; i < addNow; i++)
            graph[i][i] = 0;
        int[] mindist = new int[addNow];
        // �ýڵ��Ƿ��Ѿ��ҵ����·��
        int vs = (int) map.get(word1);
        Integer nearest = Integer.MAX_VALUE / 2;
        Integer min = Integer.MAX_VALUE / 2;
        Integer[] tmpDist = new Integer[addNow];
        boolean[] find = new boolean[addNow];
        for (int i = 0; i < addNow; i++) {
            mindist[i] = graph[vs][i];
            find[i] = false;
            tmpDist[i] = mindist[i];
        }
        find[vs] = true;
        for (int i = 1; i < addNow; i++) // ѭ����չn-1��
        {
            min = Integer.MAX_VALUE;
            for (int j = 0; j < addNow; j++) // Ѱ��δ����չ��Ȩֵ��С�Ķ���
            {
                if (find[j] == false && mindist[j] < min) {
                    min = mindist[j];
                    nearest = j;
                }
            }
            find[nearest] = true;
            for (int k = 0; k < addNow; k++) // ����dist�����ֵ��·����ֵ
            {
                if (find[k] == false && graph[nearest][k] > 0 && min + graph[nearest][k] < mindist[k]) {
                    mindist[k] = min + graph[nearest][k];
                    toShowPath[nearest][k] = 1;
                }
            }
        }
        for (int i = 0; i < addNow; i++)
            if (mindist[i] == tmpDist[i] && (int) map.get(word1) != i)
                toShowPath[(int) map.get(word1)][i] = 1;
        System.out.println("");
        HashMap<Integer, String> reverseMap = new HashMap<>();
        for (int i = 0; i < addNow; i++)
            reverseMap.put(i, mVex[i].ivex);
        Stack<Integer> path = new Stack<Integer>();
        Integer[] vistedIf = new Integer[addNow];
        // i = (int) map.get(word2)
        Integer word2Num = (int) map.get(word2);
        if (word2Num != (int) map.get(word1)) // ��㲻Ϊ�յ�
        {
            for (int k = 0; k < addNow; k++)
                vistedIf[k] = 0;
            path.push((int) map.get(word1));
            vistedIf[(int) map.get(word1)] = 1;
            while (!path.isEmpty()) {
                for (int x = 0; x < addNow; x++) {
                    if (toShowPath[path.peek()][x] != 0 && vistedIf[x] == 0) {
                        if (x == word2Num) // �ҵ���
                        {
                            result += word1 + "\t->\t" + mVex[word2Num].ivex + ": \t";
                            for (int m = 0; m < path.size(); m++)
                                result += mVex[(int) path.get(m)].ivex + "\t";
                            result += word2 + "\n";
                            path.clear();
                            break;
                        } else {
                            path.push(x);
                            vistedIf[x] = 1;
                            break;
                        }
                    } else if (x == addNow - 1)
                        path.pop();
                    if (path.isEmpty())
                        result += word1 + "\t->\t" + mVex[word2Num].ivex + ": \tNo Way";
                }
            }
        }
        return result;
    }

    public static void main(String args[]) throws IOException {
        GraphC test1 = new GraphC(5);
        test1.add("i", "write");
        test1.add("write", "write");
        test1.add("write", "write");
        test1.add("write", "to");
        test1.add("to", "i");
        test1.add("i", "call");
        test1.add("call", "to");
        test1.add("to", "i");
        test1.add("i", "write");
        test1.add("write", "x");
        test1.print();
        test1.generateBridge();
        System.out.println(test1.dijkstra("write"));
        System.out.println(test1.dijkstra("write", "call"));
        System.out.println(test1.generateNewString("i write to"));
    }

}