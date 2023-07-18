package com.danech.base;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.danech.dto.ApiGatewayResponse;
import com.danech.dto.Response;
import com.danech.exception.BusinessException;

/**
 * 
 * @author Kuntal Danech
 *
 */
public abstract class APIAbstractService implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	protected static Logger log = Logger.getRootLogger();

	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		try {
			log.info("request query param: " + input.getQueryStringParameters());
			log.info("request path param: " + input.getPathParameters());
			log.info("request body: " + input.getBody());
			String token = getAccessToken(input.getHeaders());
			// You may throw 401 and 403 from here as per our business operation.
			Map<String, Object> body = handle(input, context, token);
			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(new Response("", true, body)).build();
		} catch (BusinessException ex) {
			log.error(ex.getMessage());
			return ApiGatewayResponse.builder().setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.setObjectBody(new Response(ex.getMessage(), false, null)).build();
		}
	}

	protected String getAccessToken(Map<String, String> headers) throws BusinessException {
		String authStr = headers.get("Authorization");
		String[] parts = authStr.split(" ");
		String scheme = parts[0];
		String accessToken = parts[1];
		if (!scheme.equals("Bearer")) {
			throw new BusinessException(
					"Request header is missing bearer token. Format is Authorization: Bearer [accessToken]");
		}
		return accessToken;
	}

	protected abstract Map<String, Object> handle(APIGatewayProxyRequestEvent input, Context context, String token)
			throws BusinessException;

}
