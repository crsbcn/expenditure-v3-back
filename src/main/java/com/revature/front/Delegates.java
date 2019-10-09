package com.revature.front;

public enum Delegates {
	LOGIN,
	MAIN,
	MANAGER_REIMBURSEMENT,
	IMAGE,
	FILE_NOT_FOUND;
	
	public static Delegates getDelegate(String uri) {
		for(Delegates delegate : Delegates.values()) {
			if(uri.substring(1).toUpperCase()
					.startsWith(delegate.name()))
				return delegate;
		}
		return FILE_NOT_FOUND;
	}	
}