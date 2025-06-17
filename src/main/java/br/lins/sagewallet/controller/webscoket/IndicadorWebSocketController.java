package br.lins.sagewallet.controller.webscoket;

import br.lins.sagewallet.model.indicadores.Indicadores;
import br.lins.sagewallet.service.IndicadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class IndicadorWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private IndicadoresService indicadoresService;


    @MessageMapping("/carregar-indicadores")
    @SendTo("/topics/indicadores")
    public void carregarIndicadores(Integer id) {
        Indicadores indicadores = indicadoresService.calcularIndicadores(id);
        messagingTemplate.convertAndSend("/topics/indicadores", indicadores);
    }
}
