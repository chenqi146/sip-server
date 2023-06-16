package com.cqmike.sip.core.gb28181.event.response;

import com.cqmike.sip.common.enums.IEnum;
import com.cqmike.sip.common.exceptions.SipParseException;
import com.cqmike.sip.core.gb28181.callback.AbstractSipFuture;
import com.cqmike.sip.core.gb28181.cmd.SipCommander;
import com.cqmike.sip.core.gb28181.config.SipConfig;
import com.cqmike.sip.core.gb28181.ctx.SipCallbackContext;
import com.cqmike.sip.core.gb28181.enums.SipMethod;
import com.cqmike.sip.core.gb28181.event.SipEventFactory;
import com.cqmike.sip.core.gb28181.event.base.AbstractResponseEvent;
import com.cqmike.sip.core.gb28181.transaction.ServerTransactionFactory;
import com.cqmike.sip.core.gb28181.transaction.SipProtocolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.annotation.Resource;
import javax.sip.ResponseEvent;
import javax.sip.header.CSeqHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.message.Response;
import java.util.Objects;
import java.util.Optional;

/**
 * sip响应处理接口
 *
 * @author cqmike
 **/
public abstract class AbstractSipResponseProcessor<T extends AbstractResponseEvent> implements ISipResponseProcessor {
    protected static final Logger log = LoggerFactory.getLogger(AbstractSipResponseProcessor.class);

    @Resource
    protected SipConfig sipConfig;

    @Resource
    protected ServerTransactionFactory serverTransactionFactory;

    @Resource
    protected SipProtocolFactory sipProtocolFactory;

    @Resource
    protected HeaderFactory headerFactory;

    @Resource
    protected SipCommander sipCommander;

    @Resource
    protected SipCallbackContext callbackContext;


    @Override
    public void processEvent(ResponseEvent event) {
        try {
            T parse = parse(event);
            if (Objects.isNull(parse)) {
                return;
            }
            process(parse);
        } catch (Exception e) {
            // TODO: 2023/6/15 处理异常
            throw new RuntimeException(e);
        }
    }

    /**
     * 响应web消息的回调
     *
     * @author cqmike
     * @since 1.0.0
     * @return
     */
    protected Optional<AbstractSipFuture> sipFutureOp() {
        return callbackOp().flatMap(callBackEnum -> callbackFieldOp().flatMap(field -> callbackContext.get(callBackEnum, field)));
    }

    /**
     * 解析xml
     *
     * @param responseEvent
     * @return
     */
    protected T parse(ResponseEvent responseEvent) throws Exception {
        Response response = responseEvent.getResponse();
        log.info("收到sip响应消息: {}", response.toString());
        int status = response.getStatusCode();
        if (status >= HttpStatus.OK.value() && status <= HttpStatus.MULTIPLE_CHOICES.value()) {
            CSeqHeader cseqHeader = (CSeqHeader) response.getHeader(CSeqHeader.NAME);
            String method = cseqHeader.getMethod();
            // 从sip端口接收到sip客户端响应信息, 然后根据类型去获取继承抽象子类事件发布
            Optional<SipMethod> form = IEnum.form(SipMethod.class, method);
            SipMethod sipMethod = form.orElseThrow(() -> new SipParseException("此方法暂不支持: " + method));
            return (T) SipEventFactory.getResponseEvent(sipMethod, responseEvent);

        }
        return null;
    }

    /**
     * 处理解析后的事件数据
     *
     * @param event
     */
    public abstract void process(T event);
}
