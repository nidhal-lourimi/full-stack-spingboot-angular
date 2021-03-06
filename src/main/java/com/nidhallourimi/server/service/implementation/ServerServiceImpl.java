package com.nidhallourimi.server.service.implementation;

import com.nidhallourimi.server.enumeration.Status;
import com.nidhallourimi.server.model.Server;
import com.nidhallourimi.server.repository.ServerRepo;
import com.nidhallourimi.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import static java.lang.Boolean.*;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    @Autowired
    private final ServerRepo serverRepo;



    @Override
    public Server create(Server server) {

        log.info("Saving new server : {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);

    }

    @Override
    public Server ping(String ipAddress) throws IOException {
    log.info("pinging server IP : {}",ipAddress);
    Server server=serverRepo.findByIpAddress(ipAddress);
        InetAddress address=InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? Status.SERVER_UP : Status.SERVER_DOWN);
        serverRepo.save(server);
       return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {

        if(serverRepo.findById(id).isPresent()){
            log.info("fetching server by id: {}",id);
            return serverRepo.findById(id).get();
            //not found exception is a better approach
        }

        else
        {    log.info("server with id : {} does not exist",id);
            return null;
        }
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server : {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting server by ID : {}", id);
        serverRepo.deleteById(id);
        return TRUE;

    }

    private String setServerImageUrl(){
        String[] imageName = {"server1.jpg", "server2.png","server3.png"};
       return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/"+imageName[new Random().nextInt(3)]).toUriString();

    }
}
