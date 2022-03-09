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

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;


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
        return null;
    }

    @Override
    public Server update(Server server) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private String setServerImageUrl(){
        return null;
    }
}
