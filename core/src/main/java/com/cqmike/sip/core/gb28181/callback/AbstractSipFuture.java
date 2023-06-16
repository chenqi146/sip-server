package com.cqmike.sip.core.gb28181.callback;

import com.cqmike.sip.core.gb28181.enums.CallBackEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.CompletableFuture;

/**
 * AbstractSipFuture
 *
 * @author cqmike
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractSipFuture<T> extends CompletableFuture<T> {

    protected CallBackEnum key;

    protected String field;
}
