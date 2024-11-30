package com.projecteventplatform.glcc.restController;

import com.projecteventplatform.glcc.entities.Event;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
public class EventControllerRest {

    private final EventService eventService;


    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return ResponseEntity.ok(updatedEvent);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }


    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }


    @GetMapping("/my-events")
    public ResponseEntity<List<Event>> getEventsByOrganizer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User organizer = new User(); // Replace with a service to fetch the user by email if needed
        organizer.setEmail(email);

        List<Event> events = eventService.getEventsByOrganizer(organizer);
        return ResponseEntity.ok(events);
    }


    @PostMapping("/{eventId}/participants")
    public ResponseEntity<String> addParticipantToEvent(@PathVariable Long eventId) {
        eventService.addParticipantToEvent(eventId);
        return ResponseEntity.ok("Participant added successfully");
    }
}
