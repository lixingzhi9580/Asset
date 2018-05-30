package com.cn.compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

public class CompressUtil {
	/**
	 * 归档 循环目录下的文件打包为tar
	 * 
	 * @param entry
	 * @throws IOException
	 * @author yutao
	 * @return
	 * @date 2017年5月27日下午1:48:23
	 */
	public static String archive(String entry) throws IOException {
		File file = new File(entry);

		TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(file.getAbsolutePath() + ".tar"));
		String base = file.getName();
		if (file.isDirectory()) {
			archiveDir(file, tos, base);
		} else {
			archiveHandle(tos, file, base);
		}

		tos.close();
		return file.getAbsolutePath() + ".tar";
	}

	/**
	 * 把tar包压缩成gz
	 * 
	 * @param path
	 * @throws IOException
	 * @author yutao
	 * @return
	 * @date 2017年5月27日下午2:08:37
	 */
	public static String compressArchive(String path) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));

		GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(
				new BufferedOutputStream(new FileOutputStream(path + ".gz")));

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			gcos.write(buffer, 0, read);
		}
		gcos.close();
		bis.close();
		return path + ".gz";
	}

	/**
	 * 解压tar
	 * 
	 * @param finalName
	 * @author yutao
	 * @throws IOException
	 * @date 2017年5月27日下午4:34:41
	 */
	public static void unCompressTar(String finalName) throws IOException {

		File file = new File(finalName);
		String parentPath = file.getParent();
		TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(file));

		TarArchiveEntry tarArchiveEntry = null;

		while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
			String name = tarArchiveEntry.getName();
			File tarFile = new File(parentPath , name);
			if (tarArchiveEntry.isDirectory()) {
				if (!tarFile.exists()) {
					tarFile.mkdirs();
				}
			} else {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));
				int read = -1;
				byte[] buffer = new byte[1024];
				while ((read = tais.read(buffer)) != -1) {
					bos.write(buffer, 0, read);
				}
				bos.close();
			}
		}
		tais.close();
		file.delete();// 删除tar文件
	}

	/**
	 * 解压gz
	 * 
	 * @param archive
	 * @author yutao
	 * @throws IOException
	 * @date 2017年5月27日下午4:03:29
	 */
	public static void unCompressArchiveGz(String archive) throws IOException {

		File file = new File(archive);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));

		String finalName = file.getParent() + File.separator + fileName;

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(finalName));

		GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = gcis.read(buffer)) != -1) {
			bos.write(buffer, 0, read);
		}
		gcis.close();
		bos.close();

		unCompressTar(finalName);
	}

	/**
	 * 方法是个递归的方法。目的就是为了把文件夹中里的所有文件都遍历出来。 其实更确切的说是把文件相对路径（相对于打包目录，我这里就是test目录）
	 * 通过递归拼接好，为真正的打包写入操作做准备。
	 * 
	 * @param file
	 * @param tos
	 * @param base
	 * @throws IOException
	 * @author yutao
	 * @date 2017年5月27日下午1:48:40
	 */
	private static void archiveDir(File file, TarArchiveOutputStream tos, String basePath) throws IOException {
		File[] listFiles = file.listFiles();
		for (File fi : listFiles) {
			if (fi.isDirectory()) {
				archiveDir(fi, tos, basePath + File.separator + fi.getName());
			} else {
				archiveHandle(tos, fi, basePath);
			}
		}
	}

	/**
	 * 具体归档处理（文件） 打包方法。参数为：1、打包输出流2、需要打包的文件3、打包文件里的路径（之前拼接好的路径）
	 * 
	 * @param tos
	 * @param fi
	 * @param base
	 * @throws IOException
	 * @author yutao
	 * @date 2017年5月27日下午1:48:56
	 */
	private static void archiveHandle(TarArchiveOutputStream tos, File fi, String basePath) throws IOException {
		TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());
		tEntry.setSize(fi.length());

		tos.putArchiveEntry(tEntry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			tos.write(buffer, 0, read);
		}
		bis.close();
		tos.closeArchiveEntry();// 这里必须写，否则会失败
	}

	public static void main(String[] args) throws IOException {

		// String entry = "F:\\lxzcs";//需要压缩的文件夹
		// String archive = archive(entry);//生成tar包
		// String path = compressArchive(archive);//生成gz包

		unCompressArchiveGz("F:\\20170912.tar.gz");// 解压
	}
}
