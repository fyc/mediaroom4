package com.sunmnet.mediaroom.common.socket.server;

import com.sunmnet.mediaroom.socket.core.protocol.codec.SimpleProtocolCodecFactory;
import com.sunmnet.mediaroom.socket.core.server.AbstractSocketServer;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.stream.FileRegionWriteFilter;

import java.util.Map;

/**
 * debug用socket服务，不需要心跳，换行符分割消息，文件直接写入
 */
public class DebugSocketServer extends AbstractSocketServer {

    @Override
    protected KeepAliveFilter getKeepAliveFilter() {
        return null;
    }

    @Override
    protected ProtocolCodecFilter getProtocolCodecFilter() {
        return new ProtocolCodecFilter(new SimpleProtocolCodecFactory(new TextLineEncoder(LineDelimiter.CRLF), new TextLineDecoder()));
    }

    @Override
    protected Map<String, IoFilter> getFilters() {
        Map<String, IoFilter> map = super.getFilters();
        map.put("file", new FileRegionWriteFilter());
        return map;
    }
}
