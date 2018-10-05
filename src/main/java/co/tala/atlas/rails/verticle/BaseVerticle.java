package co.tala.atlas.rails.verticle;

import com.google.gson.Gson;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.function.Function;

public abstract class BaseVerticle extends AbstractVerticle {
    abstract List<Pair<String, Function<String, Object>>> getChannelHandlerMap();

    @Autowired
    protected Gson gson;

    @Override
    public void start() throws Exception {
        super.start();

        List<Pair<String, Function<String, Object>>> channelHandlerMap = getChannelHandlerMap();

        if (channelHandlerMap != null) {
            channelHandlerMap.forEach(h -> vertx.eventBus()
                .<String>consumer(h.getFirst())
                .handler(makeHandler(h.getSecond())));
        }
    }

    protected Handler<Message<String>> makeHandler(Function<String, Object> responseFn) {
        return (Message<String> msg) -> vertx.<String>executeBlocking(future -> {
                Object responseMessage = responseFn.apply(msg.body());
                future.complete(gson.toJson(responseMessage));
            },
            result -> replyBack(msg, result));
    }

    private <X> void replyBack(Message<X> msg, AsyncResult<String> result) {
        if (result.succeeded()) msg.reply(result.result());
        else msg.reply(result.cause().toString());
    }
}
