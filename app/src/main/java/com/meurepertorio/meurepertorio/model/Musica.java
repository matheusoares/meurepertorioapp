package com.meurepertorio.meurepertorio.model;

/**
 * Created by matheus_soares on 22/06/18.
 */
import android.net.Uri;
import java.io.Serializable;

public class Musica implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long id;
    public String nome;
    public String banda;
    public String letra;

    @Override
    public String toString() {
        return "Musica{"
                + "id='" + id + '\''
                + ", nome='" + nome + '\''
                + ", banda='" + banda + '\''
                + ", letra='" + letra + '\''
                + '}';
    }
}