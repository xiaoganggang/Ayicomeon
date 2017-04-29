package com.com.gang.aiyicomeon.publiclass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.com.gang.aiyicomeon.inputapp.App;

public class PcOperate {
	// 从相册中选择出来的路径取到图片，通过流写入到自己的路径
	public static boolean saveImage(Uri uri) {
		InputStream is = null;
		File tmpDir = new File(Environment.getExternalStorageDirectory()
				+ "/Wabao");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		try {
			is = App.getContext().getContentResolver().openInputStream(uri);
			File img = new File(tmpDir.getAbsolutePath() + "/huiyi.jpg");
			FileOutputStream out = new FileOutputStream(img);
			byte[] input = new byte[50];
			while (is.read(input) != -1) {
				out.write(input);
			}
			is.close();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			return false;
		} catch (IOException e) {
			Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			return false;
		}
	}

	// 找到自己新取出来保存的路径进行压缩，二次路径设置等
	@SuppressLint("NewApi")
	public static Bitmap setImage() {
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/Wabao/huiyi.jpg");
		/*
		 * Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		 * System.out.println("5555555555555555555555555" +
		 * bitmap.getByteCount());
		 */
		Bitmap yasuobitmap = getimage(file.getAbsolutePath());
		System.out.println("66666666666666666666" + yasuobitmap.getByteCount());
		// 写到这里已经进行压缩完了，这时候要把压缩完的这个bitmap重新以输出流的形式写入到一个自己的路径（二次写入路径）
		TwoCreateLujing(yasuobitmap);
		return yasuobitmap;
	}

	/**
	 * 图片按比例大小压缩方法（根据路径获取图片并压缩）,按比例大小压缩成功后进行质量压缩
	 *
	 * @return
	 */
	@SuppressLint("NewApi")
	public static  Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		System.out.println("55555555555555555555555555555555555"
				+ bitmap.getByteCount());
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 质量压缩方法
	 *
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	/**
	 *  压缩成功后第二次为其创建路径，目的是为了bmob上传文件，下载文件时节省流量
	 *
	 * @param
	 * @return
	 */
	public static void TwoCreateLujing(Bitmap bitmap) {
		// create a file to write bitmap data
		File f = new File(Environment.getExternalStorageDirectory() + "/Wabao");
		if (!f.exists()) {
			f.mkdir();
		}
		// Convert bitmap to byte array
		/**
		 * 这几句话重要的讲bitmap转成字节流，然后就可以文件输入流写进去了
		 */
		Bitmap bitmap1 = bitmap;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap1.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();

		// write the bytes in file
		File img = new File(f.getAbsolutePath() + "/huiyi1.jpg");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(img);
			fos.write(bitmapdata);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//upLoadImage() ;
	}

	/*//图片上传
	public static void upLoadImage() {
		String picPath = new File(Environment.getExternalStorageDirectory()
				+ "/Wabao").getAbsolutePath()
				+ "/huiyi1.jpg";
		final BmobFile bmobFile = new BmobFile(new File(picPath));
		bmobFile.uploadblock(App.getContext(), new UploadFileListener() {

			@Override
			public void onSuccess() {
				ToastStaticclass.tost("上传图片文件成功");
			}

			@Override
			public void onProgress(Integer value) {

			}

			@Override
			public void onFailure(int code, String msg) {
				ToastStaticclass.tost("呵呵失败了"+msg);
			}
		});

	}*/
}
