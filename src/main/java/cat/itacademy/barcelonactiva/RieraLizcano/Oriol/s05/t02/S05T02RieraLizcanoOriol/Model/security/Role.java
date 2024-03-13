package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security;

import lombok.Getter;

@Getter
public enum Role {
    USER("Usuario"),
    ADMIN("Administrador");

    private final String name;
    Role(String name){
        this.name = name;
    }
}
