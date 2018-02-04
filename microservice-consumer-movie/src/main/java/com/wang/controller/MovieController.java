package com.wang.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.wang.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */
@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.userPath}")
    private String userPath;

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/eureka-instance-movie")
    public String serviceUrl() {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("microservice-consumer-movie", false);
        return instance.getHomePageUrl();
    }

    @GetMapping("/eureka-instance-movie-info")
    public List<ServiceInstance> getServiceInstance(){
//        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("microservice-consumer-movie");
        return serviceInstanceList;
    }



    @GetMapping("/movie/{id}")
    public User findById(@PathVariable("id") Long id){
        return restTemplate.getForObject(this.userPath + id, User.class);
    }
}
