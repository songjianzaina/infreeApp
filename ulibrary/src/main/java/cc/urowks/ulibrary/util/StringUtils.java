package cc.urowks.ulibrary.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.urowks.ulibrary.R;

/**
 * 字符串工具类
 * <p/>
 * Created by jiang on 2015/10/8.
 */
public class StringUtils {

    /**
     * 验证是否是手机号
     *
     * @param mobiles 手机号字符串
     * @return 如果是手机号返回true，否则返回false
     */
    public static boolean isPhone(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        String regExp = "^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
        return mobiles.matches(regExp);
    }

    /**
     * 验证是否是邮箱
     *
     * @param email 邮箱地址
     * @return 如果是邮箱返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        String regExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|" +
                "(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return email.matches(regExp);
    }

    /**
     * 验证密码是否有效，密码格式为：6-18位，不能包含中文和任何不可见字符
     *
     * @param password 密码
     * @return 如果有效返回true，否则返回false
     */
    public static boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        String regExp = "^[^\\s\\u4e00-\\u9fa5]{6,20}$";
        return password.matches(regExp);
    }

    /**
     * 字符串半角转换为全角(半角空格为32,全角空格为12288.其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248)
     *
     * @param input 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String halfToFull(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) { //半角空格
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] > 32 && c[i] < 127) {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 字符串全角转换为半角(全角空格为12288，半角空格为32.其他字符全角(65281-65374)与半角(33-126)的对应关系是：均相差65248)
     *
     * @param input 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String fullToHalf(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) { //全角空格
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }


    public static SpannableString getSpannableString(final Context context, String source) {
        SpannableString spannableString = new SpannableString(source);
        String regexLink = "(@[\u4e00-\u9fa5\\w]+)|(#[\u4e00-\u9fa5\\w]+#)";
        Pattern patternLink = Pattern.compile(regexLink);
        Matcher matcherLink = patternLink.matcher(spannableString);

        for (; ; ) { // 如果可以匹配到
            if (matcherLink.find()) {
                final String key = matcherLink.group(); // 获取匹配到的具体字符
                int start = matcherLink.start(); // 匹配字符串的开始位置
                // @和#不可点击
                int blueColor = context.getResources().getColor(R.color.primary_color);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(blueColor);
                spannableString.setSpan(colorSpan, start, start + key.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


            } else {
                break;
            }
        }
        return spannableString;
    }


    /**
     * 判断手机号格式
     * * 手机号码:
     * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[0, 1, 6, 7, 8], 18[0-9]
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }


    /**
     * 判断密码格式  6-20位 数字 字母
     *
     * @param pwd
     * @return
     */
    public static boolean isTruePwd(String pwd) {
        Pattern p = Pattern
                .compile("^[0-9a-zA-Z]{6,20}$");
        Matcher m = p.matcher(pwd);

        return m.matches();
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    /**
     * 功能：多行横排文本转换为多列直排（以换行符 \n 作为断行标记）
     * 说明：可支持1～N行，但要求有换行标记符，或稍作修改以标点符号换行
     *
     * @param: strText
     * @return: 返回值为行列转置后的多行文本
     */
    public static String getTextHtoV(String strText) {
        String strResult = "";
        String br = "\n";      //断行标记，这里可改用逗号或分号等字符
        String strArr[] = strText.split(br);
        int nMaxLen = 0;      //各行最多字符数
        int nLines = strArr.length;    //总共的行数
        char charArr[][] = new char[nLines][];    //字符陈列（即二维数组）
        for (int i = 0; i < nLines; i++) {
            String str = strArr[i];
            int n = str.length();

            //以最长的行的字符数（即原列数）作为目标陈列的行数
            if (n > nMaxLen) nMaxLen = n;
            charArr[i] = strArr[i].toCharArray();
        }

        //行列转换
        for (int i = 0; i < nMaxLen; i++) {
            for (int j = 0; j < nLines; j++) {
                //若短句字符已“用完”则以空格代替
                char c = i < charArr[j].length ? charArr[j][i] : ' ';
                strResult += String.valueOf(c);

                //两列文字之间加空格，不需要的话请注释掉下面一行
                if (j < nLines - 1) strResult += " ";  //★
            }
            strResult += br;   //添加换行符
        }

        return strResult;
    }
}
