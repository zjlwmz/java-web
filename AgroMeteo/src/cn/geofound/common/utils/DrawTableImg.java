package cn.geofound.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DrawTableImg {

	/**
	 * ����ͼƬ
	 * 
	 * @param cellsValue
	 *            �Զ�ά������ʽ��� ��������ֵ
	 * @param path
	 *            �ļ�����·��
	 */
	public void myGraphicsGeneration(String cellsValue[][], String path) {
		// �����С
		int fontTitileSize = 15;
		// ���ߵ�����
		int totalrow = cellsValue.length + 1;
		// ���ߵ�����
		int totalcol = 0;
		if (cellsValue[0] != null) {
			totalcol = cellsValue[0].length;
		}
		// ͼƬ���
		int imageWidth = 1024;
		// �и�
		int rowheight = 40;
		// ͼƬ�߶�
		int imageHeight = totalrow * rowheight + 50;
		// ��ʼ�߶�
		int startHeight = 10;
		// ��ʼ���
		int startWidth = 10;
		// ��Ԫ����
		int colwidth = (int) ((imageWidth - 20) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(new Color(220, 240, 240));

		// ������
		for (int j = 0; j < totalrow; j++) {
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, startWidth + colwidth * totalcol, startHeight + (j + 1) * rowheight);
		}
		// ������
		for (int k = 0; k < totalcol + 1; k++) {
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight, startWidth + k * colwidth, startHeight + rowheight * totalrow);
		}
		// ��������
		Font font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		// д����
		String title = "��ָ����ɽ��ȡ�";
		graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		// д������

		for (int n = 0; n < cellsValue.length; n++) {
			for (int l = 0; l < cellsValue[n].length; l++) {
				if (n == 0) {
					font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
					graphics.setFont(font);
				} else if (n > 0 && l > 0) {
					font = new Font("΢���ź�", Font.PLAIN, fontTitileSize);
					graphics.setFont(font);
					graphics.setColor(Color.RED);
				} else {
					font = new Font("΢���ź�", Font.PLAIN, fontTitileSize);
					graphics.setFont(font);
					graphics.setColor(Color.BLACK);
				}

				String name = cellsValue[n][l].toString();
				int x = startWidth + colwidth * l + colwidth / 2 - String_length(name) * 3;
				int y = startHeight + rowheight * (n + 2) - 10;
				System.out.println("name:" + name + "--" + "x:" + x + "--y:" + y);
				graphics.drawString(name, x, y);
			}
		}

		// ����ͼƬ
		createImage(image, path);
	}

	public static int String_length(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * ���ͼ����ͼƬ
	 * 
	 * @param cellsValue
	 *            �Զ�ά������ʽ��� ��������ֵ
	 * @param path
	 *            �ļ�����·��
	 */
	public void PhenologyMap(String cellsValue[][], String path) {
		// �����С
		int fontTitileSize = 15;
		// ���ߵ�����
		int totalrow = cellsValue.length + 1;
		// ���ߵ�����
		int totalcol = 0;
		if (cellsValue[0] != null) {
			totalcol = cellsValue[0].length;
		}
		// ͼƬ���
		int imageWidth = 1024;
		// �и�
		int rowheight = 40;
		// ͼƬ�߶�
		int imageHeight = totalrow * rowheight + 50;
		// ��ʼ�߶�
		int startHeight = 10;
		// ��ʼ���
		int startWidth = 10;
		// ��Ԫ����
		int colwidth = (int) ((imageWidth - 20) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(new Color(220, 240, 240));

		// // ������
		// for (int j = 0; j < totalrow; j++) {
		// graphics.setColor(Color.black);
		// graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight,
		// startWidth + colwidth * totalcol, startHeight + (j + 1) * rowheight);
		// }
		// // ������
		// for (int k = 0; k < totalcol + 1; k++) {
		// graphics.setColor(Color.black);
		// graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight,
		// startWidth + k * colwidth, startHeight + rowheight * totalrow);
		// }

		// ��������
		Font font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		// д����
		String title = "�����ͼ��";
		graphics.setColor(Color.BLACK);
		graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		// д������

		for (int n = 0; n < cellsValue.length; n++) {
			for (int l = 0; l < cellsValue[n].length; l++) {
				if (n == 0) {
					/**
					 * table head ������
					 */

					// ���񱳾�ɫ
					if (l == 0) {
						Color color = new Color(245, 130, 31);
						graphics.setColor(color);
					} else {
						int rectX1 = startWidth + colwidth * l;
						int rectY1 = startHeight + rowheight * (n + 1);
						Color color = new Color(99, 100, 102);
						graphics.setColor(color);
						graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��

						Color fontColor = new Color(255, 255, 255);
						graphics.setColor(fontColor);
						font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
						graphics.setFont(font);

					}

					String name = cellsValue[n][l].toString();
					int x = startWidth + colwidth * l + colwidth / 2 - String_length(name) * 3;
					int y = startHeight + rowheight * (n + 2) - 10;
					graphics.drawString(name, x, y);

				} else if (n > 0 && l > 0) {

					if (n % 2 == 0) {
						/**
						 * ż����
						 */
						// ���񱳾�ɫ
						int rectX1 = startWidth + colwidth * l;
						int rectY1 = startHeight + rowheight * (n + 1);
						Color color = new Color(255, 245, 237);
						graphics.setColor(color);
						graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��
					} else {
						/**
						 * ������
						 */
						// ���񱳾�ɫ
						int rectX1 = startWidth + colwidth * l;
						int rectY1 = startHeight + rowheight * (n + 1);
						Color color = new Color(255, 236, 219);
						graphics.setColor(color);
						graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��
					}

					// ������Ҫ���ӵ�ͼ��
					// СͼƬ��·��
					ImageIcon imgIcon = new ImageIcon("c:/1/3.png");
					// �õ�Image����
					Image img = imgIcon.getImage();
					String name = cellsValue[n][l].toString();
					if (name.equals("1")) {
						int x = startWidth + colwidth * l + colwidth / 2 - 30 / 2;
						int y = startHeight + rowheight * (n + 2) - 35;

						graphics.drawImage(img, x, y, 30, 30, null);
					}

				} else {

					// ���񱳾�ɫ
					int rectX1 = startWidth + colwidth * l;
					int rectY1 = startHeight + rowheight * (n + 1);
					Color color = new Color(245, 130, 31);
					graphics.setColor(color);
					graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��

					/**
					 * table ����һ�� �б�
					 */
					font = new Font("΢���ź�", Font.PLAIN, fontTitileSize);
					Color fontColor = new Color(255, 255, 255);
					graphics.setColor(fontColor);
					graphics.setFont(font);

					String name = cellsValue[n][l].toString();
					int x = startWidth + colwidth * l + colwidth / 2 - String_length(name) * 3;
					int y = startHeight + rowheight * (n + 2) - 10;
					graphics.drawString(name, x, y);

				}

			}
		}

		// ������
		for (int j = 0; j < totalrow; j++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);
			graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, startWidth + colwidth * totalcol, startHeight + (j + 1) * rowheight);
		}
		// ������
		for (int k = 0; k < totalcol + 1; k++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);
			graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight, startWidth + k * colwidth, startHeight + rowheight * totalrow);
		}

		// ����ͼƬ
		createImage(image, path);
	}

	/**
	 * ���ͼ����ͼƬ--��
	 * @param cellsValue
	 * @param path
	 * @param smonth ������ʼ�·�
	 * @param emonth ���������·�
	 */
	public void PhenologyMapNew(List<List<String>> cellsValue, String path,String smonth,String emonth) {
		// �����С
		int fontTitileSize = 15;
		// ���ߵ�����
		int totalrow = cellsValue.size() + 1;
		// ���ߵ�����
		int totalcol = 0;

		if (cellsValue.get(0) != null) {
			totalcol = cellsValue.get(0).size();
		}
		// ͼƬ���
		int imageWidth = 700;
		// �и�
		int rowheight = 40;
		// ͼƬ�߶�
		int imageHeight = totalrow * rowheight + 50;
		// ��ʼ�߶�
		int startHeight = 10;
		// ��ʼ���
		int startWidth = 10;
		// ��Ԫ����
		int colwidth = (int) ((imageWidth - 20) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(new Color(220, 240, 240));

		// ��������
		Font font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		
		
		// д����
//		String title = "�����ͼ��";
//		graphics.setColor(Color.BLACK);
//		graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		
		
		// д������
		for (int n = 0; n < cellsValue.size(); n++) {

			for (int l = 0; l < cellsValue.get(n).size(); l++) {
				if (n == 0) {
					/**
					 * table head ������
					 */

					// ���񱳾�ɫ
					if (l == 0) {
						Color color = new Color(245, 130, 31);
						graphics.setColor(color);

						String name = cellsValue.get(n).get(l).toString();
						int x = startWidth + 15;
						int y = startHeight + rowheight * (n + 2) - 10;
						graphics.drawString(name, x, y);

					} else {
						if (l < cellsValue.get(n).size() - 1) {
							int rectX1 = startWidth + colwidth * (l + 1);
							int rectY1 = startHeight + rowheight * (n + 1);
							Color color = new Color(99, 100, 102);
							graphics.setColor(color);
							graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��
						}

						Color fontColor = new Color(255, 255, 255);
						graphics.setColor(fontColor);
						font = new Font("Courier"/*"΢���ź�"*/, Font.BOLD, fontTitileSize);
						graphics.setFont(font);

						String name = cellsValue.get(n).get(l).toString();
						int x = startWidth + colwidth * (l + 1) + colwidth / 2 - String_length(name) * 3;
						int y = startHeight + rowheight * (n + 2) - 10;
						graphics.drawString(name, x, y);

					}

				} else if (n > 0 && l > 0) {
					if (l < cellsValue.get(n).size() - 1) {
						if (n % 2 == 0) {
							/**
							 * ż����
							 */
							// ���񱳾�ɫ
							int rectX1 = startWidth + colwidth * (l + 1);
							int rectY1 = startHeight + rowheight * (n + 1);
							Color color = new Color(255, 245, 237);
							graphics.setColor(color);
							graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��
						} else {
							/**
							 * ������
							 */
							// ���񱳾�ɫ
							int rectX1 = startWidth + colwidth * (l + 1);
							int rectY1 = startHeight + rowheight * (n + 1);
							Color color = new Color(255, 236, 219);
							graphics.setColor(color);
							graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// ��Բ��
						}
					}

					// ������Ҫ���ӵ�ͼ��
					// СͼƬ��·��
					ImageIcon imgIcon = new ImageIcon("c:/1/3.png");
					// �õ�Image����
					Image img = imgIcon.getImage();
					String name = cellsValue.get(n).get(l).toString();
					if (name.equals("1")) {
						int x = startWidth + colwidth * (l + 1) + colwidth / 2 - 30 / 2;
						int y = startHeight + rowheight * (n + 2) - 35;

						graphics.drawImage(img, x, y, 30, 30, null);
					}

				} else {

					// ���񱳾�ɫ
					int rectX1 = startWidth + colwidth * l;
					int rectY1 = startHeight + rowheight * (n + 1);
					Color color = new Color(245, 130, 31);
					graphics.setColor(color);
					graphics.fillRoundRect(rectX1, rectY1, colwidth * 2, rowheight, 0, 0);// ��Բ��

					/**
					 * table ����һ�� �б�
					 */
					font = new Font("Courier"/*"΢���ź�"*/, Font.BOLD, fontTitileSize);
					Color fontColor = new Color(255, 255, 255);
					graphics.setColor(fontColor);
					graphics.setFont(font);

					String name = cellsValue.get(n).get(l).toString();
					int x = startWidth + colwidth * l + 5;
					int y = startHeight + rowheight * (n + 2) - 10;
					graphics.drawString(name, x, y);

				}

			}
		}

		// ������
		for (int j = 0; j < totalrow; j++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);
			int x1 = startWidth + colwidth * 0;
			int y1 = startHeight + (j + 1) * rowheight;

			int x2 = startWidth + colwidth * totalcol;
			int y2 = startHeight + (j + 1) * rowheight;

			graphics.drawLine(x1, y1, x2, y2);
		}
		// ������
		for (int k = 2; k < totalcol + 1; k++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);

			int x1 = startWidth + k * colwidth;
			int y1 = startHeight + rowheight;

			int x2 = startWidth + k * colwidth;
			int y2 = startHeight + rowheight * totalrow;
			graphics.drawLine(x1, y1, x2, y2);

		}

		
		
		
		/**
		 * ������ʾ����
		 */
		int rectX1 = startWidth + colwidth *2;
		int rectY1 = startHeight + rowheight * 2;
		Color color = new Color(238,245,12,50);
		graphics.setColor(color);
		graphics.fillRoundRect(rectX1, rectY1, colwidth*2, imageHeight-rowheight*3-10, 0, 0);// ��Բ��
		
		
		
		//
		graphics.setColor(color);
		graphics.fillRoundRect(rectX1+colwidth*2, startHeight + rowheight * 4,colwidth *9, imageHeight-rowheight*5-10, 0, 0);// ��Բ��
		
		
		
		// ����ͼƬ
		createImage(image, path);
	}

	/**
	 * ��ͼƬ���浽ָ��λ��
	 * 
	 * @param image
	 *            �����ļ���
	 * @param fileLocation
	 *            �ļ�λ��
	 */
	public void createImage(BufferedImage dstImage, String fileLocation) {
		try {
			ImageIO.write(dstImage, "png", new File(fileLocation));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DrawTableImg cg = new DrawTableImg();
		try {
			String tableData1[][] = { { "8��31��", "�ۼ��û���", "Ŀ��ֵ", "��ɽ���", "ʱ�����", "���Ȳ���" }, { "�����ͻ��ˣ�����", "469281", "1500000", "31.2%", "33.6%", "-2.4%" } };
			String[][] tableData2 = { { "8��31�գ�����", "�����û���", "�շ�����", "�ۼ��û���", "��������" }, { "�Ϸʺͳ���", "469281", "1500000", "31.2%", "33.6%" }, { "�ߺ�", "469281", "1500000", "31.2%", "33.6%" }, { "����", "469281", "1500000", "31.2%", "33.6%" }, { "����", "469281", "1500000", "31.2%", "33.6%" }, { "��ɽ", "469281", "1500000", "31.2%", "33.6%" }, { "����", "469281", "1500000", "31.2%", "33.6%" } };

			String[][] tableData3 = { { "ֲ��", "һ��", "����", "����", "����", "����", "����", "����", "����", "����", "ʮ��", "ʮһ��", "ʮ����" }, { "ˮ��", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "��", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "�絾", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "�ƶ�", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "С��", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "����", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "����", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "����", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, };

			cg.PhenologyMap(tableData3, "c:\\12.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
