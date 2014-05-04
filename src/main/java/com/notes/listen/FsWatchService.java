

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.notes.listen;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author
 */
public class FsWatchService {

    private static FsWatcher fsw;
    private static volatile ReentrantLock lock = new ReentrantLock();
    
    public FsWatchService()
    {
       
    }
    
    @Subscribe
    @AllowConcurrentEvents
    public  void proc(PathEvent event)
    {
         try {
      

            Path path = event.getEventTarget();
            String fileName = FilenameUtils.concat("D:\\temp\\", path.toString());
            if (fileName.endsWith(".aspx")) {

                String fullPath = FilenameUtils.getFullPath(fileName);
                String srcName = FilenameUtils.getBaseName(fileName);
                
            }
        } catch (Error e) {
            e.printStackTrace();
        }

    }

    public static void start(String path) {
        if (fsw == null) {
            lock.lock();
            if (fsw == null) {
                try {
                    fsw = new FsWatcher(EventInst.getInstance().getAsyncEventBus(),
                            Paths.get(path));
                    try {
                        fsw.start();
                       
                    } catch (IOException ex) {
                        Logger.getLogger(FsWatchService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}