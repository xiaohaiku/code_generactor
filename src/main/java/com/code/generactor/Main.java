package com.code.generactor;


import com.code.generactor.upload.UploadFile;
import com.code.generactor.util.ConfigUtil;
import com.code.generactor.util.ThreadLocalContextHolder;

import java.util.Scanner;

/**
 * @author 13560
 */
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要执行的配置文件名称(比如config):");
		String name = sc.nextLine();
		ThreadLocalContextHolder.initScene(name);
		long startTime = System.currentTimeMillis();
		ConfigUtil.start(name);
		UploadFile.upload();
		System.out.printf("用时%sss\n",System.currentTimeMillis()-startTime);
	}
}
