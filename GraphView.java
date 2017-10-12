import java.io.*;


public class GraphView {

	private String rootPath;  // the root path of this program
	private String dotPath; // the path of dot script
	private String commandStr;  // the command
	public static void main(String[] args) throws IOException {

	}
	
	public GraphView()
	{
		rootPath = System.getProperty("user.dir");
		dotPath = rootPath + "\\graph.dot";
		commandStr = "\"" + rootPath + "\\Graphviz2.38\\bin\\dot.exe\" -Tpng "
				+ dotPath + " -o " + rootPath + "\\graph.png";
	} 
	
	public void showDirectedGraph(OrientedGraph.Node[] mVex, int addNow)
	{

		try {
			createDot(mVex, addNow);
			Runtime.getRuntime().exec(commandStr).waitFor();
			Runtime.getRuntime().exec("rundll32 c:\\Windows\\System32\\shimgvw.dll,ImageView_Fullscreen "+ rootPath + "\\graph.png");  // open graph
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// according to graph, write a .dot and save
	private void createDot(OrientedGraph.Node[] mVex, int addNow) throws IOException
	{
		String content = "\n";
		int i;
		OrientedGraph.Node tmp;
		// write script
		content += "digraph myGraph {\n";
		// node
		for (i = 0; i < addNow; i++)
		{
			String name = mVex[i].ivex;
			if (name.equals("node")) name = "node0";
			content += name + " [label=\"" + name + "\"]\n";
		}
		// edge
		for (i = 0; i < addNow; i++)
		{
			tmp = mVex[i];
			while(tmp.nextEdge != null && tmp.nextEdge.ivex != null)
			{
				content += mVex[i].ivex + "->" + tmp.nextEdge.ivex + " [label = \"" + Integer.toString(tmp.nextEdge.weight) + "\"]\n";
				tmp = tmp.nextEdge;
			}
		}
		content += "};";
		// script finished
		FileOutputStream fos;
		fos = new FileOutputStream(dotPath);
	    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
	    osw.write(content);   
	    osw.flush();
	}
}
