package com.ituneslibrary.feign;

import com.ituneslibrary.resource.TrackResource;
import org.springframework.cloud.openfeign.FeignClient;
import static com.ituneslibrary.feign.FeignAutoConfig.SERVICE_FEIGN_NAME;
import static com.ituneslibrary.feign.FeignAutoConfig.SERVICE_URL;

@FeignClient(url = SERVICE_URL,
        name = SERVICE_FEIGN_NAME,
        contextId = "deliveryPartnerResourceClient",
        configuration = FeignAutoConfig.class)
public interface TrackResourceClient extends TrackResource {
}