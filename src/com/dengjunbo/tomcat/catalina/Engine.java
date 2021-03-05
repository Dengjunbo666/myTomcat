package com.dengjunbo.tomcat.catalina;

import com.dengjunbo.tomcat.util.ServerXMLUtil;

import java.util.List;

public class Engine {
    private String defaultHost;
    private List<Host> hosts;

    public Engine(){
        this.defaultHost = ServerXMLUtil.getEngineDefaultHost();
        this.hosts = ServerXMLUtil.getHosts(this);
        checkDefault();
    }

    private void checkDefault() {
        if(null==getDefaultHost())
            throw new RuntimeException("the defaultHost" + defaultHost + " does not exist!");
    }

    public Host getDefaultHost(){
        for (Host host : hosts) {
            if(host.getName().equals(defaultHost))
                return host;
        }
        return null;
    }

}
