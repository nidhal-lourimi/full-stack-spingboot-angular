package com.nidhallourimi.server.resource;

import com.nidhallourimi.server.model.Response;
import com.nidhallourimi.server.model.Server;
import com.nidhallourimi.server.service.ServerService;
import com.nidhallourimi.server.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static com.nidhallourimi.server.enumeration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping ( "/server" )
@RequiredArgsConstructor
public class ServerResource {
    private final ServerServiceImpl serverService;

    @GetMapping ( "/list" )
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("servers", serverService.list(30)))
                .message("servers retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping ( "/ping/{ipAddress}" )
    public ResponseEntity<Response> pingServer(@PathVariable ( "ipAddress" ) String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("servers", server))
                .message(server.getStatus() == SERVER_UP ? "ping success" : "ping failed")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping ( "/save" )
    public ResponseEntity<Response> saveServer (@RequestBody @Valid Server server)  {

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("servers", serverService.create(server)))
                .message("server created")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @GetMapping ( "/get/{id}" )
    public ResponseEntity<Response> saveServer (@PathVariable("id") Long id)  {

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("servers", serverService.get(id)))
                .message("server retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
    @GetMapping ( "/get/{id}" )
    public ResponseEntity<Response> deleteServer (@PathVariable("id") Long id)  {

        return ResponseEntity.ok(Response.builder()
                .timeStamp(now())
                .data(Map.of("deleted", serverService.delete(id)))
                .message("server deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
    @GetMapping (path= "/image/{fileName}",produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage (@PathVariable("fileName") String fileName) throws IOException {

       return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"imageserver"+fileName));

    }
}


