package com.cloverstudio.generalcore.http.images;

import android.os.AsyncTask;
import android.view.View;


import com.cloverstudio.generalcore.utils.AppFileSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * gif下载器
 * 
 * @author wlei 2015-10-8
 */
public class GifLoader {

	/**
	 * 加载gif图片
	 * 
	 * @param url
	 * @param gifImageView
	 */
	public static void loadGif(String url, GifImageView gifImageView) {
		if (url == null || gifImageView == null) {
			return;
		}
		new DownloadTask(gifImageView).execute(url);
	}

	public static void loadGif(String url, GifImageView gifImageView,
							   View loadView) {
		if (url == null || gifImageView == null || loadView == null) {
			return;
		}
		new DownloadTask(gifImageView, loadView).execute(url);
	}

	static class DownloadTask extends AsyncTask<String, Integer, String> {
		GifImageView mGifImageView;
		View mLoadView;// 加载等待视图

		public DownloadTask(GifImageView gifImageView) {
			mGifImageView = gifImageView;
		}

		public DownloadTask(GifImageView gifImageView, View loadView) {
			mGifImageView = gifImageView;
			mLoadView = loadView;
		}

		@SuppressWarnings("resource")
		@Override
		protected String doInBackground(String... sUrl) {
			InputStream input = null;
			OutputStream output = null;
			HttpURLConnection connection = null;
			String pathName = null;
			try {
				URL url = new URL(sUrl[0]);

				// gif文件存放位置
				String gifSavePath = AppFileSystem.getImageSavePath();
				pathName = gifSavePath + url.hashCode() + ".gif";// 文件存储路径
				File file = new File(pathName);
				// 如果文件存在，直接返回
				if (file.exists()) {
					return pathName;
				}
				connection = (HttpURLConnection) url.openConnection();
				connection.connect();
				// expect HTTP 200 OK, so we don't mistakenly save error report
				// instead of the file
				if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return "Server returned HTTP "
							+ connection.getResponseCode() + " "
							+ connection.getResponseMessage();
				}

				// this will be useful to display download percentage
				// might be -1: server did not report the length
				int fileLength = connection.getContentLength();

				// download the file
				input = connection.getInputStream();
				output = new FileOutputStream(pathName);

				byte data[] = new byte[4096];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return pathName;
					}
					total += count;
					// publishing the progress....
					if (fileLength > 0) // only if total length is known
						publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}
			}
			catch (Exception e) {
				return e.toString();
			}
			finally {
				try {
					if (output != null)
						output.close();
					if (input != null)
						input.close();
				}
				catch (IOException ignored) {
				}

				if (connection != null)
					connection.disconnect();
			}
			return pathName;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				File gifFile = new File(result);
				try {
					GifDrawable gifFromFile = new GifDrawable(gifFile);
					mGifImageView.setImageDrawable(gifFromFile);
					if (mLoadView != null) {
						mLoadView.setVisibility(View.GONE);
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public interface MyGifLoaderHander {
		/**
		 * 加载完成
		 */
		public void loadComplete();
	}

}
