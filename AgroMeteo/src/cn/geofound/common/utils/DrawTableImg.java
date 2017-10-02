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
	 * 生成图片
	 * 
	 * @param cellsValue
	 *            以二维数组形式存放 表格里面的值
	 * @param path
	 *            文件保存路径
	 */
	public void myGraphicsGeneration(String cellsValue[][], String path) {
		// 字体大小
		int fontTitileSize = 15;
		// 横线的行数
		int totalrow = cellsValue.length + 1;
		// 竖线的行数
		int totalcol = 0;
		if (cellsValue[0] != null) {
			totalcol = cellsValue[0].length;
		}
		// 图片宽度
		int imageWidth = 1024;
		// 行高
		int rowheight = 40;
		// 图片高度
		int imageHeight = totalrow * rowheight + 50;
		// 起始高度
		int startHeight = 10;
		// 起始宽度
		int startWidth = 10;
		// 单元格宽度
		int colwidth = (int) ((imageWidth - 20) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(new Color(220, 240, 240));

		// 画横线
		for (int j = 0; j < totalrow; j++) {
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, startWidth + colwidth * totalcol, startHeight + (j + 1) * rowheight);
		}
		// 画竖线
		for (int k = 0; k < totalcol + 1; k++) {
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight, startWidth + k * colwidth, startHeight + rowheight * totalrow);
		}
		// 设置字体
		Font font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		// 写标题
		String title = "【指标完成进度】";
		graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		// 写入内容

		for (int n = 0; n < cellsValue.length; n++) {
			for (int l = 0; l < cellsValue[n].length; l++) {
				if (n == 0) {
					font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
					graphics.setFont(font);
				} else if (n > 0 && l > 0) {
					font = new Font("微软雅黑", Font.PLAIN, fontTitileSize);
					graphics.setFont(font);
					graphics.setColor(Color.RED);
				} else {
					font = new Font("微软雅黑", Font.PLAIN, fontTitileSize);
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

		// 保存图片
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
	 * 物候图生成图片
	 * 
	 * @param cellsValue
	 *            以二维数组形式存放 表格里面的值
	 * @param path
	 *            文件保存路径
	 */
	public void PhenologyMap(String cellsValue[][], String path) {
		// 字体大小
		int fontTitileSize = 15;
		// 横线的行数
		int totalrow = cellsValue.length + 1;
		// 竖线的行数
		int totalcol = 0;
		if (cellsValue[0] != null) {
			totalcol = cellsValue[0].length;
		}
		// 图片宽度
		int imageWidth = 1024;
		// 行高
		int rowheight = 40;
		// 图片高度
		int imageHeight = totalrow * rowheight + 50;
		// 起始高度
		int startHeight = 10;
		// 起始宽度
		int startWidth = 10;
		// 单元格宽度
		int colwidth = (int) ((imageWidth - 20) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(new Color(220, 240, 240));

		// // 画横线
		// for (int j = 0; j < totalrow; j++) {
		// graphics.setColor(Color.black);
		// graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight,
		// startWidth + colwidth * totalcol, startHeight + (j + 1) * rowheight);
		// }
		// // 画竖线
		// for (int k = 0; k < totalcol + 1; k++) {
		// graphics.setColor(Color.black);
		// graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight,
		// startWidth + k * colwidth, startHeight + rowheight * totalrow);
		// }

		// 设置字体
		Font font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		// 写标题
		String title = "【物候图】";
		graphics.setColor(Color.BLACK);
		graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		// 写入内容

		for (int n = 0; n < cellsValue.length; n++) {
			for (int l = 0; l < cellsValue[n].length; l++) {
				if (n == 0) {
					/**
					 * table head 标题栏
					 */

					// 方格背景色
					if (l == 0) {
						Color color = new Color(245, 130, 31);
						graphics.setColor(color);
					} else {
						int rectX1 = startWidth + colwidth * l;
						int rectY1 = startHeight + rowheight * (n + 1);
						Color color = new Color(99, 100, 102);
						graphics.setColor(color);
						graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块

						Color fontColor = new Color(255, 255, 255);
						graphics.setColor(fontColor);
						font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
						graphics.setFont(font);

					}

					String name = cellsValue[n][l].toString();
					int x = startWidth + colwidth * l + colwidth / 2 - String_length(name) * 3;
					int y = startHeight + rowheight * (n + 2) - 10;
					graphics.drawString(name, x, y);

				} else if (n > 0 && l > 0) {

					if (n % 2 == 0) {
						/**
						 * 偶数行
						 */
						// 方格背景色
						int rectX1 = startWidth + colwidth * l;
						int rectY1 = startHeight + rowheight * (n + 1);
						Color color = new Color(255, 245, 237);
						graphics.setColor(color);
						graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块
					} else {
						/**
						 * 奇数行
						 */
						// 方格背景色
						int rectX1 = startWidth + colwidth * l;
						int rectY1 = startHeight + rowheight * (n + 1);
						Color color = new Color(255, 236, 219);
						graphics.setColor(color);
						graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块
					}

					// 创建你要附加的图象。
					// 小图片的路径
					ImageIcon imgIcon = new ImageIcon("c:/1/3.png");
					// 得到Image对象。
					Image img = imgIcon.getImage();
					String name = cellsValue[n][l].toString();
					if (name.equals("1")) {
						int x = startWidth + colwidth * l + colwidth / 2 - 30 / 2;
						int y = startHeight + rowheight * (n + 2) - 35;

						graphics.drawImage(img, x, y, 30, 30, null);
					}

				} else {

					// 方格背景色
					int rectX1 = startWidth + colwidth * l;
					int rectY1 = startHeight + rowheight * (n + 1);
					Color color = new Color(245, 130, 31);
					graphics.setColor(color);
					graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块

					/**
					 * table 左侧第一列 列表
					 */
					font = new Font("微软雅黑", Font.PLAIN, fontTitileSize);
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

		// 画横线
		for (int j = 0; j < totalrow; j++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);
			graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, startWidth + colwidth * totalcol, startHeight + (j + 1) * rowheight);
		}
		// 画竖线
		for (int k = 0; k < totalcol + 1; k++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);
			graphics.drawLine(startWidth + k * colwidth, startHeight + rowheight, startWidth + k * colwidth, startHeight + rowheight * totalrow);
		}

		// 保存图片
		createImage(image, path);
	}

	/**
	 * 物候图生成图片--新
	 * @param cellsValue
	 * @param path
	 * @param smonth 高亮开始月份
	 * @param emonth 高亮结束月份
	 */
	public void PhenologyMapNew(List<List<String>> cellsValue, String path,String smonth,String emonth) {
		// 字体大小
		int fontTitileSize = 15;
		// 横线的行数
		int totalrow = cellsValue.size() + 1;
		// 竖线的行数
		int totalcol = 0;

		if (cellsValue.get(0) != null) {
			totalcol = cellsValue.get(0).size();
		}
		// 图片宽度
		int imageWidth = 700;
		// 行高
		int rowheight = 40;
		// 图片高度
		int imageHeight = totalrow * rowheight + 50;
		// 起始高度
		int startHeight = 10;
		// 起始宽度
		int startWidth = 10;
		// 单元格宽度
		int colwidth = (int) ((imageWidth - 20) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(new Color(220, 240, 240));

		// 设置字体
		Font font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		
		
		// 写标题
//		String title = "【物候图】";
//		graphics.setColor(Color.BLACK);
//		graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		
		
		// 写入内容
		for (int n = 0; n < cellsValue.size(); n++) {

			for (int l = 0; l < cellsValue.get(n).size(); l++) {
				if (n == 0) {
					/**
					 * table head 标题栏
					 */

					// 方格背景色
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
							graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块
						}

						Color fontColor = new Color(255, 255, 255);
						graphics.setColor(fontColor);
						font = new Font("Courier"/*"微软雅黑"*/, Font.BOLD, fontTitileSize);
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
							 * 偶数行
							 */
							// 方格背景色
							int rectX1 = startWidth + colwidth * (l + 1);
							int rectY1 = startHeight + rowheight * (n + 1);
							Color color = new Color(255, 245, 237);
							graphics.setColor(color);
							graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块
						} else {
							/**
							 * 奇数行
							 */
							// 方格背景色
							int rectX1 = startWidth + colwidth * (l + 1);
							int rectY1 = startHeight + rowheight * (n + 1);
							Color color = new Color(255, 236, 219);
							graphics.setColor(color);
							graphics.fillRoundRect(rectX1, rectY1, colwidth, rowheight, 0, 0);// 画圆块
						}
					}

					// 创建你要附加的图象。
					// 小图片的路径
					ImageIcon imgIcon = new ImageIcon("c:/1/3.png");
					// 得到Image对象。
					Image img = imgIcon.getImage();
					String name = cellsValue.get(n).get(l).toString();
					if (name.equals("1")) {
						int x = startWidth + colwidth * (l + 1) + colwidth / 2 - 30 / 2;
						int y = startHeight + rowheight * (n + 2) - 35;

						graphics.drawImage(img, x, y, 30, 30, null);
					}

				} else {

					// 方格背景色
					int rectX1 = startWidth + colwidth * l;
					int rectY1 = startHeight + rowheight * (n + 1);
					Color color = new Color(245, 130, 31);
					graphics.setColor(color);
					graphics.fillRoundRect(rectX1, rectY1, colwidth * 2, rowheight, 0, 0);// 画圆块

					/**
					 * table 左侧第一列 列表
					 */
					font = new Font("Courier"/*"微软雅黑"*/, Font.BOLD, fontTitileSize);
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

		// 画横线
		for (int j = 0; j < totalrow; j++) {
			Color fontColor = new Color(255, 255, 255);
			graphics.setColor(fontColor);
			int x1 = startWidth + colwidth * 0;
			int y1 = startHeight + (j + 1) * rowheight;

			int x2 = startWidth + colwidth * totalcol;
			int y2 = startHeight + (j + 1) * rowheight;

			graphics.drawLine(x1, y1, x2, y2);
		}
		// 画竖线
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
		 * 高亮显示区域
		 */
		int rectX1 = startWidth + colwidth *2;
		int rectY1 = startHeight + rowheight * 2;
		Color color = new Color(238,245,12,50);
		graphics.setColor(color);
		graphics.fillRoundRect(rectX1, rectY1, colwidth*2, imageHeight-rowheight*3-10, 0, 0);// 画圆块
		
		
		
		//
		graphics.setColor(color);
		graphics.fillRoundRect(rectX1+colwidth*2, startHeight + rowheight * 4,colwidth *9, imageHeight-rowheight*5-10, 0, 0);// 画圆块
		
		
		
		// 保存图片
		createImage(image, path);
	}

	/**
	 * 将图片保存到指定位置
	 * 
	 * @param image
	 *            缓冲文件类
	 * @param fileLocation
	 *            文件位置
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
			String tableData1[][] = { { "8月31日", "累计用户数", "目标值", "完成进度", "时间进度", "进度差异" }, { "掌厅客户端（户）", "469281", "1500000", "31.2%", "33.6%", "-2.4%" } };
			String[][] tableData2 = { { "8月31日（户）", "新增用户数", "日访问量", "累计用户数", "环比上月" }, { "合肥和巢湖", "469281", "1500000", "31.2%", "33.6%" }, { "芜湖", "469281", "1500000", "31.2%", "33.6%" }, { "蚌埠", "469281", "1500000", "31.2%", "33.6%" }, { "淮南", "469281", "1500000", "31.2%", "33.6%" }, { "马鞍山", "469281", "1500000", "31.2%", "33.6%" }, { "淮北", "469281", "1500000", "31.2%", "33.6%" } };

			String[][] tableData3 = { { "植物", "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" }, { "水稻", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "晚稻", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "早稻", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "黄豆", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "小麦", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "玉米", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "西瓜", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, { "高粱", "0", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0" }, };

			cg.PhenologyMap(tableData3, "c:\\12.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
