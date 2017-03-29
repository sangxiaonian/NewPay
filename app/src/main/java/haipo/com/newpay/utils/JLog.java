//package haipo.com.newpay.utils;
//
//import com.orhanobut.logger.Logger;
//
//public class Logger {
//    /**
//     * tag
//     */
//    public static String tag = "PING";
//    /**
//     * 是否屏蔽log日志，当为false时，系统不再打印
//     */
//    private static boolean needLog = true;
//
//
//    public static void i(String content) {
//        if (needLog) {
//
//             Logger.i(content);
//        }
//    }
//
//    public static void i(String tag, String content) {
//        if (needLog) {
//
//            Logger.t(tag).i(content);
//        }
//    }
//
//    public static void d(String content) {
//        if (needLog) {
//
//            Logger.d(content);
//        }
//    }
//
//    public static void d(String tag, String content) {
//        if (needLog) {
//
//            Logger.t(tag).d(content);
//        }
//    }
//
//    public static void e(String content) {
//        if (needLog) {
//            Logger.e(content);
//        }
//    }
//
//    public static void e(String tag, String content) {
//        if (needLog) {
//            Logger.t(tag).e(content);
//        }
//    }
//
//    public static void v(String content) {
//        if (needLog) {
//            Logger.v(content);
//        }
//    }
//
//    public static void v(String tag, String content) {
//        if (needLog) {
//            Logger.t(tag).v(content);
//        }
//    }
//
//    public static void w(String content) {
//        if (needLog) {
//            Logger.w(content);
//        }
//    }
//
//    public static void w(String tag, String content) {
//        if (needLog) {
//            Logger.t(tag).w(content);
//        }
//    }
//
//
//}