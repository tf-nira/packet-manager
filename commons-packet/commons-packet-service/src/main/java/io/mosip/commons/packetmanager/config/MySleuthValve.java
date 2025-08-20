package io.mosip.commons.packetmanager.config;

import brave.Span;
import brave.Tracer;
import io.mosip.commons.packet.util.PacketManagerLogger;
import io.mosip.commons.packetmanager.service.PacketReaderService;
import io.mosip.kernel.core.logger.config.SleuthValve;
import io.mosip.kernel.core.logger.spi.Logger;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.ServletException;
import java.io.IOException;

public class MySleuthValve extends SleuthValve {

    private static final String TRACE_ID_NAME = "X-B3-TraceId";
    private static final String SPAN_ID_NAME = "X-B3-SpanId";
    private static Logger LOGGER = PacketManagerLogger.getLogger(PacketReaderService.class);
    private final Tracer tracer;

    public MySleuthValve(Tracer tracer) {
        super(tracer);
        this.tracer = tracer;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        enrichWithSleuthHeaderWhenMissing(request);
        Valve next = getNext();
        if (null == next) {
            // no next valve
            return;
        }
        next.invoke(request, response);
    }

    private void enrichWithSleuthHeaderWhenMissing(Request request) {
        String header = request.getHeader(TRACE_ID_NAME);
        LOGGER.info(PacketManagerLogger.SESSIONID, PacketManagerLogger.REGISTRATIONID, "Existing TraceID", header);
        LOGGER.info(PacketManagerLogger.SESSIONID, PacketManagerLogger.REGISTRATIONID, "Existing  Request URI", request.getRequestURI());
        if (null == header) {
            org.apache.coyote.Request coyoteRequest = request.getCoyoteRequest();
            MimeHeaders mimeHeaders = coyoteRequest.getMimeHeaders();
            Span span = tracer.newTrace();
            LOGGER.info(PacketManagerLogger.SESSIONID, PacketManagerLogger.REGISTRATIONID, "New TraceID", span.context().traceIdString());
            LOGGER.info(PacketManagerLogger.SESSIONID, PacketManagerLogger.REGISTRATIONID, "New spanId", span.context().spanIdString());

            addHeader(mimeHeaders, TRACE_ID_NAME, span.context().traceIdString());
            addHeader(mimeHeaders, SPAN_ID_NAME, span.context().spanIdString());
        }
    }

    private static void addHeader(MimeHeaders mimeHeaders,
                                  String traceIdName,
                                  String value) {
        MessageBytes messageBytes = mimeHeaders.addValue(traceIdName);
        messageBytes.setString(value);
    }

}

