//package nia.chapter2.oom;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
///**
// * @Author: JianLi.Gao
// * @Descripyion:
// * @Date: Create in 上午10:36 2018/7/10
// * @Modified:
// */
//@Slf4j
//@Component
//public class EntrustOrderPushTask {
//
//    private static final Logger MONITORLOG = LoggerFactory.getLogger("monitor");
//    @Autowired
//    private RealTimeEntrustManager realTimeEntrustManager;
//
//    @Autowired
//    private Helper helper;
//    /**
//     * 委托数据实时推送
//     */
//    @ElapseMonitor()
//    @Scheduled(fixedRate = 100)
//    public void push2 () {
//        try {
////            Map<Integer, Map<BigDecimal, BigDecimal>> usdtBuyEntrustMap = realTimeEntrustManager.restoreCoinListForRedis(USDK_BUY_ENTRUST_MAP);
////            Map<Integer, Map<BigDecimal, BigDecimal>> usdtSellEntrustMap = realTimeEntrustManager.restoreCoinListForRedis(USDK_SELL_ENTRUST_MAP);
////            Map<Integer, Map<BigDecimal, BigDecimal>> contractBuyEntrustMap = realTimeEntrustManager.restoreContractListForRedis(CONTRACT_BUY_ENTRUST_MAP);
////            Map<Integer, Map<BigDecimal, BigDecimal>> contractSellEntrustMap = realTimeEntrustManager.restoreContractListForRedis(CONTRACT_SELL_ENTRUST_MAP);
//
//            Map<String, EntrustDepthResponse> usdkEntrustDepthResponseMap = new HashMap<>();
//            Map<String, EntrustDepthResponse> contractEntrustDepthResponseMap = new HashMap<>();
//            if (GlobalUserUtil.channelMap.isEmpty()) {
//                return;
//            }
//            GlobalUserUtil.channelMap.forEach((key, value) -> {
//                if (key.isOpen()) {
//                    if (value == null) {
//                        return;
//                    }
//                    value.forEach((keyChild, valueChild) -> {
//                        if (keyChild == null || valueChild == null) {
//                            log.info("Entrust ==> Push Entrust order pamram error");
//                            return;
//                        }
//                        if (keyChild == ModuleEnum.RNTRUST_DEPTH.getCode()) {
//                            try {
//                                Integer id = valueChild.getId();
//                                String param = valueChild.getParam();
//                                String k = id + "_" + param;
//                                EntrustDepthResponse response = null;
//                                if (valueChild.getType() == 1) {
//                                    response = usdkEntrustDepthResponseMap.get(k);
//                                } else if (valueChild.getType() == 2) {
//                                    response = contractEntrustDepthResponseMap.get(k);
//                                }
//                                if (response == null) {
//                                    response = new EntrustDepthResponse();
//                                    response.setAsks(new ArrayList<>());
//                                    response.setBids(new ArrayList<>());
//                                    response.setId(id);
//                                }
//                                JsonRsultVO jsonRsultVO = new JsonRsultVO();
//                                jsonRsultVO.put("entrust", response);
//                                jsonRsultVO.setType(keyChild);
//                                jsonRsultVO.success();
//                                if (key.isWritable()) {
//                                    FotaChannelHandler.sendMessageToOne(helper.getSs()+helper.getSs1()+helper.getSs3(), key);
//                                } else {
//                                    log.error("write buffer overhead limit exceeded! WriteBufferHighWaterMark:{}", key.config().getWriteBufferHighWaterMark());
//                                }
//                            } catch (Exception e) {
//                                log.error("Entrust ==> Contract Entrust order push error", e);
//                            }
//                        }
//                    });
//                }
//            });
//        } catch (Exception e) {
//            log.error("e ", e);
//        }
//    }
//
//
//    /**
//     * 委托数据实时推送
//     */
//    @ElapseMonitor()
//    @Scheduled(fixedRate = 100)
//    public void push () {
//        try {
//            Map<String, EntrustDepthResponse> usdkEntrustDepthResponseMap = new HashMap<>();
//            Map<String, EntrustDepthResponse> contractEntrustDepthResponseMap = new HashMap<>();
//            if (GlobalUserUtil.channelMap.isEmpty()) {
//                return;
//            }
//            GlobalUserUtil.channelMap.forEach((key, value) -> {
//                if (key.isOpen()) {
//                    if (value == null) {
//                        return;
//                    }
//                    value.forEach((keyChild, valueChild) -> {
//                        if (keyChild == null || valueChild == null) {
//                            log.info("Entrust ==> Push Entrust order pamram error");
//                            return;
//                        }
//                        if (keyChild == ModuleEnum.RNTRUST_DEPTH.getCode()) {
//                            try {
//                                Integer id = valueChild.getId();
//                                String param = valueChild.getParam();
//                                String k = id + "_" + param;
//                                EntrustDepthResponse response = null;
//                                if (valueChild.getType() == 1) {
//                                    response = usdkEntrustDepthResponseMap.get(k);
//                                } else if (valueChild.getType() == 2) {
//                                    response = contractEntrustDepthResponseMap.get(k);
//                                }
//                                if (response == null) {
//                                    response = new EntrustDepthResponse();
//                                    response.setAsks(new ArrayList<>());
//                                    response.setBids(new ArrayList<>());
//                                    response.setId(id);
//                                }
//                                JsonRsultVO jsonRsultVO = new JsonRsultVO();
//                                jsonRsultVO.put("entrust", response);
//                                jsonRsultVO.setType(keyChild);
//                                jsonRsultVO.success();
//                                if (key.isWritable()) {
//                                    FotaChannelHandler.sendMessageToOne(helper.getSs()+helper.getSs1()+helper.getSs3(), key);
//                                } else {
//                                    log.error("write buffer overhead limit exceeded! WriteBufferHighWaterMark:{}", key.config().getWriteBufferHighWaterMark());
//                                }
//                            } catch (Exception e) {
//                                log.error("Entrust ==> Contract Entrust order push error", e);
//                            }
//                        }
//                    });
//                }
//            });
//        } catch (Exception e) {
//            log.error("e ", e);
//        }
//    }
//
//}
