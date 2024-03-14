package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.JugadorNotFoundException;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.ListaJugadoresVaciaException;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.NameAlreadyExistsException;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JuegoDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.JugadorRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.JugadorConverter;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    JugadorRepository jugadorRepository;
    @Autowired
    JuegoService juegoService;
    @Autowired
    JugadorConverter converter;

    private boolean nombreDisponible (String nombre){
        boolean disponible;
        if (nombre.equals("ANÓNIMO")){
            disponible = true;
        } else {
            disponible = jugadorRepository.findByNombre(nombre).isEmpty();
        }
        return disponible;
    }
    public Jugador findJugador (Integer id){
        return jugadorRepository.findById(id).orElse(null);
    }

    public void createJugador (Jugador jugador, User user){
        if (nombreDisponible(jugador.getNombre())){
            jugador.setUsername(user.getUsername());
            jugadorRepository.save(jugador);
        } else {
            throw new NameAlreadyExistsException(Constante.nombreEnUso);
        }
    }
    public void updateJugador (Jugador jugador){
        Optional<Jugador> optionJugador = jugadorRepository.findById(jugador.getId_jugador());
        if (optionJugador.isPresent()){
            if (nombreDisponible(jugador.getNombre())){
                Jugador jugadorUpdated = optionJugador.get();
                jugadorUpdated.setNombre(jugador.getNombre());
                jugadorRepository.save(jugadorUpdated);
            } else {
                throw new NameAlreadyExistsException(Constante.nombreEnUso);
            }
        } else {
            throw new JugadorNotFoundException(Constante.jugadorNotFound);
        }
    }
    public void createTirada (Integer id){
        Optional<Jugador> optionJugador = jugadorRepository.findById(id);
        if (optionJugador.isPresent()){
            Jugador jugador = optionJugador.get();
            jugador.getTiradas().add(juegoService.createJuego(jugador));
            jugadorRepository.save(jugador);
        } else {
            throw new JugadorNotFoundException(Constante.jugadorNotFound);
        }
    }
    public void deleteTiradasJugador (Integer id){
        Optional<Jugador> optionJugador = jugadorRepository.findById(id);
        if (optionJugador.isPresent()){
            Jugador jugador = optionJugador.get();
            jugador.getTiradas().clear();
            juegoService.deleteJuegos(id);
            jugadorRepository.save(jugador);
        } else {
            throw new JugadorNotFoundException(Constante.jugadorNotFound);
        }
    }
    public List<JuegoDTO> getTiradasJugador (Integer id){
        List<JuegoDTO> listaTiradas;
        Optional<Jugador> optionJugador = jugadorRepository.findById(id);
        if (optionJugador.isPresent()){
            JugadorDTO jugadorDTO = converter.convertToDTO(optionJugador.get());
            if (jugadorDTO != null) {
                listaTiradas = jugadorDTO.getTiradas();
            } else {
                listaTiradas = new ArrayList<>();
            }
            return listaTiradas;
        } else {
            throw new JugadorNotFoundException(Constante.jugadorNotFound);
        }
    }
    public List<JugadorDTO> getAllJugadores (){
        List<Jugador> jugadores = jugadorRepository.findAll();
        if (jugadores.isEmpty()){
            throw new ListaJugadoresVaciaException(Constante.listaJugadoresVacia);
        } else {
            return jugadores.stream().map(jugador -> converter.convertToDTO(jugador)).toList();
        }
    }
    public List<JugadorDTO> getRankingJugadores (){
        List<JugadorDTO> jugadores = getAllJugadores();
        return jugadores.stream().sorted(Comparator.comparingDouble(JugadorDTO::getPorcentajeVictorias).reversed()).toList();
    }
    public JugadorDTO getBestJugador (){
        return getRankingJugadores().getFirst();
    }
    public JugadorDTO getWorstJugador(){
        return getRankingJugadores().getLast();
    }

    public void deleteJugador (Integer id){
        Optional<Jugador> optionJugador = jugadorRepository.findById(id);
        if (optionJugador.isPresent()){
            juegoService.deleteJuegos(id);
            jugadorRepository.deleteById(id);
        } else {
            throw new JugadorNotFoundException(Constante.jugadorNotFound);
        }
    }

}
