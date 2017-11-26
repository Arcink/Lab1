
public class GraphE {
    private Node[] mVex;
    private int addNow;
    private String last;
    
    GraphE(int numberOfVex)
    {
            mVex = new Node[numberOfVex];
            addNow = 0;         // Record how many words has been added;
    }
    
    public Node[] getmVex()
    {
            return mVex;
    }
    
    public int getAddNow()
    {
            return addNow;
    }
    
    public String getLast()
    {
        return last;
    }
    
    public void add(String word1, String word2)
    {
            last = word2;
            boolean w1 = false, w2 = false;
            for (int i = 0; i < addNow; i++)
                if (mVex[i].ivex.equals(word1))
                    w1 = true; 
            for (int i = 0; i < addNow; i++)
                if (mVex[i].ivex.equals(word2))
                    w2 = true;
            if (w1 == false) 
            {
                mVex[addNow] = new Node();
                mVex[addNow].ivex = word1;
                mVex[addNow].nextEdge = new Node();
                mVex[addNow].nextEdge.weight = 1;
                addNow++;
            }
            if (w2 == false) 
            {
                mVex[addNow] = new Node();
                mVex[addNow].ivex = word2;
                mVex[addNow].nextEdge = new Node();
                mVex[addNow].nextEdge.weight = 1;
                addNow++;
            }
            for (int i = 0; i < addNow; i++)
            {
                if (mVex[i].ivex.equals(word1))
                {
                    Node tmp = mVex[i];             
                    while (tmp.nextEdge != null && tmp.nextEdge.ivex != null && !tmp.nextEdge.ivex.equals(word2))
                        tmp = tmp.nextEdge;
                    if (tmp.nextEdge == null)
                    {
                        tmp.nextEdge = new Node();
                        tmp.nextEdge.ivex = word2;
                        tmp.nextEdge.weight = 1;
                    }
                    else if (tmp.nextEdge.ivex == null)
                    {
                        tmp.nextEdge.ivex = word2;
                        tmp.nextEdge.weight = 1;
                    }
                    else 
                        tmp.nextEdge.weight++;
                }
            }
    }
}
