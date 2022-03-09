package com.nidhallourimi.server.model;


import com.nidhallourimi.server.enumeration.Status;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    @Column ( nullable = false )
    private  Long id;
    @Column(unique = true)
    @NotEmpty(message = "IP Address cannot be empty or null")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    @Enumerated
    private Status status;

}
