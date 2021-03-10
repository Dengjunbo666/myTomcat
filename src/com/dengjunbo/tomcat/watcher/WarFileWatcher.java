package com.dengjunbo.tomcat.watcher;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.WatchUtil;
import cn.hutool.core.io.watch.Watcher;
import com.dengjunbo.tomcat.catalina.Host;
import com.dengjunbo.tomcat.util.Constant;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class WarFileWatcher {
    private WatchMonitor monitor;
    public WarFileWatcher(Host host) {
        this.monitor = WatchUtil.createAll(Constant.webappsFolder, 1, new Watcher() {
            private void dealWith(WatchEvent<?> event, Path currentPath) {
                synchronized (WarFileWatcher.class) {
                    String fileName = event.context().toString();
                    if(fileName.toLowerCase().endsWith(".war")  && ENTRY_CREATE.equals(event.kind())) {
                        File warFile = FileUtil.file(Constant.webappsFolder, fileName);
                        host.loadWar(warFile);
                    }
                }
            }
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                dealWith(event, currentPath);
            }
 
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                dealWith(event, currentPath);
 
            }
            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                dealWith(event, currentPath);
            }
            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                dealWith(event, currentPath);
            }
 
        });
    }
 
    public void start() {
        monitor.start();
    }
 
    public void stop() {
        monitor.interrupt();
    }
 
}