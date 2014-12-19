package com.wh.frame.utils;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.content.Context;
import android.graphics.Paint;



/**
 * 字符串工具类
 * @author weihe
 *
 */
public class StringUtil {

    /**
     * 
     * @author LEE
     * @date:2011-7-19 上午11:44:31 功能：检测字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotNull(final String str) {
        boolean result = false;
        if (str != null && !str.equals("") && !"null".equals(str) && str.trim().length() > 0) {
            result = true;
        }
        return result;
    }

    public static boolean isNull(final String str) {
        if (str == null || "".equals(str) || " ".equals(str) || "null".equals(str)
            || "NULL".equals(str) || str.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String strToUpper(final String str) {
        String result = null;
        if (str != null && !str.equals("") && !"null".equals(str)) {
            result = str.toUpperCase();
        }
        return result;
    }

    /**
     * 
     * @author LEE
     * @date:Jan 29, 2010 2:31:06 PM 功能：获得id加逗号 的id字符串.
     * @param idsStr
     * @return
     */
    public static String getIdsStr(String idsStr) {
        if (idsStr.endsWith(",")) {
            idsStr = idsStr.substring(0, idsStr.length() - 1);
        } else if (StringUtil.isNull(idsStr)) {
            idsStr = "0";
        }
        return idsStr;
    }

    /**
     * 
     * Function:通过视频分类地址获得视频分类的名称 author:LEE 2011-11-9 下午01:00:28
     * 
     * @param categoryUrl
     * @return
     */
    public static String getCategoryFileNameFromCategoryUrl(final String categoryUrl) {
        String result = "ERROR";
        if (StringUtil.isNotNull(categoryUrl)) {
            int index = categoryUrl.lastIndexOf("/");
            result = categoryUrl.substring(index + 1);
            if (result.contains("?")) {
                index = result.indexOf("?");
                result = result.substring(0, index);
            }
        }
        return result;
    }

    public static void main(final String args[]) {
        System.out.println(StringUtil.getCategoryFileNameFromCategoryUrl("http://www.baidu.com/aa.xml?a=1"));
    }

    /**
     * 
     * Function: 把数字变为时间显示 author:LEE 2011-12-8 下午02:02:03
     * 
     * @param time
     * @return
     */
    public static String changeDigitalToDateStr(long time) {
        final int hour = 60 * 60 * 1000;
        final int min = 60 * 1000;
        final int sec = 1000;
        String hourStr = "";
        String minStr = "";
        String secStr = "";
        String resultStr = "";
        if (time / hour >= 1) {
            hourStr = String.valueOf(time / hour) + "小时";
            time = time % hour;
            if (time / min > 1) {
                minStr = String.valueOf(time / min) + "分";
                time = time % min;
                secStr = time / sec + "秒";
            } else {
                minStr = "0分";
                secStr = String.valueOf(time / sec) + "秒";
            }
        } else if (time / min >= 1) {
            minStr = String.valueOf(time / min) + "分";
            time = time % min;
            secStr = time / sec + "秒";
        } else {
            secStr = String.valueOf(time / sec) + "秒";
        }
        resultStr = hourStr + minStr + secStr;
        return resultStr;
    }

    public static String getVideoIdFromImgFileName(final String fileName) {
        String videoId = "0";
        if (StringUtil.isNotNull(fileName)) {
            final String array[] = fileName.split("\\.");
            if (array.length > 1) {
                videoId = array[0];
            }
        }
        return videoId;
    }

    public static String getUUIDFromImgFileName(final String fileName) {
        String videoId = "0";
        if (StringUtil.isNotNull(fileName)) {
            final String array[] = fileName.split("\\.");
            if (array.length > 1) {
                videoId = array[0];
            }
        }
        return videoId;
    }

    public static void clearList(final List l) {
        if (l != null) {
            for (Object obj : l) {
                obj = null;
            }
            l.clear();
        }
        System.gc();
    }

    public static void releaseObjectToNull(final Object... objArray) {
        if (objArray != null) {
            for (int i = 0; i < objArray.length; i++) {
                if (objArray[i] != null) {
                    if (objArray[i] instanceof List) {
                        ((List) objArray[i]).clear();
                    }
                    objArray[i] = null;
                }
            }
        }
        System.gc();
    }

