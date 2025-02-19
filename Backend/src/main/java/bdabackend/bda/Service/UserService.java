package bdabackend.bda.Service;

import bdabackend.bda.Entity.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    VoluntarioService voluntarioService;

    @Autowired
    CoordinadorService coordinadorService;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return getUserByEmail(correo);
    }

    public User getUserByEmail(String correo) {
        if (voluntarioService.buscarPorCorreo(correo) != null) {
            return User.voluntarioToUser(voluntarioService.buscarPorCorreo(correo));
        }
        if (coordinadorService.buscarCoordinadorPorCorreo(correo) != null) {
            return User.coordinadorToUser(coordinadorService.buscarCoordinadorPorCorreo(correo));
        }
        return null;
    }

    public String getRoleByEmail(String correo) {
        if (voluntarioService.buscarPorCorreo(correo) != null) {
            return "VOLUNTARIO";
        }
        if (coordinadorService.buscarCoordinadorPorCorreo(correo) != null) {
            return "COORDINADOR";
        }
        return null;
    }

}
