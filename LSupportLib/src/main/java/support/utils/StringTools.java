package support.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringTools {

	/**
	 * 对xml字符串数据的特殊字符进行编码
	 * @param strData
	 * @return
	 * @author Lwy
	 * @date 2015-9-15 下午3:51:14
	 */
   public static String encodeString(String strData)  
    {  
        if (strData == null)  
        {  
            return "";  
        }  
        strData = replaceString(strData, "&", "&amp;");  
        strData = replaceString(strData, "<", "&lt;");  
        strData = replaceString(strData, ">", "&gt;");  
        strData = replaceString(strData, "&apos;", "&apos;");  
        strData = replaceString(strData, "\"", "&quot;");  
        return strData;  
    }  
  /**
   * 对xml字符串数据进行解码
   * @param strData
   * @return
   * @author Lwy
   * @date 2015-9-15 下午3:51:39
   */
  public static String decodeString(String strData)  
   {  
       strData = replaceString(strData, "&lt;", "<");  
       strData = replaceString(strData, "&gt;", ">");  
       strData = replaceString(strData, "&apos;", "&apos;");  
       strData = replaceString(strData, "&quot;", "\"");  
       strData = replaceString(strData, "&amp;", "&");  
       return strData;  
   }  
   /**
    * 替换指定字符串
    * @param strData
    * @param regex 需要被替换的字符
    * @param replacement  需要替换的字符
    * @return
    * @author Lwy
    * @date 2015-9-15 下午3:51:57
    */
   public static String replaceString(String strData, String regex,  
           String replacement)  
   {  
       if (strData == null)  
       {  
           return null;  
       }  
       int index;  
       index = strData.indexOf(regex);  
       String strNew = "";  
       if (index >= 0)  
       {  
           while (index >= 0)  
           {  
               strNew += strData.substring(0, index) + replacement;  
               strData = strData.substring(index + regex.length());  
               index = strData.indexOf(regex);  
           }  
           strNew += strData;  
           return strNew;  
       }  
       return strData;  
   }  
   /**
    * 拼接xml格式数据
    * @param sfield
    * @param svalue
    * @return
    * @author Lwy
    * @date 2015-9-25 下午2:41:05
    */
    public static String getXmlParams(String sfield, String svalue) {
		return String.format("<%1$s>%2$s</%1$s>", sfield, svalue);
	}
    public static InputStream String2InputStream(String str){
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

    public static String inputStream2String(InputStream is){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
