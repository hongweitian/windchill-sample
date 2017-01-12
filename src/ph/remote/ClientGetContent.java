package ph.remote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

public class ClientGetContent {

    private String hostname;

    private String username;

    private String password;
    
    public ClientGetContent(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    /**
     * 获取下载内容的文件名
     * 
     * @param oid
     * @param cioids
     * @return
     * @throws IOException
     */
    public String getContentFileName(String oid, String cioids, String role) throws IOException {
        String redirectUrl = getAttachmentsURLRedirect(oid, cioids, role);

        String downloadURL = getDownloadURL(redirectUrl);

        String fileName = null;
        try {
            fileName = getFileNameFromURL(downloadURL);
        } catch (DecoderException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    /**
     * 获取下载数据流
     * 
     * @param oid
     * @param cioids
     * @param outStream
     * @throws IOException
     */
    public void saveContent(String oid, String cioids, String role, OutputStream outStream) throws IOException {

        String redirectUrl = getAttachmentsURLRedirect(oid, cioids, role);

        String downloadURL = getDownloadURL(redirectUrl);

        downloadContent(downloadURL, outStream);
    }

    /**
     * 保存为文件
     * 
     * @param oid
     * @param cioids
     * @param tempDir
     * @return
     * @throws IOException
     */
    public String getContentFile(String oid, String cioids, String role, String tempDir) throws IOException {

        String redirectUrl = getAttachmentsURLRedirect(oid, cioids, role);

        String downloadURL = getDownloadURL(redirectUrl);

        String fileName = "tmp";
        try {
            fileName = getFileNameFromURL(downloadURL);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        if (tempDir == null) {
            tempDir = File.createTempFile("file", ".tmp").getParentFile().getAbsolutePath();
        }

        File file = new File(tempDir + File.separator + fileName);

        FileOutputStream outStream = new FileOutputStream(file);

        downloadContent(downloadURL, outStream);

        return file.getAbsolutePath();
    }

    /**
     * 获取文档内容下载的间接地址（格式固定）
     * 
     * @param host
     * @param oid
     * @param cioids
     * @param role
     * @return
     */
    public String getAttachmentsURLRedirect(String oid, String cioids, String role) {
        StringBuffer sbf = new StringBuffer();
        sbf.append("http://").append(hostname);
        sbf.append("/Windchill/servlet/AttachmentsDownloadDirectionServlet?oid=").append(oid);
        sbf.append("&cioids=").append(cioids);
        sbf.append("&role=").append(role);

        String url = sbf.toString();
        return url;
    }

    /**
     * 从间接地址的内容中获取真正的下载地址
     * 
     * @param host
     * @param redirectUrl
     * @return
     * @throws IOException
     */
    public String getDownloadURL(String redirectUrl) throws IOException {
        File tempFile = File.createTempFile("HTML", ".txt");

        System.out.println("tempFile=" + tempFile.getAbsolutePath());

        downloadContent(redirectUrl, new FileOutputStream(tempFile));

        FileInputStream finput = new FileInputStream(tempFile);
        String downloadURL = getDownloadURL(finput);
        System.out.println("downloadURL=" + downloadURL);

        return downloadURL;
    }

    /**
     * 解析真正的下载地址
     * 
     * @param input
     * @return
     * @throws IOException
     */
    private static String getDownloadURL(InputStream input) throws IOException {
        BufferedReader dr = new BufferedReader(new InputStreamReader(input));
        String line = null;
        do {
            line = dr.readLine();
            if (line != null && line.indexOf("var downloadURL =") >= 0) {
                break;
            }
        } while (line != null);

        if (line != null) {
            int begin = line.indexOf("\"");
            int end = line.lastIndexOf("\"");
            line = line.substring(begin + 1, end);
        }
        return line;
    }

    /**
     * 从下载链接中获取文件名信息
     * 
     * @param downloadURL
     * @return
     * @throws DecoderException
     */
    public static String getFileNameFromURL(String downloadURL) throws DecoderException {
        if (downloadURL == null) {
            return null;
        }

        downloadURL = downloadURL.substring(0, downloadURL.indexOf("?"));
        System.out.println("downloadURL=" + downloadURL);
        String fileName = downloadURL.substring(downloadURL.lastIndexOf("/") + 1);

        fileName = new URLCodec().decode(fileName);
        System.out.println("fileName=" + fileName);

        return fileName;
    }

    public void downloadContent(String uri, OutputStream outStream) throws IOException {

        HttpClient client = new HttpClient();

        client.getState().setCredentials(new AuthScope(hostname, 80),
                new UsernamePasswordCredentials(username, password));

        GetMethod httpGet = new GetMethod(uri);

        InputStream is = null;
        int x = 0;
        byte buffer[] = new byte[1024];
        try {
            httpGet.setDoAuthentication(true);
            int status = client.executeMethod(httpGet);
            System.out.println("status - " + status);

            is = httpGet.getResponseBodyAsStream();

            while ((x = is.read(buffer, 0, buffer.length)) >= 0) {
                outStream.write(buffer, 0, x);
            }

        } catch (HttpException e) {
            e.printStackTrace();
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            httpGet.releaseConnection();
        }

    }
}
