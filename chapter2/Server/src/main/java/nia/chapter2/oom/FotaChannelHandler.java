package nia.chapter2.oom;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

/**
 * create on 24/08/2018
 *
 * @author JASON.TAO
 */
//@Service
public class FotaChannelHandler extends SimpleChannelInboundHandler<Object> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger("webSocket");
//
//    private static final Logger MONITORLOG = LoggerFactory.getLogger("monitor");
//
    private static final String URI = "websocket";
//
//    private static final Set<Integer> moduleCodeSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
//            ModuleEnum.RNTRUST_DEPTH.getCode(),
//            ModuleEnum.MATCH_LIST.getCode(),
//            ModuleEnum.HORSE_LAMP.getCode(),
//            ModuleEnum.SYMBOL_INFO.getCode(),
//            ModuleEnum.ASSET.getCode(),
//            ModuleEnum.ORDER.getCode())
//    ));
//
    private WebSocketServerHandshaker handshaker;
//
//    private KLineManager kLineManager = null;
//    private NotifyInfoManager notifyInfoManager = null;
//    private WsManager wsManager = null;
//    private WsManagerApp wsManagerApp = null;
//
//    public KLineManager getkLineManager() {
//        if (kLineManager == null) {
//            kLineManager = BeanUtil.getBean(KLineManager.class);
//        }
//        return kLineManager;
//    }
//
//    public NotifyInfoManager getNotifyInfoManager() {
//        if (notifyInfoManager == null) {
//            notifyInfoManager = BeanUtil.getBean(NotifyInfoManager.class);
//        }
//        return notifyInfoManager;
//    }
//
//    public WsManager getWsManager() {
//        if (wsManager == null) {
//            wsManager = BeanUtil.getBean(WsManager.class);
//        }
//        return wsManager;
//    }
//
//    public WsManagerApp getWsManagerApp() {
//        if (wsManagerApp == null) {
//            wsManagerApp = BeanUtil.getBean(WsManagerApp.class);
//        }
//        return wsManagerApp;
//    }
//
//    public static void sendMessageToAll(String msg) {
//        for (Channel channel : GlobalUserUtil.channels) {
//            channel.writeAndFlush(new TextWebSocketFrame(msg));
//        }
//    }

    public static void sendMessageToOne(String msg, Channel channel) {
        if (channel.isWritable()) {
            channel.writeAndFlush(new TextWebSocketFrame(msg));
        }
    }

    /**
     * 连接上服务器
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        int i = ctx.channel().config().getWriteBufferHighWaterMark();
        System.out.println(i);
        System.out.println("ws>>【handlerAdded】====>" + ctx.channel().id());
    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ws>>【handlerRemoved】====>" + ctx.channel().id());
//
//        GlobalUserUtil.channelMap.remove(ctx.channel());
//        GlobalUserUtil.removeUser(ctx.channel());
    }

    /**
     * 连接异常   需要关闭相关资源
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("ws>>【系统异常】: ");
    }

    /**
     * 活跃的通道  也可以当作用户连接上客户端进行使用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ws>>【channelActive】=====>" + ctx.channel());
    }

    /**
     * 不活跃的通道  就说明用户失去连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ws>>【channelInactive】=====>" + ctx.channel());
    }

    /**
     * 这里只要完成 flush
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

//    /**
//     * 这里是保持服务器与客户端长连接  进行心跳检测 避免连接断开
//     *
//     * @param ctx
//     * @param evt
//     * @throws Exception
//     */
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent stateEvent = (IdleStateEvent) evt;
//            PingWebSocketFrame ping = new PingWebSocketFrame();
//            switch (stateEvent.state()) {
//                //读空闲（服务器端）
//                case READER_IDLE:
////                    System.out.println("【"+ctx.channel().remoteAddress()+"】读空闲（服务器端）");
//                    ctx.writeAndFlush(ping);
//                    break;
//                //写空闲（客户端）
//                case WRITER_IDLE:
////                    System.out.println("【"+ctx.channel().remoteAddress()+"】写空闲（客户端）");
//                    ctx.writeAndFlush(ping);
//                    break;
//                case ALL_IDLE:
////                    System.out.println("【"+ctx.channel().remoteAddress()+"】读写空闲");
//                    ctx.writeAndFlush(ping);
//                    break;
//                default:
////                    System.out.println("【"+ctx.channel().remoteAddress()+"】IdleStateEvent Unknown");
//                    break;
//            }
//        }
//    }

    /**
     * 收发消息处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            doHandlerHttpRequest(ctx, (HttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            doHandlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }
//
    /**
     * websocket消息处理
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        FotaChannelHandler.sendMessageToOne("66666", ctx.channel());

////        System.out.println("webSocket doHandlerWebSocketFrame");
//        //判断msg 是哪一种类型  分别做出不同的反应
//        if (msg instanceof CloseWebSocketFrame) {
//            System.out.println("ws>>【关闭】");
//            ReferenceCountUtil.retain(msg);
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
//            return;
//        }
//        if (msg instanceof PingWebSocketFrame) {
//            System.out.println("【ping】");
//            PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
//            ctx.channel().writeAndFlush(pong);
//            return;
//        }
//        if (msg instanceof PongWebSocketFrame) {
////            System.out.println("【pong】");
////            PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
////            ctx.channel().writeAndFlush(ping);
//            return;
//        }
//        if (!(msg instanceof TextWebSocketFrame)) {
//            System.out.println("ws>>【不支持二进制】");
//            throw new UnsupportedOperationException("不支持二进制");
//        }
//
//        ChannelModule channelModule = null;
//        String message = ((TextWebSocketFrame) msg).text();
//        try {
//            Channel channel = ctx.channel();
//            System.out.println("ws>> receive message:{}, {}", message, channel.remoteAddress());
//            channelModule = JSON.parseObject(message, ChannelModule.class);
//        } catch (Exception e) {
//            if ("ping".equalsIgnoreCase(message)) {
//                FotaChannelHandler.sendMessageToOne(WsUtils.pong(), ctx.channel());
//                return;
//            } else {
//                System.out.println("ws>> doHandlerWebSocketFrame parseObject exception, msg:{}", ((TextWebSocketFrame) msg).text());
//            }
//        }
//        if (channelModule == null) {
//            System.out.println("ws>> doHandlerWebSocketFrame channelModule is null, channel:{}", ctx.channel());
//            return;
//        }
//
//        Integer reqType = channelModule.getReqType();
//        if (reqType == null) {
//            System.out.println("ws>> doHandlerWebSocketFrame reqType is null, channel:{}", ctx.channel());
//            return;
//        }
//
//        //app reqType greater than 1000
//        if (reqType >= 1000) {
//            Integer handleType = channelModule.getHandleType();
//            Channel channel = ctx.channel();
//            synchronized (channel) {
//                Map<Integer, ChannelModule> moduleMap = GlobalUserUtil.channelMap.get(channel);
//                if (CollectionUtils.isEmpty(moduleMap)) {
//                    moduleMap = new ConcurrentHashMap<>(16);
//                }
//                if (WsHandleTypeEnum.Query.getCode().equals(handleType)) {
//                    moduleMap.put(reqType, channelModule);
//                    GlobalUserUtil.channelMap.put(channel, moduleMap);
//                    this.getWsManagerApp().handleData(channelModule, ctx, handleType);
//                    // 登陆
//                    if (((Integer) ModuleEnum.OVERALL_USER_LOGIN.getCode()).equals(reqType)) {
//                        GlobalUserUtil.loginApp(channel, channelModule);
//                    }
//                    GlobalUserUtil.saveUserInfoApp(channel, channelModule);
//                } else if (WsHandleTypeEnum.Subscribe.getCode().equals(handleType)) {
//                    moduleMap.put(reqType, channelModule);
//                    GlobalUserUtil.channelMap.put(channel, moduleMap);
//                    // 登陆
//                    if (((Integer) ModuleEnum.OVERALL_USER_LOGIN.getCode()).equals(reqType)) {
//                        GlobalUserUtil.loginApp(channel, channelModule);
//                    }
//                    GlobalUserUtil.saveUserInfoApp(channel, channelModule);
//                } else if (WsHandleTypeEnum.UnSubscribe.getCode().equals(handleType)) {
//                    if (((Integer) ModuleEnum.OVERALL_USER_LOGIN.getCode()).equals(reqType)) {
//                        GlobalUserUtil.logOutApp(channel, reqType);
//                    }
//                    moduleMap.remove(reqType);
//                    return;
//                } else if (WsHandleTypeEnum.OnlyQuery.getCode().equals(handleType)) { // 只查询不订阅
//                    this.getWsManagerApp().handleData(channelModule, ctx, handleType);
//                    return;
//                }
//            }
//            // 不能 return; 因为 最后需要channelModuleMap.put(channelModule.getReqType(), channelModule);
//        } else {
//            // 如果不是1000，判断是否有token，如果有的话则直接废弃
//            // todo 如果以后pc也使用token，则这段逻辑还有问题
//            String token = channelModule.getToken();
//            if (token!=null) {
//                return;
//            }
//        }
//
//        //获取通知中心列表
//        if (ModuleEnum.NOTIFY_INFO.getCode() == reqType && WsHandleTypeEnum.Query.getCode().equals(channelModule.getHandleType())) {
//            FotaChannelHandler.sendMessageToOne(this.getNotifyInfoManager().listNotifyMsg(channelModule), ctx.channel());
//            return;
//        }
//
//        //需要登陆的reqType先判断是否登陆
//        if (ModuleEnum.ORDER.getCode() == reqType || ModuleEnum.ASSET.getCode() == reqType) {
//            if (StringUtils.isBlank(channelModule.getSessionId())) {
//                MONITORLOG.error("checkLoginBySessionId>>sessionId is null, id:{}, channelModule:{}", ctx.channel().id(), JSON.toJSONString(channelModule));
//            } else {
//                Long userId = WsUtils.getUserId(channelModule.getSessionId());
//                if (userId == null) {
//                    MONITORLOG.error("checkLoginBySessionId>>WsUtils.getUserId null, id:{}, sessionId:{}, channelModule:{}", ctx.channel().id(), channelModule.getSessionId(), JSON.toJSONString(channelModule));
//                } else {
//                    if (!GlobalUserUtil.checkLoginBySessionId(ctx.channel(), channelModule.getSessionId(), userId)) {
//                        GlobalUserUtil.listenLogin(ctx.channel(), channelModule.getSessionId());
//                    }
//                }
//            }
//        }
//
//        //首次查询数据
//        if (moduleCodeSet.contains(reqType)) {
//            try {
//                Optional.ofNullable(this.getWsManager().queryFotaData(channelModule, ctx.channel()))
//                        .ifPresent(result -> sendMessageToOne(result, ctx.channel()));
//            } catch (Exception e) {
//                System.out.println("ws>> doHandlerWebSocketFrame first query fail,channelModule:{}", channelModule);
//            }
//        }
//
//        /**
//         * 删除 Map<Integer, ChannelModule>
//         */
//        if (((Integer) ModuleEnum.REMOVE.getCode()).equals(reqType)) {
//            Map<Integer, ChannelModule> channelMap = GlobalUserUtil.channelMap.get(ctx.channel());
//            if (null != channelMap && channelModule.getType() > 0) {
//                channelMap.remove(channelModule.getType());
//                MONITORLOG.info("ModuleEnum.REMOVE>>type:{}, id:{}, b:{}", channelModule.getType(), ctx.channel().id(), channelMap.containsKey(channelModule.getType()));
//            } else {
//                System.out.println("ws>> doHandlerWebSocketFrame remove error, channel:{}, channelMap:{}, channelModule:{}", ctx.channel(), channelMap, channelModule);
//                MONITORLOG.error("ModuleEnum.REMOVE>>error>>type:{}, id:{}, b:{}", channelModule.getType(), ctx.channel().id(), channelMap.containsKey(channelModule.getType()));
//            }
//            return;
//        }
//
//        /**
//         * 登陆
//         */
//        if (((Integer) ModuleEnum.LOGIN.getCode()).equals(reqType)) {
//            GlobalUserUtil.login(ctx.channel(), channelModule.getSessionId());
//            Long userId = WsUtils.getUserId(channelModule.getSessionId());
//            if (userId != null) {
//                this.getNotifyInfoManager().pushLoginNotifyInfo(userId);
//            }
//            return;
//        }
//
//        /**
//         * 监听登陆
//         */
//        if (((Integer) ModuleEnum.LISTEN_LOGIN.getCode()).equals(reqType)) {
//            GlobalUserUtil.listenLogin(ctx.channel(), channelModule.getSessionId());
//            Long userId = WsUtils.getUserId(channelModule.getSessionId());
//            if (userId != null) {
//                this.getNotifyInfoManager().pushLoginNotifyInfo(userId);
//            }
//            return;
//        }
//
//        /**
//         * 登出
//         */
//        if (((Integer) ModuleEnum.LOGIN_OUT.getCode()).equals(reqType)) {
//            GlobalUserUtil.loginOut(channelModule.getSessionId());
//            return;
//        }
//
//        /**
//         * K线ws直接响应首次请求 & 后续获取历史K线
//         */
//        if (((Integer) ModuleEnum.KLINE.getCode()).equals(reqType) && !KLinePushModeEnum.SUB.getCode().equals(channelModule.getType())) {
//            System.out.println("kline channelModule:{}", JSON.toJSONString(channelModule));
//            if (KLinePushModeEnum.REQ.getCode().equals(channelModule.getType())) {
//                //移除切换前的K线推送
//                synchronized (ctx.channel()) {
//                    Map<Integer, ChannelModule> channelMap = GlobalUserUtil.channelMap.get(ctx.channel());
//                    if (null != channelMap) {
//                        channelMap.remove(reqType);
////                    GlobalUserUtil.channelMap.put(ctx.channel(), channelMap);
//                    }
//                }
//            }
//
//            try {
//                FotaChannelHandler.sendMessageToOne(this.getkLineManager().getWsKlineData(channelModule), ctx.channel());
//            } catch (Exception e) {
//                System.out.println("ws>> pushKlineReqMessage message error", e);
//                FotaChannelHandler.sendMessageToOne(ChannelResponse.getRes(channelModule, ChannelResponse.ResCodeEnum.KLINE_ERROR_GET), ctx.channel());
//                return;
//            }
//            //返回请求数据
//            FotaChannelHandler.sendMessageToOne(ChannelResponse.getRes(channelModule, ChannelResponse.ResCodeEnum.SUCCESS), ctx.channel());
//            return;
//        }
//
////        /**
////         * 历史K线ws推送
////         */
////        if (((Integer) ModuleEnum.HISTORY_KLINE.getCode()).equals(reqType)) {
////            FotaChannelHandler.sendMessageToOne(kLineManager.getWsKlineData(channelModule), ctx.channel());
////            return;
////        }
////
////        /**
////         * 保证金提醒和其他提醒登陆就连接  需要带userId
////         */
////        // TODO: 2018/9/1
////        if (((Integer) ModuleEnum.MARGIN_NOTICE.getCode()).equals(reqType) && channelModule.getUserId() != null) {
////            notifyInfoManager.pushLoginNotifyInfo(channelModule.getUserId());
////        }
//
//        Map<Integer, ChannelModule> channelModuleMap = GlobalUserUtil.channelMap.get(ctx.channel());
//        if (channelModuleMap != null) {
//            channelModuleMap.put(channelModule.getReqType(), channelModule);
//            MONITORLOG.info("ModuleEnum.ADD>>type:{}, id:{}, channelModuleMap:{}", channelModule.getReqType(), ctx.channel().id(), JSON.toJSONString(channelModuleMap));
//        } else {
//            System.out.println("ws>> doHandlerWebSocketFrame channelModuleMap is null, channel:{}", ctx.channel());
//            MONITORLOG.error("ModuleEnum.ADD>>channelModuleMap null,id:{}, ip:{}", ctx.channel().id(), ctx.channel().remoteAddress());
//
//        }
    }

    /**
     * wetsocket第一次连接握手
     *
     * @param ctx
     * @param msg
     */
    private void doHandlerHttpRequest(ChannelHandlerContext ctx, HttpRequest msg) {
//        System.out.println("webSocket doHandlerHttpRequest");
        // http 解码失败
        if (!msg.getDecoderResult().isSuccess() || (!"websocket".equalsIgnoreCase(msg.headers().get("Upgrade")))) {
            System.out.println("ws>> webSocket doHandlerHttpRequest failed");
            sendHttpResponse(ctx, (FullHttpRequest) msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }

        //可以通过url获取其他参数
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + msg.headers().get("Host") + "/" + URI + "", null, false
        );
        handshaker = factory.newHandshaker(msg);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }
        //进行连接
        handshaker.handshake(ctx.channel(), (FullHttpRequest) msg);
        //可以做其他处理
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
            System.out.println("ws>> asdfasd close ");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("ws>> webSocket channelRead0: {}", JSON.toJSONString(msg));
        if (msg instanceof HttpRequest) {
            doHandlerHttpRequest(ctx, (HttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            doHandlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }
}
