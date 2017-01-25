package com.minggo.pluto.common;

import com.minggo.pluto.PlutoConfig;
import com.minggo.pluto.util.FileUtils;
import com.minggo.pluto.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存处理工具类
 * 
 * @author minggo
 * @time 2014-12-2下午1:58:22
 */
public class CacheUtils {
	static CacheUtils cacheUtils;

	private CacheUtils() {

	}

	/**
	 * 初始化或者获取本地缓存
	 * 
	 * @return
	 */
	public static CacheUtils getInstance() {
		if (null == cacheUtils) {
			cacheUtils = new CacheUtils();
		}
		return cacheUtils;
	}

	/**
	 * 判断缓存是否失效
	 * 
	 * @param key
	 *            主键
	 * @param cache_time_millis
	 *            分钟
	 * @return
	 */
	public boolean isCacheDataFailure(String key, int cache_time_millis) {
		cache_time_millis = cache_time_millis * 60000; // 把分钟转换为毫秒
		boolean failure = false;
		File data = new File(PlutoConfig.SDPATH + "cache/" + "cache_" + key
				+ ".data");

		if (data.exists()
				&& (System.currentTimeMillis() - data.lastModified()) > cache_time_millis) {
			failure = true;
		} else if (!data.exists()) {
			failure = true;
		}
        LogUtils.info("reader", data.getPath() + "文件是否失效====>" + failure);
		return failure;
	}

	/**
	 * 判断缓存是否失效
	 * 
	 * @param path
	 *            主键
	 * @param cache_time_millis
	 *            分钟
	 * @return
	 */
	public boolean isCacheDataFailurePath(String path, int cache_time_millis) {
		cache_time_millis = cache_time_millis * 60000; // 把分钟转换为毫秒
		boolean failure = false;
		File data = new File(path);

		if (data.exists()
				&& (System.currentTimeMillis() - data.lastModified()) > cache_time_millis) {
			failure = true;
		} else if (!data.exists()) {
			failure = true;
		}
        LogUtils.info("reader", path + "文件是否失效====>" + failure);
		return failure;
	}
	
	/**
	 * 保存磁盘缓存
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setDiskCache(String key, String value) throws IOException {
		FileUtils.WriterTxtFile(PlutoConfig.SDPATH + "cache/", "cache_" + key
				+ ".data", value, false);
	}

	/**
	 * 获取磁盘缓存数据
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getDiskCache(String key) {
		String content = null;
		try {
			content = FileUtils.ReadTxtFile(PlutoConfig.SDPATH + "cache/"
					+ "cache_" + key + ".data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 删除磁盘缓存数据
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String removeDiskCache(String key) {
		String content = null;
		try {
			content = FileUtils.RemoveTxtFile(PlutoConfig.SDPATH + "cache/"
					+ "cache_" + key + ".data");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 获取所有缓存文件，判断依据：文件名开头cache_并且文件名结尾.data
	 *
	 * @return 返回列表，长度0则为空
	 */
	public List<File> getAllDiskCacheFile() {
		List<File> allFiles = new ArrayList<>();
		File cacheDir = new File(PlutoConfig.SDPATH + "cache/");

		if (cacheDir.exists()) {
			File[] files = cacheDir.listFiles();
			for (File file : files) {
				String fileName = file.getName();
				if (fileName.startsWith("cache_") && fileName.endsWith(".data")) {
					allFiles.add(file);
				}
			}
		}

		return allFiles;
	}

	public File getDiskCacheFile(String key) {
		return new File(PlutoConfig.SDPATH + "cache/" + "cache_" + key + ".data");
	}
}