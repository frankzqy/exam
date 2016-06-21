package org.inspur.exam.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class HashUtil {
	
	private static char hexChar[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };

    public HashUtil()
    {
    }

    public static String getFileMD5(String filename)
    		throws Exception
    {
        String str = "";
        FileInputStream fis = null;
        try {
        	fis = new FileInputStream(filename);
        	str = getFileMD5(fis);
        } catch(IOException e) {
        	throw e;
        } finally {
        	if(fis != null)
        		fis.close();
        	fis = null;
        }
        
        return str;
    }
    
    public static String getFileMD5(File file)
    		throws Exception
    {
        String str = "";
        FileInputStream fis = null;
        try {
        	fis = new FileInputStream(file);
        	str = getFileMD5(fis);
        } catch(IOException e) {
        	throw e;
        } finally {
        	if(fis != null)
        		fis.close();
        	fis = null;
        }
        return str;
    }
    
    public static String getFileMD5(InputStream fis)
    	throws Exception
    {
        String str = "";
        str = getHash(fis, "MD5");
        return str;
    }
    
    public static MessageDigest getMessageDigest() throws Exception {
    	return MessageDigest.getInstance("MD5");
    }

    private static String getHash(InputStream fis, String hashType)
        throws Exception
    {
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for(int numRead = 0; (numRead = fis.read(buffer)) > 0;)
        {
            md5.update(buffer, 0, numRead);
        }
        if(fis.markSupported())
        	fis.reset();
        return toHexString(md5.digest());
    }

    public static String toHexString(byte b[])
    {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for(int i = 0; i < b.length; i++)
        {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0xf]);
        }

        return sb.toString();
    }
    public static String getStringHash(String str,String hashType)throws Exception{
    	byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
 
        md5.update(str.getBytes());

        return toHexString(md5.digest());
    }
    
    public static void main(String[] args) {
    	try {
			//System.out.println(HashUtil.getFileMD5("C:\\license.txt"));
    		System.out.println(HashUtil.getStringHash("test_1234","md5"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

