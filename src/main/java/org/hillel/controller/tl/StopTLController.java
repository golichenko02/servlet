package org.hillel.controller.tl;

import org.hillel.controller.converter.JourneyMapper;
import org.hillel.controller.converter.StopMapper;
import org.hillel.controller.dto.SearchParams;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.specification.StopSpecification;
import org.hillel.service.TicketClient;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stops")
public class StopTLController {

    private final TicketClient ticketClient;

    private final StopMapper stopMapper;

    public StopTLController(TicketClient ticketClient, StopMapper stopMapper) {
        this.ticketClient = ticketClient;
        this.stopMapper = stopMapper;
    }

    @GetMapping("")
    public ModelAndView homeStopPage(Model model) {
        final SearchParams searchParams = new SearchParams(0, 100, "id", "false","BY_ONLY_ACTIVE", null);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("journeys", Collections.emptyList());
        return find(model, searchParams);
    }

    @PostMapping("/find")
    public ModelAndView find(Model model, @ModelAttribute("searchParams") SearchParams searchParams) {
        final List<StopEntity> journeys = (List<StopEntity>) ticketClient.findAllStopsWithSpecification(
                new PaginationInfo(searchParams.getPageNumber(),
                        searchParams.getPageSize(),
                        searchParams.getOrderField(),
                        Boolean.parseBoolean(searchParams.getIsAsc()),
                        StopSpecification.valueOf(searchParams.getSpecificationFilter()),
                        searchParams.getValue()));
        model.addAttribute("stops", journeys.stream()
                .map(stopMapper::fullStopToDto)
                .collect(Collectors.toList()));
        return new ModelAndView("stop/stops_view", model.asMap());
    }

    @GetMapping("{id}")
    public ModelAndView view(Model model, @PathVariable("id") long stopId) {
        model.addAttribute("stop", stopMapper.fullStopToDto(ticketClient.findStopById(stopId, true).get()));
        return new ModelAndView("stop/stop_view", model.asMap());
    }
}
