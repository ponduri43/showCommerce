package com.samskrut.showcommerce.events;

import com.samskrut.showcommerce.database.buyerClass;

public class RertyCountPojo
{

	int count;
	buyerClass bc;

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public RertyCountPojo(int count)
	{

		this.count = count;
	}

	public RertyCountPojo(buyerClass _bc)
	{

		this.bc = _bc;
	}

	public buyerClass getStr()
	{
		return bc;
	}

	public void setStr(buyerClass _bc)
	{
		this.bc = _bc;
	}

}
