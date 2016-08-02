package com.guang.client.protocol;

import org.json.JSONException;
import org.json.JSONStringer;



public class GData {
	private String mode;//ģ��
	private long length;//���ݳ���
	private String body;//������
	private long bodyLength;//�����峤��
	
	public GData()
	{
		
	}
	
	public GData(String mode,String body)
	{
		init(mode,body);
	}
	
	private void init(String mode,String body)
	{
		this.mode = mode;
		this.body = body;
		if(this.body != null)
		{
			this.bodyLength = body.length();
			this.length = pack().length();
		}		
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
		init(mode,this.body);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
		init(this.mode,body);
	}

	public String pack()
	{
		JSONStringer jsonStringer = new JSONStringer();  
        try {  
            jsonStringer.object();  
            jsonStringer.key("mode");  
            jsonStringer.value(mode);  
            jsonStringer.key("length");  
            jsonStringer.value(length);  
            jsonStringer.key("body");  
            jsonStringer.value(body);  
            jsonStringer.key("bodyLength");  
            jsonStringer.value(bodyLength); 
            jsonStringer.endObject();  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return jsonStringer.toString();  
	}
	
}
