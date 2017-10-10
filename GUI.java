import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.util.List;

public class GUI {
	public static void main(String[] args) {
		EventQueue.invokeLater(() ->
		{
			MainFrame mframe = new MainFrame();
			mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mframe.setVisible(true);
		}	
	);
	}
}

//window frame, no no no need to change
class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private TextManager textManager;
	private OrientedGraph orientedGraph;
	private GraphView graphView;
	private JButton textChooseButton;
	private JButton creatGraphButton;
	private JButton queryWordButton;
    private JButton creatTextButton;
	private JButton queryPathButton;
	private JButton ranWalkButton;
	private int mainFrameWidth;
	private int mainFrameHeight;
	
	
	public MainFrame()
	{
		// initialize field
		textManager = new TextManager();
		graphView = new GraphView();
		textChooseButton = new JButton("打开文本文件");
		creatGraphButton = new JButton("生成有向图");
		queryWordButton = new JButton("查询桥接词");
		creatTextButton = new JButton("生成新文本");
		queryPathButton = new JButton("查询最短路径");
		ranWalkButton = new JButton("随机游走");
		// set frame
		setTitle("主界面");
		setLocationByPlatform(true);
		mainFrameHeight = 400;
		mainFrameWidth = 240;
		setResizable(false);
		setSize(mainFrameWidth, mainFrameHeight);
		setLayout(null);
		// set buttons
		setTextChooseButton();
		setCreatGraphButton();
		setQueryWordButton();
		setqueryPathButton();
		setCreatTextButton();
		setRanWalkButton();
		// set pattern of component
		add(textChooseButton);
		textChooseButton.setBounds(15, 10, 200, 40);
		add(creatGraphButton);
		creatGraphButton.setBounds(15, 60, 200, 40);
		add(queryWordButton);
		queryWordButton.setBounds(15, 110, 200, 40);
		add(creatTextButton);
		creatTextButton.setBounds(15, 160, 200, 40);
		add(queryPathButton);
		queryPathButton.setBounds(15, 210, 200, 40);
		add(ranWalkButton);
		ranWalkButton.setBounds(15, 260, 200, 40);
		creatGraphButton.setEnabled(false);
		queryWordButton.setEnabled(false);
		creatTextButton.setEnabled(false);
		queryPathButton.setEnabled(false);
		ranWalkButton.setEnabled(false);
	}

	private void setTextChooseButton()
	{
		// bind function
		textChooseButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{ 
		    public void mouseClicked(java.awt.event.MouseEvent e) 
		    {
			    JFileChooser jfChooser = new JFileChooser(); 
			    jfChooser.setDialogTitle("选择文本"); 
			    jfChooser.setFileFilter(new FileFilter() 
			    { 
			        public boolean accept(File f) 
			        { 
				        	if (f.getName().endsWith("txt") || f.isDirectory()) // only txt or folder can display
				        		return true; 
				    	    return false; 
			        }
			        public String getDescription() { return "Unicode文本文件(*.txt)"; } 
			    }); 
			    if (JFileChooser.APPROVE_OPTION == jfChooser.showOpenDialog(null)) //user open a file
			    { 
		            String path = jfChooser.getSelectedFile().getAbsolutePath(); 
		            boolean nextStep =  textManager.manageText(path);
		            if (nextStep == false)  // file not accepted
		            {
			            	JOptionPane.showMessageDialog(null, "文本中没有单词或者非Unicode编码");
			        		creatGraphButton.setEnabled(false);
			        		queryWordButton.setEnabled(false);
			        		creatTextButton.setEnabled(false);
			        		queryPathButton.setEnabled(false);
			        		ranWalkButton.setEnabled(false);
		            }
		            else  // other function can be used
		            {
			            	JOptionPane.showMessageDialog(null, "文件输入完成");
			            	orientedGraph = new OrientedGraph(textManager.getWordNum());
			            List<String> adjacentWordLst = textManager.getWordPair();
			            for (int i = 0; i < adjacentWordLst.size() - 1; i += 2)
			    				orientedGraph.add(adjacentWordLst.get(i), adjacentWordLst.get(i + 1));
			            orientedGraph.print();
			    			orientedGraph.getTriple(adjacentWordLst);
			    			orientedGraph.generateBridge();
			        		creatGraphButton.setEnabled(true);
			        		queryWordButton.setEnabled(true);
			        		creatTextButton.setEnabled(true);
			        		queryPathButton.setEnabled(true);
			        		ranWalkButton.setEnabled(true);
		            }
		        }
		    }
		});
	}

	private void setCreatGraphButton()
	{
		creatGraphButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{ 
		    public void mouseClicked(java.awt.event.MouseEvent e) 
		    {
		    	graphView.showDirectedGraph(orientedGraph.getmVex(), orientedGraph.getAddNow());
				JOptionPane.showMessageDialog(null, "图像已保存至本程序根目录graph.png");
		    }
		});
	}
	
	private void setQueryWordButton()
	{
		queryWordButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				JFrame jfQueryWord = new JFrame("查询桥接词");
				JPanel bigPanel = new JPanel();
				bigPanel.setLayout(null);
				JPanel panel1 = new JPanel();
				panel1.setBounds(20, 20, 460, 50);
				panel1.setLayout(new FlowLayout());
				JPanel panel2 = new JPanel();
				panel2.setBounds(20, 90, 460, 150);
				panel2.setLayout(null);
				jfQueryWord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jfQueryWord.setVisible(true);
				jfQueryWord.setSize(500, 300);
				JTextField jtQueryWord1 = new JTextField("请输入词1");
				jtQueryWord1.setMinimumSize(new Dimension(30, 15));
				JTextField jtQueryWord2 = new JTextField("请输入词2");
				jtQueryWord2.setMinimumSize(new Dimension(30, 15));
				JTextField jtQueryWordResult = new JTextField();
				JButton jfQueryButton = new JButton("确定");
				jtQueryWordResult.setSize(460, 150);
				jtQueryWordResult.setEditable(false);
				
				panel1.add(jtQueryWord1);
				panel1.add(Box.createHorizontalStrut(20));
				panel1.add(jtQueryWord2);
				panel1.add(Box.createHorizontalStrut(20));
				panel1.add(jfQueryButton);
				panel2.add(jtQueryWordResult);
				bigPanel.add(panel1);
				bigPanel.add(panel2);
				jfQueryWord.add(bigPanel);
				jfQueryWord.setResizable(false);
				jfQueryButton.addMouseListener(new java.awt.event.MouseAdapter() 
				{
					public void mouseClicked(java.awt.event.MouseEvent e)
					{
						if (!jtQueryWord1.getText().isEmpty() && !jtQueryWord2.getText().isEmpty()) 
						{
							String result = orientedGraph.queryBridgeWord(jtQueryWord1.getText(), jtQueryWord2.getText());
							if (result != null)
								jtQueryWordResult.setText(result + "\t你猜对了吗？");
							else
								jtQueryWordResult.setText("tan90°");
						}
					}
				});
			}
		});
	}

	private void setCreatTextButton()
	{
		creatTextButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				JFrame jfQueryWord = new JFrame("生成新文本");
				JPanel bigPanel = new JPanel();
				bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
				JScrollPane panel2 = new JScrollPane();
				panel2.setBorder(BorderFactory.createTitledBorder("你猜对了吗？"));
				panel2.setLayout(null);
				jfQueryWord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jfQueryWord.setVisible(true);
				jfQueryWord.setSize(840, 400);
				jfQueryWord.setResizable(false);
				JTextField jtQueryWord1 = new JTextField("随便输入点什么");
				jtQueryWord1.setMinimumSize(new Dimension(300, 25));
				jtQueryWord1.setMaximumSize(new Dimension(300, 25));
				JTextArea jtQueryWordResult = new JTextArea();
				JButton jfQueryButton = new JButton("确定");
				jtQueryWordResult.setSize(800, 300);
				jtQueryWordResult.setLocation(14, 15);
				jtQueryWordResult.setEditable(false);
				jtQueryWordResult.setLineWrap(true);
				panel1.add(jtQueryWord1);
				panel1.add(jfQueryButton);
				panel2.add(jtQueryWordResult);
				bigPanel.add(panel1);
				bigPanel.add(panel2);
				jfQueryWord.add(bigPanel);
				jfQueryButton.addMouseListener(new java.awt.event.MouseAdapter() 
				{
					public void mouseClicked(java.awt.event.MouseEvent e)
					{
						if (!jtQueryWord1.getText().isEmpty())
						{
							String result = new String();
							try 
							{
								result = orientedGraph.generateNewString(jtQueryWord1.getText());
							} 
							catch (IOException e1) 
							{
								result = "Something wrong";
								e1.printStackTrace();
							}
							jtQueryWordResult.setText(result);
							System.out.println(result);
						}
					}
				});
			}
		});
	}
	
	private void setqueryPathButton()
	{
		queryPathButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				JFrame jfQueryWord = new JFrame("查询最短路径");
				jfQueryWord.setLayout(null);
				jfQueryWord.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jfQueryWord.setSize(715, 700);
				jfQueryWord.setResizable(false);
				
				JTextField jtQueryWord1 = new JTextField("请输入词1");
				jtQueryWord1.setBounds(75, 20, 80, 20);
				JTextField jtQueryWord2 = new JTextField("请输入词2");
				jtQueryWord2.setBounds(240, 20, 80, 20);
				JButton jfQueryButton = new JButton("确定");
				jfQueryButton.setBounds(405, 10, 80, 30);
				JTextArea jtQueryWordResult = new JTextArea();
				jtQueryWordResult.setBounds(15, 60, 670, 560);
				jtQueryWordResult.setLineWrap(true);
				jtQueryWordResult.setEditable(false);
				
				jfQueryWord.add(jtQueryWord1);
				jfQueryWord.add(jtQueryWord2);
				jfQueryWord.add(jtQueryWordResult);
				jfQueryWord.add(jfQueryButton);
				jfQueryWord.setVisible(true);
				jfQueryButton.addMouseListener(new java.awt.event.MouseAdapter() 
				{
					public void mouseClicked(java.awt.event.MouseEvent e)
					{
						if (!jtQueryWord1.getText().isEmpty())
						{
							String result = new String();
							if (jtQueryWord2.getText().isEmpty())
								result = orientedGraph.dijkstra(jtQueryWord1.getText());
							else
								result = orientedGraph.dijkstra(jtQueryWord1.getText(), jtQueryWord2.getText());
							jtQueryWordResult.setText(result);
							System.out.println(result);
						}
					}
				});
			}
		});
	}

	private void setRanWalkButton()
	{
		ranWalkButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{ 
		    public void mouseClicked(java.awt.event.MouseEvent e) 
		    {
				JFrame rwFrame = new JFrame("随机游走");
				rwFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				rwFrame.setSize(300, 180);
				rwFrame.setLocationByPlatform(true);
				JTextArea jta = new JTextArea();
				jta.setBounds(20, 20, 300, 180);
				jta.setEditable(false);
				jta.setLineWrap(true);
				String ret = orientedGraph.randomWalk();
				jta.setText(" " + ret);
				String savePath = System.getProperty("user.dir") + "\\RandomWalk.txt";
				FileOutputStream fos;
			    try {
			    	fos = new FileOutputStream(savePath);
				    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
				    osw.write(ret);   
					osw.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				rwFrame.add(jta);
				rwFrame.setVisible(true);
		    }
		});
	}
}


// Add a line of comments.