    /**
     * 去除特殊字符 "[`~''~　——|‘：”“’“”\"＂‘]"
     */
    public static String cleanStr(String str) {
        if (str != null) {
            final String regEx = "[`~''~　——|‘：”“’“”\"＂‘]";
            final Pattern pat = Pattern.compile(regEx);
            final Matcher m = pat.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }
    
    
    /**
     * @param editRemark
     * @return
     * 去除特殊字符% &，注意全角和半角
     */
    public static String isIllegalString(CharSequence editRemark) {
		final String regEx = "[%&％＆]";
		Pattern pat = Pattern.compile(regEx);
		Matcher m = pat.matcher(editRemark);
		return m.replaceAll("").toString();
	}
    
    /**
     * 匹配中文、数字、英文
     * @param editRemark
     * @return
     */
    public static String isStringCanInput(CharSequence editRemark) {
		String str = "";
		String reg_charset = "[^a-zA-Z0-9\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(reg_charset);
		Matcher m = p.matcher(editRemark);
		str = m.replaceAll("").toString();
		return str;
	}

    /**
     * 替换字符串中的特殊字符 "_r_n"-->"\r\n" "_n"-->"\r\n"
     */
    public static String freshStr(String str) {
        if (str != null) {
            str = str.replaceAll("_r_n", "\r\n").replaceAll("_n", "\r\n").replaceAll("_r", "\r\n");
        }
        return str;

    }

    /**
     * 将一个字符串数组中的字符串化为等长，按最长字符串计算（注意这里的难点是中英文混合，一个中文占两个位置）
     */
    public static String[] sameStringArray(final String[] res) {
        final String[] result = new String[res.length];
        // 让物品名称一样长
        float nameLength = 0;
        float len = 0;
        // for (int i = 0; i < res.length; i++) {
        // res[i] = StringUtil.patchStr(res[i]);
        // }
        for (final String s : res) {
            len = StringUtil.getStrWidth(s);
            // len = s.getBytes("gbk").length / 2;
            nameLength = nameLength < len ? len : nameLength;
        }

        final int remain = (int) nameLength % 3;
        switch (remain) {
        case 1:
            nameLength += 2;
            break;
        case 2:
            nameLength += 1;
            break;
        default:
            break;
        }

        final StringBuilder sbu = new StringBuilder();
        int i = 0;
        for (String s : res) {
            len = StringUtil.getStrWidth(s);
            // final int num = (int) Math.ceil((nameLength - len) / 3);
            final int num = (int) (nameLength - len) / 3;
            if (len < nameLength) {
                sbu.delete(0, sbu.length());
                for (int n = 0; n < num; n++) {
                    sbu.append(" ");
                }
                s = sbu.insert(0, s).toString();
            }
            // s = StringUtil.patchStr(s);
            // if (i == 0) {
            // s += "\r\r";
            // }

            result[i] = s;
//            LogUtil.v("test", s + ":" + StringUtil.getStrWidth(s));//
            i++;
        }
        return result;
    }

    public static String patchStr(String str) {
        final int num = (int) StringUtil.getStrWidth(str) % 3;
//        LogUtil.v("test前", str + ":" + StringUtil.getStrWidth(str));//
        // ////////////////////////////
        switch (num) {
        case 1:
            str += "\r\r";
            break;
        case 2:
            str += "\r";
            break;
        default:
            break;
        }
        // LogUtil.v("test", str + ":" + StringUtil.getStringWidth(str));//
        // ////////////////////////////
        return str;
    }

    /**
     * <pre>
     * 功能描述：获取字符串在屏幕上占的长度
     * 使用示范：
     *
     * @param str
     * @return
     * </pre>
     */
    public static float getStrWidth(final String str) {
        final Paint paint = new Paint();
        return paint.measureText(str);
    }
    /**
     * <pre>
     * 功能描述：将字符串补成指定长度
     * 使用示范：
     *
     * @param str
     * @param len
     * @return
     * </pre>
     */
    public static String getLengthString(final String str, final int len) {
        if (str.length() >= len) {
            return str.substring(0, len);
        } else {
            final StringBuilder sb = new StringBuilder();
            final int length = str.length();
            for (int i = 0; i < len - length; i++) {
                sb.append(" ");
            }
            sb.append(str);
            return sb.toString();
        }
    }
    /**
     * <pre>
     * 功能描述：从资源库中读取资源文件
     * 使用示范：
     * </pre>
     */
    public static String getStringFromRes(Context context,int resId){
    	return context.getString(resId);
    }
    
	/**
	 * 压缩字符串
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString("ISO-8859-1");
	}

	/**
	 * 解压缩字符串
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String uncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				str.getBytes("ISO-8859-1"));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString("UTF-8");
	}
	
	public static String getStringFromFormat(Context context,int id, Object... obj) {
		return getStringFormat(getStringFromRes(context,id), obj);
	}

	public static String getStringFormat(String key, Object... obj) {
		return key.format(key, obj);
	}

	/**
	 * 将double类型数据格式化为2位小数
	 * @param object
	 * @return
	 */
	public static String formatStringTwoPoint(Double object) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(object);
	}
		
		
}
