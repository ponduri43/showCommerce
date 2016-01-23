package com.samskrut.showcommerce.database;

public class cateObject
{
	int cid;
	String cname, curl;

	public cateObject()
	{

	}

	public cateObject(int cid, String cname, String curl)
	{
		super();
		this.cid = cid;
		this.cname = cname;
		this.curl = curl;
	}

	public int getCid()
	{
		return cid;
	}

	public void setCid(int cid)
	{
		this.cid = cid;
	}

	public String getCname()
	{
		return cname;
	}

	public void setCname(String cname)
	{
		this.cname = cname;
	}

	public String getCurl()
	{
		return curl;
	}

	public void setCurl(String curl)
	{
		this.curl = curl;
	}

}
