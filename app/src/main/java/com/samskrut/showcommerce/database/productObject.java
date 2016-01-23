package com.samskrut.showcommerce.database;

import java.io.Serializable;

public class productObject implements Serializable
{
	int pid, cid;
	String pname, pinfo, purl;
	public String pcost;
	private static final long serialVersionUID = 1L;

	public productObject()
	{
	}

	public productObject(int pid, int cid, String pname, String pinfo, String purl)
	{
		super();
		this.pid = pid;
		this.cid = cid;
		this.pname = pname;
		this.pinfo = pinfo;
		this.purl = purl;
	}

	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}

	public int getCid()
	{
		return cid;
	}

	public void setCid(int cid)
	{
		this.cid = cid;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getPinfo()
	{
		return pinfo;
	}

	public void setPinfo(String pinfo)
	{
		this.pinfo = pinfo;
	}

	public String getPurl()
	{
		return purl;
	}

	public void setPurl(String purl)
	{
		this.purl = purl;
	}

	public String getPcost()
	{
		return pcost;
	}

	public void setPcost(String pcost)
	{
		this.pcost = pcost;
	}

}
