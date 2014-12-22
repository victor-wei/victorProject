package com.wh.frame.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;

import android.media.ExifInterface;


/**
 * 保存照片exif信息
 * @author weihe
 *
 */
public class ExifUtil {/*
	
   private boolean saveImageWithExif(String path,String backUpPath,String fileName,Object[] obj ) {
		long currentTime = 0;
		Date date;
		try {
			final ExifInterface exif = new ExifInterface(
					path + fileName);
			final ExifInterface exif_backup = new ExifInterface(
					backUpPath + fileName);
			String makeString = "";
			makeString = "";

			currentTime = System.currentTimeMillis();
			date = new Date(currentTime);
			final String timeString = DateUtils.MMddyyyyHHmmss.get().format(date);
			BigDecimal time = new BigDecimal(timeString);
			time = time.multiply(new BigDecimal("2008")).add(
					new BigDecimal("1984"));
			
			String make;
			String model;
				make = ":"
						+ makeString
						+  ";";
				model = ":"
						+ time.toString()
						+";";
			//初始化exif信息，防止出现加密信息无法写入的问题
//			ExifUtil.emptyExif(exif);
//			ExifUtil.emptyExif(exif_backup);
			exif.setAttribute(ExifInterface.TAG_MAKE, make);
			exif.setAttribute(ExifInterface.TAG_MODEL, model);
			exif_backup.setAttribute(ExifInterface.TAG_MAKE, make);
			exif_backup.setAttribute(ExifInterface.TAG_MODEL, model);

			exif.saveAttributes();
			exif_backup.saveAttributes();

			
			 * 从exif中取出存入的信息，如果信息为空则重新拍摄
			 * 注意：不能直接使用ExifInterface.getAttribute(tagL
			 * )这个方法来判断插入是否成功，因为如果插入的信息很长的话，这个方法
			 * 返回回来的值不完整（貌似是android的一个bug）,现在使用Metadata这个类来获取加密信息（需要额外导包）
			 
			String makeGet = null;
			String modelGet = null;
			File imageFile = new File(path + fileName);
			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);
			Directory directory = metadata.getDirectory(ExifDirectory.class);
			makeGet = directory.getString(ExifDirectory.TAG_MAKE);
			modelGet = directory.getString(ExifDirectory.TAG_MODEL);

			if (!(makeGet.indexOf(";") > -1) || !(modelGet.indexOf(";") > -1)) {
				//exif信息不为空
			} else {
				//exif信息为空
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return true;
	

}
*/}
