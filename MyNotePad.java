import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.net.*;
public class MyNotePad {
	private JFrame frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyNotePad window = new MyNotePad();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public MyNotePad() {
		initialize();
	}
	private void initialize() {
		String jarFilePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String[] jarFilePathtemp = jarFilePath.split("/");
		jarFilePath = "/";
		for (int i = 1; i < jarFilePathtemp.length - 1; i++) {
			jarFilePath += jarFilePathtemp[i] + "/";
		}
		// URL Decoding
		try {
			jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}

		File f = new File(jarFilePath + "新建文本文档.txt");
		int i = 1;
		while (f.exists()) {
			f = new File(jarFilePath + "新建文本文档" + String.valueOf(i) + ".txt");
			i++;
		}
		final File path = f.getParentFile();

		Toolkit tool = Toolkit.getDefaultToolkit();
		Image tubiao = tool.getImage(this.getClass().getResource("/image/jishibentubiao.jpg"));

		frame = new JFrame(f.getName() + " - 记事本");
		frame.setBounds(100, 100, 800, 800);
		frame.setIconImage(tubiao);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // 系统风格
		} catch (Throwable e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		JScrollBar gdtheng = scrollPane.getHorizontalScrollBar(); // 获取横向滚动条
		gdtheng.setBackground(Color.WHITE);
		gdtheng.setForeground(Color.LIGHT_GRAY);

		JScrollBar gdtshu = scrollPane.getVerticalScrollBar();// 获取竖向滚动条
		gdtshu.setBackground(Color.WHITE);
		gdtshu.setForeground(Color.LIGHT_GRAY);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(new Font("宋体", Font.PLAIN, 19));
		scrollPane.setViewportView(textArea);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		menuBar.setFont(new Font("宋体", Font.PLAIN, 20));
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("\u6587\u4EF6(F)");
		mnNewMenu.setForeground(Color.BLACK);
		mnNewMenu.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(mnNewMenu);
		mnNewMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u4FDD\u5B58(S)");// save
		mntmNewMenuItem.setForeground(Color.BLACK);
		mntmNewMenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String jarFilePath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				String[] jarFilePathtemp = jarFilePath.split("/");
				jarFilePath = "/";
				for (int i = 1; i < jarFilePathtemp.length - 1; i++) {
					jarFilePath += jarFilePathtemp[i] + "/";
				}
				String temp = jarFilePath + frame.getTitle();
				String[] temp2 = temp.split(" ");
				if (temp2[0].equals(jarFilePath + "无标题")) {
					JFileChooser fileChooser = new JFileChooser(); // 选择文件
					FileSystemView fsv = FileSystemView.getFileSystemView();
					fileChooser.setCurrentDirectory(path); // 设置为当前目录
					fileChooser.showOpenDialog(frame); // 显示文件选择框,以frame为容器
					File out = fileChooser.getSelectedFile();
					if (!out.exists()) {
						try {
							out.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try {
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
						String text = textArea.getText();
						String[] textout = text.split("\n");
						for (int i = 0; i < textout.length; i++) {
							bw.write(textout[i]);
							bw.newLine();
						}

						frame.setTitle(out.getName());
						bw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					File out;
					try {
						out = new File(URLDecoder.decode(temp2[0], "UTF-8"));// URL解码
						if (!out.exists()) {
							try {
								out.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
							String text = textArea.getText();
							String[] textout = text.split("\n");
							for (int i = 0; i < textout.length; i++) {
								bw.write(textout[i]);
								bw.newLine();
							}
							bw.close();
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					} catch (UnsupportedEncodingException e1) {
						
						e1.printStackTrace();
					}

				}

			}
		});

		JMenuItem mntmn = new JMenuItem("\u65B0\u5EFA(N)");// new
		mntmn.setForeground(Color.BLACK);
		mntmn.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mnNewMenu.add(mntmn);
		mntmn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setTitle("无标题 - 记事本");
				textArea.setText(null);
			}
		});

