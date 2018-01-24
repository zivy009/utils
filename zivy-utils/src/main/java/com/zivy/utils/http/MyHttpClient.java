package com.zivy.utils.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * apache httpClient
 * 
 * @author zivy
 * 
 */
public class MyHttpClient {
	private HttpClient httpClient;

	public MyHttpClient() {
		httpClient = HttpClients.createDefault();
	}

	/**
	 * get request
	 * 
	 * @param url
	 *            请求url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String requestGet(String url) throws ClientProtocolException, IOException {
		String returnStr = null;
		// 初始化GET请求
		HttpGet get = new HttpGet(url);
		// 执行GET请求
		HttpResponse response = httpClient.execute(get);
		// 获取请求返回的数据
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);
		// 一次GET请求结束
		get.abort();
		return returnStr;
	}

	private String entityToString(HttpEntity entity) throws IOException {
		String returnStr = null;
		if (entity != null) {
			entity = new BufferedHttpEntity(entity);
			InputStream in = entity.getContent();
			byte[] read = new byte[1024];
			byte[] all = new byte[0];
			int num;
			while ((num = in.read(read)) > 0) {
				byte[] temp = new byte[all.length + num];
				System.arraycopy(all, 0, temp, 0, all.length);
				System.arraycopy(read, 0, temp, all.length, num);
				all = temp;
			}
			returnStr = new String(all);
			in.close();

		}
		return returnStr;
	}

	/**
	 * apache httpClient post request
	 * 
	 * @param url
	 *            . url of request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String requestPost(String url) throws ClientProtocolException, IOException {
		String returnStr = null;
		HttpPost post = new HttpPost(url);
		// 创建表单参数列表
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// 添加参数
		qparams.add(new BasicNameValuePair("id", "5"));
		// 填充表单
		post.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
		// 执行POST请求
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);
		return returnStr;
		// ......
		// 接下来就与GET一样了
	}

	/**
	 * 
	 * @param url
	 *            请求地址
	 * @param qparams
	 *            post参数列表
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String requestPost(String url, List<NameValuePair> qparams) throws ClientProtocolException, IOException {
		String returnStr = null;
		HttpPost post = new HttpPost(url);

		// 填充表单
		post.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
		// 执行POST请求
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);
		return returnStr;

	}
	public String requestPost(String url, List<NameValuePair> qparams, Header[] headers) throws ClientProtocolException, IOException {
		String returnStr = null;
		HttpPost post = new HttpPost(url);

		// 填充表单
		post.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
		// 设置请求头
		if (headers != null) {
			post.setHeaders(headers);
			}
		// 执行POST请求
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);
		return returnStr;

	}
	/**
	 * 流信息交互请求回应
	 * 
	 * @param url
	 *            请求的url
	 * @param requestContent
	 *            请求内容参数
	 * @param headers
	 *            请求头信息
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String requestStream(String url, final String requestContent, Header[] headers) throws ClientProtocolException, IOException {
		//System.out.println("requestContent= " + requestContent);
		String returnStr = null;
		// 封装输入流
		ContentProducer cp = new ContentProducer() {
			public void writeTo(OutputStream outstream) throws IOException {

				if (requestContent != null) {
					Writer writer = new OutputStreamWriter(outstream, "UTF-8");
					writer.write(requestContent);
					writer.flush();
				}

			}
		};

		HttpEntity requestEntity = new EntityTemplate(cp);
		HttpPost httppost = new HttpPost(url);
		// 设置请求头
		if (headers != null) {

			httppost.setHeaders(headers);
		}
		// 执行请求
		httppost.setEntity(requestEntity);
		HttpResponse response = httpClient.execute(httppost);
		// 获得请求数据
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);
		return returnStr;

	}

	/**
	 * 上传文件和文本
	 * 
	 * @param url
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void MultiUpload(String url, File targetFile, List<NameValuePair> qparams) throws ClientProtocolException, IOException {
		String returnStr = null;
		HttpPost post = new HttpPost(url);
		FileBody bin = new FileBody(targetFile);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.addPart("bin", bin);
		for (NameValuePair nameValuePair : qparams) {
			StringBody temp = new StringBody(nameValuePair.getValue(), ContentType.TEXT_PLAIN);
			multipartEntityBuilder.addPart(nameValuePair.getName(), temp);
		}
		HttpEntity reqEntity = multipartEntityBuilder.build();

		// 填充表单
		post.setEntity(reqEntity);
		// 执行POST请求
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);
		System.out.println(returnStr);
	}

	/**
	 * 上传文件和文本
	 * 
	 * @param url
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String MultiUploadNew(String url, File targetFile,String namefileparam, List<NameValuePair> qparams) throws ClientProtocolException, IOException {
		String returnStr = null;

		CloseableHttpClient closeablehttpclient = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);
		FileBody bin = new FileBody(targetFile);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.addPart(namefileparam, bin);
		for (NameValuePair nameValuePair : qparams) {
			StringBody temp = new StringBody(nameValuePair.getValue(), ContentType.TEXT_PLAIN);
			multipartEntityBuilder.addPart(nameValuePair.getName(), temp);
		}
		HttpEntity reqEntity = multipartEntityBuilder.build();

		// 填充表单
		post.setEntity(reqEntity);
		// 执行POST请求
		CloseableHttpResponse response = closeablehttpclient.execute(post);
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);

		try {
			System.out.println(response.getStatusLine());
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		closeablehttpclient.close();
		return returnStr;

	}
	public String MultiUploadNew(String url, File targetFile,String namefileparam, List<NameValuePair> qparams,Header[] headers ) throws ClientProtocolException, IOException {
		String returnStr = null;

		CloseableHttpClient closeablehttpclient = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);
		FileBody bin = new FileBody(targetFile);
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		multipartEntityBuilder.addPart(namefileparam, bin);
		for (NameValuePair nameValuePair : qparams) {
			StringBody temp = new StringBody(nameValuePair.getValue(), ContentType.TEXT_PLAIN);
			multipartEntityBuilder.addPart(nameValuePair.getName(), temp);
		}
		HttpEntity reqEntity = multipartEntityBuilder.build();

		// 填充表单
		post.setEntity(reqEntity);
		post.setHeaders(headers);
		// 执行POST请求
		CloseableHttpResponse response = closeablehttpclient.execute(post);
		HttpEntity entity = response.getEntity();
		returnStr = entityToString(entity);

		try {
			//System.out.println(response.getStatusLine());
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		closeablehttpclient.close();
		return returnStr;

	}
}
