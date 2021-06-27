package org.hillel.controller.tl;

import org.hillel.controller.converter.JourneyMapper;
import org.hillel.controller.dto.SearchParams;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.jpa.repository.specification.JourneySpecification;
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
@RequestMapping("/journeys")
public class JourneyTLController {

    private final TicketClient ticketClient;

    private final JourneyMapper journeyMapper;

    public JourneyTLController(TicketClient ticketClient, JourneyMapper journeyMapper) {
        this.ticketClient = ticketClient;
        this.journeyMapper = journeyMapper;
    }

    @GetMapping("")
    public ModelAndView homeJourneyPage(Model model) {
        final SearchParams searchParams = new SearchParams(0, 100, "id", "false","BY_ONLY_ACTIVE", null);

        model.addAttribute("searchParams", searchParams);
        model.addAttribute("journeys", Collections.emptyList());
        return find(model, searchParams);
    }

    @PostMapping("/find")
    public ModelAndView find(Model model, @ModelAttribute("searchParams") SearchParams searchParams) {
        final List<JourneyEntity> journeys = (List<JourneyEntity>) ticketClient.findAllJourneysWithSpecification(
                new PaginationInfo(searchParams.getPageNumber(),
                        searchParams.getPageSize(),
                        searchParams.getOrderField(),
                        Boolean.parseBoolean(searchParams.getIsAsc()),
                        JourneySpecification.valueOf(searchParams.getSpecificationFilter()),
                        searchParams.getValue()));
        model.addAttribute("journeys", journeys.stream()
                .map(journeyMapper::journeyToDto)
                .collect(Collectors.toList()));
        return new ModelAndView("journey/journeys_view", model.asMap());
    }

    @GetMapping("/{id}")
    public String journeyInfo(@PathVariable("id") long id, Model model) {
        model.addAttribute("journey", journeyMapper.fullJourneyToDto(ticketClient.findJourneyById(id, true).get()));
        return "/journey/journey_view";
    }
}