		JMenuItem mntmo = new JMenuItem("\u6253\u5F00(O)...");// open
		mntmo.setForeground(Color.BLACK);
		mntmo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mntmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(); // 选择文件
				FileSystemView fsv = FileSystemView.getFileSystemView();
				fileChooser.setCurrentDirectory(path); // 设置为当前目录
				fileChooser.showOpenDialog(frame); // 显示文件选择框,以frame为容器
				File fin = fileChooser.getSelectedFile();
				BufferedReader br;
				try {
					frame.setTitle(fin.getName() + " - 记事本");
					br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "utf-8"));
					String temp = null, textin = "";
					while ((temp = br.readLine()) != null) {
						textin += temp;
						textin += "\n";
					}
					textArea.setText(textin);
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}

			}
		});
		mnNewMenu.add(mntmo);
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntma = new JMenuItem("\u53E6\u5B58\u4E3A(A)...");// save
																	// otherpath
		mntma.setForeground(Color.BLACK);
		mntma.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mnNewMenu.add(mntma);
		mntma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(); // 选择文件
				FileSystemView fsv = FileSystemView.getFileSystemView();
				fileChooser.setCurrentDirectory(path); // 设置为当前目录
				fileChooser.showOpenDialog(frame); // 显示文件选择框,以frame为容器
				File out = fileChooser.getSelectedFile();
				if (!out.exists()) {
					try {
						out.createNewFile();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				try {
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out)));
					String text = textArea.getText();
					String[] textout = text.split("\n");
					for (int i = 0; i < textout.length; i++) {
						bw.write(textout[i]);
						bw.newLine();
					}

					frame.setTitle(out.getName());
					bw.close();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});

		JMenuItem mntmx = new JMenuItem("\u9000\u51FA(X)");// exit
		mntmx.setForeground(Color.BLACK);
		mntmx.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mntmx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] options = { "确定", "取消" };
				int response = JOptionPane.showOptionDialog(frame, "确定退出？", "记事本", JOptionPane.YES_OPTION,
						JOptionPane.DEFAULT_OPTION, null, options, options[0]);
				if (response == 0) {
					System.exit(0);
				} else if (response == 1) {
				}
			}
		});
		mnNewMenu.add(mntmx);

		JMenu mno = new JMenu("\u683C\u5F0F(O)");
		mno.setForeground(Color.BLACK);
		mno.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(mno);
		mno.setMnemonic(KeyEvent.VK_O);

		JMenu mnNewMenu_1 = new JMenu("\u5E2E\u52A9(H)");
		mnNewMenu_1.setForeground(Color.BLACK);
		mnNewMenu_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuBar.add(mnNewMenu_1);
		mnNewMenu_1.setMnemonic(KeyEvent.VK_H);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5173\u4E8E\u8BB0\u4E8B\u672C(A)");
		mntmNewMenuItem_1.setForeground(Color.BLACK);
		mntmNewMenuItem_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "软件名：记事本\n制作时间：2017年5月29日\n制作人：muyangren907", "关于“记事本”",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		mntmNewMenuItem_1.setMnemonic('A');
		mnNewMenu_1.add(mntmNewMenuItem_1);

		JMenuItem menuItem = new JMenuItem("\u8BF4\u660E(S)");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,
						"Ⅰ、该款记事本由Java编写,支持跨平台使用(实测支持Windows 10、Centos 7)\nⅡ、支持快捷键操作,界面较为美观,功能比较完善\nⅢ、由于开发周期短,难免存在Bug,有任何问题请与作者交流",
						"“记事本”说明", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuItem.setForeground(Color.BLACK);
		menuItem.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		menuItem.setMnemonic('S');
		mnNewMenu_1.add(menuItem);

		JCheckBoxMenuItem chckbxw = new JCheckBoxMenuItem("\u81EA\u52A8\u6362\u884C(W)");
		chckbxw.setSelected(true);
		chckbxw.setForeground(Color.BLACK);
		chckbxw.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		// 自动换行
		chckbxw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setLineWrap(chckbxw.isSelected());
			}
		});
		chckbxw.setMnemonic('W');
		chckbxw.setHorizontalAlignment(SwingConstants.LEFT);
		mno.add(chckbxw);

		JMenuItem mntmf = new JMenuItem("\u5B57\u4F53(F)...");
		mntmf.setForeground(Color.BLACK);
		mntmf.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		mntmf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame tanchu = new JFrame("字体选择");

				JComboBox fontList = new JComboBox();
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				String[] fonts = ge.getAvailableFontFamilyNames();
				DefaultComboBoxModel fontModel = new DefaultComboBoxModel(fonts);
				fontList.setModel(fontModel);
				fontList.setVisible(true);

				tanchu.setBounds(frame.getBounds().x, frame.getBounds().y, 400, 300);
				tanchu.getContentPane().setLayout(null);
				tanchu.getContentPane().add(fontList);
				fontList.setBounds(20, 50, 220, 25);

				JLabel label = new JLabel("\u5B57\u4F53");
				label.setBounds(20, 25, 50, 25);
				tanchu.getContentPane().add(label);

				JLabel label_1 = new JLabel("\u98CE\u683C");
				label_1.setBounds(20, 75, 72, 25);
				tanchu.getContentPane().add(label_1);

				JLabel label_2 = new JLabel("\u5B57\u53F7");
				label_2.setBounds(20, 125, 72, 25);
				tanchu.getContentPane().add(label_2);

				JComboBox comboBox = new JComboBox();
				comboBox.setModel(new DefaultComboBoxModel(
						new String[] { "\u5E38\u89C4", "\u503E\u659C", "\u7C97\u4F53", "\u7C97\u504F\u659C\u4F53" }));
				comboBox.setBounds(20, 100, 80, 25);
				tanchu.getContentPane().add(comboBox);

				Object[] FontSize = new Object[71];
				for (int i = 0; i < 71; i++) {
					FontSize[i] = i + 1;
				}
				JComboBox comboBox_1 = new JComboBox();
				comboBox_1.setBounds(20, 150, 60, 25);
				comboBox_1.setModel(new DefaultComboBoxModel(FontSize));
				tanchu.getContentPane().add(comboBox_1);
				comboBox_1.setSelectedIndex(17);

				JButton btnNewButton = new JButton("\u786E\u5B9A");
				btnNewButton.setBounds(100, 175, 75, 25);
				tanchu.getContentPane().add(btnNewButton);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String Fontname = fontList.getSelectedItem().toString();
						int FontStyle = comboBox.getSelectedIndex();
						int FontSize = comboBox_1.getSelectedIndex() + 1;
						textArea.setFont(new Font(Fontname, FontStyle, FontSize));
						tanchu.dispose();
					}
				});
				JButton btnNewButton_1 = new JButton("\u6062\u590D\u9ED8\u8BA4\u503C");
				btnNewButton_1.setBounds(190, 175, 113, 25);
				tanchu.getContentPane().add(btnNewButton_1);
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.setFont(new Font("宋体", 0, 19));
						tanchu.dispose();
					}
				});

				tanchu.setVisible(true);
				tanchu.setDefaultCloseOperation(1);
			}
		});
		mno.add(mntmf);

	}
}