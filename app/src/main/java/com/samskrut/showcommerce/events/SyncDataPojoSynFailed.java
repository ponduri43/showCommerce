package com.samskrut.showcommerce.events;

import com.samskrut.showcommerce.database.buyerClass;

public class SyncDataPojoSynFailed
{

	buyerClass bc;

	public SyncDataPojoSynFailed(buyerClass _bc)
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
