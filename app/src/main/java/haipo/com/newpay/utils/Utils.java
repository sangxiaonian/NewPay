package haipo.com.newpay.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    public static void showStarOfPin(Activity activity, final EditText edit,
                                     final int length) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++)
                    sb.append('*');
                edit.setText(sb.toString());
            }
        });
    }

    /**
     * 移动号段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,
     * 187,188,147,178,1705
     * <p>
     * 联通号段: 130,131,132,155,156,185,186,145,176,1709
     * <p>
     * 电信号段:133,153,180,181,189,177,1700
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNumber(String phone) {
        if (phone == null)
            return false;
        String str = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断手机号类型 移动号段:
     * 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,
     * 187,188,147,178,1705 联通号段: 130,131,132,155,156,185,186,145,176,1709 电信号段:
     * 133,153,180,181,189,177,1700
     *
     * @param phone
     * @return 0移动 1联通 2电信 3为虚拟号段 否则为其他号码
     */
    public static int PhoneNumberType(String phone) {

        int type = -1;

        // 移动
        String CMCC = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)";
        // 联通
        String WCDMA = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)";
        // 电信
        String CTWAP = "(^1(33|53|77|8[019])\\d{8}$)";

        String XU = "(^170\\d{8}$)";

        // 移动
        Pattern p1 = Pattern.compile(CMCC);
        Matcher m1 = p1.matcher(phone);

        // 联通
        Pattern p2 = Pattern.compile(WCDMA);
        Matcher m2 = p2.matcher(phone);

        // 电信
        Pattern p3 = Pattern.compile(CTWAP);
        Matcher m3 = p3.matcher(phone);

        // 虚拟号段
        Pattern p4 = Pattern.compile(XU);
        Matcher m4 = p4.matcher(phone);

        // 移动
        if (m1.matches()) {
            type = 0;
            // 联通
        } else if (m2.matches()) {
            type = 1;
            // 电信
        } else if (m3.matches()) {
            type = 2;
            // 虚拟号段
        } else if (m4.matches()) {
            type = 3;

            // 未知号码
        } else {
            type = 4;
        }

        return type;
    }

    /**
     * 判断是否为有效的IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIpAddress(String ipAddress) {
        if (ipAddress == null)
            return false;
        String str = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(ipAddress);
        return m.matches();
    }

    /**
     * 判断是否为有效email地址
     *
     * @param email
     * @return
     */
    public static boolean isEmailAddress(String email) {
        if (email == null)
            return false;
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否是有效的终端号码
     *
     * @param devicePort
     * @return
     */
    public static boolean isDevicePort(String devicePort) {
        if (TextUtils.isEmpty(devicePort))
            return false;
        String str = "^\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(devicePort);
        return m.matches();
    }

    public static byte hex2byte(char hex) {
        if (hex <= 'f' && hex >= 'a') {
            return (byte) (hex - 'a' + 10);
        }

        if (hex <= 'F' && hex >= 'A') {
            return (byte) (hex - 'A' + 10);
        }

        if (hex <= '9' && hex >= '0') {
            return (byte) (hex - '0');
        }

        return 0;
    }

    /**
     * byte 转换成16进制字符串
     */
    public static String bytes2HexString(byte[] data) {
        if (data == null)
            return "";
        StringBuilder buffer = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                buffer.append('0');
            }
            buffer.append(hex);
        }
        return buffer.toString().toUpperCase();
    }

    /**
     * 16进制字符串转换成2进制byte数组
     *
     * @param data
     * @return
     */
    public static byte[] hexString2Bytes(String data) {
        if (data == null)
            return null;
        byte[] result = new byte[(data.length() + 1) / 2];
        if ((data.length() & 1) == 1) {
            data += "0";
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (hex2byte(data.charAt(i * 2 + 1)) | (hex2byte(data
                    .charAt(i * 2)) << 4));
        }
        return result;
    }

    public static String bcd2Ascii(final byte[] bcd) {
        if (bcd == null)
            return "";
        StringBuilder sb = new StringBuilder(bcd.length << 1);
        for (byte ch : bcd) {
            byte half = (byte) (ch >> 4);
            sb.append((char) (half + ((half > 9) ? ('A' - 10) : '0')));
            half = (byte) (ch & 0x0f);
            sb.append((char) (half + ((half > 9) ? ('A' - 10) : '0')));
        }
        return sb.toString();
    }

    public static byte[] ascii2Bcd(String ascii) {
        if (ascii == null)
            return null;
        if ((ascii.length() & 0x01) == 1)
            ascii = "0" + ascii;
        byte[] asc = ascii.getBytes();
        byte[] bcd = new byte[ascii.length() >> 1];
        for (int i = 0; i < bcd.length; i++) {
            bcd[i] = (byte) (hex2byte((char) asc[2 * i]) << 4 | hex2byte((char) asc[2 * i + 1]));
        }
        return bcd;
    }

    public static String showCardNumber(String cardNo) {
        if (cardNo == null)
            return "";
        if (cardNo.length() <= 10)
            return cardNo;
        StringBuilder sb = new StringBuilder();
        sb.append(cardNo.substring(0, 6));
        for (int i = 0; i < cardNo.length() - 10; i++)
            sb.append('*');
        sb.append(cardNo.substring(cardNo.length() - 4, cardNo.length()));
        return sb.toString();
    }

    public static String splitCardNumber(String track) {
        if (track == null)
            return "";
        // return track.split("=")[0];
        return track.split("D")[0];
    }

    public static String removeSpaceChar(String string) {
        return string.replace(" ", "");
    }

    private static final int DATE_TIME_LEN = 10;
    private static final int DATE_LEN = 4;
    private static final int TIME_LEN = 6;
    private static final int DATA_LEN = 8;

    public static String formDateTime(String dateTime) {
        StringBuilder sb = new StringBuilder();
        switch (dateTime.length()) {
            case DATE_TIME_LEN:
                sb.append(dateTime.substring(0, 2));
                sb.append('/');
                sb.append(dateTime.substring(2, 4));
                sb.append(' ');
                sb.append(dateTime.substring(4, 6));
                sb.append(':');
                sb.append(dateTime.substring(6, 8));
                sb.append(':');
                sb.append(dateTime.substring(8, 10));
                break;
            case DATE_LEN:
                sb.append(dateTime.substring(0, 2));
                sb.append('/');
                sb.append(dateTime.substring(2, 4));
                break;
            case TIME_LEN:
                sb.append(dateTime.substring(0, 2));
                sb.append(':');
                sb.append(dateTime.substring(2, 4));
                sb.append(':');
                sb.append(dateTime.substring(4, 6));
                break;
            case DATA_LEN:
                sb.append(dateTime.substring(0, 4));
                sb.append('-');
                sb.append(dateTime.substring(4, 6));
                sb.append('-');
                sb.append(dateTime.substring(6, 8));
                break;
            default:
                return dateTime;
        }
        return sb.toString();
    }

    // 判断交易卡号是否合法
    public static boolean isCardNumber(String cardNum) {
        int sum = 0;
        int doubleValue = 0;
        int i = 0;
        for (i = cardNum.length() - 2; i >= 0; i = i - 2) {
            if (i == 0) {
                doubleValue = (cardNum.charAt(i) - '0') * 2;
                if (doubleValue < 10) {
                    sum = sum + doubleValue;
                } else {
                    sum = sum + doubleValue - 9;
                }
            } else {
                doubleValue = (cardNum.charAt(i) - '0') * 2;
                if (doubleValue < 10) {
                    sum = sum + doubleValue;
                } else {
                    sum = sum + doubleValue - 9;
                }
                sum = sum + cardNum.charAt(i - 1) - '0';
            }
        }
        if ((10 - sum % 10) % 10 == (cardNum.charAt(cardNum.length() - 1) - '0')) {
            return true;
        } else {
            return false;
        }
    }

    public static byte[] GetTLVdata(byte tag, byte[] TLVdata) {
        int TLVlen = 0, i;
        for (i = 0; i < TLVdata.length; i++) {
            if (TLVdata[i] == tag) {
                TLVlen = TLVdata[i + 1];
                break;
            }
        }
        if (i == TLVdata.length) {
            return null;
        }
        byte[] TLVsrc = new byte[TLVlen];
        System.arraycopy(TLVdata, i + 2, TLVsrc, 0, TLVlen);
        return TLVsrc;
    }

    /**
     * 描述：MD5加密.
     *
     * @param str 要加密的字符串
     */
    public final static String MD5(String str) {
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        try {
            byte[] strTemp = str.getBytes("utf-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte tmp[] = mdTemp.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char strs[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                strs[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                strs[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(strs).toLowerCase(); // 换后的结果转换为字符串
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前时间
     *
     * @param type 时间格式化的格式
     * @return 格式化的字符串
     */
    public static String getStringDate(String type) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将float转换成保留两位小数的字符串
     *
     * @param f
     * @return
     */
    public static String twoDecimals(float f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue() + "";

    }

    /**
     * 格式化手机号
     *
     * @param phone
     * @return
     */
    public static String formatPhone(String phone) {
        String result = phone;
        StringWriter sw = new StringWriter();
        char[] charArray = phone.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            sw.append(charArray[i]);
            if (i == 2 || i == 6) {
                sw.append("-");
            }
        }
        result = sw.toString();
        return result;
    }

    /**
     * 获取两位小数
     *
     * @param data
     * @return
     */
    public static float get2Double(double data) {
        BigDecimal b = new BigDecimal(data);
        // return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        // b.setScale(2, BigDecimal.ROUND_HALF_UP) 表明四舍五入，保留两位小数
        float v = (float) Double.parseDouble(String.format("%.2f", data));

        return v;
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    public static int dp2px(int dpVal, Context c) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, c.getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(int spVal, Context c) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, c.getResources().getDisplayMetrics());

    }


    /**
     * 获取一个随机数
     *
     * @param count 随机数的位数
     * @return
     */
    public static String getRandomData(int count) {
        String[] data = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        Random random = new Random();
        String result = "";
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(10);
            if (i == 0 && index == 0) {
                i--;
                continue;
            }
            result += data[index];
        }

        return result;
    }

    /**
     * 获取卡类型
     *
     * @param str
     * @return
     */
    public static String CheckCardType(String str) {
        String type = "";
        if (str.equals("0000")) {
            type = "普通卡（普通）";
        } else if (str.equals("0009")) {
            type = "异型卡（普通）";
        } else if (str.equals("0100")) {
            type = "普通卡（记名）";
        } else if (str.equals("0109")) {
            type = "异型卡（记名）";
        } else if (str.equals("0200")) {
            type = "普通卡（优惠）";
        } else if (str.equals("0201")) {
            type = "学生卡（优惠）";
        } else if (str.equals("0300")) {
            type = "普通卡（低保）";
        } else if (str.equals("0301")) {
            type = "学生卡（低保）";
        } else if (str.equals("0500")) {
            type = "普通卡（员工）";
        } else if (str.equals("0505")) {
            type = "司机卡（员工）";
        } else if (str.equals("0506")) {
            type = "临时卡（员工）";
        }

        return type;
    }

    public static String getMoney(String f79, String origAmt) {
        String balance = f79;
        if (f79.length() > 10) {
            balance = f79.substring(6);
        }
        Float money = Float.parseFloat(balance) / 100;
        Float addMoney = Float.parseFloat(origAmt) / 100;
        String str = (addMoney + money) * 100 + "";
        String finalMoney = str.substring(0, str.length() - 2);
        String newMoney = "";
        for (int i = 0; i < 12 - finalMoney.length(); i++) {
            newMoney += "0";
        }
        newMoney += finalMoney;
        return newMoney;
    }

    /**
     * 将Map集合转换成字符串
     *
     * @param params
     * @return
     */
    public static String encodeParameters(Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            encodedParams.append(URLEncoder.encode(entry.getKey()));
            encodedParams.append('=');
            // URLEncoder.encode(entry.getValue())
            encodedParams.append(entry.getValue());
            encodedParams.append('&');
        }
        return encodedParams.toString();
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @param imageSize 图片的大小
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, int imageSize) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrayOutputStream);

        int compressSize = 100;
        while (arrayOutputStream.toByteArray().length / 1024 > imageSize) {
            arrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressSize,
                    arrayOutputStream);
            compressSize -= 10;
        }
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(
                arrayOutputStream.toByteArray(), 0,
                arrayOutputStream.toByteArray().length);
        return bitmap2;
    }

    /**
     * 格式化彩票接口返回的日期
     *
     * @param dateString
     * @return 2016-08-08 10：00格式
     */
    public static String formatTime(String dateString) {
        String year = dateString.substring(0, 4);
        String month = dateString.substring(4, 6);
        String day = dateString.substring(6, 8);
        String hour = dateString.substring(8, 10);
        String minute = dateString.substring(10, 12);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }


    public static String formatOrderReturnTime(String dateString) {
        String year = dateString.substring(7, 11);
        String month = dateString.substring(11, 13);
        String day = dateString.substring(13, 15);
        String hour = dateString.substring(15, 17);
        String minute = dateString.substring(17, 19);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }

    /**
     * 福彩组六和值
     *
     * @param mNums
     * @return
     */
    public static int calculation_zhuliu(List<Integer> mNums) {
        int number = 0;
        for (int i = 0; i < mNums.size(); i++) {
            number += ten(mNums.get(i));
        }
        return number;
    }

    public static int ten(int n) {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j != i) {
                    for (int k = 0; k < 10; k++) {
                        if (i != k && j != k && i + j + k == n) {
                            result++;
                        }
                    }
                }
            }
        }
        return result / 6;
    }

    /**
     * 福彩组六直选
     *
     * @param mNums
     * @return
     */
    public static int calculation_zuliu_zhi(List<Integer> mNums) {
        return (mNums.size() * (mNums.size() - 1) * (mNums.size() - 2) / 6);
    }

    /**
     * 福彩组三复式
     *
     * @param mNums
     * @return
     */
    public static int calculation_zusan_double(List<Integer> mNums) {

        int number = 0;
        for (int i = 0; i < mNums.size(); i++) {
            number += math(mNums.get(i));
        }
        return number;
    }

    private static int math(int number) {
        int j = 0;
        for (int i = 0; i <= Math.min(number / 2, 9); i++) {
            if (number - i * 2 <= 9 && number - i * 2 != i) {
                System.out.println(i + "" + i + (number - (i + i)));
                j++;
            }
        }
        return j;
    }

    public static String encodeParametersInte(Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            encodedParams.append(URLEncoder.encode(String.valueOf(entry.getKey())));
            encodedParams.append('=');
            // URLEncoder.encode(entry.getValue())
            encodedParams.append(entry.getValue());
            encodedParams.append('&');
        }

        return encodedParams.toString();
    }

}
