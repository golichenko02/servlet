package org.hillel.controller.tl;

import org.hillel.controller.converter.VehicleMapper;
import org.hillel.controller.dto.SearchParams;
import org.hillel.controller.dto.VehicleDto;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;
import org.hillel.service.TicketClient;
import org.hillel.service.query_info.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vehicles")
public class VehicleTLController {

    private final TicketClient ticketClient;

    private final VehicleMapper vehicleMapper;

    public VehicleTLController(TicketClient ticketClient, VehicleMapper vehicleMapper) {
        this.ticketClient = ticketClient;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping("")
    public ModelAndView homeVehiclesPage(Model model) {
        final SearchParams searchParams = new SearchParams(0, 100, "id", "false","BY_ONLY_ACTIVE", null);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("vehicles", Collections.emptyList());
        return find(model, searchParams); // под капотом    --->     return new ModelAndView("vehicles_view", model.asMap());
    }

    @PostMapping("/find")
    public ModelAndView find(Model model, @ModelAttribute("searchParams") SearchParams searchParams) {
        final List<VehicleEntity> vehicles = (List<VehicleEntity>) ticketClient.findAllVehiclesWithSpecification(
                new PaginationInfo(searchParams.getPageNumber(),
                        searchParams.getPageSize(), searchParams.getOrderField(),
                        Boolean.parseBoolean(searchParams.getIsAsc()),
                        VehicleSpecification.valueOf(searchParams.getSpecificationFilter()),
                        searchParams.getValue()));
        model.addAttribute("vehicles", vehicles.stream()
                .map(vehicleMapper::vehicleToVehicleDto)
                .collect(Collectors.toList()));
        return new ModelAndView("vehicle/vehicles_view", model.asMap());
    }

    @GetMapping("/delete/{vehicleId}")
    public String deleteVehicle(@PathVariable("vehicleId") long vehicleId) {
        ticketClient.removeVehicleById(vehicleId);
        return "redirect:/tl/vehicles";
//        return new RedirectView("/tl/vehicles");
    }

    @GetMapping("/{id}")
    public String vehicleInfo(@PathVariable("id") long id, Model model) {
        model.addAttribute("vehicle", vehicleMapper.vehicleToVehicleDto(ticketClient.findVehicleById(id).get()));
        return "/vehicle/vehicle_view";
    }


  /*   /vehicle/delete?id=1&name=test
    @GetMapping("/vehicle/delete")
    public String deleteVehicle(@RequestParam("id") long vehicleId,
                                @RequestParam(value = "name", required = false) String vehicleName) {

    }*/

    @PostMapping("/save")
    public RedirectView save(@ModelAttribute("vehSave") VehicleDto vehicleDto) {
        ticketClient.createOrUpdateVehicle(vehicleMapper.vehicleDtoToVehicle(vehicleDto));
        return new RedirectView("/tl/vehicles");
    }

}
