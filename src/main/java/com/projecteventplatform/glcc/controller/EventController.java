package com.projecteventplatform.glcc.controller;

import com.projecteventplatform.glcc.entities.Event;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.enums.EventType;
import com.projecteventplatform.glcc.service.EventService;
import com.projecteventplatform.glcc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService ;

    @GetMapping
    public String listEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "event-list";
    }

    @GetMapping("/new")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("eventTypes", EventType.values());
        return "event-form";
    }

    @PostMapping
    public String createEvent(@ModelAttribute Event event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User organizer = userService.getUserByEmail(currentEmail) ;
        event.setOrganizer(organizer);
        eventService.createEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/{id}/edit")
    public String showEditEventForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("eventTypes", EventType.values());
        return "event-update";
    }

    @PostMapping("/update/{id}")
    public String eventUpdate(@PathVariable Long id, @ModelAttribute Event updatedEvent) {
        Event existingEvent = eventService.updateEvent(id,updatedEvent);
        return "redirect:/events/my-events"; //
    }

    @PostMapping("/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute Event event) {
        eventService.updateEvent(id, event);
        return "redirect:/events";
    }

    @GetMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events/my-events";
    }

    @GetMapping("/my-events")
    public String getMyEvents(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        User currentUser = userService.getUserByEmail(currentEmail);

        List<Event> events = eventService.getEventsByOrganizer(currentUser);
        model.addAttribute("events", events);

        return "my-events" ;
    }

    @GetMapping("/events/{id}")
    public String getEventDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return "error/404";
        }
        model.addAttribute("event", event);
        return "event-details";
    }

    @GetMapping("/{eventId}/participants")
    public String showParticipants(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        model.addAttribute("event", event);
        model.addAttribute("participants", event.getParticipants());
        return "event-participants";
    }
}
