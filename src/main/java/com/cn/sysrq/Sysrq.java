package com.cn.sysrq;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
public class Sysrq {
	/**
	 * 截图
	 * @param fileName
	 * @param folder
	 * @throws Exception
	 */
	public static void captureScreen(String fileName, String folder) throws Exception {
		  Dimension screenSize = new Dimension(100,200);//Toolkit.getDefaultToolkit().getScreenSize();
		  Rectangle screenRectangle = new Rectangle(screenSize);
		  Robot robot = new Robot();
		  BufferedImage image = robot.createScreenCapture(screenRectangle);
		  //保存路径
		  File screenFile = new File(fileName);
		  if (!screenFile.exists()) {
		   screenFile.mkdir();
		  }
		  File f = new File(screenFile, folder);
		  ImageIO.write(image, "png", f);
		  //自动打开
		  if (Desktop.isDesktopSupported()
		     && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
		     Desktop.getDesktop().open(f);
	 }
	 public static void main(String[] args) {
		  try {
		   captureScreen("d:\\你好","11.png");
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	 }
}
