package com.samskrut.showcommerce.database;

import java.io.Serializable;

public class buyerClass implements Serializable
{
	String cid, pid, vid, imid, rfalg, time, bname, pstatus, sid, pname, count, infostatus, phone, mail, age, gender;
	private static final long serialVersionUID = 1L;

	public buyerClass(String cid, String pid, String vid, String imid, String rfalg, String time, String bname, String pstatus, String sid, String pname, String count,
			String infostatus, String phone, String mail, String age, String gender)
	{
		super();
		this.cid = cid;
		this.pid = pid;
		this.vid = vid;
		this.imid = imid;
		this.rfalg = rfalg;
		this.time = time;
		this.bname = bname;
		this.pstatus = pstatus;
		this.sid = sid;
		this.pname = pname;
		this.count = count;
		this.infostatus = infostatus;
		this.phone = phone;
		this.mail = mail;
		this.age = age;
		this.gender = gender;
	}

	public buyerClass()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	public String getVid()
	{
		return vid;
	}

	public void setVid(String vid)
	{
		this.vid = vid;
	}

	public String getImid()
	{
		return imid;
	}

	public void setImid(String imid)
	{
		this.imid = imid;
	}

	public String getRfalg()
	{
		return rfalg;
	}

	public void setRfalg(String rfalg)
	{
		this.rfalg = rfalg;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getBname()
	{
		return bname;
	}

	public void setBname(String bname)
	{
		this.bname = bname;
	}

	public String getPstatus()
	{
		return pstatus;
	}

	public void setPstatus(String pstatus)
	{
		this.pstatus = pstatus;
	}

	public String getSid()
	{
		return sid;
	}

	public void setSid(String sid)
	{
		this.sid = sid;
	}

	public String getPname()
	{
		return pname;
	}

	public void setPname(String pname)
	{
		this.pname = pname;
	}

	public String getCount()
	{
		return count;
	}

	public void setCount(String count)
	{
		this.count = count;
	}

	public String getInfostatus()
	{
		return infostatus;
	}

	public void setInfostatus(String infostatus)
	{
		this.infostatus = infostatus;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

}
