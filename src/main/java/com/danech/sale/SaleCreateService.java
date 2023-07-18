package com.danech.sale;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.danech.base.APIAbstractService;
import com.danech.exception.BusinessException;

/**
 * 
 * @author Kuntal Danech
 *
 */
public class SaleCreateService extends APIAbstractService {

	@Override
	protected Map<String, Object> handle(APIGatewayProxyRequestEvent input, Context context, String token)
			throws BusinessException {
		String body = input.getBody(); // This is your body
		// Parse the Body into Specific DTO
		createSalesData(body);
		Map<String,Object> map = new HashMap<>();
		map.put("data", "Sales data created");
		return map;
	}
	
	private void createSalesData(String body) {
		// Open a new JDBC connection and insert data into DB.
		// Throw specific Exception if needed.
		log.info("Data has been saved");
	}

}
