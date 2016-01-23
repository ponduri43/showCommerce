package com.samskrut.showcommerce.events;

import com.samskrut.showcommerce.database.buyerClass;

public class SyncDataPojoSynced
{

	public SyncDataPojoSynced(buyerClass _bc)
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
