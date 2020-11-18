package com.soprabanking.ips.helper;
/** 
* This is a helper class which contains the object of the tokenId.
* @author kavsharma
 */

import java.util.UUID;

public class TokenId {
	
	private UUID id;

	/**
	 * To get the token object
	 * @return a newly created {@link UUID}object
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * To set the the token object
	 * @param id:{@link UUID}object
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	
}
