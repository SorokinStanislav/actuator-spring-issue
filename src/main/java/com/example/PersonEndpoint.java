package com.example;

import io.micrometer.core.annotation.Timed;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

@Endpoint
public class PersonEndpoint {
    public static final String NAMESPACE_URI = "http://example.com/";

    private final ObjectFactory objectFactory = new ObjectFactory();
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "personRequest")
    @ResponsePayload
    @Timed
    public JAXBElement<PersonResponse> personRequest(@RequestPayload PersonRequest request) throws ServiceFaultException {
        try {
            int num = Integer.valueOf(request.getId()) % 3 + 1;
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < num; i++) {
                str.append("I");
            }
            PersonResponse response = new PersonResponse();
            response.setName("Peter " + str);
            return objectFactory.createPersonResponse(response);
        }
        catch (Exception e) {
            throw new ServiceFaultException(e.getMessage(), new ServiceException());
        }
    }
}
