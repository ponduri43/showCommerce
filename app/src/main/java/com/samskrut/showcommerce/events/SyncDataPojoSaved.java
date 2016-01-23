package com.samskrut.showcommerce.events;

import com.samskrut.showcommerce.database.buyerClass;

public class SyncDataPojoSaved
{

	public SyncDataPojoSaved(buyerClass _bc)
	{

		this.bc = _bc;
	}

	buyerClass bc;

	public buyerClass getStr()
	{
		return bc;
	}

	public void setStr(buyerClass _bc)
	{
		this.bc = _bc;
	}

}
