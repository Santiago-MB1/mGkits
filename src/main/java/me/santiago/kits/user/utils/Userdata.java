package me.santiago.kits.user.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Userdata {

    private UUID uuid;
    private String name;

    private Map<String, Long> normalKitDelays;
    private Map<String, Long> specialKitDelays;
    private Map<String, Long> vipKitDelays;

    public Userdata(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;

        this.normalKitDelays = new HashMap<>();
        this.specialKitDelays  = new HashMap<>();
        this.vipKitDelays = new HashMap<>();
    }

}