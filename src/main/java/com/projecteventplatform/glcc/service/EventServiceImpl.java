package com.projecteventplatform.glcc.service;

import com.projecteventplatform.glcc.entities.Event;
import com.projecteventplatform.glcc.entities.User;
import com.projecteventplatform.glcc.repositories.EventRepository;
import com.projecteventplatform.glcc.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository ;

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    existingEvent.setTitle(event.getTitle());
                    existingEvent.setDescription(event.getDescription());
                    existingEvent.setDate(event.getDate());
                    existingEvent.setLocation(event.getLocation());
                    existingEvent.setCapacity(event.getCapacity());
                    existingEvent.setType(event.getType());
                    existingEvent.setPrice(event.getPrice());
                    return eventRepository.save(existingEvent);
                })
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }

    @Override
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with id " + id);
        }
        eventRepository.deleteById(id);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getEventsByOrganizer(User organizer) {
        return eventRepository.findByOrganizer(organizer);
    }

    @Override
    public void addParticipantToEvent(Long eventId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findUserByEmail(email) ;
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getParticipants().size() >= event.getCapacity()) {
            throw new RuntimeException("Event is at full capacity");
        }
        if (event.getParticipants().contains(user)) {
            throw new RuntimeException("You are already a participant in this event");
        }
        event.getParticipants().add(user);
        user.getEvents().add(event);
        event.setCapacity(event.getCapacity() - 1 );
        eventRepository.save(event);
    }
}