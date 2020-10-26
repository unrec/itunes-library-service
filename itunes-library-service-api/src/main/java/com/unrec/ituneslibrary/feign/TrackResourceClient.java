package com.unrec.ituneslibrary.feign;

import com.unrec.ituneslibrary.resource.TrackResource;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = FeignAutoConfig.SERVICE_URL,
        name = FeignAutoConfig.SERVICE_FEIGN_NAME,
        contextId = "deliveryPartnerResourceClient",
        configuration = FeignAutoConfig.class)
public interface TrackResourceClient extends TrackResource {
}