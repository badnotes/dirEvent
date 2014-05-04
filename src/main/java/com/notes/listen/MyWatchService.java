package com.notes.listen;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/***
 * 
 * @author 
 * 实时监控某个目录
 * 
 * **/
public class MyWatchService {

	
	public static void main(String[] args)throws Exception {
		
		//获取当前文件系统的WatchService监控对象
		WatchService watchService=FileSystems.getDefault().newWatchService();
		//监听的事件类型，有创建，删除，以及修改
		Paths.get("D:\\测试java监听").register(watchService, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.OVERFLOW);
	
		while(true){
		  //获取下一个文件变化事件
		  WatchKey key=watchService.take();
		  for(WatchEvent<?> event:key.pollEvents()){
			  
			  System.out.println(event.context()+"文件发生了"+event.kind()+"事件"+"此事件发生的次数: "+event.count());
		  }
		  //重设WatchKey
		  boolean valid=key.reset();
		  //监听失败，退出监听
		  if(!valid){
			  break;
		  }
		}
	}
}
