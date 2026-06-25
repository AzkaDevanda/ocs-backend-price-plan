//package com.sts.sinorita.services;
package com.ocs.portal.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BasicRechargeBC {

	private static final Logger logger = LoggerFactory.getLogger(BasicRechargeBC.class);

	@Autowired
	private MessageSource messageSource;

 	public String prosess(String id)
 	{
 		 return messageSource.getMessage(id,null,Locale.getDefault());
 	}






}
